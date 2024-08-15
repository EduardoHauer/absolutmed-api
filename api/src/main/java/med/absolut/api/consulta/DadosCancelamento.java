package med.absolut.api.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamento(
		
		@NotNull
		Long idConsulta, 
		
		@NotNull
		MotivoCancelamento motivo
		
		) {

	public boolean existsById(@NotNull Long idConsulta) {
		return false;
	}
	
	public DadosCancelamento(Long idConsulta, MotivoCancelamento motivo) {
		this.idConsulta = idConsulta;
		this.motivo = motivo;
	}
}
