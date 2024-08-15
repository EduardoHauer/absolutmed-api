package med.absolut.api.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.absolut.api.endereco.DadosEndereco;

public record DadosPaciente(

//		@NotNull
		Long id,

		@NotBlank
		String nome,

		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String cpf,

		@NotBlank
		@Email
		String email,

		@NotBlank
		@Pattern(regexp= "\\d{11}")
		String telefone,

		@NotNull @Valid
		DadosEndereco endereco) {


}
