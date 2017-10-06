package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class VisitTypeMasterGetterSetter implements Serializable {

    String table_VISIT_TYPE_MASTER;

    ArrayList<String> VISIT_TYPE_ID = new ArrayList<>();
    ArrayList<String> VISIT_TYPE = new ArrayList<>();

    public String getTable_VISIT_TYPE_MASTER() {
        return table_VISIT_TYPE_MASTER;
    }

    public void setTable_VISIT_TYPE_MASTER(String table_VISIT_TYPE_MASTER) {
        this.table_VISIT_TYPE_MASTER = table_VISIT_TYPE_MASTER;
    }

    public ArrayList<String> getVISIT_TYPE_ID() {
        return VISIT_TYPE_ID;
    }

    public void setVISIT_TYPE_ID(String VISIT_TYPE_ID) {
        this.VISIT_TYPE_ID.add(VISIT_TYPE_ID);
    }

    public ArrayList<String> getVISIT_TYPE() {
        return VISIT_TYPE;
    }

    public void setVISIT_TYPE(String VISIT_TYPE) {
        this.VISIT_TYPE.add(VISIT_TYPE);
    }
}
