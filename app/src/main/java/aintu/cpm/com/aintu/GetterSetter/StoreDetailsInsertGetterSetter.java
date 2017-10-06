package aintu.cpm.com.aintu.GetterSetter;

import java.io.Serializable;

import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;

public class StoreDetailsInsertGetterSetter implements Serializable {

    private String store_name="";
    private  String visit_date="";
    private  String store_image;

    public String getStore_image() {
        return store_image;
    }

    public void setStore_image(String store_image) {
        this.store_image = store_image;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(String upload_status) {
        this.upload_status = upload_status;
    }

    private  String upload_status="";

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    private String store_address="";
    private String store_city="";
    private String city_cd="0";
    private String key_id="0";
    private String geotag="";

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }
//  getGEOTAG

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }


    public String getCity_cd() {
        return city_cd;
    }

    public void setCity_cd(String city_cd) {
        this.city_cd = city_cd;
    }

    private String store_pincode="";
    private String store_state="";
    private String store_owner_person="";
    private String store_phone="";
    private String store_mobile="";
    private String store_cd="";
    private String store_kyc="";


/*   getKYC*/

  /*  private String store_pincode="";
    private String store_number="";*/
  public String getStore_kyc() {
      return store_kyc;
  }

    public void setStore_kyc(String store_kyc) {
        this.store_kyc = store_kyc;
    }


    public String getStore_cd() {
        return store_cd;
    }

    public void setStore_cd(String store_cd) {
        this.store_cd = store_cd;
    }

    public  StoreDetailsInsertGetterSetter( ){

    }

    public  StoreDetailsInsertGetterSetter(StoreSearchGetterSetter store_search_data){
        this.store_address=store_search_data.getSTORE_ADDRESS().get(0);
        this.store_city=store_search_data.getCITY().get(0);

        this.city_cd=store_search_data.getCITY_CD().get(0);
        this.geotag=store_search_data.getGEOTAG().get(0);

        this.store_pincode=store_search_data.getSTORE_PINCODE().get(0);
        this.store_kyc=store_search_data.getKYC().get(0);
        this.store_state="";
        this.store_owner_person=store_search_data.getOWNER_NAME().get(0);
        this.store_phone=store_search_data.getCONTACT_NUMBER().get(0);
       // this.store_mobile="";
        this.store_mobile=store_search_data.getMOBILE().get(0);
        this.store_name=store_search_data.getSTORE_NAME().get(0);
        this.upload_status="N";

    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_city() {
        return store_city;
    }

    public void setStore_city(String store_city) {
        this.store_city = store_city;
    }

    public String getStore_pincode() {
        return store_pincode;
    }

    public void setStore_pincode(String store_pincode) {
        this.store_pincode = store_pincode;
    }

    public String getStore_state() {
        return store_state;
    }

    public void setStore_state(String store_state) {
        this.store_state = store_state;
    }

    public String getStore_owner_person() {
        return store_owner_person;
    }

    public void setStore_owner_person(String store_owner_person) {
        this.store_owner_person = store_owner_person;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getStore_mobile() {
        return store_mobile;
    }

    public void setStore_mobile(String store_mobile) {
        this.store_mobile = store_mobile;
    }
}
