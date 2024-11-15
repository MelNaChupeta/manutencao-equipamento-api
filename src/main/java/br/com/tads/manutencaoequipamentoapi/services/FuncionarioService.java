package br.com.tads.manutencaoequipamentoapi.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.entities.dto.funcionario.FuncionarioFormDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Funcionario;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import br.com.tads.manutencaoequipamentoapi.repositories.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;

    public Funcionario save(FuncionarioFormDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario(funcionarioDTO);
        funcionario.setRole(Role.FUNCIONARIO);
        funcionario = repository.save(funcionario);
        return funcionario;
    }
    
    public Funcionario update(FuncionarioFormDTO funcionarioDTO , Long id) {
        Funcionario funcionario = new Funcionario(funcionarioDTO);
        Funcionario funcionarioExists = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        funcionario.setRole(Role.FUNCIONARIO);
        funcionario.setId(id);
        funcionario.setDtHrCriacao(funcionarioExists.getDtHrCriacao()); 
        funcionario = repository.save(funcionario);
        return funcionario;
    }

    public Boolean delete(Long id) {
        Funcionario funcionarioExists = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        funcionarioExists.setStatus(false);
        funcionarioExists = repository.save(funcionarioExists);
        return true;
    }
    
    public Funcionario findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
    }
    
    public Funcionario findAll() {
        return repository.findByStatus(true);
    }

}
