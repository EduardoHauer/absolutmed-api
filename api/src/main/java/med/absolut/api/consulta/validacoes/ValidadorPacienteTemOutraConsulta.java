package med.absolut.api.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.absolut.api.consulta.ConsultaRepository;
import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.infra.exception.ValidacaoException;

@Component
public class ValidadorPacienteTemOutraConsulta implements Validador{

	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosAgendamento dados) {
		
		var primeiroHorario = dados.data().withHour(7);
		var ultimoHorario = dados.data().withHour(18);
		var pacientePossuiOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

		if(pacientePossuiOutraConsulta) {
			throw new ValidacaoException("Paciente já possui consulta nesse dia");
		}
	}

}
