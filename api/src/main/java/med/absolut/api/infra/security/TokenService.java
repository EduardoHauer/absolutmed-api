package med.absolut.api.infra.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import med.absolut.api.usuario.Usuario;

@Service
public class TokenService {

	
	@Value("${api.security.token.secret}")
	private String secret;

	public String geraToken(Usuario usuario ) {
		
	try {
	    var algorithm = Algorithm.HMAC256(secret);
	    
	    return JWT.create()
	        .withIssuer("Api Absolut.med")
	        .withSubject(usuario.getLogin())
	        .withExpiresAt(dadosTempo())
	        .sign(algorithm);
	    
	} catch (JWTCreationException exception){
		throw new RuntimeException("Erro ao gerar o token");
	}
  }

	public String getSubject(String tokenJWT) {
		try {
			  var algorithm = Algorithm.HMAC256(secret);		   
			  return JWT.require(algorithm)
		        .withIssuer("Api Absolut.med")
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		        
		  
		} catch (JWTVerificationException exception){
			throw new RuntimeException("Token invalido ou expirado");
		}
	}
	
	private Instant dadosTempo() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
