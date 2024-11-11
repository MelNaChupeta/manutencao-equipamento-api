package br.com.tads.manutencaoequipamentoapi.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.entities.dto.ClienteDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import br.com.tads.manutencaoequipamentoapi.repositories.ClienteRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;
    
    @Transactional(rollbackOn = Exception.class)
    public Cliente save(ClienteDTO clienteDTO) throws MessagingException{
        Cliente cliente = new Cliente(clienteDTO);
        cliente.setRole(Role.CLIENT);
        String senha = generateRandomPassword();
        cliente.setSenha(senha);
        cliente = clienteRepository.save(cliente);
        emailService.sendHtmlEmail("math.christo@gmail.com" , "Novo cadastro de usuário" , GenerateEmailHtmlService.generatePasswordEmailTemplate(cliente.getUsername(), senha));
        return cliente;
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int randomNum = 1000 + random.nextInt(9000);
        return String.valueOf(randomNum);
    }
}
