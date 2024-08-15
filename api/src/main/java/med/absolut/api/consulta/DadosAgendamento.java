package med.absolut.api.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.absolut.api.medico.Especialidades;

public record DadosAgendamento(
		
		Long idMedico,
		
		@NotNull
		Long idPaciente,
		
		@NotNull
		@Future
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime data,
		
		Especialidades especialidade
		) {

}
