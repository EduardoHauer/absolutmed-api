package med.absolut.api.medico;

import med.absolut.api.endereco.Endereco;

public record DadosInformacionalMedico(Long id, String nome, String email, String crm, String telefone, Especialidades especialidade, Endereco endereco) {

	public DadosInformacionalMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
	}
}
