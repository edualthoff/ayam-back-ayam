package br.aym.base.file;

public enum FilesDirectoryEnum {

	PRODUTO("produto"),
	INFORMATIVO("informativo");
	
	private String directory;
	
	FilesDirectoryEnum(String directory) {
		this.directory = directory;
	}

	public String getDirectory() {
		return directory;
	}
	
}
