package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.Date;

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

    private String timeOfEntry;


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
            case 1:
            case 2:
            case 3:
            case 4:
                this.stressLevel = "tief";
                break;
            case 5:
            case 6:
            case 7:
                this.stressLevel = "mittel";
                break;
            case 8:
            case 9:
            case 10:
                this.stressLevel = "hoch";
                break;
            default:
                this.stressLevel = "k.A.";
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

        this.timeOfEntry = "TODO: morgens, mittags, abends";
    }
}
