package br.flower.boot.auth.person;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.flower.boot.auth.base.BaseImplementsSQL;
import br.flower.boot.auth.upload.UploadFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "cl_pessoa")
public class Person extends BaseImplementsSQL {
	private static final long serialVersionUID = 384071407704965608L;

	@Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "pessoa_id")
	private UUID pessoaId;
	@Column(name = "nome")
	private String nome;
	@Column(name = "nome_completo")
	private String nomeCompleto;
	@Column(name = "genero")
	private String genero;
	@Column(name = "date_nascimento")
	private Date dateNascimento;
	@Column(name = "telefone")
	private String telefone;
	@Column(name = "email")
	private String email;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "cnh")
	private String cnh;
	@Column(name = "perfil_verificado")
	private boolean perfilVerificado;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "upload_id_upload_file", columnDefinition = "upload_id")
	private UploadFile fotoDaPessoa;

}
