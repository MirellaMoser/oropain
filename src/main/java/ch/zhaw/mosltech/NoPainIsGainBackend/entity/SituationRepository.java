package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Situation} entities.
 * <p>
 * Provides CRUD operations and repository management for {@code Situation} entities without the need for
 * boilerplate code. This interface can be extended to include custom queries and methods for more complex
 * data retrieval and manipulation tasks related to health situations.
 * </p>
 */
public interface SituationRepository extends JpaRepository<Situation, Long> {
    // Custom query methods can be added here
}