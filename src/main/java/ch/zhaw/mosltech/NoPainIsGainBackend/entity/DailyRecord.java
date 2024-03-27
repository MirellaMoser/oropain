package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class DailyRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateTime = new Date();

    @ManyToMany
    private List<Situation> situations = new ArrayList<>();

    @ManyToMany
    private List<CounterMeasure> counterMeasures = new ArrayList<>();


    public double getAveragePainLevel() {
        if(situations.size() == 0) return 0;
        double avg = 0.0;
        for (Situation situation: situations) {
            avg += situation.getPainLevel();
        }
        avg = avg / situations.size();
        return avg;
    }

    public double getAverageStressLevel() {
        if(situations.size() == 0) return 0;
        double avg = 0.0;
        for (Situation situation: situations) {
            avg += situation.getStressLevel();
        }
        avg = avg / situations.size();
        return avg;
    }

    public Situation getLatestSituation() {
        if(situations.size() == 0) return null;
        return situations.stream().sorted((s1,s2) -> s1.getTimeOfDay().compareTo(s2.getTimeOfDay())).collect(Collectors.toList()).get(0);
    }
}
