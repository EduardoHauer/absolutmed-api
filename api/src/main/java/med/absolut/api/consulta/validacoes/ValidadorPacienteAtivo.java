package med.absolut.api.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.infra.exception.ValidacaoException;
import med.absolut.api.paciente.PacienteRepository;

@Component

public class ValidadorPacienteAtivo implements Validador{

	@Autowired
	private PacienteRepository repository;
	
	@Override
	public void validar(DadosAgendamento dados) {
		
		var pacienteAtivo = repository.finAtivoById(dados.idPaciente());
		
		if(!pacienteAtivo) {
			throw new ValidacaoException("Este paciente não está ativo");
		}
	}

}
