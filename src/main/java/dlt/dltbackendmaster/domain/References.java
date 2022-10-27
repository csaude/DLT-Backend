package dlt.dltbackendmaster.domain;
// Generated Jun 13, 2022, 4:04:49 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.domain.watermelondb.ReferenceSyncModel;
import dlt.dltbackendmaster.serializers.BeneficiarySerializer;
import dlt.dltbackendmaster.serializers.UsSerializer;
import dlt.dltbackendmaster.serializers.UsersSerializer;

/**
 * References generated by hbm2java
 */
@Entity
@Table(name = "references", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "References.findAll", query = "SELECT r FROM References r"),
		@NamedQuery(name = "References.findAllByUser", query = "SELECT r FROM References r where r.userCreated like :userCreated"),
		@NamedQuery(name = "References.findAllByUserPermission", query = "SELECT r FROM References r where r.userCreated = cast(:userId as string) or r.users.id = : userId or r.referredBy.id = : userId"),
		@NamedQuery(name = "References.findByDateCreated", query = "SELECT b from References b where b.dateUpdated is null and b.dateCreated > :lastpulledat"
		/* "SELECT r FROM References r WHERE r.dateCreated = :lastpulledat" */),
		@NamedQuery(name = "References.findByDateUpdated", query = "SELECT b from References b where (b.dateUpdated >= :lastpulledat) or (b.dateUpdated >= :lastpulledat and b.dateCreated = b.dateUpdated)"
		/* "SELECT r FROM References r WHERE r.dateUpdated = :lastpulledat" */) })
public class References implements java.io.Serializable {

	private static final long serialVersionUID = -6756894395137677466L;

	private Integer id;
	private Beneficiaries beneficiaries;
	private Users referredBy;
	private Users users;
	private String referenceNote;
	private String description;
	private String referTo;
	private String bookNumber;
	private String referenceCode;
	private String serviceType;
	private String remarks;
	private Integer status;
	private Us us;
	private Integer cancelReason;
	private String otherReason;
	// private int createdBy;
	private String userCreated;
	private Date dateCreated;
	private Integer updatedBy;
	private Date dateUpdated;
	private String offlineId;
	private Set<ReferencesServices> referencesServiceses = new HashSet<ReferencesServices>(0);

	public References() {
	}

	public References(Beneficiaries beneficiaries, Users referredBy, Users users, String referTo, Us us,
			String userCreated, Date dateCreated) {
		this.beneficiaries = beneficiaries;
		this.referredBy = referredBy;
		this.users = users;
		this.referTo = referTo;
		this.us = us;
		this.userCreated = userCreated;
		this.dateCreated = dateCreated;
	}

	public References(Beneficiaries beneficiaries, Users referredBy, Users users, String referenceNote,
			String description, String referTo, String bookNumber, String referenceCode, String serviceType,
			String remarks, Integer status, Us us, Integer cancelReason, String otherReason, String userCreated,
			Date dateCreated, Integer updatedBy, Date dateUpdated, Set<ReferencesServices> referencesServiceses) {
		this.beneficiaries = beneficiaries;
		this.referredBy = referredBy;
		this.users = users;
		this.referenceNote = referenceNote;
		this.description = description;
		this.referTo = referTo;
		this.bookNumber = bookNumber;
		this.referenceCode = referenceCode;
		this.serviceType = serviceType;
		this.remarks = remarks;
		this.status = status;
		this.us = us;
		this.cancelReason = cancelReason;
		this.otherReason = otherReason;
		this.userCreated = userCreated;
		this.dateCreated = dateCreated;
		this.updatedBy = updatedBy;
		this.dateUpdated = dateUpdated;
		this.referencesServiceses = referencesServiceses;
	}

	public References(ReferenceSyncModel model, String timestamp) {
		Long t = Long.valueOf(timestamp);
		Date regDate = new Date(t);
		this.offlineId = model.getId();
		this.beneficiaries = new Beneficiaries();
		this.beneficiaries.setId(model.getBeneficiary_id());
		this.bookNumber = model.getBook_number();
		this.description = model.getDescription();
		this.referenceCode = model.getReference_code();
		this.referenceNote = model.getReference_note();
		this.serviceType = model.getService_type();
		this.remarks = model.getRemarks();
		this.referTo = model.getRefer_to();
		this.users = new Users();
		this.users.setId(model.getNotify_to());
		this.referredBy = new Users();
		String createdBy = model.getUser_created();
		this.getReferredBy().setId(Integer.valueOf(createdBy));
		this.status = model.getStatus();
		this.cancelReason = model.getCancel_reason();
		this.otherReason = model.getOther_reason();
		this.userCreated = createdBy;
		this.dateCreated = regDate;
		this.dateUpdated = regDate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "beneficiary_id", nullable = false)
	@JsonProperty("beneficiaries")
	@JsonSerialize(using = BeneficiarySerializer.class)
	public Beneficiaries getBeneficiaries() {
		return this.beneficiaries;
	}

	public void setBeneficiaries(Beneficiaries beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referred_by", nullable = false)
	@JsonProperty("referredBy")
	@JsonSerialize(using = UsersSerializer.class)
	public Users getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(Users referredBy) {
		this.referredBy = referredBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notify_to", nullable = false)
	@JsonProperty("users")
	@JsonSerialize(using = UsersSerializer.class)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "reference_note", length = 50)
	public String getReferenceNote() {
		return this.referenceNote;
	}

	public void setReferenceNote(String referenceNote) {
		this.referenceNote = referenceNote;
	}

	@Column(name = "description", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "refer_to", nullable = false, length = 50)
	public String getReferTo() {
		return this.referTo;
	}

	public void setReferTo(String referTo) {
		this.referTo = referTo;
	}

	@Column(name = "book_number", length = 50)
	public String getBookNumber() {
		return this.bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

	@Column(name = "reference_code", length = 50)
	public String getReferenceCode() {
		return this.referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	@Column(name = "service_type", length = 50)
	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Column(name = "remarks", length = 250)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "us_id", nullable = false)
	@JsonProperty("us")
	@JsonSerialize(using = UsSerializer.class)
	public Us getUs() {
		return us;
	}

	public void setUs(Us us) {
		this.us = us;
	}

	@Column(name = "cancel_reason")
	public Integer getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name = "other_reason", length = 150)
	public String getOtherReason() {
		return this.otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	// @Column(name = "created_by", nullable = false)
	@Column(name = "user_created", length = 45)
	public String getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
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

	@Column(name = "offline_id", length = 45)
	public String getOfflineId() {
		return this.offlineId;
	}

	public void setOfflineId(String offlineId) {
		this.offlineId = offlineId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "references")
	public Set<ReferencesServices> getReferencesServiceses() {
		return this.referencesServiceses;
	}

	public void setReferencesServiceses(Set<ReferencesServices> referencesServiceses) {
		this.referencesServiceses = referencesServiceses;
	}

	public ObjectNode toObjectNode(String lastPulledAt) {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		ObjectNode reference = mapper.createObjectNode();

		if (offlineId != null) {
			reference.put("id", offlineId);
		} else {
			reference.put("id", id);
		}

		if (dateUpdated == null || dateUpdated.after(dateCreated) || lastPulledAt == null
				|| lastPulledAt.equals("null")) {
			reference.put("beneficiary_id", beneficiaries.getId());
			reference.put("referred_by", referredBy.getId());
			reference.put("notify_to", users.getId());
			reference.put("reference_note", referenceNote);
			reference.put("description", description);
			reference.put("refer_to", referTo);
			reference.put("book_number", bookNumber);
			reference.put("reference_code", referenceCode);
			reference.put("service_type", serviceType);
			reference.put("remarks", remarks);
			reference.put("status", status);
			reference.put("us_id", us.getId());
			reference.put("cancel_reason", cancelReason);
			reference.put("other_reason", otherReason);
			reference.put("online_id", id);
			reference.put("user_created", userCreated);
			reference.put("is_awaiting_sync", 0); // flag to control if reference is synced in mobile
			reference.put("date_created", dateFormat.format(dateCreated));
		} else { // ensure online_id is updated first
			reference.put("is_awaiting_sync", 0); // flag to control if reference is synced in mobile
			reference.put("beneficiary_id", beneficiaries.getId());
			reference.put("online_id", id);
		}

		return reference;
	}

	public void update(ReferenceSyncModel model, String timestamp) throws ParseException {
		Long t = Long.valueOf(timestamp);

		this.offlineId = model.getId();
		this.dateUpdated = new Date(t);
		this.beneficiaries.setId(model.getBeneficiary_id());
		this.bookNumber = model.getBook_number();
		this.description = model.getDescription();
		this.referenceCode = model.getReference_code();
		this.referenceNote = model.getReference_note();
		this.serviceType = model.getService_type();
		this.remarks = model.getRemarks();
		this.referTo = model.getRefer_to();
		this.users.setId(model.getNotify_to());
		this.referredBy.setId(model.getReferred_by());
		this.status = model.getStatus();
		this.us.setId(model.getUs_id());
		this.cancelReason = model.getCancel_reason();
		this.otherReason = model.getOther_reason();
		this.userCreated = model.getUser_created();
		this.dateCreated = model.getDate_created();
	}

}
