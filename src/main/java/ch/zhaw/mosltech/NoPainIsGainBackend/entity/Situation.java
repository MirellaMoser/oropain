package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Situation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private ETimeOfDay timeOfDay;
    
    private Integer painLevel;
    private Integer stressLevel;

    @ManyToMany
    private List<Symptom> symptoms = new ArrayList<>();

    @ManyToMany
    private List<Stressor> stressors = new ArrayList<>();

}
