package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.GraphDataDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecordRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

@RestController
@RequestMapping("/api/overview")
public class OverviewServiceREST {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @GetMapping("/painPlot")
    private GraphDataDTO getPainData(Principal principal) {
        GraphDataDTO res = new GraphDataDTO();       
        List<DailyRecord> dailyRecords = getAllRecordsOfPrincipal(principal);

        for (DailyRecord dailyRecord : dailyRecords) {
            res.getLabels().add(dailyRecord.getDateTime().toString());
            res.getData().add(dailyRecord.getAveragePainLevel());
        }
        return res;
    }

    @GetMapping("/stressPlot")
    private GraphDataDTO getStressData(Principal principal) {
        GraphDataDTO res = new GraphDataDTO();        
        List<DailyRecord> dailyRecords = getAllRecordsOfPrincipal(principal);

        for (DailyRecord dailyRecord : dailyRecords) {
            res.getLabels().add(dailyRecord.getDateTime().toString());
            res.getData().add(dailyRecord.getAverageStressLevel());
        }

        return res;
    }

    @GetMapping("/dailyRecords")
    private List<DailyRecord> getDailyRecords(Principal principal) {
        return getAllRecordsOfPrincipal(principal);
    }

    private List<DailyRecord> getAllRecordsOfPrincipal(Principal principal) {
        String loginName = principal.getName();
        User user = userRepository.findById(loginName).get();
        return dailyRecordRepository.findAllSiutationsOrdered(user);
    }

}
