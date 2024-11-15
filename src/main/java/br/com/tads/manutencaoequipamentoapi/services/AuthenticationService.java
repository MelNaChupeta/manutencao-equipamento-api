package br.com.tads.manutencaoequipamentoapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.tads.manutencaoequipamentoapi.commom.Response;
import br.com.tads.manutencaoequipamentoapi.entities.dto.user.LoginDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.User;

@Service
public class AuthenticationService {

	@Autowired
	private UserService userSerivice;

	@Autowired
	private TokenService tokenService;

	public ResponseEntity<Response> generateToken(LoginDTO user) throws JsonProcessingException {
		String token = tokenService.generateToken(user, false);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false, "Credenciais inválidas"));
		} else if (token.equals("BLOQUEADO")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(false, "Usuário bloqueado"));
		}
		return ResponseEntity.ok().body(new Response(true, token));
	}

	public ResponseEntity<Response> validateToken() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (userSerivice.validateUser(user, true)) {
			return ResponseEntity.ok().body(new Response(true));
		} else {
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "").body(new Response(false));
		}
	}

	public ResponseEntity<Object> getUser() {
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user != null) {
				return ResponseEntity.ok().body(user);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
	}

}