package br.flower.boot.auth.pessoa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import br.flower.boot.auth.base.BaseImplementsSQL;
import br.flower.boot.auth.upload.UploadFile;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "tb_pessoa")
public class Pessoa extends BaseImplementsSQL {
	private static final long serialVersionUID = 384071407704965608L;

	@Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "pessoa_id")
	private UUID pessoaId;
	@NotBlank
	@Column(name = "nome")
	private String nome;
	@NotBlank
	@Column(name = "sobrenome")
	private String sobrenome;
	@Column(name = "genero")
	@Enumerated(EnumType.STRING)
	private PessoaGeneroEnum genero;
	@Column(name = "date_nascimento")
	private Date dateNascimento;
	@Setter(value = AccessLevel.NONE)
	@Column(name = "telefone")
	private String telefone;
	@NotBlank
	@Column(name = "email")
	private String email;
	@Column(name = "cpf")
	private String cpf;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "upload_id_tb_upload_img", columnDefinition = "upload_id")
	private UploadFile fotoDaPessoa;
	
	
	public void setTelefone(String telefone) {
		this.telefone = telefone.replaceAll("[^0-9a-zA-Z:,]+", "");
	}
		
}
