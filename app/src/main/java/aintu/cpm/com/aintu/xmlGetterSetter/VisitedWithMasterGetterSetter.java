package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class VisitedWithMasterGetterSetter implements Serializable {

    String table_VISITED_WITH_MASTER;

    ArrayList<String> WVISIT_ID = new ArrayList<>();
    ArrayList<String> VISITED_WITH = new ArrayList<>();

    public String getTable_VISITED_WITH_MASTER() {
        return table_VISITED_WITH_MASTER;
    }

    public void setTable_VISITED_WITH_MASTER(String table_VISITED_WITH_MASTER) {
        this.table_VISITED_WITH_MASTER = table_VISITED_WITH_MASTER;
    }

    public ArrayList<String> getWVISIT_ID() {
        return WVISIT_ID;
    }

    public void setWVISIT_ID(String WVISIT_ID) {
        this.WVISIT_ID.add(WVISIT_ID);
    }

    public ArrayList<String> getVISITED_WITH() {
        return VISITED_WITH;
    }

    public void setVISITED_WITH(String VISITED_WITH) {
        this.VISITED_WITH.add(VISITED_WITH);
    }
}
