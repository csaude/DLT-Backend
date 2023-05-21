package dlt.dltbackendmaster.domain.watermelondb;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BeneficiaryInterventionSyncModel
{
    private String id;
    private Integer beneficiary_id;
    private String beneficiary_offline_id;
    private Integer sub_service_id;
    private String result;
    private String date;
    private Integer us_id;
    private Integer activist_id;
    private String entry_point;
    private String provider;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Africa/Maputo")
	private Date date_created;
    private String remarks;
    private Integer status;
    private String _status;
    private String _changed;
    private String online_id;

    public BeneficiaryInterventionSyncModel() {}

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

    public String getBeneficiary_offline_id() {
		return beneficiary_offline_id;
	}

	public void setBeneficiary_offline_id(String beneficiaty_offline_id) {
		this.beneficiary_offline_id = beneficiaty_offline_id;
	}

	public Integer getSub_service_id() {
        return sub_service_id;
    }

    public void setSub_service_id(Integer sub_service_id) {
        this.sub_service_id = sub_service_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUs_id() {
        return us_id;
    }

    public void setUs_id(Integer us_id) {
        this.us_id = us_id;
    }

    public Integer getActivist_id() {
        return activist_id;
    }

    public void setActivist_id(Integer activist_id) {
        this.activist_id = activist_id;
    }

    public String getEntry_point() {
        return entry_point;
    }

    public void setEntry_point(String entry_point) {
        this.entry_point = entry_point;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getOnline_id() {
        return online_id;
    }

    public void setOnline_id(String online_id) {
        this.online_id = online_id;
    }
}
