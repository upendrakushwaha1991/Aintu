package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class BillingTypeMasterGetterSetter implements Serializable {

    String table_BILLING_TYPE_MASTER;

    ArrayList<String> BTYPE_ID = new ArrayList<>();
    ArrayList<String> BILLING_TYPE = new ArrayList<>();

    public String getTable_BILLING_TYPE_MASTER() {
        return table_BILLING_TYPE_MASTER;
    }

    public void setTable_BILLING_TYPE_MASTER(String table_BILLING_TYPE_MASTER) {
        this.table_BILLING_TYPE_MASTER = table_BILLING_TYPE_MASTER;
    }

    public ArrayList<String> getBTYPE_ID() {
        return BTYPE_ID;
    }

    public void setBTYPE_ID(String BTYPE_ID) {
        this.BTYPE_ID.add(BTYPE_ID);
    }

    public ArrayList<String> getBILLING_TYPE() {
        return BILLING_TYPE;
    }

    public void setBILLING_TYPE(String BILLING_TYPE) {
        this.BILLING_TYPE.add(BILLING_TYPE);
    }
}
