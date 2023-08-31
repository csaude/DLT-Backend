package dlt.dltbackendmaster.domain;
// Generated Jun 13, 2022, 9:37:47 AM by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BeneficiariesInterventionsId generated by hbm2java
 */
@Embeddable
public class ServiceAgebandId implements java.io.Serializable {

	private static final long serialVersionUID = -3312323271412669443L;
	private int serviceId;
	private int agebandId;

	public ServiceAgebandId() {
	}

	public ServiceAgebandId(int agebandId, int subServiceId) {
		this.agebandId = agebandId;
		this.serviceId = subServiceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ServiceAgebandId))
			return false;
		ServiceAgebandId castOther = (ServiceAgebandId) other;

		return (this.getAgebandId() == castOther.getAgebandId() && (this.getServiceId() == castOther.getServiceId()));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getAgebandId();
		result = 37 * result + this.getServiceId();
		return result;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + agebandId + "," + serviceId + "".toString();
	}

	@Column(name = "Service_id", nullable = false)
	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "age_band", nullable = false)
	public int getAgebandId() {
		return agebandId;
	}

	public void setAgebandId(int agebandId) {
		this.agebandId = agebandId;
	}

}