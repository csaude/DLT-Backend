package dlt.dltbackendmaster.domain;
// Generated Jan 25, 2022, 4:05:43 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.serializers.LocalitySerializer;

/**
 * Neighborhood generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "neighborhood", catalog = "dreams_db")
@NamedQueries({
    @NamedQuery(name = "Neighborhood.findAll", query = "SELECT c FROM Neighborhood c"),
    @NamedQuery(name = "Neighborhood.findByDateCreated", query = "SELECT c FROM Neighborhood c WHERE c.dateCreated = :lastpulledat"),
    @NamedQuery(name = "Neighborhood.findByDateUpdated", query = "SELECT c FROM Neighborhood c WHERE c.dateUpdated = :lastpulledat")})
public class Neighborhood implements java.io.Serializable
{
    private Integer id;
    private Locality locality;
    private String name;
    private String description;
    private int status;
    private int createdBy;
    private Date dateCreated;
    private Integer updatedBy;
    private Date dateUpdated;

    public Neighborhood() {}

    public Neighborhood(Locality locality, String name, int status, int createdBy, Date dateCreated) {
        this.locality = locality;
        this.name = name;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
    }

    public Neighborhood(Locality locality, String name, String description, int status, int createdBy, Date dateCreated,
                        Integer updatedBy, Date dateUpdated) {
        this.locality = locality;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.updatedBy = updatedBy;
        this.dateUpdated = dateUpdated;
    }

    public Neighborhood(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locality_id", nullable = false)
    @JsonProperty("locality")
    @JsonSerialize(using = LocalitySerializer.class)
    public Locality getLocality() {
        return this.locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 250)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "created_by", nullable = false)
    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, length = 19)
    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "updated_by")
    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", length = 19)
    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        
        ObjectNode neighborhood = mapper.createObjectNode();
        neighborhood.put("id", id);
        neighborhood.put("locality_id", locality.getId());
        neighborhood.put("name", name);
        neighborhood.put("description", description);
        neighborhood.put("status", status);
        neighborhood.put("online_id", id); // flag to control if entity is synchronized with the backend
        return neighborhood;
    }
}
