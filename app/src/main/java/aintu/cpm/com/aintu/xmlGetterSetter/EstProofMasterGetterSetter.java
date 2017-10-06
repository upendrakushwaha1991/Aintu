package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class EstProofMasterGetterSetter implements Serializable {

    String table_EST_PROOF_MASTER;

    ArrayList<String> EST_PROOF_ID = new ArrayList<>();
    ArrayList<String> EST_PROOF = new ArrayList<>();

    public String getTable_EST_PROOF_MASTER() {
        return table_EST_PROOF_MASTER;
    }

    public void setTable_EST_PROOF_MASTER(String table_EST_PROOF_MASTER) {
        this.table_EST_PROOF_MASTER = table_EST_PROOF_MASTER;
    }

    public ArrayList<String> getEST_PROOF_ID() {
        return EST_PROOF_ID;
    }

    public void setEST_PROOF_ID(String EST_PROOF_ID) {
        this.EST_PROOF_ID.add(EST_PROOF_ID);
    }

    public ArrayList<String> getEST_PROOF() {
        return EST_PROOF;
    }

    public void setEST_PROOF(String EST_PROOF) {
        this.EST_PROOF.add(EST_PROOF);
    }
}
