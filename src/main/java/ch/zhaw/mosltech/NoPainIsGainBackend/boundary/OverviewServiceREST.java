package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.GraphDataDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SituationRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

@RestController
@RequestMapping("/api/overview")
public class OverviewServiceREST {
    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SituationRepository situationRepository;

    @GetMapping("/painPlot")
    private GraphDataDTO getPainData(Principal principal) {
        GraphDataDTO res = new GraphDataDTO();
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();

        List<Situation> situations = situationRepository.findAllSiutationsOrdered(user);
        
        for (Situation situation : situations) {
            res.getLabels().add(situation.getDateTime().toString());
            res.getData().add(situation.getPainLevel());
        }

        return res;
    }

    @GetMapping("/stressPlot")
    private GraphDataDTO getStressData(Principal principal) {
        GraphDataDTO res = new GraphDataDTO();
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();

        List<Situation> situations = situationRepository.findAllSiutationsOrdered(user);
        
        for (Situation situation : situations) {
            res.getLabels().add(situation.getDateTime().toString());
            res.getData().add(situation.getStressLevel());
        }

        return res;
    }
}
