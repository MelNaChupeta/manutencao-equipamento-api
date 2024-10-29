package br.com.tads.manutencao_equipamento_api.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencao_equipamento_api.entities.dto.ClienteDTO;
import br.com.tads.manutencao_equipamento_api.entities.entity.Cliente;
import br.com.tads.manutencao_equipamento_api.entities.entity.Role;
import br.com.tads.manutencao_equipamento_api.repositories.ClienteRepository;
import jakarta.mail.MessagingException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;
    
    public Cliente save(ClienteDTO clienteDTO) throws MessagingException{
        Cliente cliente = new Cliente(clienteDTO);
        cliente.setRole(Role.CLIENT);
        String password = generateRandomPassword();
        cliente.setPassword(password);
        cliente = clienteRepository.save(cliente);
        emailService.sendHtmlEmail("math.christo@gmail.com" , "Novo cadastro de usuário" , GenerateEmailHtmlService.generatePasswordEmailTemplate(cliente.getUsername(), password));
        return cliente;
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int randomNum = 1000 + random.nextInt(9000);
        return String.valueOf(randomNum);
    }
}
