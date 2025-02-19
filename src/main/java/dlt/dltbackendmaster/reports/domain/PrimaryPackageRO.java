package dlt.dltbackendmaster.reports.domain;

import static dlt.dltbackendmaster.reports.utils.ReportsConstants.*;

public class PrimaryPackageRO {

	private Integer beneficiaryId;
	private String nui;
	private Integer age;
	private String ageBand;
	private String province;
	private String district;
	private String servicePackage;
	private Integer vulnerabilitiesCount;
	private String completedSocialAsset;
	private String completedSAAJ;
	private String completedFinancialLiteracy;
	private String completedHivTesting;
	private String completedCondoms;

	public PrimaryPackageRO(Integer beneficiaryId, Integer age, String ageBand, Integer vulnerabilities,
			String servicePackage) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.age = age;
		this.ageBand = ageBand;
		this.vulnerabilitiesCount = vulnerabilities;
		this.servicePackage = servicePackage;
		this.completedSocialAsset = COMPLETION_STATUSES[0];
		this.completedSAAJ = COMPLETION_STATUSES[0];
		this.completedFinancialLiteracy = COMPLETION_STATUSES[0];
		this.completedHivTesting = COMPLETION_STATUSES[0];
		this.completedCondoms = COMPLETION_STATUSES[0];
	}

	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getNui() {
		return nui;
	}

	public void setNui(String nui) {
		this.nui = nui;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAgeBand() {
		return ageBand;
	}

	public void setAgeBand(String ageBand) {
		this.ageBand = ageBand;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public Integer getVulnerabilitiesCount() {
		return vulnerabilitiesCount;
	}

	public void setVulnerabilitiesCount(Integer vulnerabilitiesCount) {
		this.vulnerabilitiesCount = vulnerabilitiesCount;
	}

	public String getCompletedSocialAsset() {
		return completedSocialAsset;
	}

	public void setCompletedSocialAsset(String completedSocialAsset) {
		this.completedSocialAsset = completedSocialAsset;
	}

	public String getCompletedSAAJ() {
		return completedSAAJ;
	}

	public void setCompletedSAAJ(String completedSAAJ) {
		this.completedSAAJ = completedSAAJ;
	}

	public String getCompletedFinancialLiteracy() {
		return completedFinancialLiteracy;
	}

	public void setCompletedFinancialLiteracy(String completedFinancialLiteracy) {
		this.completedFinancialLiteracy = completedFinancialLiteracy;
	}

	public String getCompletedHivTesting() {
		return completedHivTesting;
	}

	public void setCompletedHivTesting(String completedHivTesting) {
		this.completedHivTesting = completedHivTesting;
	}

	public String getCompletedCondoms() {
		return completedCondoms;
	}

	public void setCompletedCondoms(String completedCondoms) {
		this.completedCondoms = completedCondoms;
	}

}
