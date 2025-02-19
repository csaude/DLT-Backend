package dlt.dltbackendmaster.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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

@Entity
@Table(name = "old_passwords", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "OldPasswords.findByUserId", query = "SELECT p FROM OldPasswords p WHERE p.user.id = :userId ORDER BY p.id DESC") })
public class OldPasswords implements Serializable {

	private static final long serialVersionUID = 5722267159631853433L;

	private Integer id;

	private String password;

	private Users user;

	private Date dateCreated;

	public OldPasswords() {
	}

	public OldPasswords(Integer id) {
		this.id = id;
	}

	public OldPasswords(String password, Users user, Date dateCreated) {
		super();
		this.password = password;
		this.user = user;
		this.dateCreated = dateCreated;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "password", nullable = false, length = 150)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
