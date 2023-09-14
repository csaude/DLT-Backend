package dlt.dltbackendmaster.domain;

import java.time.LocalDate;
import java.util.Date;

public class ReferencesCancel {
    private int[] ids;
    private int status;
    private int cancelReason;
    private String otherReason;
    private int updatedBy;
    private LocalDate dateUpdated;
    
    public ReferencesCancel setDateUpdated(Date date) {
        return null;
    }
}
