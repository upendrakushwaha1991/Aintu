package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class FolloowupReasonMasterGetterSetter implements Serializable {

    String table_FOLLOWUP_REASON_MASTER;

    public String getTable_FOLLOWUP_REASON_MASTER() {
        return table_FOLLOWUP_REASON_MASTER;
    }

    public void setTable_FOLLOWUP_REASON_MASTER(String table_FOLLOWUP_REASON_MASTER) {
        this.table_FOLLOWUP_REASON_MASTER = table_FOLLOWUP_REASON_MASTER;
    }

    ArrayList<String> FREASON_ID = new ArrayList<>();
    ArrayList<String> FREASON = new ArrayList<>();



    public ArrayList<String> getFREASON_ID() {
        return FREASON_ID;
    }

    public void setFREASON_ID(String FREASON_ID) {
        this.FREASON_ID.add(FREASON_ID);
    }

    public ArrayList<String> getFREASON() {
        return FREASON;
    }

    public void setFREASON(String FREASON) {
        this.FREASON.add(FREASON);
    }
}
