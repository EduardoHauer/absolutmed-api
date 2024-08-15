package med.absolut.api.medico;

public record ListagemMedicos(Long id, String nome, String email, String crm, Especialidades especilaidade) {

	public ListagemMedicos(Medico medico){
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
