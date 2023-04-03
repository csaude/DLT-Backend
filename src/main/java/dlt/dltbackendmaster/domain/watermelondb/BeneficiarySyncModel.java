package dlt.dltbackendmaster.domain.watermelondb;

import java.util.Date;

public class BeneficiarySyncModel extends BasicLifeCycleSyncModel
{
    private String nui;
    private String surname;
    private String nick_name;
    private Integer organization_id;
    private Date date_of_birth;
    private Character gender;
    private String address;
    private String phone_number;
    private String e_mail;
    private Date enrollment_date;
    private Date date_created;
    private Date date_update;
    private Integer via;
    private Integer nationality;
    private Integer partner_id;
    private String entry_point;
    private Integer neighborhood_id;
    private Integer locality_id;
    private String locality_name;
    private Integer district_id;
    private String district_code;
    private Integer province_id;
    private Integer us_id;
    private String vblt_lives_with;
    private Byte vblt_is_orphan;
    private Byte vblt_is_student;
    private Integer vblt_school_grade;
    private String vblt_school_name;
    private Byte vblt_is_deficient;
    private String vblt_deficiency_type;
    private Byte vblt_married_before;
    private Byte vblt_pregnant_before;
    private Byte vblt_children;
    private Byte vblt_pregnant_or_breastfeeding;
    private String vblt_is_employed;
    private String vblt_tested_hiv;
    private Byte vblt_sexually_active;
    private Byte vblt_multiple_partners;
    private Byte vblt_is_migrant;
    private Byte vblt_trafficking_victim;
    private Byte vblt_sexual_exploitation;
    private String vblt_sexploitation_time;
    private Byte vblt_vbg_victim;
    private String vblt_vbg_type;
    private String vblt_vbg_time;
    private Byte vblt_alcohol_drugs_use;
    private Byte vblt_sti_history;
    private Byte vblt_sex_worker;
    private Byte vblt_house_sustainer;
    private String references_a;

    public BeneficiarySyncModel() {}

    public String getNui() {
        return nui;
    }

    public void setNui(String nui) {
        this.nui = nui;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Integer organization_id) {
        this.organization_id = organization_id;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getE_mail() {
        return e_mail;
    }
    
    public Date getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(Date enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_update() {
        return date_update;
    }

    public void setDate_update(Date date_update) {
        this.date_update = date_update;
    }
	
    public Integer getVia() {
        return via;
    }

    public void setVia(Integer via) {
        this.via = via;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public Integer getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public String getEntry_point() {
        return entry_point;
    }

    public void setEntry_point(String entry_point) {
        this.entry_point = entry_point;
    }

    public Integer getNeighborhood_id() {
        return neighborhood_id;
    }

    public void setNeighborhood_id(Integer neighborhood_id) {
        this.neighborhood_id = neighborhood_id;
    }

    public Integer getLocality_id() {
		return locality_id;
	}

	public void setLocality_id(Integer locality_id) {
		this.locality_id = locality_id;
	}

	public String getLocality_name() {
		return locality_name;
	}

	public void setLocality_name(String locality_name) {
		this.locality_name = locality_name;
	}
	
	public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }

    public Integer getUs_id() {
        return us_id;
    }

    public void setUs_id(Integer us_id) {
        this.us_id = us_id;
    }

	public String getVblt_lives_with() {
		return vblt_lives_with;
	}

	public void setVblt_lives_with(String vblt_lives_with) {
		this.vblt_lives_with = vblt_lives_with;
	}

	public Byte getVblt_is_orphan() {
		return vblt_is_orphan;
	}

	public void setVblt_is_orphan(Byte vblt_is_orphan) {
		this.vblt_is_orphan = vblt_is_orphan;
	}

	public Byte getVblt_is_student() {
		return vblt_is_student;
	}

	public void setVblt_is_student(Byte vblt_is_student) {
		this.vblt_is_student = vblt_is_student;
	}

	public Integer getVblt_school_grade() {
		return vblt_school_grade;
	}

	public void setVblt_school_grade(Integer vblt_school_grade) {
		this.vblt_school_grade = vblt_school_grade;
	}

	public String getVblt_school_name() {
		return vblt_school_name;
	}

	public void setVblt_school_name(String vblt_school_name) {
		this.vblt_school_name = vblt_school_name;
	}

	public Byte getVblt_is_deficient() {
		return vblt_is_deficient;
	}

	public void setVblt_is_deficient(Byte vblt_is_deficient) {
		this.vblt_is_deficient = vblt_is_deficient;
	}

	public String getVblt_deficiency_type() {
		return vblt_deficiency_type;
	}

	public void setVblt_deficiency_type(String vblt_deficiency_type) {
		this.vblt_deficiency_type = vblt_deficiency_type;
	}

	public Byte getVblt_married_before() {
		return vblt_married_before;
	}

	public void setVblt_married_before(Byte vblt_married_before) {
		this.vblt_married_before = vblt_married_before;
	}

	public Byte getVblt_pregnant_before() {
		return vblt_pregnant_before;
	}

	public void setVblt_pregnant_before(Byte vblt_pregnant_before) {
		this.vblt_pregnant_before = vblt_pregnant_before;
	}

	public Byte getVblt_children() {
		return vblt_children;
	}

	public void setVblt_children(Byte vblt_children) {
		this.vblt_children = vblt_children;
	}

	public Byte getVblt_pregnant_or_breastfeeding() {
		return vblt_pregnant_or_breastfeeding;
	}

	public void setVblt_pregnant_or_breastfeeding(Byte vblt_pregnant_or_breastfeeding) {
		this.vblt_pregnant_or_breastfeeding = vblt_pregnant_or_breastfeeding;
	}

	public String getVblt_is_employed() {
		return vblt_is_employed;
	}

	public void setVblt_is_employed(String vblt_is_employed) {
		this.vblt_is_employed = vblt_is_employed;
	}

	public String getVblt_tested_hiv() {
		return vblt_tested_hiv;
	}

	public void setVblt_tested_hiv(String vblt_tested_hiv) {
		this.vblt_tested_hiv = vblt_tested_hiv;
	}

	public Byte getVblt_sexually_active() {
		return vblt_sexually_active;
	}

	public void setVblt_sexually_active(Byte vblt_sexually_active) {
		this.vblt_sexually_active = vblt_sexually_active;
	}

	public Byte getVblt_multiple_partners() {
		return vblt_multiple_partners;
	}

	public void setVblt_multiple_partners(Byte vblt_multiple_partners) {
		this.vblt_multiple_partners = vblt_multiple_partners;
	}

	public Byte getVblt_is_migrant() {
		return vblt_is_migrant;
	}

	public void setVblt_is_migrant(Byte vblt_is_migrant) {
		this.vblt_is_migrant = vblt_is_migrant;
	}

	public Byte getVblt_trafficking_victim() {
		return vblt_trafficking_victim;
	}

	public void setVblt_trafficking_victim(Byte vblt_trafficking_victim) {
		this.vblt_trafficking_victim = vblt_trafficking_victim;
	}

	public Byte getVblt_sexual_exploitation() {
		return vblt_sexual_exploitation;
	}

	public void setVblt_sexual_exploitation(Byte vblt_sexual_exploitation) {
		this.vblt_sexual_exploitation = vblt_sexual_exploitation;
	}

	public String getVblt_sexploitation_time() {
		return vblt_sexploitation_time;
	}

	public void setVblt_sexploitation_time(String vblt_sexploitation_time) {
		this.vblt_sexploitation_time = vblt_sexploitation_time;
	}

	public Byte getVblt_vbg_victim() {
		return vblt_vbg_victim;
	}

	public void setVblt_vbg_victim(Byte vblt_vbg_victim) {
		this.vblt_vbg_victim = vblt_vbg_victim;
	}

	public String getVblt_vbg_type() {
		return vblt_vbg_type;
	}

	public void setVblt_vbg_type(String vblt_vbg_type) {
		this.vblt_vbg_type = vblt_vbg_type;
	}

	public String getVblt_vbg_time() {
		return vblt_vbg_time;
	}

	public void setVblt_vbg_time(String vblt_vbg_time) {
		this.vblt_vbg_time = vblt_vbg_time;
	}

	public Byte getVblt_alcohol_drugs_use() {
		return vblt_alcohol_drugs_use;
	}

	public void setVblt_alcohol_drugs_use(Byte vblt_alcohol_drugs_use) {
		this.vblt_alcohol_drugs_use = vblt_alcohol_drugs_use;
	}

	public Byte getVblt_sti_history() {
		return vblt_sti_history;
	}

	public void setVblt_sti_history(Byte vblt_sti_history) {
		this.vblt_sti_history = vblt_sti_history;
	}

	public Byte getVblt_sex_worker() {
		return vblt_sex_worker;
	}

	public void setVblt_sex_worker(Byte vblt_sex_worker) {
		this.vblt_sex_worker = vblt_sex_worker;
	}

	public Byte getVblt_house_sustainer() {
		return vblt_house_sustainer;
	}

	public void setVblt_house_sustainer(Byte vblt_house_sustainer) {
		this.vblt_house_sustainer = vblt_house_sustainer;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getReferences_a() {
		return references_a;
	}

	public void setReferences_a(String references_a) {
		this.references_a = references_a;
	}

}
