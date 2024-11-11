package br.com.tads.manutencaoequipamentoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria , Long>{

    Categoria findByStatus(boolean b);

}
