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
    private int via;
    private Integer partner_id;
    private String entry_point;
    private Integer neighbourhood_id;
    private Integer us_id;
    private String vblt_lives_with;
    private int vblt_is_orphan;
    private int vblt_is_student;
    private Integer vblt_school_grade;
    private String vblt_school_name;
    private int vblt_is_deficient;
    private String vblt_deficiency_type;
    private int vblt_married_before;
    private int vblt_pregnant_before;
    private int vblt_children;
    private int vblt_pregnant_or_breastfeeding;
    private String vblt_is_employed;
    private String vblt_tested_hiv;
    private int vblt_sexually_active;
    private int vblt_multiple_partners;
    private int vblt_is_migrant;
    private int vblt_trafficking_victim;
    private int vblt_sexual_exploitation;
    private String vblt_sexploitation_time;
    private int vblt_vbg_victim;
    private String vblt_vbg_type;
    private String vblt_vbg_time;
    private int vblt_alcohol_drugs_use;
    private int vblt_sti_history;
    private int vblt_sex_worker;
    private int vblt_house_sustainer;

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

    public int getVia() {
        return via;
    }

    public void setVia(int via) {
        this.via = via;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public String getEntry_point() {
        return entry_point;
    }

    public void setEntry_point(String entry_point) {
        this.entry_point = entry_point;
    }

    public Integer getNeighbourhood_id() {
        return neighbourhood_id;
    }

    public void setNeighbourhood_id(Integer neighborhood_id) {
        this.neighbourhood_id = neighborhood_id;
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

	public int getVblt_is_orphan() {
		return vblt_is_orphan;
	}

	public void setVblt_is_orphan(int vblt_is_orphan) {
		this.vblt_is_orphan = vblt_is_orphan;
	}

	public int getVblt_is_student() {
		return vblt_is_student;
	}

	public void setVblt_is_student(int vblt_is_student) {
		this.vblt_is_student = vblt_is_student;
	}

	public int getVblt_school_grade() {
		return vblt_school_grade;
	}

	public void setVblt_school_grade(int vblt_school_grade) {
		this.vblt_school_grade = vblt_school_grade;
	}

	public String getVblt_school_name() {
		return vblt_school_name;
	}

	public void setVblt_school_name(String vblt_school_name) {
		this.vblt_school_name = vblt_school_name;
	}

	public int getVblt_is_deficient() {
		return vblt_is_deficient;
	}

	public void setVblt_is_deficient(int vblt_is_deficient) {
		this.vblt_is_deficient = vblt_is_deficient;
	}

	public String getVblt_deficiency_type() {
		return vblt_deficiency_type;
	}

	public void setVblt_deficiency_type(String vblt_deficiency_type) {
		this.vblt_deficiency_type = vblt_deficiency_type;
	}

	public int getVblt_married_before() {
		return vblt_married_before;
	}

	public void setVblt_married_before(int vblt_married_before) {
		this.vblt_married_before = vblt_married_before;
	}

	public int getVblt_pregnant_before() {
		return vblt_pregnant_before;
	}

	public void setVblt_pregnant_before(int vblt_pregnant_before) {
		this.vblt_pregnant_before = vblt_pregnant_before;
	}

	public int getVblt_children() {
		return vblt_children;
	}

	public void setVblt_children(int vblt_children) {
		this.vblt_children = vblt_children;
	}

	public int getVblt_pregnant_or_breastfeeding() {
		return vblt_pregnant_or_breastfeeding;
	}

	public void setVblt_pregnant_or_breastfeeding(int vblt_pregnant_or_breastfeeding) {
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

	public int getVblt_sexually_active() {
		return vblt_sexually_active;
	}

	public void setVblt_sexually_active(int vblt_sexually_active) {
		this.vblt_sexually_active = vblt_sexually_active;
	}

	public int getVblt_multiple_partners() {
		return vblt_multiple_partners;
	}

	public void setVblt_multiple_partners(int vblt_multiple_partners) {
		this.vblt_multiple_partners = vblt_multiple_partners;
	}

	public int getVblt_is_migrant() {
		return vblt_is_migrant;
	}

	public void setVblt_is_migrant(int vblt_is_migrant) {
		this.vblt_is_migrant = vblt_is_migrant;
	}

	public int getVblt_trafficking_victim() {
		return vblt_trafficking_victim;
	}

	public void setVblt_trafficking_victim(int vblt_trafficking_victim) {
		this.vblt_trafficking_victim = vblt_trafficking_victim;
	}

	public int getVblt_sexual_exploitation() {
		return vblt_sexual_exploitation;
	}

	public void setVblt_sexual_exploitation(int vblt_sexual_exploitation) {
		this.vblt_sexual_exploitation = vblt_sexual_exploitation;
	}

	public String getVblt_sexploitation_time() {
		return vblt_sexploitation_time;
	}

	public void setVblt_sexploitation_time(String vblt_sexploitation_time) {
		this.vblt_sexploitation_time = vblt_sexploitation_time;
	}

	public int getVblt_vbg_victim() {
		return vblt_vbg_victim;
	}

	public void setVblt_vbg_victim(int vblt_vbg_victim) {
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

	public int getVblt_alcohol_drugs_use() {
		return vblt_alcohol_drugs_use;
	}

	public void setVblt_alcohol_drugs_use(int vblt_alcohol_drugs_use) {
		this.vblt_alcohol_drugs_use = vblt_alcohol_drugs_use;
	}

	public int getVblt_sti_history() {
		return vblt_sti_history;
	}

	public void setVblt_sti_history(int vblt_sti_history) {
		this.vblt_sti_history = vblt_sti_history;
	}

	public int getVblt_sex_worker() {
		return vblt_sex_worker;
	}

	public void setVblt_sex_worker(int vblt_sex_worker) {
		this.vblt_sex_worker = vblt_sex_worker;
	}

	public int getVblt_house_sustainer() {
		return vblt_house_sustainer;
	}

	public void setVblt_house_sustainer(int vblt_house_sustainer) {
		this.vblt_house_sustainer = vblt_house_sustainer;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

}
