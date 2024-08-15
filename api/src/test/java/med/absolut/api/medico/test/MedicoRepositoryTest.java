package med.absolut.api.medico.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.Table;
import med.absolut.api.consulta.Consulta;
import med.absolut.api.endereco.DadosEndereco;
import med.absolut.api.medico.DadosMedico;
import med.absolut.api.medico.Especialidades;
import med.absolut.api.medico.Medico;
import med.absolut.api.medico.MedicoRepository;
import med.absolut.api.paciente.DadosPaciente;
import med.absolut.api.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Table(name = "medicos")
@ActiveProfiles("test")
class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private TestEntityManager em;

	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
	void escolherMedicoAleatoriamenteCenario1() {
		//given ou arrenge
		var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

		var medico = cadastrarMedico("Medico", "medico@email.com", "123456", Especialidades.CARDIOLOGIA);
		var paciente = cadastrarPaciente("Paciente", "0000000000", "paciente@email.com" );
		cadastrarConsulta(medico, paciente, proximaSegundaAs10);
		
		//when ou act
		var medicoLivre = medicoRepository.escolherMedicoAleatoriamente(Especialidades.CARDIOLOGIA, proximaSegundaAs10);
		
		//then ou assert
		assertThat(medicoLivre).isNull();
	}

	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
	void escolherMedicoAleatoriamenteCenario2() {
		//given ou arrenge
		var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
		var medico = cadastrarMedico("Medico", "medico@email.com", "123456", Especialidades.CARDIOLOGIA);
		
		//when ou act
		var medicoLivre = medicoRepository.escolherMedicoAleatoriamente(Especialidades.CARDIOLOGIA, proximaSegundaAs10);
		
		//then ou assert
		assertThat(medicoLivre).isEqualTo(medico);
	}

	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
		em.persist(new Consulta(
				null, 
				medico, 
				paciente, 
				data
			)
		);
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidades especialidade) {
		var medico = new Medico(dadosMedico(
				nome, 
				email, 
				crm, 
				especialidade
			)
		);	
		em.persist(medico);
		return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
		var paciente = new Paciente(dadosPaciente(
				nome, 
				email, 
				cpf
			 )
		);
		em.persist(paciente);
		return paciente;
	}

	private DadosMedico dadosMedico(String nome, String email, String crm, Especialidades especialidade) {
		return new DadosMedico(
				null, 
				nome, 
				email, 
				crm, 				
				"61999999999", 
				especialidade, 
				dadosEndereco()
		);
	}

	private DadosPaciente dadosPaciente(String nome, String email, String cpf) {
		return new DadosPaciente(
				null, 
				nome, 
				email, 
				cpf,				
				"61999999999", 
				dadosEndereco()
		);
	}

	private DadosEndereco dadosEndereco() {
		return new DadosEndereco(
				"rua xpto", 
				"bairro", 
				"00000000", 
				"Brasilia", 
				"DF", 
				null, 
				null
		);
	}
}
