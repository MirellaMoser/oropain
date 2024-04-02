package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {
    @Query("SELECT r FROM User u JOIN u.records r where u=?1 ORDER BY r.dateTime DESC LIMIT 1")
    DailyRecord findMostRecentSituation(User user);

    @Query("SELECT r FROM User u JOIN u.records r where u=?1 ORDER BY r.dateTime ASC")
    List<DailyRecord> findAllSiutationsOrdered(User user);
}
