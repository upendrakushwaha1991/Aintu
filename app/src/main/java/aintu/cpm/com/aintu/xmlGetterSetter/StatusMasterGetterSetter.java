package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class StatusMasterGetterSetter implements Serializable {
   /* ArrayList<String> Closed = new ArrayList<>();*/

    String table_STATUS_MASTER;
    ArrayList<String> STATUS_ID = new ArrayList<>();
    ArrayList<String> STATUS = new ArrayList<>();
    ArrayList<String> ALLOW_REJECTION_AINTU = new ArrayList<>();
    ArrayList<String> ALLOW_KYC_PITCH_DATE = new ArrayList<>();
    ArrayList<String> ALLOW_REASON_REJECTION_RETAILER = new ArrayList<>();
    ArrayList<String> ALLOW_REASON_FOLLOWUP = new ArrayList<>();

    public String getTable_STATUS_MASTER() {
        return table_STATUS_MASTER;
    }

    public void setTable_STATUS_MASTER(String table_STATUS_MASTER) {
        this.table_STATUS_MASTER = table_STATUS_MASTER;
    }

    public ArrayList<String> getSTATUS_ID() {
        return STATUS_ID;
    }

    public void setSTATUS_ID(String STATUS_ID) {
        this.STATUS_ID.add(STATUS_ID);
    }

    public ArrayList<String> getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS.add(STATUS);;
    }

    public ArrayList<String> getALLOW_REJECTION_AINTU() {
        return ALLOW_REJECTION_AINTU;
    }

    public void setALLOW_REJECTION_AINTU(String ALLOW_REJECTION_AINTU) {
        this.ALLOW_REJECTION_AINTU.add(ALLOW_REJECTION_AINTU);
    }

    public ArrayList<String> getALLOW_KYC_PITCH_DATE() {
        return ALLOW_KYC_PITCH_DATE;
    }

    public void setALLOW_KYC_PITCH_DATE(String ALLOW_KYC_PITCH_DATE) {
        this.ALLOW_KYC_PITCH_DATE.add(ALLOW_KYC_PITCH_DATE);
    }

    public ArrayList<String> getALLOW_REASON_REJECTION_RETAILER() {
        return ALLOW_REASON_REJECTION_RETAILER;
    }

    public void setALLOW_REASON_REJECTION_RETAILER(String ALLOW_REASON_REJECTION_RETAILER) {
        this.ALLOW_REASON_REJECTION_RETAILER.add(ALLOW_REASON_REJECTION_RETAILER);
    }

    public ArrayList<String> getALLOW_REASON_FOLLOWUP() {
        return ALLOW_REASON_FOLLOWUP;
    }

    public void setALLOW_REASON_FOLLOWUP(String ALLOW_REASON_FOLLOWUP) {
        this.ALLOW_REASON_FOLLOWUP.add(ALLOW_REASON_FOLLOWUP);
    }
}
