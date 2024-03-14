package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.OverviewDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasure;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasureRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SituationRepository;
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
    private SituationRepository situationRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @GetMapping("/current/countermeasures")
    public List<ElementSelectionDTO> getAllDefaultCounterMeasures(Principal principal) {
        Situation situation = getCurrentSituation(principal);
        List<CounterMeasure> defaultCounterMeasures = counterMeasureRepository.findAllByIsDefault(true);
        List<ElementSelectionDTO> selectedCounterMeasures = new ArrayList<>();
        for (CounterMeasure defaultCM : defaultCounterMeasures) {
            if (situation.getCounterMeasures().contains(defaultCM)) {
                selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), true));
            } else {
                selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), false));
            }
        }
        return selectedCounterMeasures;
    }

    @PostMapping("/current/countermeasures")
    public void updateCounterMeasures(Principal principal, @RequestBody List<ElementSelectionDTO> currentSelection) {
        Situation situation = getCurrentSituation(principal);
        situation.getCounterMeasures().clear();
        for (ElementSelectionDTO elementSelectionDTO : currentSelection) {
            if (elementSelectionDTO.isSelected()) {
                if (counterMeasureRepository.existsById(elementSelectionDTO.getName())) {
                    situation.getCounterMeasures()
                            .add(counterMeasureRepository.findById(elementSelectionDTO.getName()).get());
                } else {
                    CounterMeasure cm = new CounterMeasure();
                    cm.setName(elementSelectionDTO.getName());
                    cm.setDefault(false);
                    counterMeasureRepository.save(cm);
                    situation.getCounterMeasures().add(cm);
                }
            }
        }
        situationRepository.save(situation);
    }

    @GetMapping("/current/overview")
    public ResponseEntity<OverviewDTO> getOverview(Principal principal) {
        Situation situation = getCurrentSituation(principal);
        return new ResponseEntity<>(new OverviewDTO(situation), HttpStatus.OK);
    }

    @GetMapping("/current/symptoms")
    public List<ElementSelectionDTO> getAllSymptoms(Principal principal) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();
        Situation situation = getCurrentSituation(principal);

        List<Symptom> symptoms = symptomRepository.findByIsDefault(true);
        symptoms.addAll(symptomRepository.findOwnButNotDefault(user));

        List<ElementSelectionDTO> selectedSymptoms = new ArrayList<>();

        for (Symptom symptom : symptoms) {
            if (situation.getSymptoms().contains(symptom)) {
                selectedSymptoms.add(new ElementSelectionDTO(symptom.getName(), true));
            } else {
                selectedSymptoms.add(new ElementSelectionDTO(symptom.getName(), false));
            }
        }

        return selectedSymptoms;
    }

    private Situation getCurrentSituation(Principal principal) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();
        Situation situation = situationRepository.findMostRecentSituation(user);

        if (situation == null) {
            situation = new Situation();
            user.getSituations().add(situation);
            situationRepository.save(situation);
            userRepository.save(user);
        }

        return situation;
    }

}
