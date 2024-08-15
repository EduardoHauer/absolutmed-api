package med.absolut.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.absolut.api.infra.security.DadosToken;
import med.absolut.api.infra.security.TokenService;
import med.absolut.api.usuario.DadosAutenticacao;
import med.absolut.api.usuario.Usuario;

@RestController
@RequestMapping("/login")
public class AutentincacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var authenticationtoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(authenticationtoken);
		
		var tokenJWT =  tokenService.geraToken((Usuario)authentication.getPrincipal());
		return ResponseEntity.ok(new DadosToken(tokenJWT));
	}
}
