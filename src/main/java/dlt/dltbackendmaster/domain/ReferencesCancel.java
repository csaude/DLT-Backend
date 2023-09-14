package dlt.dltbackendmaster.domain;
public class ReferencesCancel {
    private int[] ids;
    private int status;
    private int cancelReason;
    private String otherReason;
    private int updatedBy;

    
    public int[] gitIds() {
        return this.ids;
    } 

    public void setIds(int[] ids){
        this.ids = ids;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public Integer getCancelReason() {
        return this.cancelReason;
    }

    public void setCancelReason(int cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getOtherReason() {
        return this.otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

}
