package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class LeadClosureReasonMasterGetterSetter implements Serializable {

    String table_LEAD_CLOSURE_REASON_MASTER;

    ArrayList<String> CREASON_ID = new ArrayList<>();
    ArrayList<String> CLOSURE_REASON = new ArrayList<>();

    public String getTable_LEAD_CLOSURE_REASON_MASTER() {
        return table_LEAD_CLOSURE_REASON_MASTER;
    }

    public void setTable_LEAD_CLOSURE_REASON_MASTER(String table_LEAD_CLOSURE_REASON_MASTER) {
        this.table_LEAD_CLOSURE_REASON_MASTER = table_LEAD_CLOSURE_REASON_MASTER;
    }

    public ArrayList<String> getCREASON_ID() {
        return CREASON_ID;
    }

    public void setCREASON_ID(String CREASON_ID) {
        this.CREASON_ID.add(CREASON_ID);
    }

    public ArrayList<String> getCLOSURE_REASON() {
        return CLOSURE_REASON;
    }

    public void setCLOSURE_REASON(String CLOSURE_REASON) {
        this.CLOSURE_REASON.add(CLOSURE_REASON);
    }
}
