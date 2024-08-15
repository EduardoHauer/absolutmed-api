package med.absolut.api.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.absolut.api.consulta.ConsultaRepository;
import med.absolut.api.consulta.DadosCancelamento;
import med.absolut.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAtecidencaCancelamento implements ValidadorCancelamento {

	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosCancelamento dados) {

		var consulta = repository.getReferenceById(dados.idConsulta());
		var agora = LocalDateTime.now();
		var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();
	
		if(diferencaEmHoras < 24){
		throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
	}
  }
}
