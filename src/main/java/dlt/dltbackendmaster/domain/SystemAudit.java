package dlt.dltbackendmaster.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * SystemAudit for audit Service Provision
 */
@Entity
@Table(name = "system_audit", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "SystemAudit.findBybeneficiary", query = "SELECT sa FROM  SystemAudit sa "
		+ "left join fetch sa.beneficiariesIntervention bi " + "where bi.beneficiarie = :beneficiarie"), })
public class SystemAudit implements java.io.Serializable {
	private static final long serialVersionUID = 5523319676856300152L;
	
	private BeneficiariesInterventions beneficiariesIntervention;
	
	private SubServices fromSubService;
	private SubServices toSubService;
	private Date fromDate;
	private Date toDate;
	
	private Date fromProvisionDate;
	private Date toProvisionDate;
	
	private Us fromUS;
	private Us toUS;
	
	private int fromEntryPoint;
	private int toEntryPoint;
	private String fromProvider;
	private String toProvider;
	private String fromRemarks;
	private String toRemarks;
    
	private Integer updatedBy;
	private Date dateUpdated;
	public BeneficiariesInterventions getBeneficiariesIntervention() {
		return beneficiariesIntervention;
	}
	public void setBeneficiariesIntervention(BeneficiariesInterventions beneficiariesIntervention) {
		this.beneficiariesIntervention = beneficiariesIntervention;
	}
	public SubServices getFromSubService() {
		return fromSubService;
	}
	public void setFromSubService(SubServices fromSubService) {
		this.fromSubService = fromSubService;
	}
	public SubServices getToSubService() {
		return toSubService;
	}
	public void setToSubService(SubServices toSubService) {
		this.toSubService = toSubService;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getFromProvisionDate() {
		return fromProvisionDate;
	}
	public void setFromProvisionDate(Date fromProvisionDate) {
		this.fromProvisionDate = fromProvisionDate;
	}
	public Date getToProvisionDate() {
		return toProvisionDate;
	}
	public void setToProvisionDate(Date toProvisionDate) {
		this.toProvisionDate = toProvisionDate;
	}
	public Us getFromUS() {
		return fromUS;
	}
	public void setFromUS(Us fromUS) {
		this.fromUS = fromUS;
	}
	public Us getToUS() {
		return toUS;
	}
	public void setToUS(Us toUS) {
		this.toUS = toUS;
	}
	public int getFromEntryPoint() {
		return fromEntryPoint;
	}
	public void setFromEntryPoint(int fromEntryPoint) {
		this.fromEntryPoint = fromEntryPoint;
	}
	public int getToEntryPoint() {
		return toEntryPoint;
	}
	public void setToEntryPoint(int toEntryPoint) {
		this.toEntryPoint = toEntryPoint;
	}
	public String getFromProvider() {
		return fromProvider;
	}
	public void setFromProvider(String fromProvider) {
		this.fromProvider = fromProvider;
	}
	public String getToProvider() {
		return toProvider;
	}
	public void setToProvider(String toProvider) {
		this.toProvider = toProvider;
	}
	public String getFromRemarks() {
		return fromRemarks;
	}
	public void setFromRemarks(String fromRemarks) {
		this.fromRemarks = fromRemarks;
	}
	public String getToRemarks() {
		return toRemarks;
	}
	public void setToRemarks(String toRemarks) {
		this.toRemarks = toRemarks;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	
}
