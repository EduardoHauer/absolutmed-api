package med.absolut.api.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import med.absolut.api.consulta.validacoes.Validador;
import med.absolut.api.consulta.validacoes.cancelamento.ValidadorCancelamento;
import med.absolut.api.infra.exception.ValidacaoException;
import med.absolut.api.medico.Medico;
import med.absolut.api.medico.MedicoRepository;
import med.absolut.api.paciente.PacienteRepository;

@Service
public class AgendaConsulta {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private List<Validador> validadores;
	
	@Autowired
	private List<ValidadorCancelamento> validadoresCancelamento;

	public DadosDetalhamentoConsulta agendar(DadosAgendamento dados) {

		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Id do paciente não esta preenchido");
		}
		if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("Id do medico não esta preenchido");
		}

		validadores.forEach(v -> v.validar(dados));
		var paciente = pacienteRepository.findById(dados.idPaciente()).get();
		var medico = escolherMedico(dados);
		if (medico == null) {
			throw new ValidacaoException("Não possui nmedico disponivel nesta data");
		}

		var consulta = new Consulta(null, medico, paciente, dados.data());
		consultaRepository.save(consulta);

		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamento dados) {
		if (dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}

		if (dados.especialidade() == null) {
			throw new ValidationException("Escolha uma especialidade para a consulta");
		}
		return medicoRepository.escolherMedicoAleatoriamente(dados.especialidade(), dados.data());
	}
}
