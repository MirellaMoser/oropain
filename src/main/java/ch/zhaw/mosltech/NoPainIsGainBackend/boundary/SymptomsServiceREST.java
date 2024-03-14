package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SymptomRepository;

@RestController
@RequestMapping("/api/symptoms")
public class SymptomsServiceREST {
    
    @Autowired
    private SymptomRepository symptomRepository;

    @GetMapping("/defaultSelection")
    public List<ElementSelectionDTO> getDefaultSymptoms() {

        List<ElementSelectionDTO> defaultSelection = new ArrayList<>();

        List<Symptom> defaultSymptoms = symptomRepository.findByIsDefault(true);
        for (Symptom symptom : defaultSymptoms) {
            defaultSelection.add(new ElementSelectionDTO(symptom.getName(), false));
        }

        return defaultSelection;
    }

}
