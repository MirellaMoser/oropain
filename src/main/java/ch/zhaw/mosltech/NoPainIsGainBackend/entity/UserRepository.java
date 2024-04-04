package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link User} entities.
 * <p>
 * Facilitates CRUD (Create, Read, Update, Delete) operations and simplifies the process of interacting
 * with the database to manage {@code User} entities. By extending {@link JpaRepository}, this interface
 * inherits a suite of methods for working with user data, enabling straightforward data access and manipulation
 * without the need for boilerplate code.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, String> {
    // Additional query methods specific to user data can be defined here as needed.
}