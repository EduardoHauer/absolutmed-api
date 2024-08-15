package med.absolut.api.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.absolut.api.consulta.validacoes.cancelamento.ValidadorCancelamento;
import med.absolut.api.infra.exception.ValidacaoException;

@Service
public class CancelamentoConsulta {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private List<ValidadorCancelamento> validadoresCancelamento;
	
	public void cancelar(DadosCancelamento cancelamento) {

		var c = consultaRepository.findById(cancelamento.idConsulta())
				.orElseThrow(() -> new ValidacaoException("Consulta não existe."));
		
		if(c.getMotivoCancelamento() != null) {
			throw new ValidacaoException("Consulta já está cancelada.");
		}
		
		validadoresCancelamento.forEach(v -> v.validar(cancelamento));
		
		var c2 = consultaRepository.getReferenceById(cancelamento.idConsulta());

		c.cancelar(cancelamento.motivo());
		consultaRepository.save(c);
	}
}
