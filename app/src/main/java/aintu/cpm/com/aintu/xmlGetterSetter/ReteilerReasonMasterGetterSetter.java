package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class ReteilerReasonMasterGetterSetter implements Serializable {

    String table_RETAILER_REASON_MASTER;

    ArrayList<String> RETAILER_REASON_ID = new ArrayList<>();
    ArrayList<String> RETAILER_REASON = new ArrayList<>();

    public String getTable_RETAILER_REASON_MASTER() {
        return table_RETAILER_REASON_MASTER;
    }

    public void setTable_RETAILER_REASON_MASTER(String table_RETAILER_REASON_MASTER) {
        this.table_RETAILER_REASON_MASTER = table_RETAILER_REASON_MASTER;
    }

    public ArrayList<String> getRETAILER_REASON_ID() {
        return RETAILER_REASON_ID;
    }

    public void setRETAILER_REASON_ID(String RETAILER_REASON_ID) {
        this.RETAILER_REASON_ID.add(RETAILER_REASON_ID);
    }

    public ArrayList<String> getRETAILER_REASON() {
        return RETAILER_REASON;
    }

    public void setRETAILER_REASON(String RETAILER_REASON) {
        this.RETAILER_REASON.add(RETAILER_REASON);
    }
}
