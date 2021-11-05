package br.aym.base.informativo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.aym.base.upload.UploadFile;
import br.aym.base.util.BaseImplementsSQL;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_informativo")
@NamedEntityGraph(name = "Informativo.uploadFile", attributeNodes = {
		@NamedAttributeNode(value = "uploadFile"),})
public class Informativo extends BaseImplementsSQL {
	private static final long serialVersionUID = -8703265121351281150L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "informativo_id")
	private Long id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "sub_titulo")
	private String subTitulo;
	
	@Column(name = "corpo_mensagem")
	private String corpoMensagem;
	
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoMensagemEnum tipo;

	@Column(name = "texto_curto")
	private String textoCurto;
	
	@Column(name = "destaque")
	private boolean destacar;
	
	@Column(name = "status")
	private boolean status;
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
	// @Fetch(FetchMode.JOIN)
	@JoinTable(name = "tb_file_informativo", joinColumns = @JoinColumn(name = "informativo_id_tb_informativo"), 
	inverseJoinColumns = @JoinColumn(name = "file_id_tb_file_upload"))
	private Set<UploadFile> uploadFile = new HashSet<UploadFile>();
	
	public Informativo() {
		super();
	}
	
	public Informativo(Long id, String titulo, String corpoMensagem, TipoMensagemEnum tipo) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.corpoMensagem = corpoMensagem;
		this.tipo = tipo;
	}

	public Informativo(String titulo, String corpoMensagem, TipoMensagemEnum tipo, Set<UploadFile> uploadFile) {
		super();
		this.titulo = titulo;
		this.corpoMensagem = corpoMensagem;
		this.tipo = tipo;
		this.uploadFile = uploadFile;
	}
	
}
