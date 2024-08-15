package med.absolut.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.absolut.api.paciente.DadosAtualizadoPaciente;
import med.absolut.api.paciente.DadosInformacaoPaciente;
import med.absolut.api.paciente.DadosPaciente;
import med.absolut.api.paciente.ListagemPacientes;
import med.absolut.api.paciente.Paciente;
import med.absolut.api.paciente.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

	@Autowired
	private PacienteRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosPaciente dados, UriComponentsBuilder uriBuilder) {
		var paciente = new Paciente(dados);
		repository.save(paciente);
		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosInformacaoPaciente(paciente));
	}

	@GetMapping
	public ResponseEntity<Page<ListagemPacientes>> listar(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		var page = repository.findAllByAtivoTrue(paginacao).map(ListagemPacientes::new);
		return ResponseEntity.ok(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualiza(@RequestBody @Valid DadosAtualizadoPaciente dados) {
		var paciente = repository.getReferenceById(dados.id());
		paciente.atualizaInformacao(dados);
		return ResponseEntity.ok(new DadosInformacaoPaciente(paciente));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		var paciente = repository.getReferenceById(id);
		paciente.excluir();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var paciente = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosInformacaoPaciente(paciente));
	}
}
