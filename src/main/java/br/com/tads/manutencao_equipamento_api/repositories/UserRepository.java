package br.com.tads.manutencao_equipamento_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencao_equipamento_api.entities.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmailAndStatus(String email, boolean i);

    Optional<User> findByEmail(String email);
    
}
