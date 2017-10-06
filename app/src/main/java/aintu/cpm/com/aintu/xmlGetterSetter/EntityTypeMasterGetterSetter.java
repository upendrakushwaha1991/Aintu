package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class EntityTypeMasterGetterSetter implements Serializable {

    String table_ENTITY_TYPE_MASTER;

    ArrayList<String> ETYPE_ID = new ArrayList<>();
    ArrayList<String> ENTITY_TYPE = new ArrayList<>();

    public String getTable_ENTITY_TYPE_MASTER() {
        return table_ENTITY_TYPE_MASTER;
    }

    public void setTable_ENTITY_TYPE_MASTER(String table_ENTITY_TYPE_MASTER) {
        this.table_ENTITY_TYPE_MASTER = table_ENTITY_TYPE_MASTER;
    }

    public ArrayList<String> getETYPE_ID() {
        return ETYPE_ID;
    }

    public void setETYPE_ID(String ETYPE_ID) {
        this.ETYPE_ID.add(ETYPE_ID);
    }

    public ArrayList<String> getENTITY_TYPE() {
        return ENTITY_TYPE;
    }

    public void setENTITY_TYPE(String ENTITY_TYPE) {
        this.ENTITY_TYPE.add(ENTITY_TYPE);
    }
}
