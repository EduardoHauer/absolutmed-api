package med.absolut.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import med.absolut.api.usuario.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService  {

	@Autowired
	public UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		return repository.findByLogin(username);
	}

	
}
