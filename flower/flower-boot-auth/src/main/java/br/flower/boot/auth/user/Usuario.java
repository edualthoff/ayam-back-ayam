package br.flower.boot.auth.user;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.flower.boot.auth.base.BaseImplementsSQL;
import br.flower.boot.auth.pessoa.Pessoa;
import br.flower.boot.auth.user.role.UserAuthRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "auth_usuario")
public class Usuario extends BaseImplementsSQL {
	private static final long serialVersionUID = 1762732781720344591L;

	@Id
    //@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    //@GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "pessoa_id_tb_pessoa")
	private UUID userId;

	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_id_tb_pessoa", columnDefinition = "pessoa_id")
	private Pessoa pessoa;
	@NotBlank
	@Column(name = "username")
	private String username;
	@Column(name = "pass")
	@NotBlank
	private String password;
	@Column(name = "verificado")
	private boolean verificado;
	@Column(name = "disabled")
	private boolean disabled;
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name = "auth_usuario_auth_role", joinColumns =  @JoinColumn(name = "pessoa_id_tb_pessoa_auth_usuario"),
	inverseJoinColumns = @JoinColumn(name = "role_name_id_auth_role"))
	private List<UserAuthRole> userAuthRole;
	
}
