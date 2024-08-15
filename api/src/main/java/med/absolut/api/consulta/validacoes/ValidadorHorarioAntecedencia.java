package med.absolut.api.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAntecedencia implements Validador {

	@Override
	public void validar(DadosAgendamento dados) {
		
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var duracaoEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
		
		if(duracaoEmMinutos < 30) {
			throw new ValidacaoException("A consulta deve ser agendada com pelo menos 30 minutos de antecedencia");
		}
	}
}
