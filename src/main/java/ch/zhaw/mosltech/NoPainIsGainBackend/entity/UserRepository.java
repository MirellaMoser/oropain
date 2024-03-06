package ch.zhaw.mosltech.NoPainIsGainBackend.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{
    
}
