package br.flower.boot.exception.msg;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.flower.boot.exception.config.ApiMessageSourceError;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class ApiErrorMessage implements Serializable{
	private static final long serialVersionUID = -3700781911864594061L;

	@JsonIgnore
	private final UUID uniqueId;
	private final String apiVersion;
	private final String code;
	private final String message;
	private final String reasonMessage;
	private final String domain;
	private final String messageReport;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private final LocalDateTime data;
	
	
	public ApiErrorMessage(final String apiVersion, final String code, final String message, final String domain,
			final String reasonMessage) {
		this.apiVersion = apiVersion;
		this.code = code;
		this.message = message;
		this.uniqueId = UUID.randomUUID();
		this.data = LocalDateTime.now();
		this.reasonMessage = reasonMessage;
		this.messageReport =  ApiMessageSourceError.toMessage("msg.padrao");
		this.domain = domain;

	}
	
	public ApiErrorMessage(final String apiVersion, final String code, final String message, final String domain,
			final String reasonMessage, final String messageReport) {
		this.apiVersion = apiVersion;
		this.code = code;
		this.message = message;
		this.uniqueId = UUID.randomUUID();
		this.data = LocalDateTime.now();
		this.reasonMessage = reasonMessage;
		this.messageReport =  messageReport;
		this.domain = domain;

	}
	public static ApiErrorMessage fromDefaultAttributeMap(final String apiVersion, final String sendReportUri,
			final Map<String, Object> defaultErrorAttributes) {
		// original attribute values are documented in
		// org.springframework.boot.web.servlet.error.DefaultErrorAttributes
		return new ApiErrorMessage(apiVersion, 
				((Integer) defaultErrorAttributes.get("status")).toString(),
				(String) defaultErrorAttributes.getOrDefault("message", "no message available"),
				(String) defaultErrorAttributes.getOrDefault("path", "no domain available"),
				(String) defaultErrorAttributes.getOrDefault("error", "no reason available"),
				(String) defaultErrorAttributes.get("message"));
	}

	// utility method to return a map of serialized root attributes,
	// see the last part of the guide for more details
	public Map<String, Object> toAttributeMap() {
		return Map.of("apiVersion", apiVersion,"uniqueId", uniqueId,"code",code,"message",message);
	}

	public String getApiVersion() {
		return apiVersion;
	}
	
	@JsonGetter
	public UUID getUniqueId() {
		return uniqueId;
	}
	@JsonGetter
	public String getCode() {
		return code;
	}
	@JsonGetter
	public String getMessage() {
		return message;
	}
	@JsonGetter
	public String getDomain() {
		return domain;
	}
	@JsonGetter
	public LocalDateTime getData() {
		return data;
	}
	@JsonGetter
	public String getReasonMessage() {
		return reasonMessage;
	}
	@JsonGetter
	public String getMessageReport() {
		return messageReport;
	}
	
}
