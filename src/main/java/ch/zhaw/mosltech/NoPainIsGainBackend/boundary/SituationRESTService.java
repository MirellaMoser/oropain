package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

@RestController
public class SituationRESTService {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/situations")
    public List<Situation> getAllSituations(Principal principal) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();

        return user.getSituations();
    }
}
