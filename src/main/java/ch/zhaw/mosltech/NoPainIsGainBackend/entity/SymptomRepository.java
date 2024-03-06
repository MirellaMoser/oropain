package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SymptomRepository extends JpaRepository<Symptom, String>{    
    List<Symptom> findByIsDefault(boolean isDefault);

    @Query("SELECT DISTINCT(sym) FROM User u LEFT  JOIN u.situations s LEFT  JOIN s.symptoms sym WHERE sym.isDefault = false AND u = ?1")
    List<Symptom> findOwnButNotDefault(User user);
}
