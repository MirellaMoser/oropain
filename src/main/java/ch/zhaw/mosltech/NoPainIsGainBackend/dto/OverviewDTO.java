package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.Date;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Stressor;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import lombok.Value;

@Value
public class OverviewDTO {
    private int intensity;
    private String symptoms;
    private String stressLevel;
    private String stressors;
    private Date dateOfEntry;

    private ETimeOfDay timeOfEntry;


    public OverviewDTO(Situation situation) {
        this.intensity = situation.getPainLevel();
        
        String symptomsAggregated = "";
        for (Symptom s : situation.getSymptoms()) {
            symptomsAggregated += s.getName() + ", ";
        }
        if (!situation.getSymptoms().isEmpty()) {
            symptomsAggregated = symptomsAggregated.substring(0,symptomsAggregated.length()-2);
        }
        this.symptoms = symptomsAggregated;

        switch(situation.getStressLevel()) {
            case 0:
                this.stressLevel = "kein";
                break;
            case 1:
                this.stressLevel = "tief";
                break;
            case 2:
                this.stressLevel = "mittel";
                break;
            case 3:
                 this.stressLevel = "hoch";
                break;
            default:
                this.stressLevel = "kein";
                break;
        }

        String stressorsAggregated = "";
        for (Stressor s : situation.getStressors()) {
            stressorsAggregated += s.getName() + ", ";
        }
        if (!situation.getStressors().isEmpty()) {
            stressorsAggregated = stressorsAggregated.substring(0,stressorsAggregated.length()-2);
        }
        this.stressors = stressorsAggregated;
        this.dateOfEntry = situation.getDateTime();

        this.timeOfEntry = situation.getTimeOfDay();
    }
}
