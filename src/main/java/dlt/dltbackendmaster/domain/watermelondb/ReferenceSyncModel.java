package dlt.dltbackendmaster.domain.watermelondb;
import java.util.Date;

public class ReferenceSyncModel{
	
	private String id;
	private Integer beneficiary_id;
	private String refer_to;
	private Integer notify_to;
	private String reference_note;
	private String description;
	private String book_number;
	private String reference_code;
	private String service_type;
	private String remarks;
	private Integer status_ref;
	private Integer cancel_reason;
	private String other_reason;
	private String user_created;
	private Date date_created;
    private Integer status;
    private String _status;
    private String _changed;
    private Integer is_awaiting_sync;
    private Integer online_id;
	
	public ReferenceSyncModel() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getBeneficiary_id() {
		return beneficiary_id;
	}
	public void setBeneficiary_id(Integer beneficiary_id) {
		this.beneficiary_id = beneficiary_id;
	}
	public String getRefer_to() {
		return refer_to;
	}
	public void setRefer_to(String refer_to) {
		this.refer_to = refer_to;
	}
	public Integer getNotify_to() {
		return notify_to;
	}
	public void setNotify_to(Integer notify_to) {
		this.notify_to = notify_to;
	}
	public String getReference_note() {
		return reference_note;
	}
	public void setReference_note(String reference_note) {
		this.reference_note = reference_note;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBook_number() {
		return book_number;
	}
	public void setBook_number(String book_number) {
		this.book_number = book_number;
	}
	public String getReference_code() {
		return reference_code;
	}
	public void setReference_code(String reference_code) {
		this.reference_code = reference_code;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getStatus_ref() {
		return status_ref;
	}
	public void setStatus_ref(Integer status_ref) {
		this.status_ref = status_ref;
	}
	
	public Integer getCancel_reason() {
		return cancel_reason;
	}
	public void setCancel_reason(Integer cancel_reason) {
		this.cancel_reason = cancel_reason;
	}
	public String getOther_reason() {
		return other_reason;
	}
	public void setOther_reason(String other_reason) {
		this.other_reason = other_reason;
	}
	
	public String getUser_created() {
		return user_created;
	}

	public void setUser_created(String user_created) {
		this.user_created = user_created;
	}

	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
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

	public Integer getIs_awaiting_sync() {
		return is_awaiting_sync;
	}

	public void setIs_awaiting_sync(Integer is_awaiting_sync) {
		this.is_awaiting_sync = is_awaiting_sync;
	}

	public String get_changed() {
		return _changed;
	}

	public void set_changed(String _changed) {
		this._changed = _changed;
	}

	public Integer getOnline_id() {
		return online_id;
	}

	public void setOnline_id(Integer online_id) {
		this.online_id = online_id;
	}
	
}
