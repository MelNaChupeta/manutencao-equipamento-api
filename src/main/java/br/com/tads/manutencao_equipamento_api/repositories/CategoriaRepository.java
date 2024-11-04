package br.com.tads.manutencao_equipamento_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencao_equipamento_api.entities.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria , Long>{

    Categoria findByStatus(boolean b);

}
