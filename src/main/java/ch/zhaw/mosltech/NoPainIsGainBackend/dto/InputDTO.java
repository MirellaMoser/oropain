package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.Date;
import java.util.List;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import lombok.Value;

/**
 * Data Transfer Object (DTO) for capturing user input related to health tracking, particularly for recording
 * situations including symptoms, stress levels, and countermeasures.
 * <p>
 * This class is used to gather data from the user about their health status at a specific time, including
 * the intensity of any pain or discomfort, stress level, symptoms experienced, stressors identified, and the
 * time of day these were recorded.
 * </p>
 */
@Value
public class InputDTO {

    /**
     * The intensity level of the pain or discomfort, typically on a predefined scale (e.g., 0-10).
     */
    private int intensity;

    /**
     * A list of symptoms selected by the user, with each symptom's name and selection state (selected or not).
     */
    private List<ElementSelectionDTO> symptoms;

    /**
     * The level of stress, quantified similarly to intensity, indicating the user's stress level at the time of entry.
     */
    private int stressLevel;

    /**
     * A list of stressors affecting the user, each with a name, category, and selection state.
     */
    private List<StressorSelectionDTO> stressors;

    /**
     * The date on which the entry is made, serving as a record of when the symptoms and stress levels were reported.
     */
    private Date dateOfEntry;

    /**
     * The time of day the entry corresponds to, allowing users to track how their symptoms and stress levels vary throughout the day.
     */
    private ETimeOfDay timeOfDay;

    /**
     * A list of time slots available for entry, indicating when during the day the user has not yet made an entry.
     */
    private List<ETimeOfDay> availableEntries;
}
