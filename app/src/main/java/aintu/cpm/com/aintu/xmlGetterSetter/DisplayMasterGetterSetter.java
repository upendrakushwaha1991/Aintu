package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class DisplayMasterGetterSetter implements Serializable {

    String table_VISIT_TYPE_MASTER;

    ArrayList<String> VISIT_TYPE_ID = new ArrayList<>();
    ArrayList<String> VISIT_TYPE = new ArrayList<>();

    public String getTable_VISIT_TYPE_MASTER() {
        return table_VISIT_TYPE_MASTER;
    }


}
