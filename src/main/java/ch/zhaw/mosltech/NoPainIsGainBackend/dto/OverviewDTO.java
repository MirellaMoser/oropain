package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.Date;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Stressor;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import lombok.Value;

/**
 * Data Transfer Object (DTO) for providing an overview of a daily record.
 * <p>
 * This DTO encapsulates a summary of the daily health record, including details
 * about symptoms, stress levels,
 * stressors, and other relevant data for a specific date and time of day. It is
 * constructed directly from a
 * {@link DailyRecord} entity, aggregating necessary details into a simplified
 * form suitable for API responses.
 * </p>
 */
@Value
public class OverviewDTO {
    private Integer intensity;
    private String symptoms;
    private String stressLevel;
    private String stressors;
    private Date dateOfEntry;
    private ETimeOfDay timeOfEntry;

    /**
     * Constructs an {@code OverviewDTO} from a {@link DailyRecord}.
     * <p>
     * This constructor maps the complex structure of a {@code DailyRecord} into a
     * more
     * simplified overview. It aggregates symptoms and stressors into
     * comma-separated strings,
     * interprets stress levels into meaningful descriptions, and captures the date
     * and time of the record.
     * </p>
     * 
     * @param record The daily record to be summarized.
     */
    public OverviewDTO(DailyRecord record) {
        // Handle the case where no situations are recorded for the day
        if (record.getSituations().size() == 0) {
            intensity = 0;
            symptoms = "";
            stressLevel = "kein";
            stressors = "";
            dateOfEntry = new Date();
            timeOfEntry = ETimeOfDay.MORNING;
            return;
        }

        // Extract and set the pain intensity from the latest recorded situation
        this.intensity = record.getLatestSituation().getPainLevel();

        // Aggregate symptom names into a single string
        String symptomsAggregated = "";
        for (Symptom s : record.getLatestSituation().getSymptoms()) {
            symptomsAggregated += s.getName() + ", ";
        }
        // Remove trailing comma and space from the aggregated string
        if (!record.getLatestSituation().getSymptoms().isEmpty()) {
            symptomsAggregated = symptomsAggregated.substring(0, symptomsAggregated.length() - 2);
        }
        this.symptoms = symptomsAggregated;

        // Map numerical stress levels to descriptive strings
        switch (record.getLatestSituation().getStressLevel()) {
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

        // Aggregate stressor names into a single string
        String stressorsAggregated = "";
        for (Stressor s : record.getLatestSituation().getStressors()) {
            stressorsAggregated += s.getName() + ", ";
        }
        // Remove trailing comma and space from the aggregated string
        if (!record.getLatestSituation().getStressors().isEmpty()) {
            stressorsAggregated = stressorsAggregated.substring(0, stressorsAggregated.length() - 2);
        }
        this.stressors = stressorsAggregated;

        // Set the date of entry and time of entry based on the latest situation
        this.dateOfEntry = record.getDateTime();
        this.timeOfEntry = record.getLatestSituation().getTimeOfDay();
    }
}