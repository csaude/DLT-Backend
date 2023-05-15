package dlt.dltbackendmaster.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import dlt.dltbackendmaster.domain.Account;
import dlt.dltbackendmaster.dto.AccountCloner;

/**
 * 
 * @author derciobucuane
 *
 */
public class UserAuthentication implements Authentication{
	
	private static final long serialVersionUID = 1L;
	private final Account account;
	private boolean authenticated = true;
	private final String platform;

    public UserAuthentication(Account account, String platform) {
        this.account = account;
        this.platform=platform;
    }

	@Override
	public String getName() {
		return account.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return account.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return platform=="web"? null : account.getPassword();
	}

	@Override
	public Object getDetails() {
		return getAccountResponse(platform);
	}

	@Override
	public Object getPrincipal() {
		return getAccountResponse(platform);
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}
	
	private Object getAccountResponse(String platform) {
		if("web".equals(platform)) {
			try {
			    Account clonedAccount = AccountCloner.cloneAndRemoveField(account);
				return clonedAccount;
			} catch (CloneNotSupportedException e) {
			    e.printStackTrace();
			}
		}
		return account;
	}

}
