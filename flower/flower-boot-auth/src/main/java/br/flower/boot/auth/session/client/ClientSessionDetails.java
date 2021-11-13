package br.flower.boot.auth.session.client;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.flower.boot.auth.client.resource.ClientResource;
import br.flower.boot.auth.user.Usuario;
import lombok.Data;

@Data
@Entity
@Table(name = "client_session_details")
public class ClientSessionDetails implements Serializable{
	private static final long serialVersionUID = 8543784125537746726L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "session_id")
	private UUID sessionId;
	@Column(name = "device_id")
	private String deviceId;
	@Column(name = "device_type")
	private String devicetype;
	@Column(name = "operation_system")
	private String operationSystem;
	@Column(name = "location_acess")
	private String location;
	@Column(name = "platform")
	private String platform;
	@Column(name = "platform_name_version")
	private String platformNameVersion;
	@Column(name = "acess_ip")
	private String acessIp;
	@Column(name = "token_refresh")
	private String tokenRefresh;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "pessoa_id_tb_pessoa_auth_usuario", columnDefinition = "user_id")
	private Usuario usuario;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "client_id_auth_client_resource", columnDefinition = "client_id")
	private ClientResource oauthClientResource;
	
	@Column(name = "date_created", updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date dateCreated;
	@Column(name = "date_modified", updatable = true)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date dateModified;
	@Column(name = "date_last_expire")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date dateLastExpire;

	public ClientSessionDetails() {
		super();
	}

	
	public ClientSessionDetails(UUID sessionId, String tokenRefresh, Usuario usuario,
			ClientResource oauthClientResource, Date dateCreated, Date dateLastExpire) {
		super();
		System.out.println("session cli "+usuario.getPessoa().getPessoaId());
		this.sessionId = sessionId;
		this.tokenRefresh = tokenRefresh;
		this.usuario = usuario;
		this.oauthClientResource = oauthClientResource;
		this.dateCreated = dateCreated;
		this.dateLastExpire = dateLastExpire;
	}



	
	
}
