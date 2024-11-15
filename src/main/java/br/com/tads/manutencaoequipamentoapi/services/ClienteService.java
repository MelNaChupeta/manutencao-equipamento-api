package br.com.tads.manutencaoequipamentoapi.services;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tads.manutencaoequipamentoapi.entities.dto.cliente.ClienteDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Cliente;
import br.com.tads.manutencaoequipamentoapi.entities.entity.Role;
import br.com.tads.manutencaoequipamentoapi.entities.entity.User;
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
    public ClienteDTO save(ClienteDTO clienteDTO) throws Exception{
        Cliente cliente = new Cliente(clienteDTO);
        validaDadosCliente(cliente);
        cliente.setRole(Role.CLIENT);
        String senha = generateRandomPassword();
        cliente.setSenha(senha);
        cliente = clienteRepository.save(cliente);
        emailService.sendHtmlEmail("math.christo@gmail.com" , "Novo cadastro de usuário" , GenerateEmailHtmlService.generatePasswordEmailTemplate(cliente.getUsername(), senha));
        return new ClienteDTO(cliente);
    }

    public void validaDadosCliente(Cliente cliente) throws ValidationException {

        Optional<User> email = userRepository.findByEmail(cliente.getEmail());
        if(email.isPresent()) {
            throw new ValidationException("o Email já consta na base de dados");
        }
        Optional<Cliente> cpf = clienteRepository.findByCpf(cliente.getEmail());
        if(cpf.isPresent()){
            throw new ValidationException("o Cpf já consta na base de dados");
        }
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int randomNum = 1000 + random.nextInt(9000);
        return String.valueOf(randomNum);
    }
}
