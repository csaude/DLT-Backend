package dlt.dltbackendmaster.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dlt.dltbackendmaster.serializers.NeighborhoodSerializer;
import dlt.dltbackendmaster.serializers.UsersSerializer;

@MappedSuperclass
public abstract class BasicLifeCycle
{
    protected int id;
    protected String name;
    protected Integer status;
    private Users createdBy;
    protected Date dateCreated;
    private Users updatedBy;
    protected Date dateUpdated;
    protected String offlineId;

    public BasicLifeCycle() {}

    public BasicLifeCycle(int id, String name, Integer status,
                          Date dateCreated, Date dateUpdated, Users createdBy, Users updatedBy) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public BasicLifeCycle(String name, Integer status) {
        this.name = name;
        this.status = status;
        this.createdBy = new Users();
        this.updatedBy = new Users();
    }

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
    
    public void setId(Integer id) {
		this.id = id;
	}

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name = "created_by", nullable = false)
    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "updated_by")
    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_updated", length = 19)
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
