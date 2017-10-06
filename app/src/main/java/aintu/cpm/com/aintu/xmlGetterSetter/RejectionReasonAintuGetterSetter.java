package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 27-07-2017.
 */

public class RejectionReasonAintuGetterSetter implements Serializable {

    String table_REJECTION_REASON_AINTU;

    ArrayList<String> AREASON_ID = new ArrayList<>();
    ArrayList<String> AREASON = new ArrayList<>();

    public String getTable_REJECTION_REASON_AINTU() {
        return table_REJECTION_REASON_AINTU;
    }

    public void setTable_REJECTION_REASON_AINTU(String table_REJECTION_REASON_AINTU) {
        this.table_REJECTION_REASON_AINTU = table_REJECTION_REASON_AINTU;
    }

    public ArrayList<String> getAREASON_ID() {
        return AREASON_ID;
    }

    public void setAREASON_ID(String AREASON_ID) {
        this.AREASON_ID.add(AREASON_ID);
    }

    public ArrayList<String> getAREASON() {
        return AREASON;
    }

    public void setAREASON(String AREASON) {
        this.AREASON.add(AREASON);
    }
}
