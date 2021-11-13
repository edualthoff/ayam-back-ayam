
package br.flower.boot.auth.socialmedia;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.flower.boot.auth.user.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "auth_social_media")
public class SocialMediaAuth implements Serializable{
	private static final long serialVersionUID = -2347062153408686572L;

	@Data
	@NoArgsConstructor
	@Embeddable
	public static class SocialMediaAuthId implements Serializable {
		private static final long serialVersionUID = 1824812710946739625L;

		
		@Column(name = "nome_midia")
		@Enumerated(EnumType.STRING)
		private SocialMediaNameEnum name;
		
		@Column(name = "user_midia_id")
		private String userId;
		
		public SocialMediaAuthId(SocialMediaNameEnum name, String userId) {
			super();
			this.name = name;
			this.userId = userId;
		}		
	}
	
	@EmbeddedId
	private SocialMediaAuthId socialMediaAuthId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_id_tb_pessoa_auth_usuario", columnDefinition = "user_id")
	private Usuario usuario;
}
