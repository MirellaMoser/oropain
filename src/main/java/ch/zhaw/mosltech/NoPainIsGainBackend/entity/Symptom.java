package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing a health symptom.
 * <p>
 * This class models a symptom that can be experienced by an individual, capturing both the symptom's name
 * and a flag indicating whether it is a default symptom (predefined by the system) or a custom symptom
 * added by the user or administrator.
 * </p>
 */
@Entity // Marks this class as a JPA entity for persistence.
@Data   // Lombok annotation to automatically generate getters, setters, and other common methods.
public class Symptom {

    /**
     * The unique identifier for the symptom, typically its name.
     * <p>
     * Serves as the primary key in the database, allowing for unique identification of each symptom.
     * </p>
     */
    @Id // Designates this field as the primary key of the entity.
    private String name;

    /**
     * Indicates whether the symptom is a default system entry.
     * <p>
     * This boolean flag distinguishes between symptoms that are predefined in the system (true) and those
     * that have been added by users or administrators (false), facilitating easier management and categorization.
     * </p>
     */
    private boolean isDefault;
}