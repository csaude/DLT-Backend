package dlt.dltbackendmaster.domain;
// Generated Jul 12, 2022, 4:54:56 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AlphanumericSequence generated by hbm2java
 */
@Entity
@Table(name = "users_last_sync", catalog = "dreams_db")
@NamedQueries({
		@NamedQuery(name = "UserLastSync.findByUsername", query = "SELECT a FROM UserLastSync a WHERE a.username =:username"),
		@NamedQuery(name = "UserLastSync.findAll", query = "SELECT u FROM UserLastSync u "
														+ " LEFT JOIN u.user.districts d "
														+ " Where u.user.username like :searchUsername "
														+ " AND (:searchUserCreator IS NULL OR u.user.createdBy = :searchUserCreator OR u.user.updatedBy =:searchUserCreator) "
										                + " AND (:searchDistrict IS NULL OR d.id = :searchDistrict) "
				+ ""), })
public class UserLastSync implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private Users user;
	private Date lastSyncDate;

	public UserLastSync() {
	}

	public UserLastSync(String username) {

		this.username = username;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_sync_date", length = 19)
	public Date getLastSyncDate() {
		return lastSyncDate;
	}

	public void setLastSyncDate(Date lastSyncDate) {
		this.lastSyncDate = lastSyncDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
