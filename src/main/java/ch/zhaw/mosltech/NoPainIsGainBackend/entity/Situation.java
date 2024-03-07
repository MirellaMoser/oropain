package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Situation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime = new Date();

    private Integer painLevel;
    private Integer stressLevel;

    @ManyToMany
    private List<Symptom> symptoms = new ArrayList<>();

    @ManyToMany
    private List<Stressor> stressors = new ArrayList<>();

    @ManyToMany
    private List<CounterMeasure> counterMeasures = new ArrayList<>();

}
