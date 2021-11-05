package br.aym.base.file.type;

public enum ContentTypeEnum {

	SVG("image/svg+xml");

	private String type;
	
	ContentTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
