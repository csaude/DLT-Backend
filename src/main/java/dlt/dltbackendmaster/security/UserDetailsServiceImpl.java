package dlt.dltbackendmaster.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.AccountLockedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import dlt.dltbackendmaster.domain.Account;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

/**
 * 
 * @author derciobucuane
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private static final String QUERY_FIND_USER_BY_USERNAME = "select u from Users u where u.username = :username";
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	@Autowired
	DAOService service;

	@Override
	public Account loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> todo = new HashMap<String, Object>();
        todo.put("username", username);
 
		try {
			List<Users> users = service.findByJPQuery(QUERY_FIND_USER_BY_USERNAME, todo);
			
			if(users != null && !users.isEmpty()) {
				Users user = users.get(0);
				Account account = new Account(user);
				detailsChecker.check(account);
				
				Date now = new Date();
				user.setLastLoginDate(now);
				user.setDateUpdated(now);
				
				service.update(user);

				return account;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
		throw new UsernameNotFoundException("no user found with " + username);
	}

	public ResponseEntity<Users> verifyUserByUsername(@PathVariable String username) throws AccountLockedException {
		Map<String, Object> todo = new HashMap<String, Object>();
		todo.put("username", username);
		ResponseEntity<Users> responseEntity = null;
		try {
			List<Users> users = service.findByJPQuery(QUERY_FIND_USER_BY_USERNAME, todo);

			if (users != null && !users.isEmpty()) {
				Users user = users.get(0);
				if (user.getStatus() == 0) {
					logger.warn("user " +user.getUsername()+" tried to login, but this user is locked, check error code returned "+HttpStatus.LOCKED);
					responseEntity = new ResponseEntity<>(null, HttpStatus.LOCKED);
				} else {
					responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
				}
			} else {
				logger.warn("user " +username+" tried to login, but this user does not exists on system , check error code returned "+HttpStatus.NOT_FOUND);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			/*** Its OK **/
		}
		return responseEntity;

	}
	
	public ResponseEntity<Users> checkPasswordValidity(@PathVariable String username) {
		Map<String, Object> todo = new HashMap<String, Object>();
		todo.put("username", username);
		ResponseEntity<Users> responseEntity = null;
	
		List<Users> users = service.findByJPQuery(QUERY_FIND_USER_BY_USERNAME, todo);
		if (users != null && !users.isEmpty()) {
			Users user = users.get(0);
			if(isPasswordVeryOld(user)){
				logger.warn("user " +user.getUsername()+" tried to login, the user password expired, the user has been redirected to password change page "+HttpStatus.TEMPORARY_REDIRECT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.TEMPORARY_REDIRECT);
			}else {
				responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
			}
		}
		return responseEntity;
	}

	private boolean isPasswordVeryOld(Users user) {
		if(user.getPasswordLastChangeDate() != null){
			long diffInMillies = Math.abs(new Date().getTime() - user.getPasswordLastChangeDate().getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			return diff > 182;
		}
		else {
		    long diffInMillies = Math.abs(new Date().getTime() - user.getDateCreated().getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			return diff > 182;
		}
	}

}
