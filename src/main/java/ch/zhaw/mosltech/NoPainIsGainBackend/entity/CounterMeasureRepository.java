package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link CounterMeasure} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations and additional querying capabilities
 * for {@link CounterMeasure} entities. It leverages Spring Data JPA to reduce boilerplate code required to interact
 * with the database.
 * </p>
 */
public interface CounterMeasureRepository extends JpaRepository<CounterMeasure, String> {

    /**
     * Finds all {@link CounterMeasure} entities based on their default status.
     * <p>
     * This method allows for the retrieval of countermeasures that are either marked as default or not,
     * facilitating filtering and selection within the application. It extends the repository's querying
     * capabilities to support customized business logic.
     * </p>
     *
     * @param isDefault The default status of the countermeasures to retrieve. True for default countermeasures,
     *                  false for non-default.
     * @return A list of {@link CounterMeasure} entities that match the specified default status.
     */
    public List<CounterMeasure> findAllByIsDefault(boolean isDefault);
}
