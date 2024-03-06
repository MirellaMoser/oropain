package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserTable")
public class User {
    
    @Id
    private String loginName;
    private String passwordHash;


    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Situation> situations = new ArrayList<>();
    

}
