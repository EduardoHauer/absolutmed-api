package med.absolut.api.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.absolut.api.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	private String nome;
	private String email;
	
    private String telefone;
	private String crm;

    @Enumerated(EnumType.STRING) 
    private Especialidades especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosMedico dados) {
    	this.ativo = true;
    	this.nome = dados.nome();
    	this.email = dados.email();
    	this.telefone = dados.telefone();
    	this.crm = dados.crm();
    	this.especialidade = dados.especialidade();
    	this.endereco = new Endereco(dados.endereco());
    }

    public void atualizaInformacao(DadosAtualizadoMedico dados) {
    	if(dados.id() != null) {
    		this.id = dados.id();
    	}

    	if(dados.nome() != null) {
    		this.nome = dados.nome();
    	}

    	if(dados.telefone()!= null) {
    		this.telefone = dados.telefone();
    	}
    }

	public void excluir() {
		this.ativo = false;
	}
}
