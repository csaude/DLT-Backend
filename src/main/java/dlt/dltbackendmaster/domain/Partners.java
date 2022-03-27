package dlt.dltbackendmaster.domain;
// Generated Jan 25, 2022, 4:05:43 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Partner generated by hbm2java
 */
@Entity
@Table(name = "partners", catalog = "dreams_db")
public class Partners implements java.io.Serializable {

	private Integer id;
	private District district;
	private String name;
	private String abbreviation;
	private String description;
	private String partnerType;
	private String logo;
	private int status;
	private int createdBy;
	private Date createdAt;
	private Integer updatedBy;
	private Date updatedAt;
	private String offlineId;
	private Set<Users> userses = new HashSet<Users>(0);

	public Partners() {
	}

	public Partners(String name, String partnerType, int status, int createdBy, Date dateCreated) {
		this.name = name;
		this.partnerType = partnerType;
		this.status = status;
		this.createdBy = createdBy;
		this.createdAt = dateCreated;
	}

	public Partners(District district, String name, String abbreviation, String description, String partnerType,
			String logo, int status, int createdBy, Date dateCreated, Integer updatedBy, Date dateUpdated,
			Set<Users> userses) {
		this.district = district;
		this.name = name;
		this.abbreviation = abbreviation;
		this.description = description;
		this.partnerType = partnerType;
		this.logo = logo;
		this.status = status;
		this.createdBy = createdBy;
		this.createdAt = dateCreated;
		this.updatedBy = updatedBy;
		this.updatedAt = dateUpdated;
		this.userses = userses;
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

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	public District getDistrict() {
		return this.district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "abbreviation", length = 150)
	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Column(name = "description", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "partner_type", nullable = false, length = 50)
	public String getPartnerType() {
		return this.partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	@Column(name = "logo", length = 250)
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "created_by", nullable = false)
	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}


	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "partners")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

	public String getOfflineId() {
		return offlineId;
	}

	public void setOfflineId(String offlineId) {
		this.offlineId = offlineId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 19)
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", length = 19)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
