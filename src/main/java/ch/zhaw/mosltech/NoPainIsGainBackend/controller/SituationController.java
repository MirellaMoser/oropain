package ch.zhaw.mosltech.NoPainIsGainBackend.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.InputDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.StressorSelectionDTO;
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
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;

@Service
public class SituationController {

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

    @Autowired
    private RecordController recordController;

   

    public InputDTO populateBlankInputDTO(String loginName) throws EntityNotFoundException {

        List<ElementSelectionDTO> defaultSymptoms = getDefaultSymptomDTOs();
        List<StressorSelectionDTO> defaultStressors = getDefaultStressorDTOs();
        List<ETimeOfDay> availableEntries = new ArrayList<>();
        availableEntries.add(ETimeOfDay.MORNING);
        availableEntries.add(ETimeOfDay.AFTERNOON);
        availableEntries.add(ETimeOfDay.EVENING);

        DailyRecord dailyRecord = recordController.getCurrentDailyRecord(loginName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (sdf.format(dailyRecord.getDateTime()).equals(sdf.format(new Date()))) {
            for (Situation situation : dailyRecord.getSituations()) {
                availableEntries.remove(situation.getTimeOfDay());
            }
        }
        InputDTO blankInput = new InputDTO(0, defaultSymptoms, 0, defaultStressors, new Date(), ETimeOfDay.MORNING, availableEntries);
        return blankInput;
    }

    public List<ElementSelectionDTO> getCurrentSituationSymptomsSelection(String loginName) throws EntityNotFoundException {
        DailyRecord dailyRecord = recordController.getCurrentDailyRecord(loginName);
        List<Symptom> symptoms = symptomRepository.findByIsDefault(true);

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

    public void addSituation(String loginName, InputDTO newSituation) throws EntityNotFoundException {

        Situation situation = new Situation();
        situation.setTimeOfDay(newSituation.getTimeOfDay());
        situation.setPainLevel(newSituation.getIntensity());
        situation.setStressLevel(newSituation.getStressLevel());
        for (StressorSelectionDTO element : newSituation.getStressors()) {
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

        for (ElementSelectionDTO element : newSituation.getSymptoms()) {
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

        DailyRecord dailyRecord = recordController.getCurrentDailyRecord(loginName);
        Optional<User> userOptional = userRepository.findById(loginName);

        if(!userOptional.isPresent()) {
            throw new EntityNotFoundException();
        }
        
        User user = userOptional.get();

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


    private List<ElementSelectionDTO> getDefaultSymptomDTOs() {

        List<ElementSelectionDTO> defaultSelection = new ArrayList<>();

        List<Symptom> defaultSymptoms = symptomRepository.findByIsDefault(true);
        for (Symptom symptom : defaultSymptoms) {
            defaultSelection.add(new ElementSelectionDTO(symptom.getName(), false));
        }

        return defaultSelection;
    }

    private List<StressorSelectionDTO> getDefaultStressorDTOs() {

        List<StressorSelectionDTO> defaultSelection = new ArrayList<>();

        List<Stressor> defaultStressors = stressorRepository.findByIsDefault(true);
        for (Stressor stressor : defaultStressors) {
            defaultSelection.add(new StressorSelectionDTO(stressor.getName(), stressor.getCategory(), false));
        }

        return defaultSelection;
    }

}
