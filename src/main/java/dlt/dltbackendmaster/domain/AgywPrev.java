package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import dlt.dltbackendmaster.reports.queries.AgywPrevQueries;

/**
 * Entidade ligada à view de relatórios
 * 
 * @author Hamilton Mutaquiha
 *
 */
@Entity
@Table(name = "agyw_prev_mview", catalog = "dreams_db")
@NamedNativeQueries({
		@NamedNativeQuery(name = "AgywPrev.findAll", query = "SELECT * FROM agyw_prev_mview", resultClass = AgywPrev.class),
		@NamedNativeQuery(name = "AgywPrev.findByDistricts", query = AgywPrevQueries.AGYW_PREV, resultClass = AgywPrev.class),
		@NamedNativeQuery(name = "AgywPrev.findSimplifiedByDistricts", query = AgywPrevQueries.SIMPLIFIED_AGYW_PREV, resultClass = AgywPrev.class), 
		@NamedNativeQuery(name = "AgywPrev.findByNewlyEnrolledAgywAndServices", query = AgywPrevQueries.NEWLY_ENROLLED_AGYW_AND_SERVICES), 
		@NamedNativeQuery(name = "AgywPrev.findByNewlyEnrolledAgywAndServicesSummary", query = AgywPrevQueries.NEWLY_ENROLLED_AGYW_AND_SERVICES_SUMMARY),
		@NamedNativeQuery(name = "AgywPrev.findByBeneficiariesVulnerabilitiesAndServices", query = AgywPrevQueries.VULNERABILITIES_AND_SERVICES), 
		@NamedNativeQuery(name = "AgywPrev.findByBeneficiariesVulnerabilitiesAndServicesSummary", query = AgywPrevQueries.VULNERABILITIES_AND_SERVICES_SUMMARY),
		})
public class AgywPrev implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer beneficiary_id;
	private Integer current_age;
	private Date date_of_birth;
	private Integer district_id;
	private Integer vblt_is_student;
	private Integer vblt_sexually_active;
	private Date date_created;
	private Date enrollment_date;
	private Integer current_age_band;
	private Integer vulnerabilities;
	private Integer mandatory_social_assets;
	private Integer other_social_assets;
	private Integer old_social_assets;
	private Integer saaj_educational_sessions;
	private Integer hiv_testing;
	private Integer financial_literacy;
	private Integer condoms;
	private Integer contraception;
	private Integer hiv_gbv_sessions;
	private Integer hiv_gbv_sessions_prep;
	private Integer hiv_sessions;
	private Integer gbv_sessions;
	private Integer school_allowance;
	private Integer post_violence_care_us;
	private Integer post_violence_care_us_others;
	private Integer post_violence_care_comunity;
	private Integer post_violence_care_comunity_others;
	private Integer other_saaj_services;
	private Integer social_assets_15_plus;
	private Integer social_economics_approaches;
	private Integer disag_social_economics_approaches;
	private Integer prep;
	private Integer girl_violence_prevention;
	private Integer student_vilence_prevention;
	private Integer violence_prevention_15_plus;
	private Integer financial_literacy_aflatoun;
	private Integer financial_literacy_aflateen;
	private Date approaches_date;
	private Date intervention_date;

	public AgywPrev() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "beneficiary_id", nullable = false)
	public Integer getBeneficiary_id() {
		return beneficiary_id;
	}

	public void setBeneficiary_id(Integer beneficiary_id) {
		this.beneficiary_id = beneficiary_id;
	}

	@Column(name = "current_age")
	public Integer getCurrent_age() {
		return current_age;
	}

	public void setCurrent_age(Integer current_age) {
		this.current_age = current_age;
	}

	@Column(name = "date_of_birth")
	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	@Column(name = "district_id", nullable = false)
	public Integer getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(Integer district_id) {
		this.district_id = district_id;
	}

	@Column(name = "vblt_is_student", nullable = false)
	public Integer getVblt_is_student() {
		return vblt_is_student;
	}

	public void setVblt_is_student(Integer vblt_is_student) {
		this.vblt_is_student = vblt_is_student;
	}

	@Column(name = "vblt_sexually_active", nullable = false)
	public Integer getVblt_sexually_active() {
		return vblt_sexually_active;
	}

	public void setVblt_sexually_active(Integer vblt_sexually_active) {
		this.vblt_sexually_active = vblt_sexually_active;
	}

	@Column(name = "date_created")
	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	@Column(name = "enrollment_date")
	public Date getEnrollment_date() {
		return enrollment_date;
	}

	public void setEnrollment_date(Date enrollment_date) {
		this.enrollment_date = enrollment_date;
	}

	@Column(name = "current_age_band")
	public Integer getCurrent_age_band() {
		return current_age_band;
	}

	public void setCurrent_age_band(Integer current_age_band) {
		this.current_age_band = current_age_band;
	}

	public Integer getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(Integer vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public Integer getMandatory_social_assets() {
		return mandatory_social_assets;
	}

	public void setMandatory_social_assets(Integer mandatory_social_assets) {
		this.mandatory_social_assets = mandatory_social_assets;
	}

	public Integer getOther_social_assets() {
		return other_social_assets;
	}

	public void setOther_social_assets(Integer other_social_assets) {
		this.other_social_assets = other_social_assets;
	}

	public Integer getOld_social_assets() {
		return old_social_assets;
	}

	public void setOld_social_assets(Integer old_social_assets) {
		this.old_social_assets = old_social_assets;
	}

	public Integer getSaaj_educational_sessions() {
		return saaj_educational_sessions;
	}

	public void setSaaj_educational_sessions(Integer saaj_educational_sessions) {
		this.saaj_educational_sessions = saaj_educational_sessions;
	}

	public Integer getHiv_testing() {
		return hiv_testing;
	}

	public void setHiv_testing(Integer hiv_testing) {
		this.hiv_testing = hiv_testing;
	}

	public Integer getFinancial_literacy() {
		return financial_literacy;
	}

	public void setFinancial_literacy(Integer financial_literacy) {
		this.financial_literacy = financial_literacy;
	}

	public Integer getCondoms() {
		return condoms;
	}

	public void setCondoms(Integer condoms) {
		this.condoms = condoms;
	}

	public Integer getContraception() {
		return contraception;
	}

	public void setContraception(Integer contraception) {
		this.contraception = contraception;
	}

	public Integer getHiv_gbv_sessions() {
		return hiv_gbv_sessions;
	}

	public void setHiv_gbv_sessions(Integer hiv_gbv_sessions) {
		this.hiv_gbv_sessions = hiv_gbv_sessions;
	}

	public Integer getHiv_gbv_sessions_prep() {
		return hiv_gbv_sessions_prep;
	}

	public void setHiv_gbv_sessions_prep(Integer hiv_gbv_sessions_prep) {
		this.hiv_gbv_sessions_prep = hiv_gbv_sessions_prep;
	}

	public Integer getHiv_sessions() {
		return hiv_sessions;
	}

	public void setHiv_sessions(Integer hiv_sessions) {
		this.hiv_sessions = hiv_sessions;
	}

	public Integer getGbv_sessions() {
		return gbv_sessions;
	}

	public void setGbv_sessions(Integer gbv_sessions) {
		this.gbv_sessions = gbv_sessions;
	}

	public Integer getSchool_allowance() {
		return school_allowance;
	}

	public void setSchool_allowance(Integer school_allowance) {
		this.school_allowance = school_allowance;
	}

	public Integer getPost_violence_care_us() {
		return post_violence_care_us;
	}

	public void setPost_violence_care_us(Integer post_violence_care_us) {
		this.post_violence_care_us = post_violence_care_us;
	}

	public Integer getPost_violence_care_us_others() {
		return post_violence_care_us_others;
	}

	public void setPost_violence_care_us_others(Integer post_violence_care_us_others) {
		this.post_violence_care_us_others = post_violence_care_us_others;
	}

	public Integer getPost_violence_care_comunity() {
		return post_violence_care_comunity;
	}

	public void setPost_violence_care_comunity(Integer post_violence_care_comunity) {
		this.post_violence_care_comunity = post_violence_care_comunity;
	}

	public Integer getPost_violence_care_comunity_others() {
		return post_violence_care_comunity_others;
	}

	public void setPost_violence_care_comunity_others(Integer post_violence_care_comunity_others) {
		this.post_violence_care_comunity_others = post_violence_care_comunity_others;
	}

	public Integer getOther_saaj_services() {
		return other_saaj_services;
	}

	public void setOther_saaj_services(Integer other_saaj_services) {
		this.other_saaj_services = other_saaj_services;
	}

	public Integer getSocial_assets_15_plus() {
		return social_assets_15_plus;
	}

	public void setSocial_assets_15_plus(Integer social_assets_15_plus) {
		this.social_assets_15_plus = social_assets_15_plus;
	}

	public Integer getSocial_economics_approaches() {
		return social_economics_approaches;
	}

	public void setSocial_economics_approaches(Integer social_economics_approaches) {
		this.social_economics_approaches = social_economics_approaches;
	}

	public Integer getDisag_social_economics_approaches() {
		return disag_social_economics_approaches;
	}

	public void setDisag_social_economics_approaches(Integer disag_social_economics_approaches) {
		this.disag_social_economics_approaches = disag_social_economics_approaches;
	}

	public Integer getPrep() {
		return prep;
	}

	public void setPrep(Integer prep) {
		this.prep = prep;
	}

	public Integer getGirl_violence_prevention() {
		return girl_violence_prevention;
	}

	public void setGirl_violence_prevention(Integer girl_violence_prevention) {
		this.girl_violence_prevention = girl_violence_prevention;
	}

	public Integer getStudent_vilence_prevention() {
		return student_vilence_prevention;
	}

	public void setStudent_vilence_prevention(Integer student_vilence_prevention) {
		this.student_vilence_prevention = student_vilence_prevention;
	}

	public Integer getViolence_prevention_15_plus() {
		return violence_prevention_15_plus;
	}

	public void setViolence_prevention_15_plus(Integer violence_prevention_15_plus) {
		this.violence_prevention_15_plus = violence_prevention_15_plus;
	}

	public Integer getFinancial_literacy_aflatoun() {
		return financial_literacy_aflatoun;
	}

	public void setFinancial_literacy_aflatoun(Integer financial_literacy_aflatoun) {
		this.financial_literacy_aflatoun = financial_literacy_aflatoun;
	}

	public Integer getFinancial_literacy_aflateen() {
		return financial_literacy_aflateen;
	}

	public void setFinancial_literacy_aflateen(Integer financial_literacy_aflateen) {
		this.financial_literacy_aflateen = financial_literacy_aflateen;
	}

	public Date getApproaches_date() {
		return approaches_date;
	}

	public void setApproaches_date(Date approaches_date) {
		this.approaches_date = approaches_date;
	}

	@Column(name = "intervention_date")
	public Date getIntervention_date() {
		return intervention_date;
	}

	public void setIntervention_date(Date intervention_date) {
		this.intervention_date = intervention_date;
	}

}
