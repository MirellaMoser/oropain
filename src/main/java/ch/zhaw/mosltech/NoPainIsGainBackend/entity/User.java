package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a user in the system.
 * <p>
 * This class models a system user, encapsulating login credentials (such as a username and password hash)
 * and a collection of daily health records associated with the user. It supports operations to manage user
 * authentication and health data tracking.
 * </p>
 */
@Entity // Marks this class as a JPA entity.
@Data   // Lombok annotation for generating boilerplate code like getters and setters.
@Table(name = "UserTable") // Specifies the table name in the database.
public class User {

    /**
     * The login name of the user, serving as a unique identifier.
     * <p>
     * Acts as the primary key in the database table. It's used for user identification during login
     * and other authentication processes.
     * </p>
     */
    @Id // Designates this field as the primary key of the entity.
    private String loginName;

    /**
     * The hash of the user's password.
     * <p>
     * Stores a secure hash of the user's password to ensure privacy and security. The hash is used for
     * authentication purposes, verifying user credentials without storing actual passwords.
     * </p>
     */
    private String passwordHash;

    /**
     * A list of daily health records associated with the user.
     * <p>
     * Maintains a one-to-many relationship with the {@link DailyRecord} entity, representing all health
     * records logged by the user. The {@code CascadeType.ALL} indicates that persistence actions on the
     * {@code User} entity should cascade to the associated {@code DailyRecord} entities.
     * </p>
     */
    @OneToMany // Establishes a one-to-many relationship.
    @Cascade(CascadeType.ALL) // Configures cascading persistence actions.
    private List<DailyRecord> records = new ArrayList<>();
}
