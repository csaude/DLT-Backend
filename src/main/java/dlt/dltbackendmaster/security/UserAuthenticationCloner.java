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
public class UserAuthenticationCloner {
	
	private final AccountCloner account;
	public UserAuthenticationCloner(AccountCloner account) {
        this.account = account;
    }

	

	

}
