package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria , Long>{

    List<Categoria> findByStatus(boolean b);

}
