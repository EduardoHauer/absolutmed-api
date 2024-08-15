package med.absolut.api.paciente;

import jakarta.validation.constraints.NotNull;
import med.absolut.api.endereco.DadosEndereco;

public record DadosAtualizadoPaciente(

		@NotNull
		Long id,

		String nome,

		String email,

		String telefone,

		DadosEndereco endereco) {


}
