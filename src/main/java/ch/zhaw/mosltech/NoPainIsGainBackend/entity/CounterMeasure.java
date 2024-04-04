package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing a countermeasure for health management or treatment plans.
 * <p>
 * This entity is used to store information about specific countermeasures that can be taken to mitigate symptoms
 * or stressors. Countermeasures are identified by their name, which serves as a unique identifier, and a flag indicating
 * whether they are considered a default option within the system.
 * </p>
 */
@Entity // Marks the class as a JPA entity.
@Data   // Lombok annotation to generate getters, setters, equals, hashCode, and toString methods.
public class CounterMeasure {

    /**
     * The unique identifier for the countermeasure, typically its name.
     * <p>
     * This serves as the primary key within the database and is used to uniquely identify each countermeasure.
     * </p>
     */
    @Id // Marks this field as the primary key of the entity.
    private String name;

    /**
     * Indicates whether the countermeasure is a default option provided by the system.
     * <p>
     * A boolean flag where true signifies that the countermeasure is one of the default options available within
     * the application, and false indicates it is a custom addition by a user or administrator.
     * </p>
     */
    private boolean isDefault;
}
