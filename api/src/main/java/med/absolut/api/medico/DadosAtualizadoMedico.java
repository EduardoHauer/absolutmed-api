package med.absolut.api.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.absolut.api.endereco.DadosEndereco;

public record DadosAtualizadoMedico(

		@NotNull
		Long id,

		@NotBlank
		String nome,
		
		@NotBlank
		String telefone,

		@NotNull
		DadosEndereco endereco) {

}
