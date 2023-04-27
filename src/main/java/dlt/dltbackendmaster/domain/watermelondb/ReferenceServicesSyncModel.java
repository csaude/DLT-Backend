package dlt.dltbackendmaster.domain.watermelondb;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReferenceServicesSyncModel {

	private String id;
    private String reference_id;
    private Integer service_id;
    private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Africa/Maputo")
    private String date_created;
    private Integer status;
    private String _status;
    private String _changed;
    private Integer is_awaiting_sync;
    private String online_id;
    
	public ReferenceServicesSyncModel() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getIs_awaiting_sync() {
		return is_awaiting_sync;
	}

	public void setIs_awaiting_sync(Integer is_awaiting_sync) {
		this.is_awaiting_sync = is_awaiting_sync;
	}

	public String getOnline_id() {
		return online_id;
	}

	public void setOnline_id(String online_id) {
		this.online_id = online_id;
	}
}
