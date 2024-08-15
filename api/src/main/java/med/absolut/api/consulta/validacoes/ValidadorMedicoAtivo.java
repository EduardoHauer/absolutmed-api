package med.absolut.api.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.infra.exception.ValidacaoException;
import med.absolut.api.medico.MedicoRepository;

@Component

public class ValidadorMedicoAtivo implements Validador{

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public void validar(DadosAgendamento dados) {
		
		if(dados.idMedico() == null) {
			return;
		}
		var medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());

		if(!medicoAtivo) {
			throw new ValidacaoException("Medico selecionado não está ativo ou inesistente");
		}
	}

	
}
