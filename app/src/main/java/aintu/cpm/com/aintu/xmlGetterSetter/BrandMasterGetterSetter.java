package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class BrandMasterGetterSetter implements Serializable {

    String table_BRAND_MASTER;

    ArrayList<String> BRAND_ID = new ArrayList<>();
    ArrayList<String> BRAND = new ArrayList<>();

    public String getTable_BRAND_MASTER() {
        return table_BRAND_MASTER;
    }

    public void setTable_BRAND_MASTER(String table_BRAND_MASTER) {
        this.table_BRAND_MASTER = table_BRAND_MASTER;
    }

    public ArrayList<String> getBRAND_ID() {
        return BRAND_ID;
    }

    public void setBRAND_ID(String BRAND_ID) {
        this.BRAND_ID.add(BRAND_ID);
    }

    public ArrayList<String> getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND.add(BRAND);
    }
}
