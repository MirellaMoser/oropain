package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Symptom} entities.
 * <p>
 * Provides CRUD operations and enables custom queries for fetching symptom entities from the database.
 * This interface allows for easy data access and manipulation of symptoms, supporting both predefined 
 * system symptoms and custom symptoms added by users or administrators.
 * </p>
 */
public interface SymptomRepository extends JpaRepository<Symptom, String> {

    /**
     * Finds all symptoms based on their default status.
     * <p>
     * This method allows querying for symptoms that are either marked as default by the system
     * or added by users. It supports filtering symptoms for various application scenarios, such as
     * populating default selections in user interfaces or analyzing common symptoms.
     * </p>
     * 
     * @param isDefault A boolean value indicating whether to retrieve default symptoms (true) or custom symptoms (false).
     * @return A list of {@link Symptom} entities that match the specified default status.
     */
    List<Symptom> findByIsDefault(boolean isDefault);
}
