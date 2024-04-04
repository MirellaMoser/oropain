package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Stressor} entities.
 * <p>
 * Facilitates CRUD operations and simplifies data access for stressor entities.
 * This repository interface
 * extends {@link JpaRepository}, automatically providing common database
 * operations without the need
 * for implementing them. Additional queries specific to stressors can be
 * defined for more customized data retrieval.
 * </p>
 */
public interface StressorRepository extends JpaRepository<Stressor, String> {

        /**
         * Finds all stressors based on their default status.
         * <p>
         * This method allows querying stressors that are either marked as defaults
         * provided by the system or
         * added by users, enabling targeted data retrieval based on this criteria.
         * </p>
         * 
         * @param isDefault A boolean value indicating the default status of stressors
         *                  to be retrieved.
         *                  True for retrieving default stressors, false for non-default
         *                  ones.
         * @return A list of {@link Stressor} entities matching the specified default
         *         status.
         */
        List<Stressor> findByIsDefault(boolean isDefault);
}
