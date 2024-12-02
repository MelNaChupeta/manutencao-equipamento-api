package br.com.tads.manutencaoequipamentoapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tads.manutencaoequipamentoapi.models.entity.Categoria;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria , Long>{

    List<Categoria> findByStatus(boolean b);

}
