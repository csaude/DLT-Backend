package dlt.dltbackendmaster.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import dlt.dltbackendmaster.domain.Account;

/**
 * 
 * @author derciobucuane
 *
 */
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter{

	private final TokenAuthenticationService tokenAuthenticationService;
	private UserDetailsServiceImpl userServiceImpl;

	protected StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, 
			UserDetailsServiceImpl userDetailsService, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userServiceImpl = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		String username = request.getParameter("username");
        String[] params = request.getQueryString().split("=");
        String password = params[params.length -1];
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(username, URLDecoder.decode(password, StandardCharsets.UTF_8.toString()));
        
        try {
			getAuthenticationManager().authenticate(loginToken);
		} catch (AuthenticationException e) {
			logger.warn("User "+username+" unable to log in, details: "+e.getMessage());
		}
        
		return getAuthenticationManager().authenticate(loginToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

		final Account authenticatedUser = userServiceImpl.loadUserByUsername(authResult.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);
		tokenAuthenticationService.addAuthentication(response, userAuthentication);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		
		logger.warn("User "+authResult.getName()+" logged to system");
	}
	
	

}
