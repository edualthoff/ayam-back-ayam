package br.flower.boot.auth.upload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.flower.boot.auth.base.BaseImplementsSQL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Table(name = "upload_file")
public class UploadFile extends BaseImplementsSQL {
	private static final long serialVersionUID = -5671502251030948745L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="upload_id")
	private long id;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_type")
	private String tipoType;
	
	@Column(name="data_file")
	private byte[] data;
	
	@Column(name="tamanho")
	private long tamanho;
	
	@Column(name="height")
	private String height;
	
	@Column(name="width")
	private String width;
	

	public UploadFile(String fileName, String tipoType, byte[] data) {
		this.fileName = fileName;
		this.tipoType = tipoType;
		this.data = data;
		this.tamanho = data.length;
	}

	public UploadFile() {
		super();
	}
	
}