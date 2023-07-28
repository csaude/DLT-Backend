package dlt.dltbackendmaster.domain.watermelondb;

public class UsersSyncModel {
	private String id;
	private String _status;
	private String _changed;
	private String name;
	private String surname;
	private String phone_number;
	private String email;
	private String username;
	private String password;
	private String entry_point;
	private String status;
	private String localities_ids;
	private Integer partner_id;
	private Integer profile_id;
	private String us_ids;
	private Integer online_id;
	private String organization_name;
	private String password_last_change_date;
	private Integer is_awaiting_sync;

	public UsersSyncModel() {
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEntry_point() {
		return entry_point;
	}

	public void setEntry_point(String entry_point) {
		this.entry_point = entry_point;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocalities_ids() {
		return localities_ids;
	}

	public void setLocalities_ids(String localities_ids) {
		this.localities_ids = localities_ids;
	}

	public Integer getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Integer partner_id) {
		this.partner_id = partner_id;
	}

	public Integer getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(Integer profile_id) {
		this.profile_id = profile_id;
	}

	public String getUs_ids() {
		return us_ids;
	}

	public void setUs_ids(String us_id) {
		this.us_ids = us_id;
	}

	public Integer getOnline_id() {
		return online_id;
	}

	public void setOnline_id(Integer online_id) {
		this.online_id = online_id;
	}
	
	public String toString() {
		String object = ""
				+ "id: " + id + "\n"
				+ "name: " + name + "\n"
				+ "surname: " + surname + "\n"
				+ "phone_number: " + phone_number + "\n"
				+ "email: " + email + "\n"
				+ "username: " + username + "\n"
				+ "password: " + password + "\n"
				+ "entry_point: " + entry_point + "\n"
				+ "status: " + status + "\n"
				+ "localities_ids: " + localities_ids + "\n"
				+ "partner_id" + partner_id + "\n"
				+ "profile_id: " + profile_id + "\n"
				+ "us_id: " + us_ids + "\n"
				+ "password_last_change_date: "+password_last_change_date+ "\n"
				+ "online_id: " + online_id + "\n";
		return object;
	}

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getPassword_last_change_date() {
		return password_last_change_date;
	}

	public void setPassword_last_change_date(String password_last_change_date) {
		this.password_last_change_date = password_last_change_date;
	}

	public Integer getIs_awaiting_sync() {
		return is_awaiting_sync;
	}

	public void setIs_awaiting_sync(Integer is_awaiting_sync) {
		this.is_awaiting_sync = is_awaiting_sync;
	}

}
