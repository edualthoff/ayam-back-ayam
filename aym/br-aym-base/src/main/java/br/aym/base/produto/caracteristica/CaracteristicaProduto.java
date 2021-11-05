package br.aym.base.produto.caracteristica;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.aym.base.produto.Produto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "tb_caracteriscas")
@EqualsAndHashCode(exclude = "listProduto")
@NamedEntityGraph(name = "CaracteristicaProduto.listProduto",
	attributeNodes = {
	@NamedAttributeNode(value = "listProduto"),
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CaracteristicaProduto implements Serializable {
	private static final long serialVersionUID = 374498309034499202L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "caracteristica_id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	@JsonFormat(shape = Shape.STRING)
	private CaracteristicaProdutoEnum tipo;

	@Column(name = "status")
	private boolean status;
	
	@Column(name = "font_icon")
	private String fontIcon;
	
	@Column(name = "path_url_icon")
	private String pathUrlIcon;
	
	@Column(name = "path_relative_icon")
	private String pathRelativeIcon;
	
	@ManyToMany(mappedBy="listCaracteristicaProduto")
	//@JsonIgnoreProperties(value = "listCaracteristicaProduto")
	private Set<Produto> listProduto = new HashSet<>();
	
	public CaracteristicaProduto() {
		super();
	}

	public CaracteristicaProduto(String nome, CaracteristicaProdutoEnum tipo, String descricao) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.descricao = descricao;
	}
	
	public CaracteristicaProduto(String nome, CaracteristicaProdutoEnum tipo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
	}
}
