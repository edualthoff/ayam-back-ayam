package br.aym.base.file;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileInfo implements Serializable {
	private static final long serialVersionUID = 2697760741684956131L;

	private String name;
	private String url;
	private String pathRelative;
	private String other;
	
	public FileInfo(String name, String url, String pathRelative) {
		super();
		this.name = name;
		this.url = url;
		this.pathRelative = pathRelative;
	}

	public FileInfo(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public FileInfo(String name, String url, String pathRelative, String other) {
		super();
		this.name = name;
		this.url = url;
		this.pathRelative = pathRelative;
		this.other = other;
	}
	
}
