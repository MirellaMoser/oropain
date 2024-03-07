package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterMeasureRepository extends JpaRepository<CounterMeasure, String> {
    public List<CounterMeasure> findAllByIsDefault(boolean isDefault);
}
