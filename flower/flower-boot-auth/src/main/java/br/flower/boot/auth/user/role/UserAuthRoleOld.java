package br.flower.boot.auth.user.role;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
/*
@Data
@NoArgsConstructor
@Entity
@Table(name = "auth_role")*/
public class UserAuthRoleOld {
/*
	@Data
	@NoArgsConstructor
	@Embeddable
	public static class UserAuthRoleId implements Serializable{
		private static final long serialVersionUID = -4484512241674493392L;

		@Id
		@Column(name = "role_name_id")
		@Enumerated(EnumType.STRING)
		private UserRoleEnum roleName;
		@Id
		@Column(name = "user_id_auth_usuario")
		private String userId;
	}
	
	@EmbeddedId
	private UserAuthRoleId userAuthRoleId;
	
	*/
}
