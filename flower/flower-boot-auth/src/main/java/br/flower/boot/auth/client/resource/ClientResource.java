package br.flower.boot.auth.client.resource;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import br.flower.boot.auth.base.BaseImplementsSQL;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "auth_client_resource")
public class ClientResource extends BaseImplementsSQL {
	private static final long serialVersionUID = -7154458427006416716L;

	@Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "client_id", columnDefinition = "uuid")
	@Type(type="pg-uuid")
	private UUID clientId;
	@Column(name = "client_secret", columnDefinition = "uuid")
	private UUID clientSecret;
	@Column(name = "name_client")
	private String name;
	@Column(name = "grant_types")
	private String grantTypes;
	@Column(name = "redirect_url")
	private String redirectUrl;
	@Column(name = "scope_client")
	private String scope;
	@Column(name = "domain_name")
	private String domainName;
	
}
