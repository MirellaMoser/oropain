package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing a stressor that can affect an individual's health status.
 * <p>
 * A stressor is any physical or psychological factor that causes stress or anxiety in individuals. This class
 * captures not only the name and category of each stressor but also whether it is a default stressor provided by
 * the system or one added by a user.
 * </p>
 */
@Entity // Marks this class as a JPA entity.
@Data   // Lombok annotation for generating boilerplate code (getters, setters, hashCode, equals, toString).
public class Stressor {

    /**
     * The name of the stressor, acting as its unique identifier.
     * <p>
     * This field serves as the primary key in the database and is used to uniquely identify the stressor.
     * </p>
     */
    @Id // Marks this field as the primary key.
    private String name;

    /**
     * The category of the stressor, helping to classify it within broader groups.
     * <p>
     * Categories can be used to group stressors by their nature (e.g., environmental, psychological, physical),
     * which can aid in analysis and reporting.
     * </p>
     */
    private String category;

    /**
     * Indicates whether the stressor is a default option within the system.
     * <p>
     * A boolean value where true signifies that the stressor is provided as a default by the system, and false
     * indicates it is a custom addition by a user or administrator. This distinction allows for predefined
     * stressors to be differentiated from those added based on individual experiences.
     * </p>
     */
    private boolean isDefault;
}