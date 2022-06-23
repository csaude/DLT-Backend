package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author derciobucuane
 *
 */
public class Account extends Users implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean isaccountenabled;
	private Boolean isaccountexpired;
	private Boolean isaccountlocked;
	private Boolean iscredentialsexpired;

	public Account() {
		super();
	}

	public Account(int id, Locality locality, Partners partner, Profiles profiles, Us us, String surname, String name,
			String phoneNumber, String email, String username, String password, String entryPoint, int status,
			int newPassword,
			Byte isLocked, Byte isExpired, Byte isCredentialsExpired, Byte isEnabled, int createdBy, Date dateCreated,
			Integer updatedBy, Date dateUpdated) {

		super(id, partner, profiles, us, surname, name,
				phoneNumber, email, username, password, newPassword, entryPoint, status,
				isLocked, isExpired, isCredentialsExpired, isEnabled, createdBy, dateCreated,
				updatedBy, dateUpdated);

		this.isaccountenabled = isEnabled == 0 ? false : true;
		this.isaccountexpired = isExpired == 0 ? false : true;
		this.isaccountlocked = isLocked == 0 ? false : true;
		this.iscredentialsexpired = isCredentialsExpired == 0 ? false : true;

	}

	public Account(Users user) {
		super(user.getId(), user.getPartners(), user.getProfiles(), user.getUs(), user.getSurname(),
				user.getName(), user.getPhoneNumber(), user.getEmail(), user.getUsername(), user.getPassword(),
				user.getNewPassword(), user.getEntryPoint(),
				user.getStatus(), user.getIsLocked(), user.getIsExpired(), user.getIsCredentialsExpired(),
				user.getIsEnabled(),
				user.getCreatedBy(), user.getDateCreated(), user.getUpdatedBy(), user.getDateUpdated());

		this.setNewPassword(user.getNewPassword());
		this.isaccountenabled = user.getIsEnabled() == 0 ? false : true;
		this.isaccountexpired = user.getIsExpired() == 0 ? false : true;
		this.isaccountlocked = user.getIsLocked() == 0 ? false : true;
		this.iscredentialsexpired = user.getIsCredentialsExpired() == 0 ? false : true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isaccountexpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isaccountlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !iscredentialsexpired;
	}

	@Override
	public boolean isEnabled() {
		return isaccountenabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority authority = () -> getProfiles().getName();
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);
		return authorities;
	}

	public Users toUser() {

		return new Users(getId(), getPartners(), getProfiles(), getUs(),
				getSurname(), getName(), getPhoneNumber(), getEmail(), getUsername(), getPassword(), getNewPassword(),
				getEntryPoint(), getStatus(), getIsLocked(), getIsExpired(), getIsCredentialsExpired(),
				getIsEnabled(), getCreatedBy(), getDateCreated(), getUpdatedBy(), getDateUpdated());
	}

}
