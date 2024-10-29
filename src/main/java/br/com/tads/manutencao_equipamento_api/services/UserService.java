package br.com.tads.manutencao_equipamento_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tads.manutencao_equipamento_api.entities.entity.User;
import br.com.tads.manutencao_equipamento_api.exceptions.UserNotFoundException;
import br.com.tads.manutencao_equipamento_api.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById (Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Erro ao buscar user " + id));
    }
    
    public User findByEmail (String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Erro ao buscar user: " + email));
    }

   /* public int getUltimoAcesso(User user) {
		Optional<User> userOpt = userRepository.findByEmail(user.getEmail());

		if (userOpt.isPresent()) {
			return userRepository.verificaPrimeiroAcesso(userOpt.get().getId());
		} else {
			return 0;
		}
	} */

    public boolean validateUser(User user, boolean status) {

		Optional<User> userOpt = userRepository.findByEmailAndStatus(user.getEmail(), true);
		if (userOpt.isPresent()) {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            if (status) {
                if (user.getPassword().equals(userOpt.get().getPassword())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (bc.matches(user.getPassword(), userOpt.get().getPassword())) {
                    return true;
                } else {
                    return false;
                }
            }
		} else {
			return false;
		}
	}

   
}
