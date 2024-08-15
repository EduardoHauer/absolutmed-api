package med.absolut.api.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.absolut.api.consulta.ConsultaRepository;
import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.infra.exception.ValidacaoException;

@Component
public class ValidadorMedicoPossuiOutraConsulta implements Validador{

	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosAgendamento dados) {
		
		var medicoPossuiOutraConsulta = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
		
		if(medicoPossuiOutraConsulta) {
			throw new ValidacaoException("Medico já possui uma consulta neste horario");
		}
	}
}
