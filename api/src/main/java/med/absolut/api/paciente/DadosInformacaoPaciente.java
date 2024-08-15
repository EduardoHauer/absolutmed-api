package med.absolut.api.paciente;

import med.absolut.api.endereco.Endereco;

public record DadosInformacaoPaciente(Long id, String nome, String email, String cpf, String telefone, Endereco endereco) {

	public DadosInformacaoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(),paciente.getEndereco());
	}
}
