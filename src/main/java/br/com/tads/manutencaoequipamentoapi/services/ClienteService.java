package br.com.tads.manutencaoequipamentoapi.services;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.commom.Util;
import br.com.tads.manutencaoequipamentoapi.models.dto.cliente.ClienteDTO;
import br.com.tads.manutencaoequipamentoapi.models.dto.cliente.ClienteFormDTO;
import br.com.tads.manutencaoequipamentoapi.models.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.models.entity.Role;
import br.com.tads.manutencaoequipamentoapi.models.entity.User;
import br.com.tads.manutencaoequipamentoapi.exceptions.ValidationException;
import br.com.tads.manutencaoequipamentoapi.repositories.ClienteRepository;
import br.com.tads.manutencaoequipamentoapi.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    
    @Transactional(rollbackOn = Exception.class)
    public ClienteDTO save(ClienteFormDTO clienteDTO) throws Exception{
        Cliente cliente = new Cliente(clienteDTO);
        validaDadosCliente(cliente);
        cliente.setRole(Role.CLIENTE);
        String senha = generateRandomPassword();
        cliente.setSenha(senha);
        cliente = clienteRepository.save(cliente);
        emailService.sendHtmlEmail(cliente.getEmail(), "Novo cadastro de usuário" , GenerateEmailHtmlService.generatePasswordEmailTemplate(cliente.getUsername(), senha));
        return new ClienteDTO(cliente);
    }

    public void validaDadosCliente(Cliente cliente) throws ValidationException {
        if(cliente.getCep() != null)
            cliente.setCep(Util.onlyNumbers(cliente.getCep()));
        cliente.setCpf(Util.onlyNumbers(cliente.getCpf()));
       
        Optional<User> email = userRepository.findByEmailAndStatus(cliente.getEmail() , true);
        if(email.isPresent()) {
            throw new ValidationException("o email já consta na base de dados");
        }
        Optional<Cliente> cpf = clienteRepository.findByCpfAndStatus(cliente.getCpf() , true);
        if(cpf.isPresent()){
            throw new ValidationException("o cpf já consta na base de dados");
        }
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int randomNum = 1000 + random.nextInt(9000);
        return String.valueOf(randomNum);
    }
}
