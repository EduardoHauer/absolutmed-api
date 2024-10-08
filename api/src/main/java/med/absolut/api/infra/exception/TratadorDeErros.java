package med.absolut.api.infra.exception;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratadorErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosSaidaErro::new).toList());
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity tratadorValidacao(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity tratarErroBadCredentials() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity tratarErroAuthentication() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity tratarErroAcessoNegado() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity tratarErro500(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
	}
	
	private record DadosSaidaErro(String campo, String menssagem) {

		public DadosSaidaErro(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}