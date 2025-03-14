package dlt.dltbackendmaster.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;

import dlt.dltbackendmaster.domain.Account;

/**
 * 
 * @author derciobucuane
 *
 */
public class TokenAuthenticationService {
	private static final String SECRET = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0In0.B_rfuYrsvIkgwz2HB8QzzmFJY4d9xPruIuGN21hFr9Sp1FsliZi-HNZUqEkcXkikvOHiJDBHKQQLO6SdYwMJ8g";
	private static final String AUTH_HEADER_NAME = "Authorization";

	private final TokenHandler tokenHandler;

	@Autowired
	private final UserDetailsServiceImpl serviceImpl;

	public TokenAuthenticationService(UserDetailsServiceImpl serviceImpl) {
		this.serviceImpl = serviceImpl;
		tokenHandler = new TokenHandler(SECRET);
	}

	@SuppressWarnings("unchecked")
	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) throws IOException {
		final Account account = (Account) authentication.getDetails();
		// account.setExpires(System.currentTimeMillis() + ONE_HOUR);

		List<GrantedAuthority> auths = (List<GrantedAuthority>) account.getAuthorities();
		Map<String, Object> mapResponse = new HashMap<>();
		mapResponse.put("token", tokenHandler.createTokenForUser(account));
		mapResponse.put("account", account.toUser());
		new ObjectMapper().writeValue(response.getOutputStream(), mapResponse);

	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String authorizationHandler = request.getHeader(AUTH_HEADER_NAME);
		String token = null;
		String username = null;

		if (authorizationHandler != null && authorizationHandler.startsWith("Bearer ")) {
			token = authorizationHandler.substring(7);

			if (!token.equals("") && !token.equals("undefined")) { // validate if token was provided or is valid
				username = tokenHandler.parseUserFromToken(token);
				final Account account = serviceImpl.loadUserByUsername(username);
				if (account != null) {
					return new UserAuthentication(account);
				}
			}

		}
		return null;
	}

}
