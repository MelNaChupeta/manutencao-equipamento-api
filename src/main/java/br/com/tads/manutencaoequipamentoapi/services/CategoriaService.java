package br.com.tads.manutencaoequipamentoapi.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.entities.dto.CategoriaDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Categoria;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import br.com.tads.manutencaoequipamentoapi.exceptions.UserNotFoundException;
import br.com.tads.manutencaoequipamentoapi.repositories.CategoriaRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;


    @Transactional(rollbackOn = Exception.class)
    public Categoria save(CategoriaDTO categoriaDTO) throws MessagingException{
        Categoria categoria = new Categoria(categoriaDTO);
        categoria.setRole(Role.CATEGORIA);
        categoria = repository.save(categoria);
        return categoria;
    }

    @Transactional(rollbackOn = Exception.class)
    public Categoria update(CategoriaDTO categoriaDTO , Long id) throws MessagingException{
        Categoria categoria = new Categoria(categoriaDTO);
        Categoria categoriaExists = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Categoria não encontrada"));
        categoria.setRole(Role.CATEGORIA);
        categoria.setId(id);
        categoria.setDtHrCriacao(categoriaExists.getDtHrCriacao());
        categoria = repository.save(categoria);
        return categoria;
    }

    @Transactional(rollbackOn = Exception.class)
    public Categoria delete(Long id) throws MessagingException{
        Categoria categoriaExists = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Categoria não encontrada"));
        categoriaExists.setStatus(false);
        categoriaExists = repository.save(categoriaExists);
        return categoriaExists;
    }

    public Categoria findById(Long id) throws MessagingException{
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Categoria não encontrada"));
    }

    public Categoria findAll() throws MessagingException{
        return repository.findByStatus(true);
    }

}
