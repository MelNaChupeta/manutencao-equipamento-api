package br.com.tads.manutencao_equipamento_api.exceptions;

import java.sql.SQLException;

import org.hibernate.TransientPropertyValueException;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tads.manutencao_equipamento_api.commom.Response;
import jakarta.persistence.NoResultException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(RuntimeException ex, WebRequest request) {
		if (ex.getMessage().contains("CPF")) {
			return new ResponseEntity<>(new Response(false, "CPF já existe na base de dados!"), HttpStatus.BAD_REQUEST);
		} else if (ex.getMessage().contains("EMAIL")) {
			return new ResponseEntity<>(new Response(false, "EMAIL já existe na base de dados!"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new Response(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(IdentifierGenerationException.class)
	public ResponseEntity<?> identifierGenerationException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new Response(false, "Sequência de ID não configurado, contate o administrador do sistema: "
						+ ex.getMessage().split(":")[1].split(";")[0]),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<?> invalidDataAccessResourceUsageException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new Response(false, ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<?> noResultExceptionException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new Response(false, ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MappingException.class)
	public ResponseEntity<?> mappingException(RuntimeException ex, WebRequest request) {

		if (ex.getMessage().contains("DATE")) {
			return new ResponseEntity<>(
					new Response(false, "Formato de data inválido. Formato aceito: yyyy-mm-dd"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(
					new Response(false, ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@ExceptionHandler(JpaSystemException.class)
	public ResponseEntity<?> jpaSystemExceptionException(RuntimeException ex) {
		if (ex.getMessage().contains("CPF")) {
			return new ResponseEntity<>(
					new Response(false, "Quantidade de caracteres para o campo CPF excedida. Máximo permitido: 11."),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new Response(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@ExceptionHandler(TransientPropertyValueException.class)
	public ResponseEntity<?> transientPropertyValueException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new Response(false, "Erro interno no servidor. Contate o administrador do sistema."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> SQLException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new Response(false, "Erro interno no servidor. Contate o administrador do sistema."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request) {
		ex.printStackTrace();
		return new ResponseEntity<>(
				new Response(false, "Erro interno no servidor. Contate o administrador do sistema."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
