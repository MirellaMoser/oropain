package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasure;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Stressor;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminServiceREST {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/csv")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> getDataAsCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append("LoginName;Datum;Massnahmen;Zeitpunkt 1;Schmerz;Stress;Symptome;Stressoren;Zeitpunkt 2;Schmerz;Stress;Symptome;Stressoren;Zeitpunkt 3;Schmerz;Stress;Symptome;Stressoren;\n");
        for (User user : userRepository.findAll()) {
            for (DailyRecord dr : user.getRecords()) {
                sb.append(user.getLoginName() + ';');
                sb.append(dr.getDateTime());
                sb.append(';');
                sb.append("\"");
                for (CounterMeasure cm : dr.getCounterMeasures()) {
                    sb.append(cm.getName() + ',');
                }
                if (!dr.getCounterMeasures().isEmpty()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("\";");

                if (findSituation(ETimeOfDay.MORNING, dr) == null)
                    sb.append(";;;;;");
                else
                    sb.append(printSituationDetails(findSituation(ETimeOfDay.MORNING, dr)));

                if (findSituation(ETimeOfDay.AFTERNOON, dr) == null)
                    sb.append(";;;;;");
                else
                    sb.append(printSituationDetails(findSituation(ETimeOfDay.AFTERNOON, dr)));

                if (findSituation(ETimeOfDay.EVENING, dr) == null)
                    sb.append(";;;;;");
                else
                    sb.append(printSituationDetails(findSituation(ETimeOfDay.EVENING, dr)));

                sb.append("\n");

            }

        }
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    private Situation findSituation(ETimeOfDay timeofDay, DailyRecord dr) {
        for (Situation s : dr.getSituations()) {
            if (s.getTimeOfDay() == timeofDay) {
                return s;
            }
        }
        return null;
    }

    private String printSituationDetails(Situation situation) {
        StringBuilder sb = new StringBuilder();
        sb.append(situation.getTimeOfDay() == ETimeOfDay.MORNING ? "Morgen"
                : situation.getTimeOfDay() == ETimeOfDay.AFTERNOON ? "Nachmittag"
                        : situation.getTimeOfDay() == ETimeOfDay.EVENING ? "Abend" : "");
        sb.append(";");
        sb.append(situation.getPainLevel() + ";");
        sb.append(situation.getStressLevel() + ";");

        sb.append("\"");
        for (Symptom s : situation.getSymptoms()) {
            sb.append(s.getName() + ',');
        }
        if (!situation.getSymptoms().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("\";");
        sb.append("\"");
        for (Stressor s : situation.getStressors()) {
            sb.append(s.getName() + ',');
        }
        if (!situation.getStressors().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("\";");
        return sb.toString();
    }

}
