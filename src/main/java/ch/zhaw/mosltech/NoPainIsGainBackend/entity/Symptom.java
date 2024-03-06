package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Symptom {
    @Id
    private String name;
    private boolean isDefault;
}
