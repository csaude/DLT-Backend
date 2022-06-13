package dlt.dltbackendmaster.domain;

//import java.util.Date;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dlt.dltbackendmaster.serializers.BeneficiarySerializer;
import dlt.dltbackendmaster.serializers.UsersSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "references", catalog = "dreams_db")

public class References implements Serializable
{

	private Integer id;
	private Beneficiary beneficiary;
	private Integer referTo;
	private Users notifyTo;
	private Integer createdBy;
	private Integer updatedBy;
//	
	private String referenceNote;
	private String description;
	private String bookNumber;
	private String referenceCode;
	private String serviceType;
	private String remarks;
	private Integer statusRef;
	private Integer status;
	private Integer cancelReason;
	private String otherReason;
	private Date dateCreated;
	private Date dateUpdated;
	
	public References() {}	
	
	public References(Beneficiary beneficiary, Integer referTo, String referenceNote, String description, String bookNumber, 
			String referenceCode, String serviceType, String remarks, Integer statusRef, Integer status, Integer cancelReason,
			String otherReason, Date dateCreated, Date dateUpdated) {
		this.beneficiary = beneficiary;
		this.referTo = referTo;
		this.referenceNote = referenceNote;
		this.description = description;
		this.bookNumber = bookNumber;
		this.referenceCode = referenceCode;
		this.serviceType = serviceType;
		this.remarks = remarks;
		this.statusRef = statusRef;
		this.status = status;
		this.cancelReason = cancelReason;
		this.otherReason = otherReason;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
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
	
//	@JsonIgnore 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "beneficiary_id")
    @JsonProperty("beneficiary")
    @JsonSerialize(using = BeneficiarySerializer.class)
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notify_to")
    @JsonProperty("users")
    @JsonSerialize(using = UsersSerializer.class)
    public Users getNotifyTo() {
        return notifyTo;
    }

    public void setNotifyTo(Users notifyTo) {
        this.notifyTo = notifyTo;
    }

    @Column(name = "reference_note")
	public String getReferenceNote() {
		return referenceNote;

	}

	public void setReferenceNote(String referenceNote) {
		this.referenceNote = referenceNote;
	}

    @Column(name = "description")
	public String getDescription() {
		return description;

	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Column(name = "book_number")
	public String getBookNumber() {
		return bookNumber;

	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

    @Column(name = "reference_code")
	public String getReferenceCode() {
		return referenceCode;

	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

    @Column(name = "service_type")
	public String getServiceType() {
		return serviceType;

	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

    @Column(name = "remarks")
	public String getRemarks() {
		return remarks;

	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    @Column(name = "status_ref")
	public Integer getStatusRef() {
		return statusRef;
	}

	public void setStatusRef(Integer statusRef) {
		this.statusRef = statusRef;
	}

    @Column(name = "status")
	public Integer getStatus() {
		return status;

	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    @Column(name = "cancel_reason")
	public Integer getCancelReason() {
		return cancelReason;

	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

    @Column(name = "other_reason")
	public String getOtherReason() {
		return otherReason;

	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

    @Column(name = "date_created")
	public Date getDateCreated() {
		return dateCreated;

	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

    @Column(name = "date_updated")
	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

    @Column(name = "refer_to")
	public Integer getReferTo() {
		return referTo;
	}

	public void setReferTo(Integer referTo) {
		this.referTo = referTo;
	}

    @Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
