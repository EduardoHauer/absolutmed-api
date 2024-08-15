package med.absolut.api.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.absolut.api.endereco.Endereco;


@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String cpf;
	private String email;
	private String telefone;

	@Embedded
	private Endereco endereco;

	private Boolean ativo;

	public Paciente(DadosPaciente dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.email = dados.email();
		this.cpf = dados.cpf();
		this.telefone = dados.telefone();
		this.endereco = new Endereco(dados.endereco());

	}

	public void atualizaInformacao(DadosAtualizadoPaciente dados) {

		if(dados.nome()!= null) {
			this.nome = dados.nome();
		}
		if(dados.email() != null) {
			this.email = dados.email();
		}
		if(dados.telefone() != null) {
			this.telefone= dados.telefone();
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
