package med.absolut.api.paciente;

public record ListagemPacientes(String nome, String email, String cpf, String telefone) {

	public ListagemPacientes(Paciente paciente) {

		this(paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone());
	}

}
