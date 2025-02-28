package dlt.dltbackendmaster.domain.watermelondb;

public class UserDetailsSyncModel {
	private String id;
	private String _status;
	private String _changed;
	private String districts;
	private String provinces;
	private String localities;
	private String uss;
	private String user_id;
	private String last_login_date;
	private String password_last_change_date;
	private String profile_id;
	private String entry_point;
	private String partner_id;
	private String next_clean_date;
	private String was_cleaned;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String get_status() {
		return _status;
	}

	public void set_status(String _status) {
		this._status = _status;
	}

	public String get_changed() {
		return _changed;
	}

	public void set_changed(String _changed) {
		this._changed = _changed;
	}

	public String getDistricts() {
		return districts;
	}

	public void setDistricts(String districts) {
		this.districts = districts;
	}

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getLocalities() {
		return localities;
	}

	public void setLocalities(String localities) {
		this.localities = localities;
	}

	public String getUss() {
		return uss;
	}

	public void setUss(String uss) {
		this.uss = uss;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(String last_login_date) {
		this.last_login_date = last_login_date;
	}

	public String getPassword_last_change_date() {
		return password_last_change_date;
	}

	public void setPassword_last_change_date(String password_last_change_date) {
		this.password_last_change_date = password_last_change_date;
	}

	public String getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(String profile_id) {
		this.profile_id = profile_id;
	}

	public String getEntry_point() {
		return entry_point;
	}

	public void setEntry_point(String entry_point) {
		this.entry_point = entry_point;
	}

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public String getNext_clean_date() {
		return next_clean_date;
	}

	public void setNext_clean_date(String next_clean_date) {
		this.next_clean_date = next_clean_date;
	}

	public String getWas_cleaned() {
		return was_cleaned;
	}

	public void setWas_cleaned(String was_cleaned) {
		this.was_cleaned = was_cleaned;
	}

	@Override
	public String toString() {
		return "UsersSyncModel [districts=" + districts + ", provinces=" + provinces + ", localities=" + localities
				+ ", uss=" + uss + ", user_id=" + user_id + ", last_login_date=" + last_login_date
				+ ", password_last_change_date=" + password_last_change_date + ", profile_id=" + profile_id
				+ ", entry_point=" + entry_point + ", partner_id=" + partner_id + ", next_clean_date=" + next_clean_date
				+ ", was_cleaned=" + was_cleaned + "]";
	}

}