package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.InputDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.OverviewDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.StressorSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasure;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasureRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecordRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SituationRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Stressor;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.StressorRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SymptomRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

@RestController
@RequestMapping("/api/situation")
public class SituationServiceREST {

    @Autowired
    private CounterMeasureRepository counterMeasureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private SituationRepository situationRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private StressorRepository stressorRepository;

    @GetMapping("/current/countermeasures")
    public List<ElementSelectionDTO> getAllDefaultCounterMeasures(Principal principal) {
        DailyRecord dailyRecord = getCurrentDailyRecord(principal);
        List<CounterMeasure> defaultCounterMeasures = counterMeasureRepository.findAllByIsDefault(true);
        List<ElementSelectionDTO> selectedCounterMeasures = new ArrayList<>();
        for (CounterMeasure defaultCM : defaultCounterMeasures) {
            if (dailyRecord.getCounterMeasures().contains(defaultCM)) {
                selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), true));
            } else {
                selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), false));
            }
        }
        return selectedCounterMeasures;
    }

    @PostMapping("/current/countermeasures")
    public void updateCounterMeasures(Principal principal, @RequestBody List<ElementSelectionDTO> currentSelection) {
        DailyRecord dailyRecord = getCurrentDailyRecord(principal);
        dailyRecord.getCounterMeasures().clear();
        for (ElementSelectionDTO elementSelectionDTO : currentSelection) {
            if (elementSelectionDTO.isSelected()) {
                if (counterMeasureRepository.existsById(elementSelectionDTO.getName())) {
                    dailyRecord.getCounterMeasures()
                            .add(counterMeasureRepository.findById(elementSelectionDTO.getName()).get());
                } else {
                    CounterMeasure cm = new CounterMeasure();
                    cm.setName(elementSelectionDTO.getName());
                    cm.setDefault(false);
                    counterMeasureRepository.save(cm);
                    dailyRecord.getCounterMeasures().add(cm);
                }
            }
        }
        dailyRecordRepository.save(dailyRecord);
    }

    @GetMapping("/current/overview")
    public ResponseEntity<OverviewDTO> getOverview(Principal principal) {
        DailyRecord dailyRecord = getCurrentDailyRecord(principal);
        return new ResponseEntity<>(new OverviewDTO(dailyRecord), HttpStatus.OK);
    }

    @GetMapping("/current/symptoms")
    public List<ElementSelectionDTO> getAllSymptoms(Principal principal) {
        DailyRecord dailyRecord = getCurrentDailyRecord(principal);
        List<Symptom> symptoms = symptomRepository.findByIsDefault(true);
       // symptoms.addAll(symptomRepository.findOwnButNotDefault(user));

        List<ElementSelectionDTO> selectedSymptoms = new ArrayList<>();

        for (Symptom symptom : symptoms) {
            if (dailyRecord.getLatestSituation().getSymptoms().contains(symptom)) {
                selectedSymptoms.add(new ElementSelectionDTO(symptom.getName(), true));
            } else {
                selectedSymptoms.add(new ElementSelectionDTO(symptom.getName(), false));
            }
        }

        return selectedSymptoms;
    }

    @GetMapping("/new/empty")
    public InputDTO getNewEntrySituation(Principal principal) {

        List<ElementSelectionDTO> defaultSymptoms = getDefaultSymptoms();
        List<StressorSelectionDTO> defaultStressors = getDefaultStressors();

        return new InputDTO(0, defaultSymptoms, 0, defaultStressors, new Date(), ETimeOfDay.UNSET);
    }

    @PostMapping
    public void updateCounterMeasures(Principal principal, @RequestBody InputDTO input) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();

        Situation situation = new Situation();
        situation.setTimeOfDay(input.getTimeOfDay());
        situation.setPainLevel(input.getIntensity());
        situation.setStressLevel(input.getStressLevel());
        for (StressorSelectionDTO element : input.getStressors()) {
            if (!element.isSelected())
                continue;
            Optional<Stressor> so = stressorRepository.findById(element.getName());
            if (so.isPresent()) {
                situation.getStressors().add(so.get());
            } else {
                Stressor s = new Stressor();
                s.setName(element.getName());
                s.setCategory("Custom");
                stressorRepository.save(s);
                situation.getStressors().add(s);
            }
        }

        for (ElementSelectionDTO element : input.getSymptoms()) {
            if (!element.isSelected())
                continue;
            Optional<Symptom> so = symptomRepository.findById(element.getName());
            if (so.isPresent()) {
                situation.getSymptoms().add(so.get());
            } else {
                Symptom s = new Symptom();
                s.setName(element.getName());
                symptomRepository.save(s);
                situation.getSymptoms().add(s);
            }
        }

        situationRepository.save(situation);

        DailyRecord dailyRecord = getCurrentDailyRecord(principal);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (!sdf.format(dailyRecord.getDateTime()).equals(sdf.format(new Date()))) {
            DailyRecord newRecord = new DailyRecord();
            newRecord.setDateTime(new Date());
            newRecord.getSituations().add(situation);
            dailyRecordRepository.save(newRecord);
            user.getRecords().add(newRecord);
            userRepository.save(user);

        } else {
            dailyRecord.getSituations().add(situation);
            dailyRecordRepository.save(dailyRecord);
        }
    }

    private DailyRecord getCurrentDailyRecord(Principal principal) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();
        DailyRecord situation = dailyRecordRepository.findMostRecentSituation(user);

        if (situation == null) {
            situation = new DailyRecord();
            user.getRecords().add(situation);
            dailyRecordRepository.save(situation);
            userRepository.save(user);
        }

        return situation;
    }

    private List<ElementSelectionDTO> getDefaultSymptoms() {

        List<ElementSelectionDTO> defaultSelection = new ArrayList<>();

        List<Symptom> defaultSymptoms = symptomRepository.findByIsDefault(true);
        for (Symptom symptom : defaultSymptoms) {
            defaultSelection.add(new ElementSelectionDTO(symptom.getName(), false));
        }

        return defaultSelection;
    }

    private List<StressorSelectionDTO> getDefaultStressors() {

        List<StressorSelectionDTO> defaultSelection = new ArrayList<>();

        List<Stressor> defaultStressors = stressorRepository.findByIsDefault(true);
        for (Stressor stressor : defaultStressors) {
            defaultSelection.add(new StressorSelectionDTO(stressor.getName(), stressor.getCategory(), false));
        }

        return defaultSelection;
    }
}
