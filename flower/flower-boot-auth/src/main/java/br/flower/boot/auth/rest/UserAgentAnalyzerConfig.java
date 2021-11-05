package br.flower.boot.auth.rest;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Component
public class UserAgentAnalyzerConfig {

	private static int cacheValue = 500;
	private static String[] filds = {"DeviceClass", "OperatingSystemNameVersion", "AgentClass", "AgentNameVersion"};
	
	
	public UserAgent userAgentParse(String userAgent) {
		return UserAgentAnalyzer
	            .newBuilder()
	            .hideMatcherLoadStats()
	            .withFields(Arrays.asList(filds))
	            .withCache(cacheValue)
	            .build().parse(userAgent);
	}
}
