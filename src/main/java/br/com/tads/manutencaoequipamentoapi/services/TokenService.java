package br.com.tads.manutencaoequipamentoapi.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tads.manutencaoequipamentoapi.entities.dto.user.LoginDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.user.UserDTO;
import br.com.tads.manutencaoequipamentoapi.entities.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.persistence.EntityManager;

@Service
public class TokenService {
	@Value("${manutencao.equipamento.jwt.expiration}")
	private String expiration;

	@Value("${manutencao.equipamento.jwt.secret}")
	private String secret;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager em;

	public boolean isTokenValid(String token) {
		if (token == null) {
			return false;
		}
		try {
			Jwts.parser()
					.setSigningKey(Base64.getEncoder()
							.encodeToString("MANUTENCAO_EQUIPAMENTO".getBytes(StandardCharsets.UTF_8)))
					.parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Token mal formado");
		} catch (UnsupportedJwtException e) {
			System.out.println("Token nao suportado");
		} catch (ExpiredJwtException e) {
			System.out.println("Token expirado");
		} catch (IllegalArgumentException e) {
			System.out.println("Token nulo");
		}

		return false;

	}

	public Long getIdUser(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(
						Base64.getEncoder().encodeToString("MANUTENCAO_EQUIPAMENTO".getBytes(StandardCharsets.UTF_8)))
				.parseClaimsJws(token).getBody();
		ObjectMapper mapper = new ObjectMapper();
		UserDTO user = null;
		try {
			user = mapper.readValue(claims.getSubject(), UserDTO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return user.id();
	}

	@Transactional
	public String generateToken(LoginDTO loginDTO, boolean status) throws JsonProcessingException {

		Date today = new Date();
		Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));
		String token = null;

		User user = userService.findByEmail(loginDTO.email());

		String senha = userService.findById(user.getId()).getPassword();
		em.refresh(user);
		user.setSenha(senha);	

		if (user.isStatus()) {
			UserDTO subject = null;
			subject = new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getRole().getDescricao());

			ObjectMapper mapper = new ObjectMapper();
			token = Jwts.builder()
					.setIssuer("mutencaoequipamentoapi")
					.setSubject(mapper.writeValueAsString(subject))
					.claim("id", subject.id())
					.claim("email", subject.email())
					.claim("nome", subject.nome())
					.claim("role", subject.role())
					.setIssuedAt(today)
					.setExpiration(dateExpiration)
					.signWith(SignatureAlgorithm.HS256,	Base64.getEncoder().encodeToString("MANUTENCAO_EQUIPAMENTO".getBytes(StandardCharsets.UTF_8)))
					.compact();

			// agenteService.registraUltimoAcesso(agente.getCodAgente());
		} else {
			return "BLOQUEADO";
		}
		// }
		return token;
	}
}
