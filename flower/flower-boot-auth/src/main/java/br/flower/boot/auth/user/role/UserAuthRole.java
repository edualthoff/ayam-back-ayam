package br.flower.boot.auth.user.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "auth_role")
public class UserAuthRole {

	@Id
	@Column(name = "role_name_id")
	@Enumerated(EnumType.STRING)
	private UserRoleEnum roleName;
	@Column(name = "descricao")
	private String descricao;
	
	public UserAuthRole(UserRoleEnum roleName, String descricao) {
		super();
		this.roleName = roleName;
		this.descricao = descricao;
	}

	public UserAuthRole() {
		super();
	}	
}
