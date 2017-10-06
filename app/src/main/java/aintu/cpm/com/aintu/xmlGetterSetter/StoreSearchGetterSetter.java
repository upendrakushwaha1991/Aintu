package aintu.cpm.com.aintu.xmlGetterSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by upendrak on 13-07-2017.
 */

public class StoreSearchGetterSetter implements Serializable {

    String table_STORE_SEARCH;

    ArrayList<String> STORE_ID = new ArrayList<>();
    ArrayList<String> STORE_NAME = new ArrayList<>();
    ArrayList<String> STORE_ADDRESS = new ArrayList<>();
    ArrayList<String> CITY_CD = new ArrayList<>();
    ArrayList<String> GEOTAG = new ArrayList<>();
    ArrayList<String> STORE_PINCODE = new ArrayList<>();
    ArrayList<String> MOBILE = new ArrayList<>();
    ArrayList<String> KYC = new ArrayList<>();

    public ArrayList<String> getKYC() {
        return KYC;
    }

    public void setKYC(String KYC) {
        this.KYC.add(KYC);
    }

    public ArrayList<String> getSTORE_PINCODE() {
        return STORE_PINCODE;
    }

    public void setSTORE_PINCODE(String STORE_PINCODE) {
        this.STORE_PINCODE.add(STORE_PINCODE);
    }

    public ArrayList<String> getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE.add(MOBILE);
    }

    public ArrayList<String> getGEOTAG() {
        return GEOTAG;
    }

    public void setGEOTAG(String GEOTAG) {
        this.GEOTAG.add(GEOTAG);
    }

    public ArrayList<String> getCITY_CD() {
        return CITY_CD;
    }

    public void setCITY_CD(String CITY_CD) {
        this.CITY_CD.add(CITY_CD);
    }

    ArrayList<String> CITY = new ArrayList<>();
    ArrayList<String> CONTACT_PERSON = new ArrayList<>();
    ArrayList<String> OWNER_NAME = new ArrayList<>();
    ArrayList<String> CONTACT_NUMBER = new ArrayList<>();


    public ArrayList<String> getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY.add(CITY);
    }



    public ArrayList<String> getOWNER_NAME() {
        return OWNER_NAME;
    }

    public void setOWNER_NAME(String OWNER_NAME) {
        this.OWNER_NAME.add(OWNER_NAME);
    }

    public ArrayList<String> getCONTACT_NUMBER() {
        return CONTACT_NUMBER;
    }

    public void setCONTACT_NUMBER(String CONTACT_NUMBER) {
        this.CONTACT_NUMBER.add(CONTACT_NUMBER);
    }

    public ArrayList<String> getSTORE_ADDRESS() {
        return STORE_ADDRESS;
    }

    public void setSTORE_ADDRESS(String STORE_ADDRESS) {
        this.STORE_ADDRESS.add(STORE_ADDRESS);
    }

    public String getTable_STORE_SEARCH() {
        return table_STORE_SEARCH;
    }

    public void setTable_STORE_SEARCH(String table_STORE_SEARCH) {
        this.table_STORE_SEARCH = table_STORE_SEARCH;
    }

    public ArrayList<String> getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID.add(STORE_ID);
    }

    public ArrayList<String> getSTORE_NAME() {
        return STORE_NAME;
    }

    public void setSTORE_NAME(String STORE_NAME) {
        this.STORE_NAME.add(STORE_NAME);
    }

    public ArrayList<String> getCONTACT_PERSON() {
        return CONTACT_PERSON;
    }

    public void setCONTACT_PERSON(String CONTACT_PERSON) {
        this.CONTACT_PERSON.add(CONTACT_PERSON);
    }

}
