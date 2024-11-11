package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmailAndStatus(String email, boolean i);

    Optional<User> findByEmail(String email);
    
}
