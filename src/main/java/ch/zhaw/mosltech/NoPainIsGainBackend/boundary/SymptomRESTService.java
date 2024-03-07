package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SymptomRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

@RestController
public class SymptomRESTService {

    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/symptoms")
    public List<Symptom> getAllSymptoms(Principal principal) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();
        List<Symptom> resList = symptomRepository.findByIsDefault(true);
        resList.addAll(symptomRepository.findOwnButNotDefault(user));
        return resList;
    }
}
