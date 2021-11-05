package br.aym.base.upload;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_file_upload")
@EntityListeners(AuditingEntityListener.class)
public class UploadFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;
	@Column(name = "file_name")
	private String name;
	@Column(name = "file_url")
	private String fileUrl;
	@Column(name = "path_url")
	private String pathUrl;
	@Column(name = "ordem")
	private int ordem;
	
	@Column(name="height")
	private String height;
	@Column(name="width")
	private String width;
	
	@Transient
	@JsonProperty
	private boolean remove;
	
	@Column(name = "date_created", updatable = false)
	@JsonProperty("dateCreated")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") 
	@CreatedDate
	private OffsetDateTime dateCreated;
	@Column(name = "date_modified", updatable = true)
	@JsonProperty("dateModified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") 
	@LastModifiedDate
	private OffsetDateTime dateModified;
	
	public UploadFile(String name, String url) {
		super();
		this.name = name;
		this.fileUrl = url;
	}

	public UploadFile() {
		super();
	}

	public UploadFile(String name, String url, String pathUrl) {
		super();
		this.name = name;
		this.fileUrl = url;
		this.pathUrl = pathUrl;
	}

}
