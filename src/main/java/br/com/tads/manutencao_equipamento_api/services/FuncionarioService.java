package br.com.tads.manutencao_equipamento_api.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencao_equipamento_api.entities.dto.FuncionarioDTO;
import br.com.tads.manutencao_equipamento_api.entities.entity.Funcionario;
import br.com.tads.manutencao_equipamento_api.entities.entity.Role;
import br.com.tads.manutencao_equipamento_api.exceptions.UserNotFoundException;
import br.com.tads.manutencao_equipamento_api.repositories.FuncionarioRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;


    @Transactional(rollbackOn = Exception.class)
    public Funcionario save(FuncionarioDTO funcionarioDTO) throws MessagingException{
        Funcionario funcionario = new Funcionario(funcionarioDTO);
        funcionario.setRole(Role.FUNCIOARIO);
        funcionario = repository.save(funcionario);
        return funcionario;
    }
    
    @Transactional(rollbackOn = Exception.class)
    public Funcionario update(FuncionarioDTO funcionarioDTO , Long id) throws MessagingException{
        Funcionario funcionario = new Funcionario(funcionarioDTO);
        Funcionario funcionarioExists = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Funcionário não encontrado"));
        funcionario.setRole(Role.FUNCIOARIO);
        funcionario.setId(id);
        funcionario.setDtHrCriacao(funcionarioExists.getDtHrCriacao()); 
        funcionario = repository.save(funcionario);
        return funcionario;
    }

    @Transactional(rollbackOn = Exception.class)
    public Funcionario delete(Long id) throws MessagingException{
        Funcionario funcionarioExists = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Funcionário não encontrado"));
        funcionarioExists.setStatus(false);
        funcionarioExists = repository.save(funcionarioExists);
        return funcionarioExists;
    }
    
    public Funcionario findById(Long id) throws MessagingException{
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Funcionário não encontrado"));
    }
    
    public Funcionario findAll() throws MessagingException{
        return repository.findByStatus(true);
    }

}
