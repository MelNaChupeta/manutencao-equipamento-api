package br.com.tads.manutencaoequipamentoapi.exceptions;

import java.sql.SQLException;

import org.hibernate.TransientPropertyValueException;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import br.com.tads.manutencaoequipamentoapi.commom.Response;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler  {

     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>(new Response(false,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(NoResourceFoundException ex, WebRequest request) {
        String errorMessage = String.format("Método não encontrado.");
		return new ResponseEntity<>(new Response(false,errorMessage), HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Response> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        String errorMessage = String.format("Você não possui permissão para acessar esse recurso.");
		return new ResponseEntity<>(new Response(false,errorMessage), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String errorMessage = String.format("Método '%s' não é suportado para esta solicitação.", ex.getMethod());
		return new ResponseEntity<>(new Response(false,errorMessage), HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
		return new ResponseEntity<>(new Response(false,"Parâmetro de requisição obrigatório está ausente: " + ex.getParameterName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
		return new ResponseEntity<>(new Response(false,"Argumento inválido."), HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(EntityNotFoundException ex , WebRequest request) {
		return new ResponseEntity<>(new Response(false,ex.getMessage()), HttpStatus.NOT_FOUND);

    }
    
	@ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException ex , WebRequest request) {
		return new ResponseEntity<>(new Response(false,"Ocorreu um erro ao enviar email de cadastro"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) {
		return new ResponseEntity<>(new Response(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(RuntimeException ex, WebRequest request) {
		if (ex.getMessage().contains("cpf")) {
			return new ResponseEntity<>(new Response(false, "CPF já existe na base de dados!"), HttpStatus.BAD_REQUEST);
		} else if (ex.getMessage().contains("email")) {
			return new ResponseEntity<>(new Response(false, "EMAIL já existe na base de dados!"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new Response(false, "Erro interno no servidor. Contate o administrador do sistema."), HttpStatus.BAD_REQUEST);
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
