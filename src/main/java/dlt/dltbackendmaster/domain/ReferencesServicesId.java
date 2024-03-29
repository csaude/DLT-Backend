package dlt.dltbackendmaster.domain;
// Generated Jun 13, 2022, 4:04:49 PM by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ReferencesServicesId generated by hbm2java
 */
@Embeddable
public class ReferencesServicesId implements java.io.Serializable {

	private static final long serialVersionUID = 6987733917961073464L;
	private int referenceId;
	private int serviceId;

	public ReferencesServicesId() {
	}

	public ReferencesServicesId(int referenceId, int serviceId) {
		this.referenceId = referenceId;
		this.serviceId = serviceId;
	}

	@Column(name = "reference_id", nullable = false)
	public int getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	@Column(name = "service_id", nullable = false)
	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+referenceId+","+serviceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReferencesServicesId))
			return false;
		ReferencesServicesId castOther = (ReferencesServicesId) other;

		return (this.getReferenceId() == castOther.getReferenceId())
				&& (this.getServiceId() == castOther.getServiceId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getReferenceId();
		result = 37 * result + this.getServiceId();
		return result;
	}

}
