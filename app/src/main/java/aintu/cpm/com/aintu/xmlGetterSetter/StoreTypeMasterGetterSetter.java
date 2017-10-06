package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class StoreTypeMasterGetterSetter implements Serializable {

    String table_STORETYPE_MASTER;

    ArrayList<String> STORETYPE_ID = new ArrayList<>();
    ArrayList<String> STORE_TYPE = new ArrayList<>();

    public String getTable_STORETYPE_MASTER() {
        return table_STORETYPE_MASTER;
    }

    public void setTable_STORETYPE_MASTER(String table_STORETYPE_MASTER) {
        this.table_STORETYPE_MASTER = table_STORETYPE_MASTER;
    }

    public ArrayList<String> getSTORETYPE_ID() {
        return STORETYPE_ID;
    }

    public void setSTORETYPE_ID(String STORETYPE_ID) {
        this.STORETYPE_ID.add(STORETYPE_ID);
    }

    public ArrayList<String> getSTORE_TYPE() {
        return STORE_TYPE;
    }

    public void setSTORE_TYPE(String STORE_TYPE) {
        this.STORE_TYPE.add(STORE_TYPE);
    }
}
