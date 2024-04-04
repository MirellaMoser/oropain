package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * Entity representing a daily health record.
 * <p>
 * Stores information about the health status of a user on a specific day, including recorded situations and
 * selected countermeasures. It also provides methods to calculate average pain and stress levels based on the
 * recorded situations, and to retrieve the latest situation recorded for the day.
 * </p>
 */
@Entity // Marks this class as a JPA entity.
@Data   // Lombok annotation to generate getters, setters, equals, hashCode, and toString methods automatically.
public class DailyRecord {

    /**
     * The unique identifier for the daily record.
     * <p>
     * It is generated automatically by the database. Marked with @JsonIgnore to prevent it from being serialized
     * into JSON responses, as it's primarily for internal use.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /**
     * The date of the record.
     * <p>
     * Stores the date when the record was created. It's initialized to the current date.
     * </p>
     */
    @Temporal(TemporalType.DATE)
    private Date dateTime = new Date();
    
    /**
     * List of situations recorded on this day.
     * <p>
     * Each situation represents a specific instance of health status recording, including pain level, stress level,
     * symptoms, and stressors.
     * </p>
     */
    @ManyToMany
    private List<Situation> situations = new ArrayList<>();

    /**
     * List of countermeasures selected for this day.
     * <p>
     * Countermeasures are strategies or actions taken to address symptoms or stressors recorded in situations.
     * </p>
     */
    @ManyToMany
    private List<CounterMeasure> counterMeasures = new ArrayList<>();

    /**
     * Calculates the average pain level across all situations for the day.
     * 
     * @return The average pain level as a double. Returns 0 if no situations are recorded.
     */
    public double getAveragePainLevel() {
        if (situations.isEmpty()) return 0;
        return situations.stream().mapToDouble(Situation::getPainLevel).average().orElse(0);
    }

    /**
     * Calculates the average stress level across all situations for the day.
     * 
     * @return The average stress level as a double. Returns 0 if no situations are recorded.
     */
    public double getAverageStressLevel() {
        if (situations.isEmpty()) return 0;
        return situations.stream().mapToDouble(Situation::getStressLevel).average().orElse(0);
    }

    /**
     * Retrieves the latest situation of the day based on the time of day.
     * <p>
     * This method is useful for fetching the most recent health status update. Marked with @JsonIgnore to
     * prevent serialization into JSON responses.
     * </p>
     * 
     * @return The latest {@link Situation} recorded for the day, or null if no situations are recorded.
     */
    @JsonIgnore
    public Situation getLatestSituation() {
        return situations.stream()
                         .sorted((s1, s2) -> s1.getTimeOfDay().compareTo(s2.getTimeOfDay()))
                         .findFirst().orElse(null);
    }
}
