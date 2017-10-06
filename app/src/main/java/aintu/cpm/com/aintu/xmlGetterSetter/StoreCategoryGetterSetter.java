package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 27-07-2017.
 */

public class StoreCategoryGetterSetter implements Serializable {

    String table_STORE_CATEGORY;
    String toggolevalue="0";

    public String getToggolevalue() {
        return toggolevalue;
    }

    public void setToggolevalue(String toggolevalue) {
        this.toggolevalue = toggolevalue;
    }

    public String getTable_STORE_CATEGORY() {
        return table_STORE_CATEGORY;
    }

    public void setTable_STORE_CATEGORY(String table_STORE_CATEGORY) {
        this.table_STORE_CATEGORY = table_STORE_CATEGORY;
    }

    ArrayList<String> CATEGORY_ID = new ArrayList<>();
    ArrayList<String> CATEGORY = new ArrayList<>();

    public ArrayList<String> getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(String CATEGORY_ID) {
        this.CATEGORY_ID.add(CATEGORY_ID);
    }

    public ArrayList<String> getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY.add(CATEGORY);
    }
}
