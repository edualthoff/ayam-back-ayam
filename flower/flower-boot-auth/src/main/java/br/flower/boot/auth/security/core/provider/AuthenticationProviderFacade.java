package br.flower.boot.auth.security.core.provider;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.client.SecurityClientResourceContext;
import br.flower.boot.auth.client.resource.ClientResource;
import br.flower.boot.auth.rest.UserAgentAnalyzerConfig;
import br.flower.boot.auth.security.core.ResponseAuthDto;
import br.flower.boot.auth.security.jwt.JwtTokenDto;
import br.flower.boot.auth.security.jwt.TokenManage;
import br.flower.boot.auth.security.socialmedia.AccountLoginSocialMedia;
import br.flower.boot.auth.security.username.AccountUsername;
import br.flower.boot.auth.session.client.ClientSessionDetails;
import br.flower.boot.auth.session.client.ClientSessionDetailsService;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.ApiBadRequestException;
import nl.basjes.parse.useragent.UserAgent;

@Service
public class AuthenticationProviderFacade {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationProviderFacade.class);

	@Autowired
	private AccountUsername accountUsername;
	@Autowired
	private AccountLoginSocialMedia accountLoginSocialMedia;
	@Autowired 
	private TokenManage tokenManage;
	@Autowired
	private SecurityClientResourceContext securityClientResourceContext;
	@Autowired
	private HttpServletResponse httpServletResponse;
	
	@Autowired
	private UserAgentAnalyzerConfig userAgentAnalyzerConfig;
	@Autowired
	private ClientSessionDetailsService ClientSessionDetailsService;
	
	public ResponseAuthDto grantType(AuthenticationProvider authenticationProvider) {
		Usuario user = new Usuario();
		switch (authenticationProvider.getGrantType()) {
		
		case AUTHORIZATIONMEDIASOCIAL: {
			user = accountLoginSocialMedia.login(authenticationProvider.getTokenOrigem(), authenticationProvider.getTokenAcess());
			break;
		}
		case AUTHORIZATIONCODE: {
			user = accountUsername.login(authenticationProvider.getUsername(), authenticationProvider.getPassword());
			break;
		}
		default:
			log.error("Grant Type informado errado");
			throw new ApiBadRequestException(ApiMessageSourceError.toMessage("bad_request.erro.grant_type.code"), ApiMessageSourceError.toMessage("bad_request.erro.grant_type.msg"));
		}
		return this.createdTokenAndSessionLogin(user);
	}
	
	private ResponseAuthDto createdTokenAndSessionLogin(Usuario user) {
		UUID sessionId = UUID.randomUUID();
		ResponseAuthDto response = tokenManage.gerarToken(user, sessionId);
		//System.out.println("token "+ response.getToken());
		//System.out.println("refresh "+response.getRefreshTorkn());
		JwtTokenDto jwt = tokenManage.deserializeToken(response.getRefreshTorkn());
		this.createSession(sessionId, jwt, user);
		
		return response;
	}
	
	/**
	 * Refatorar - Melhorar a parte de criação de sessao
	 * @param sessionId
	 * @param jwt
	 * @param user
	 */
	private void createSession(UUID sessionId, JwtTokenDto jwt, Usuario user) {
		UserAgent userAgent = userAgentAnalyzerConfig.userAgentParse(httpServletResponse.getHeader("User-Agent"));
		
		ClientResource oa = new ClientResource();
		oa.setClientId(securityClientResourceContext.getContext().get().getClientId());
		
		ClientSessionDetails clientSessionDetails = new ClientSessionDetails(sessionId, jwt.getJti(), user, oa,
				new Date(jwt.getIat()), new Date(jwt.getExp()));
		clientSessionDetails.setDevicetype(userAgent.getValue(UserAgent.DEVICE_CLASS));
		clientSessionDetails.setOperationSystem(userAgent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION));
		clientSessionDetails.setPlatform(userAgent.getValue(UserAgent.AGENT_CLASS));
		clientSessionDetails.setPlatformNameVersion(userAgent.getValue(UserAgent.AGENT_NAME_VERSION));
		this.ClientSessionDetailsService.save(clientSessionDetails);
	}
}
