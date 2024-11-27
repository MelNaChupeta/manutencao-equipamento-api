package br.com.tads.manutencaoequipamentoapi.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.entities.dto.categoria.CategoriaDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.categoria.CategoriaFormDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public Categoria save(CategoriaFormDTO categoriaDTO){
        Categoria categoria = new Categoria(categoriaDTO);
        categoria = repository.save(categoria);
        return categoria;
    }

    @Transactional(rollbackOn = Exception.class)
    public Categoria update(CategoriaFormDTO categoriaDTO , Long id) {
        Categoria categoriaExists = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        categoriaExists.setNome(categoriaDTO.nome());
        categoriaExists = repository.save(categoriaExists);
        return categoriaExists;
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

    public List<CategoriaDTO> findAll() {
        return repository.findByStatus(true).stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

}
