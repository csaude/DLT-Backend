package dlt.dltbackendmaster.domain.watermelondb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UsSyncModel {

	private String id;
	private String _status;
	private String _changed;
	private String name;
	private String description;
	private int locality_id;
	private int entry_point;
	private int status;
	private Integer online_id;
	
	public UsSyncModel() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getLocality_id() {
		return locality_id;
	}

	public void setLocality_id(int locality_id) {
		this.locality_id = locality_id;
	} 	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getOnline_id() {
		return online_id;
	}

	public void setOnline_id(Integer online_id) {
		this.online_id = online_id;
	}
	
	public int getEntry_point() {
		return entry_point;
	}

	public void setEntry_point(int entry_point) {
		this.entry_point = entry_point;
	}

	public ObjectNode toObjectNode() {
		ObjectMapper mapper = new ObjectMapper();
		
		ObjectNode us = mapper.createObjectNode();
		us.put("id", id);
		us.put("name", name);
		us.put("description", description);
		us.put("locality_id", locality_id);
		us.put("status", status);
		us.put("online_id", id); // flag to control if entity is synchronized with the backend
		return us;
	}

	
}
