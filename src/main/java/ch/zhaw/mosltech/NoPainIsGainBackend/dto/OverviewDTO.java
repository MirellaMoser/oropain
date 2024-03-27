package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.Date;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Stressor;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import lombok.Value;

@Value
public class OverviewDTO {
    private Double intensity;
    private String symptoms;
    private String stressLevel;
    private String stressors;
    private Date dateOfEntry;

    private ETimeOfDay timeOfEntry;


    public OverviewDTO(DailyRecord record) {
        if(record.getSituations().size() == 0) {
            intensity = 0.0;
            symptoms = "";
            stressLevel ="kein";
            stressors = "";
            dateOfEntry = new Date();
            timeOfEntry = ETimeOfDay.UNSET;
            return;
        }
        this.intensity = record.getAveragePainLevel();
        
        String symptomsAggregated = "";
        for (Symptom s : record.getLatestSituation().getSymptoms()) {
            symptomsAggregated += s.getName() + ", ";
        }
        if (!record.getLatestSituation().getSymptoms().isEmpty()) {
            symptomsAggregated = symptomsAggregated.substring(0,symptomsAggregated.length()-2);
        }
        this.symptoms = symptomsAggregated;

        switch(record.getLatestSituation().getStressLevel()) {
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
        for (Stressor s : record.getLatestSituation().getStressors()) {
            stressorsAggregated += s.getName() + ", ";
        }
        if (!record.getLatestSituation().getStressors().isEmpty()) {
            stressorsAggregated = stressorsAggregated.substring(0,stressorsAggregated.length()-2);
        }
        this.stressors = stressorsAggregated;
        this.dateOfEntry = record.getDateTime();

        this.timeOfEntry = record.getLatestSituation().getTimeOfDay();
    }
}
