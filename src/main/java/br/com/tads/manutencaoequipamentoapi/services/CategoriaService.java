package br.com.tads.manutencaoequipamentoapi.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.entities.dto.categoria.CategoriaDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import br.com.tads.manutencaoequipamentoapi.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public Categoria save(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria(categoriaDTO);
        categoria.setRole(Role.CATEGORIA);
        categoria = repository.save(categoria);
        return categoria;
    }

    public Categoria update(CategoriaDTO categoriaDTO , Long id) {
        Categoria categoria = new Categoria(categoriaDTO);
        Categoria categoriaExists = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        categoria.setRole(Role.CATEGORIA);
        categoria.setId(id);
        categoria.setDtHrCriacao(categoriaExists.getDtHrCriacao());
        categoria = repository.save(categoria);
        return categoria;
    }

    public boolean delete(Long id){
        Categoria categoriaExists = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        categoriaExists.setStatus(false);
        categoriaExists = repository.save(categoriaExists);
        return true;
    }

    public Categoria findById(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    public List<Categoria> findAll() {
        return repository.findByStatus(true);
    }

}
