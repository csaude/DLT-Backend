package dlt.dltbackendmaster.domain;
// Generated Jan 20, 2022, 4:57:19 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "dreams_db")
public class Users implements java.io.Serializable {

	private int id;
	private Locality locality;
	private Partner partner;
	private Profiles profiles;
	private Us us;
	private String surname;
	private String name;
	private String phoneNumber;
	private String email;
	private String username;
	private String password;
	private String entryPoint;
	private int status;
	private Byte isLocked;
	private Byte isExpired;
	private Byte isCredentialsExpired;
	private Byte isEnabled;
	private int createdBy;
	private Date dateCreated;
	private Integer updatedBy;
	private Date dateUpdated;

	public Users() {
	}

	public Users(int id, Partner partner, Profiles profiles, String surname, String name, String phoneNumber,
			String email, String username, String password, int status, int createdBy, Date dateCreated) {
		this.id = id;
		this.partner = partner;
		this.profiles = profiles;
		this.surname = surname;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.status = status;
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
	}

	public Users(int id, Locality locality, Partner partner, Profiles profiles, Us us, String surname, String name,
			String phoneNumber, String email, String username, String password, String entryPoint, int status,
			Byte isLocked, Byte isExpired, Byte isCredentialsExpired, Byte isEnabled, int createdBy, Date dateCreated,
			Integer updatedBy, Date dateUpdated) {
		this.id = id;
		this.locality = locality;
		this.partner = partner;
		this.profiles = profiles;
		this.us = us;
		this.surname = surname;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.entryPoint = entryPoint;
		this.status = status;
		this.isLocked = isLocked;
		this.isExpired = isExpired;
		this.isCredentialsExpired = isCredentialsExpired;
		this.isEnabled = isEnabled;
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
		this.updatedBy = updatedBy;
		this.dateUpdated = dateUpdated;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locality_id")
	public Locality getLocality() {
		return this.locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	public Partner getPartner() {
		return this.partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	public Profiles getProfiles() {
		return this.profiles;
	}

	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "us_id")
	public Us getUs() {
		return this.us;
	}

	public void setUs(Us us) {
		this.us = us;
	}

	@Column(name = "surname", nullable = false, length = 50)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "phone_number", nullable = false, length = 50)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email", nullable = false, length = 150)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 150)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "entry_point", length = 50)
	public String getEntryPoint() {
		return this.entryPoint;
	}

	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "is_locked")
	public Byte getIsLocked() {
		return this.isLocked;
	}

	public void setIsLocked(Byte isLocked) {
		this.isLocked = isLocked;
	}

	@Column(name = "is_expired")
	public Byte getIsExpired() {
		return this.isExpired;
	}

	public void setIsExpired(Byte isExpired) {
		this.isExpired = isExpired;
	}

	@Column(name = "is_credentials_expired")
	public Byte getIsCredentialsExpired() {
		return this.isCredentialsExpired;
	}

	public void setIsCredentialsExpired(Byte isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	@Column(name = "is_enabled")
	public Byte getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Byte isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "created_by", nullable = false)
	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_updated", length = 19)
	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

}
