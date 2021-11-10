package br.aym.base.produto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.aym.base.produto.caracteristica.CaracteristicaProduto;
import br.aym.base.upload.UploadFile;
import br.aym.base.util.BaseImplementsSQL;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_produto")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@NamedEntityGraph(name = "Produto.all", attributeNodes = {
		@NamedAttributeNode(value = "listCaracteristicaProduto"), 
		@NamedAttributeNode(value = "uploadFile"),})
@NamedEntityGraph(name = "Produto.uploadFile", attributeNodes = {
		@NamedAttributeNode(value = "uploadFile"),})
public class Produto extends BaseImplementsSQL implements Serializable {
	private static final long serialVersionUID = -4931181123511553274L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "nome_curto")
	private String nomeCurto;

	@NotNull
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "status")
	private boolean status;
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
	// @Fetch(FetchMode.JOIN)
	@JoinTable(name = "tb_file_produto", joinColumns = @JoinColumn(name = "produto_id_tb_produto"), 
	inverseJoinColumns = @JoinColumn(name = "file_id_tb_file_upload"))
	private Set<UploadFile> uploadFile;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_caracteristica_produto",  
		joinColumns = @JoinColumn(name = "produto_id_tb_produto", referencedColumnName = "produto_id"), inverseJoinColumns =
			@JoinColumn(name = "caracteristica_id_tb_caracteriscas", referencedColumnName = "caracteristica_id"))
	//@JsonIgnoreProperties(value = "listProduto")
	private Set<CaracteristicaProduto> listCaracteristicaProduto;

	public Produto() {
		super();
	}

	public Produto(String nome, @NotNull String descricao, Set<UploadFile> uploadFile,
			Set<CaracteristicaProduto> listCaracteristicaProduto) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.uploadFile = uploadFile;
		this.listCaracteristicaProduto = listCaracteristicaProduto;
	}

}
