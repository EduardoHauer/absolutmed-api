package med.absolut.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.absolut.api.consulta.AgendaConsulta;
import med.absolut.api.consulta.CancelamentoConsulta;
import med.absolut.api.consulta.DadosAgendamento;
import med.absolut.api.consulta.DadosCancelamento;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	@Autowired 
	private AgendaConsulta agendaConsulta; 
	
	@Autowired
private CancelamentoConsulta cancelamentoConsulta;
	
	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DadosAgendamento dados) {
		var dto = agendaConsulta.agendar(dados);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity cancelar(@PathVariable Long id, @RequestBody @Valid DadosCancelamento dados) {
		new DadosCancelamento(id, dados.motivo());
		cancelamentoConsulta.cancelar(dados);
		return ResponseEntity.noContent().build();
	}
}
