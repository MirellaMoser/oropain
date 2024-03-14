package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SituationRepository extends JpaRepository<Situation, Long> {

    @Query("SELECT s FROM User u JOIN u.situations s where u=?1 ORDER BY s.dateTime DESC LIMIT 1")
    Situation findMostRecentSituation(User user);
    
} 