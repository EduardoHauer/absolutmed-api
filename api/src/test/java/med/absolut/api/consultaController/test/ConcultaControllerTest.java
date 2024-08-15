package med.absolut.api.consultaController.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.absolut.api.consulta.AgendaConsulta;
import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.consulta.DadosDetalhamentoConsulta;
import med.absolut.api.medico.Especialidades;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConcultaControllerTest { 

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<DadosAgendamento> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
	
	@MockBean
	private AgendaConsulta agendamentoConsulta;
	@Test
	@DisplayName("Deveria devolver erro 400 se as informações forem erradas")
	@WithMockUser
	void agendarConcultaCenario1() throws Exception {
		
		var response = mvc.perform(post("/consultas"))
					.andReturn()
					.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver erro 200 se informçao for correta")
	@WithMockUser
	void agendarConcultaCenario2() throws Exception {
		
		var data = LocalDateTime.now().plusHours(1);
		var especialidade = Especialidades.CARDIOLOGIA;
		
		when(agendamentoConsulta.agendar(null))
					.thenReturn(new DadosDetalhamentoConsulta(
							null, 1l, 2l, data)
							);
		
		var response = mvc
					.perform(post("/consultas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(dadosAgendamentoConsultaJson.write(
							new DadosAgendamento(1l, 2l, data, especialidade))
							.getJson()))
					.andReturn()
					.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var JsonEsperado = dadosDetalhamentoConsultaJson
						.write(
								new DadosDetalhamentoConsulta(null, 1l, 2l, data)
								).getJson();
		
		assertThat(response.getContentAsString());
	}
}
