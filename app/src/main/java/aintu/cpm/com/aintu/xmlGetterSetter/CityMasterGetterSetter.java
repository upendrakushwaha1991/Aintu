package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class CityMasterGetterSetter implements Serializable {

    String table_CITY_MASTER;

    ArrayList<String> CITY_CD = new ArrayList<>();
    ArrayList<String> CITY = new ArrayList<>();

    public String getTable_CITY_MASTER() {
        return table_CITY_MASTER;
    }

    public void setTable_CITY_MASTER(String table_CITY_MASTER) {
        this.table_CITY_MASTER = table_CITY_MASTER;
    }

    public ArrayList<String> getCITY_CD() {
        return CITY_CD;
    }

    public void setCITY_CD(String CITY_CD) {
        this.CITY_CD.add(CITY_CD);
    }

    public ArrayList<String> getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY.add(CITY);
    }
}
