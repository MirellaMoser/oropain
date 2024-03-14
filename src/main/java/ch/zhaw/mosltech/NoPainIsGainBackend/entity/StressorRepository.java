package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StressorRepository extends JpaRepository<Stressor, String>{
        List<Stressor> findByIsDefault(boolean isDefault);

}
