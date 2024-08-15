package med.absolut.api.consulta.validacoes;

import java.time.DayOfWeek;

import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.infra.exception.ValidacaoException;

public class ValidadorHorarioFuncionnamento implements Validador{

	@Override
	public void validar(DadosAgendamento dados) {
		
		var dataConsulta = dados.data();
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var horaAbertura = dados.data().getHour() < 7;
		var horaFechamento = dados.data().getHour() > 18;
		
		if(domingo || horaAbertura || horaFechamento) {
			throw new ValidacaoException("Clinica não abre aos domingos");
		}
		
				
	}
}
