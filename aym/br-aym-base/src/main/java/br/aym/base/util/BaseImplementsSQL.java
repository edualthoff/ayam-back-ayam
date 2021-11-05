package br.aym.base.util;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @class Abstract
 * 
 * Base para inserir, alterar, remover um objeto no Postgree
 * - Para Multi Tenant
 * - Contem  @parm date_created, @parm date_modified, @parm tenantID
 * 
 * @author edu
 *
 */
//@EqualsAndHashCode(exclude = {"dateModified"})
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseImplementsSQL implements Serializable {
	private static final long serialVersionUID = -508204400061084184L;

	@Column(name = "date_created", updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") 
	@JsonProperty("dateCreated")
	//@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private OffsetDateTime dateCreated;
	@Column(name = "date_modified", updatable = true)
	@JsonProperty("dateModified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") 
	//@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private OffsetDateTime dateModified;

	
	public BaseImplementsSQL() {
		super();
	}

	public OffsetDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(OffsetDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public OffsetDateTime getDateModified() {
		return dateModified;
	}

	public void setDateModified(OffsetDateTime dateModified) {
		this.dateModified = dateModified;
	}

}