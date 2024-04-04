package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JPA repository for {@link DailyRecord} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries for {@link DailyRecord}
 * entities. It enables the retrieval of daily records associated with a specific user, either fetching the most
 * recent record or all records in a specified order.
 * </p>
 */
public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {
    
    /**
     * Finds the most recent daily record for a given user.
     * <p>
     * This custom query joins {@link User} entities with their associated {@link DailyRecord} entities, ordering
     * the results by the dateTime field in descending order to get the most recent record. Note: the actual query
     * might need adjustment to correctly limit the results to 1, depending on the JPA provider's capabilities.
     * </p>
     * 
     * @param user The user whose most recent daily record is being queried.
     * @return The most recent {@link DailyRecord} associated with the specified user.
     */
    @Query("SELECT r FROM User u JOIN u.records r where u=?1 ORDER BY r.dateTime DESC LIMIT 1")
    DailyRecord findMostRecentSituation(User user);

    /**
     * Retrieves all daily records for a given user, ordered by the dateTime field.
     * <p>
     * This query joins {@link User} entities with their {@link DailyRecord} entities and orders the result by
     * the dateTime field in ascending order, providing a chronological list of the user's health records.
     * </p>
     * 
     * @param user The user whose daily records are being retrieved.
     * @return A list of {@link DailyRecord} entities associated with the specified user, ordered chronologically.
     */
    @Query("SELECT r FROM User u JOIN u.records r where u=?1 ORDER BY r.dateTime ASC")
    List<DailyRecord> findAllSiutationsOrdered(User user);
}
