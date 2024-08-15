package med.absolut.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.absolut.api.endereco.DadosEndereco;

public record DadosMedico(

		Long id,

		@NotBlank
		String nome,

		@NotBlank
		@Email
		String email,

		@NotBlank
		@Pattern(regexp = "\\d{6}")
		String crm,

		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String telefone,

		@NotNull
		Especialidades especialidade,

		@NotNull @Valid
		DadosEndereco endereco
		) {
}
