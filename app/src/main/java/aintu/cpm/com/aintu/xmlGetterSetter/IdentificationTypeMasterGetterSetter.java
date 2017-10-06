package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class IdentificationTypeMasterGetterSetter implements Serializable {

    String table_IDENTIFICATION_TYPE_MASTER;

    ArrayList<String> PROOF_TYPE_ID = new ArrayList<>();
    ArrayList<String> PROOF_TYPE = new ArrayList<>();

    public String getTable_IDENTIFICATION_TYPE_MASTER() {
        return table_IDENTIFICATION_TYPE_MASTER;
    }

    public void setTable_IDENTIFICATION_TYPE_MASTER(String table_IDENTIFICATION_TYPE_MASTER) {
        this.table_IDENTIFICATION_TYPE_MASTER = table_IDENTIFICATION_TYPE_MASTER;
    }

    public ArrayList<String> getPROOF_TYPE_ID() {
        return PROOF_TYPE_ID;
    }

    public void setPROOF_TYPE_ID(String PROOF_TYPE_ID) {
        this.PROOF_TYPE_ID.add(PROOF_TYPE_ID);
    }

    public ArrayList<String> getPROOF_TYPE() {
        return PROOF_TYPE;
    }

    public void setPROOF_TYPE(String PROOF_TYPE) {
        this.PROOF_TYPE.add(PROOF_TYPE);
    }
}
