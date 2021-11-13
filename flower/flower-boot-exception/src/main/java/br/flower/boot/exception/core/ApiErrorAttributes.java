package br.flower.boot.exception.core;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import br.flower.boot.exception.msg.ApiErrorMessage;




public class ApiErrorAttributes extends DefaultErrorAttributes {

    private final String currentApiVersion;
    private final String sendReportUri;

    public ApiErrorAttributes(final String currentApiVersion, final String sendReportUri) {
        this.currentApiVersion = currentApiVersion;
        this.sendReportUri = sendReportUri;
    }

    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final ErrorAttributeOptions options) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, options);
        final ApiErrorMessage superHeroAppError = ApiErrorMessage.fromDefaultAttributeMap(
                currentApiVersion, sendReportUri, defaultErrorAttributes
        );
        return superHeroAppError.toAttributeMap();
    }
}
