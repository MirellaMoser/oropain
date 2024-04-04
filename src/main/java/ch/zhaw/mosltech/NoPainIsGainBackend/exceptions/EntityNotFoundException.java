package ch.zhaw.mosltech.NoPainIsGainBackend.exceptions;

/**
 * Custom exception class representing the scenario where an entity is not found within the database.
 * <p>
 * This exception can be thrown by services or repositories in situations where an expected database entity
 * (such as a user, symptom, stressor, or daily record) cannot be located. Utilizing this custom exception
 * allows for more granular error handling and provides clarity on the specific nature of the error encountered
 * during database operations.
 * </p>
 */
public class EntityNotFoundException extends Exception {

    /**
     * Constructs a new EntityNotFoundException with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Constructs a new EntityNotFoundException with the specified detail message.
     *
     * @param message The detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityNotFoundException with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is not automatically incorporated into
     * this exception's detail message.
     * </p>
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EntityNotFoundException with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     *
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
