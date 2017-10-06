package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class StoreFormateMasterGetterSetter implements Serializable {

    String table_STORE_FORMAT_MASTER;

    ArrayList<String> FORMAT_TYPE_ID = new ArrayList<>();
    ArrayList<String> STORE_FORMAT = new ArrayList<>();

    public String getTable_STORE_FORMAT_MASTER() {
        return table_STORE_FORMAT_MASTER;
    }

    public void setTable_STORE_FORMAT_MASTER(String table_STORE_FORMAT_MASTER) {
        this.table_STORE_FORMAT_MASTER = table_STORE_FORMAT_MASTER;
    }

    public ArrayList<String> getFORMAT_TYPE_ID() {
        return FORMAT_TYPE_ID;
    }

    public void setFORMAT_TYPE_ID(String FORMAT_TYPE_ID) {
        this.FORMAT_TYPE_ID.add(FORMAT_TYPE_ID);
    }

    public ArrayList<String> getSTORE_FORMAT() {
        return STORE_FORMAT;
    }

    public void setSTORE_FORMAT(String STORE_FORMAT) {
        this.STORE_FORMAT.add(STORE_FORMAT);
    }
}
