package aintu.cpm.com.aintu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import aintu.cpm.com.aintu.GetterSetter.CallsInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.CoverageBean;
import aintu.cpm.com.aintu.GetterSetter.GeotaggingBeans;
import aintu.cpm.com.aintu.GetterSetter.KycInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.ProfileInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.dailyentry.ProfileActivity;
import aintu.cpm.com.aintu.xmlGetterSetter.BillingTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.BrandMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.EntityTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.EstProofMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.FolloowupReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.IdentificationTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.JourneyPlanGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.LeadClosureReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.RejectionReasonAintuGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.ReteilerReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StatusMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreCategoryGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreFormateMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.TableBean;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitedWithMasterGetterSetter;
import constent.CommonString;


/**
 * Created by upendrak on 04-07-2017.
 */
public class AintuREDB extends SQLiteOpenHelper {
    /* public static final String DATABASE_NAME = "GSK_ORANGE_DB5";*/
    public static final String DATABASE_NAME = "AINTU_RE_DB4";
    public static final int DATABASE_VERSION = 13;
    private SQLiteDatabase db;
    Context context;

    public AintuREDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        try {
            db.execSQL(TableBean.getVisitTypeMaster());
            db.execSQL(TableBean.getVisitedWithMaster());
            db.execSQL(TableBean.getStatusMaster());
            db.execSQL(TableBean.getRetailerReasonMaster());
            db.execSQL(TableBean.getFollowupReasonMaster());
            db.execSQL(TableBean.getStoreFormatMaster());
            db.execSQL(TableBean.getBrandMaster());
            db.execSQL(TableBean.getStoretypeMaster());
            db.execSQL(TableBean.getCityMaster());
            db.execSQL(TableBean.getLeadClosureReasonMaster());
            db.execSQL(TableBean.getEntityTypeMaster());
            db.execSQL(TableBean.getBillingTypeMaster());
            db.execSQL(TableBean.getEstProofMaster());
            db.execSQL(TableBean.getIdentificationTypeMaster());

            db.execSQL(TableBean.getRejectionReasonAintu());
            db.execSQL(TableBean.getStoreCategory());

            db.execSQL(CommonString.CREATE_TABLE_STORE_DETAILS);
            db.execSQL(CommonString.CREATE_TABLE_CALLS);
            db.execSQL(CommonString.CREATE_TABLE_PROFILE);
            db.execSQL(CommonString.CREATE_TABLE_KYC);
            db.execSQL(CommonString.CREATE_TABLE_GEOTAG);
            db.execSQL(CommonString.CREATE_TABLE_COVERAGE_DATA);

            db.execSQL(CommonString.CREATE_TABLE_PROFILE_TOGGLE);


        }
        catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error -" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableBean.getVisitTypeMaster());

    }


    public void deleteTableWithCommonID(String common_id) {

        db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.COMMON_ID + "='" + common_id + "'", null);
        db.delete(CommonString.TABLE_INSERT_CALLS, CommonString.COMMON_ID + "='" + common_id + "'", null);
        db.delete(CommonString.TABLE_INSERT_PROFILE, CommonString.COMMON_ID + "='" + common_id + "'", null);
        db.delete(CommonString.TABLE_INSERT_KYC, CommonString.COMMON_ID + "='" + common_id + "'", null);
        db.delete(CommonString.TABLE_INSERT_GEOTAG, CommonString.COMMON_ID + "='" + common_id + "'", null);


    }

    public void deleteStoreDetailTable() {

        db.delete(CommonString.TABLE_INSERT_STORE_DETAILS, null, null);
        db.delete(CommonString.TABLE_COVERAGE_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_CALLS, null, null);
        db.delete(CommonString.TABLE_INSERT_PROFILE, null, null);
        db.delete(CommonString.TABLE_INSERT_KYC,null, null);
        db.delete(CommonString.TABLE_INSERT_GEOTAG, null, null);


    }


    //Store search list insert
    public void InsertStoreSearchJCP(StoreSearchGetterSetter data) {

        db.delete("STORE_SEARCH", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getSTORE_ID().size(); i++) {

                values.put("STORE_ID", data.getSTORE_ID().get(i));
                values.put("STORE_NAME", data.getSTORE_NAME().get(i));
                values.put("STORE_ADDRESS", data.getSTORE_ADDRESS().get(i));
                values.put("CONTACT_PERSON", data.getCONTACT_PERSON().get(i));
                values.put("OWNER_NAME", data.getOWNER_NAME().get(i));
                values.put("CONTACT_NUMBER", data.getCONTACT_NUMBER().get(i));

                db.insert("STORE_SEARCH", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert VISIT_TYPE_MASTER Data ", ex.toString());
        }

    }


    public void InsertVISIT_TYPE_MASTER(VisitTypeMasterGetterSetter data) {

        db.delete("VISIT_TYPE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getVISIT_TYPE_ID().size(); i++) {

                values.put("VISIT_TYPE_ID", data.getVISIT_TYPE_ID().get(i));
                values.put("VISIT_TYPE", data.getVISIT_TYPE().get(i));

                db.insert("VISIT_TYPE_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert VISIT_TYPE_MASTER Data ", ex.toString());
        }

    }


    public void InsertVISITED_WITH_MASTER(VisitedWithMasterGetterSetter data) {

        db.delete("VISITED_WITH_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getWVISIT_ID().size(); i++) {

                values.put("WVISIT_ID", data.getWVISIT_ID().get(i));
                values.put("VISITED_WITH", data.getVISITED_WITH().get(i));

                db.insert("VISITED_WITH_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert VISITED_WITH_MASTER Data ", ex.toString());
        }

    }

    public void InsertSTATUS_MASTER(StatusMasterGetterSetter data) {

        db.delete("STATUS_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getSTATUS_ID().size(); i++) {

                values.put("STATUS_ID", data.getSTATUS_ID().get(i));
                values.put("STATUS", data.getSTATUS().get(i));
                values.put("ALLOW_REJECTION_AINTU", data.getALLOW_REJECTION_AINTU().get(i));
                values.put("ALLOW_KYC_PITCH_DATE", data.getALLOW_KYC_PITCH_DATE().get(i));
                values.put("ALLOW_REASON_REJECTION_RETAILER", data.getALLOW_REASON_REJECTION_RETAILER().get(i));
                values.put("ALLOW_REASON_FOLLOWUP", data.getALLOW_REASON_FOLLOWUP().get(i));

                db.insert("STATUS_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert STATUS_MASTER Data ", ex.toString());
        }

    }

    public void InsertRETAILER_REASON_MASTER(ReteilerReasonMasterGetterSetter data) {

        db.delete("RETAILER_REASON_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getRETAILER_REASON_ID().size(); i++) {

                values.put("RETAILER_REASON_ID", data.getRETAILER_REASON_ID().get(i));
                values.put("RETAILER_REASON", data.getRETAILER_REASON().get(i));

                db.insert("RETAILER_REASON_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert RETAILER_REASON_MASTER Data ", ex.toString());
        }

    }

    public void InsertFOLLOWUP_REASON_MASTER(FolloowupReasonMasterGetterSetter data) {

        db.delete("FOLLOWUP_REASON_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getFREASON_ID().size(); i++) {

                values.put("FREASON_ID", data.getFREASON_ID().get(i));
                values.put("FREASON", data.getFREASON().get(i));

                db.insert("FOLLOWUP_REASON_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert FOLLOWUP_REASON_MASTER Data ", ex.toString());
        }

    }

    public void InsertSTORE_FORMAT_MASTER(StoreFormateMasterGetterSetter data) {

        db.delete("STORE_FORMAT_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getFORMAT_TYPE_ID().size(); i++) {

                values.put("FORMAT_TYPE_ID", data.getFORMAT_TYPE_ID().get(i));
                values.put("STORE_FORMAT", data.getSTORE_FORMAT().get(i));

                db.insert("STORE_FORMAT_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert STORE_FORMAT_MASTER Data ", ex.toString());
        }

    }


    public void InsertBRAND_MASTER(BrandMasterGetterSetter data) {

        db.delete("BRAND_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getBRAND_ID().size(); i++) {

                values.put("BRAND_ID", data.getBRAND_ID().get(i));
                values.put("BRAND", data.getBRAND().get(i));

                db.insert("BRAND_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert BRAND_MASTER Data ", ex.toString());
        }

    }
    public void InsertCITY_MASTER(CityMasterGetterSetter data) {

        db.delete("CITY_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCITY_CD().size(); i++) {

                values.put("CITY_CD", data.getCITY_CD().get(i));
                values.put("CITY", data.getCITY().get(i));

                db.insert("CITY_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert CITY_MASTER Data ", ex.toString());
        }

    }

    public void InsertREJECTION_REASON_AINTU(RejectionReasonAintuGetterSetter data) {

        db.delete("REJECTION_REASON_AINTU", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getAREASON_ID().size(); i++) {

                values.put("AREASON_ID", data.getAREASON_ID().get(i));
                values.put("AREASON", data.getAREASON().get(i));

                db.insert("REJECTION_REASON_AINTU", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert REJECTION_REASON_AINTU Data ", ex.toString());
        }

    }

    public void InsertSTORE_CATEGORY(StoreCategoryGetterSetter data) {

        db.delete("STORE_CATEGORY", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCATEGORY_ID().size(); i++) {

                values.put("CATEGORY_ID", data.getCATEGORY_ID().get(i));
                values.put("CATEGORY", data.getCATEGORY().get(i));

                db.insert("STORE_CATEGORY", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert STORE_CATEGORY Data ", ex.toString());
        }

    }


    public void InsertSTORETYPE_MASTER(StoreTypeMasterGetterSetter data) {

        db.delete("STORETYPE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getSTORETYPE_ID().size(); i++) {

                values.put("STORETYPE_ID", data.getSTORETYPE_ID().get(i));
                values.put("STORE_TYPE", data.getSTORE_TYPE().get(i));

                db.insert("STORETYPE_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert STORETYPE_MASTER Data ", ex.toString());
        }

    }



    public void InsertLEAD_CLOSURE_REASON_MASTER(LeadClosureReasonMasterGetterSetter data) {

        db.delete("LEAD_CLOSURE_REASON_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCREASON_ID().size(); i++) {

                values.put("CREASON_ID", data.getCREASON_ID().get(i));
                values.put("CLOSURE_REASON", data.getCLOSURE_REASON().get(i));

                db.insert("LEAD_CLOSURE_REASON_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert LEAD_CLOSURE_REASON_MASTER Data ", ex.toString());
        }

    }


    public void InsertENTITY_TYPE_MASTER(EntityTypeMasterGetterSetter data) {

        db.delete("ENTITY_TYPE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getETYPE_ID().size(); i++) {

                values.put("ETYPE_ID", data.getETYPE_ID().get(i));
                values.put("ENTITY_TYPE", data.getENTITY_TYPE().get(i));

                db.insert("ENTITY_TYPE_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert ENTITY_TYPE_MASTER Data ", ex.toString());
        }

    }

    public void InsertBILLING_TYPE_MASTER(BillingTypeMasterGetterSetter data) {

        db.delete("BILLING_TYPE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getBTYPE_ID().size(); i++) {

                values.put("BTYPE_ID", data.getBTYPE_ID().get(i));
                values.put("BILLING_TYPE", data.getBILLING_TYPE().get(i));

                db.insert("BILLING_TYPE_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert BILLING_TYPE_MASTER Data ", ex.toString());
        }

    }

    public void InsertEST_PROOF_MASTER(EstProofMasterGetterSetter data) {

        db.delete("EST_PROOF_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getEST_PROOF_ID().size(); i++) {

                values.put("EST_PROOF_ID", data.getEST_PROOF_ID().get(i));
                values.put("EST_PROOF", data.getEST_PROOF().get(i));

                db.insert("EST_PROOF_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert EST_PROOF_MASTER Data ", ex.toString());
        }

    }


    public void InsertIDENTIFICATION_TYPE_MASTER(IdentificationTypeMasterGetterSetter data) {

        db.delete("IDENTIFICATION_TYPE_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getPROOF_TYPE_ID().size(); i++) {

                values.put("PROOF_TYPE_ID", data.getPROOF_TYPE_ID().get(i));
                values.put("PROOF_TYPE", data.getPROOF_TYPE().get(i));

                db.insert("IDENTIFICATION_TYPE_MASTER", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert IDENTIFICATION_TYPE_MASTER Data ", ex.toString());
        }

    }

    //create store search table

    public  void  createTable(String query){

        try {
            db.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InsertStoreSearch(StoreSearchGetterSetter data) {

        db.delete("STORE_SEARCH", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getSTORE_ID().size(); i++) {

                values.put("STORE_ID", data.getSTORE_ID().get(i));
                values.put("STORE_NAME", data.getSTORE_NAME().get(i));
                values.put("STORE_ADDRESS", data.getSTORE_ADDRESS().get(i));
                values.put("CITY_CD", data.getCITY_CD().get(i));
                values.put("CITY", data.getCITY().get(i));
                values.put("CONTACT_PERSON", data.getCONTACT_PERSON().get(i));
                values.put("OWNER_NAME", data.getOWNER_NAME().get(i));
                values.put("CONTACT_NUMBER", data.getCONTACT_NUMBER().get(i));
                values.put("GEOTAG", data.getGEOTAG().get(i));
                values.put("STORE_PINCODE", data.getSTORE_PINCODE().get(i));
                values.put("MOBILE", data.getMOBILE().get(i));
                values.put("KYC", data.getKYC().get(i));

                db.insert("STORE_SEARCH", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert VISIT_TYPE_MASTER Data ", ex.toString());
        }

    }

    public long InsertCoverageData(CoverageBean data, String key_id) {
        db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_ID + "='" + key_id + "'", null);
        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.COMMON_ID, key_id);
            values.put(CommonString.KEY_STORE_ID, data.getStore_id());
            values.put(CommonString.KEY_USER_ID, data.getUser_id());
            values.put(CommonString.KEY_IN_TIME, data.getIn_time());
            values.put(CommonString.KEY_OUT_TIME, data.getOut_time());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisit_date());
            values.put(CommonString.KEY_LATITUDE, data.getLattitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_REASON_ID, data.getReason_id());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_COVERAGE_STATUS, data.getCoverage_status());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_COVERAGE_REMARK, data.getRemark());
            values.put(CommonString.KEY_GEO_TAG, data.getGeo_tag());
            values.put(CommonString.KEY_CHECKOUT_IMAGE, data.getCheckout_image());

            return db.insert(CommonString.TABLE_COVERAGE_DATA, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
        }
        return 0;
    }

    public ArrayList<CoverageBean> getCoverageData(String visitdate) {

        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where "
                    + CommonString.KEY_VISIT_DATE + " <> '" + visitdate + "'", null);


            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();

                    sb.setStore_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setUser_id((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
                    sb.setIn_time(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
                    sb.setOut_time(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
                    sb.setVisit_date((((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
                    sb.setLattitude(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
                    sb.setCoverage_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS)));
                    sb.setImage((((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
                    sb.setReason((((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON))))));
                    sb.setReason_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setCommon_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    if (dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
                        sb.setRemark("");
                    } else {
                        sb.setRemark((((dbcursor.getString(dbcursor
                                .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
                    }
                    //sb.setCheckout_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKOUT_IMAGE)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }

    //get STORE SEARCH Data
    public ArrayList<StoreSearchGetterSetter> getStoreSearchData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StoreSearchGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from STORE_SEARCH", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreSearchGetterSetter sb = new StoreSearchGetterSetter();

                    sb.setSTORE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_ID")));

                    sb.setSTORE_NAME(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_NAME")));

                    sb.setSTORE_ADDRESS(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_ADDRESS")));
                    sb.setCITY_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY_CD")));
                    sb.setCITY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY")));

                    sb.setCONTACT_PERSON((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CONTACT_PERSON"))));

                    sb.setOWNER_NAME(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("OWNER_NAME")));

                    sb.setCONTACT_NUMBER(dbcursor.getString(dbcursor
                                   .getColumnIndexOrThrow("CONTACT_NUMBER")));
                    sb.setGEOTAG(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("GEOTAG")));

                    sb.setMOBILE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("MOBILE")));
                    sb.setSTORE_PINCODE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_PINCODE")));

                    sb.setKYC(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("KYC")));
                   // setKYC

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching JCP!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingJCP data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }
    public ArrayList<StoreSearchGetterSetter> getStoreGEoTAgData(String key_id) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StoreSearchGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from STORE_SEARCH WHERE Id = '"+key_id+"'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreSearchGetterSetter sb = new StoreSearchGetterSetter();

                    sb.setSTORE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_ID")));

                    sb.setSTORE_NAME(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_NAME")));

                    sb.setSTORE_ADDRESS(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_ADDRESS")));
                    sb.setCITY_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY_CD")));
                    sb.setCITY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY")));

                    sb.setCONTACT_PERSON((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CONTACT_PERSON"))));

                    sb.setOWNER_NAME(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("OWNER_NAME")));

                    sb.setCONTACT_NUMBER(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CONTACT_NUMBER")));
                    sb.setGEOTAG(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("GEOTAG")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching JCP!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingJCP data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    // get City Master data
    public ArrayList<CityMasterGetterSetter> getCityMasterData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<CityMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM CITY_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CityMasterGetterSetter sb = new CityMasterGetterSetter();


                    sb.setCITY_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY_CD")));

                    sb.setCITY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }
///new today

    // get VISIT_TYPE_MASTER data
/*
    public ArrayList<VisitTypeMasterGetterSetter> geVISIT_TYPE_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<VisitTypeMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM VISIT_TYPE_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    VisitTypeMasterGetterSetter sb = new VisitTypeMasterGetterSetter();


                    sb.setVISIT_TYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISIT_TYPE_ID")));

                    sb.setVISIT_TYPE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISIT_TYPE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching VISIT_TYPE_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }
*/

    public ArrayList<VisitTypeMasterGetterSetter> geVISIT_TYPE_MASTERData(String kyc) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<VisitTypeMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

           // dbcursor = db.rawQuery("SELECT * FROM VISIT_TYPE_MASTER", null);

            if (kyc.equalsIgnoreCase("Yes")){

                dbcursor = db.rawQuery("SELECT  * from VISIT_TYPE_MASTER" +" where "
                        + " VISIT_TYPE" + " = '" + "Relationship Call" + "'", null);
            }else {

                dbcursor = db.rawQuery("SELECT  * from VISIT_TYPE_MASTER" +" where "
                        + " VISIT_TYPE" + " <> '" + "Relationship Call" + "'", null);
            }


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    VisitTypeMasterGetterSetter sb = new VisitTypeMasterGetterSetter();


                    sb.setVISIT_TYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISIT_TYPE_ID")));

                    sb.setVISIT_TYPE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISIT_TYPE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching VISIT_TYPE_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get VISITED_WITH_MASTER data
    public ArrayList<VisitedWithMasterGetterSetter> geVISITED_WITH_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<VisitedWithMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM VISITED_WITH_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    VisitedWithMasterGetterSetter sb = new VisitedWithMasterGetterSetter();


                    sb.setWVISIT_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("WVISIT_ID")));

                    sb.setVISITED_WITH(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISITED_WITH")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching VISITED_WITH_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get FOLLOWUP_REASON_MASTER data
    public ArrayList<FolloowupReasonMasterGetterSetter> geFOLLOWUP_REASON_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<FolloowupReasonMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM FOLLOWUP_REASON_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FolloowupReasonMasterGetterSetter sb = new FolloowupReasonMasterGetterSetter();


                    sb.setFREASON_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FREASON_ID")));

                    sb.setFREASON(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FREASON")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching FOLLOWUP_REASON_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get STORE_FORMAT_MASTER data
    public ArrayList<StoreFormateMasterGetterSetter> geSTORE_FORMAT_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StoreFormateMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM STORE_FORMAT_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreFormateMasterGetterSetter sb = new StoreFormateMasterGetterSetter();


                    sb.setFORMAT_TYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FORMAT_TYPE_ID")));

                    sb.setSTORE_FORMAT(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_FORMAT")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching STORE_FORMAT_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    // get BRAND_MASTER data
    public ArrayList<BrandMasterGetterSetter> geBRAND_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<BrandMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM BRAND_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    BrandMasterGetterSetter sb = new BrandMasterGetterSetter();


                    sb.setBRAND_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_ID")));

                    sb.setBRAND(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching BRAND_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    // get ENTITY_TYPE_MASTER data
    public ArrayList<EntityTypeMasterGetterSetter> geENTITY_TYPE_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<EntityTypeMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM ENTITY_TYPE_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    EntityTypeMasterGetterSetter sb = new EntityTypeMasterGetterSetter();


                    sb.setETYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ETYPE_ID")));

                    sb.setENTITY_TYPE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ENTITY_TYPE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching ENTITY_TYPE_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get BILLING_TYPE_MASTER data
    public ArrayList<BillingTypeMasterGetterSetter> geBILLING_TYPE_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<BillingTypeMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT 0 as BTYPE_ID,'Select' as BILLING_TYPE union all Select BTYPE_ID,BILLING_TYPE  FROM BILLING_TYPE_MASTER", null);
           // dbcursor = db.rawQuery("SELECT * FROM BILLING_TYPE_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    BillingTypeMasterGetterSetter sb = new BillingTypeMasterGetterSetter();


                    sb.setBTYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BTYPE_ID")));

                    sb.setBILLING_TYPE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BILLING_TYPE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching BILLING_TYPE_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get EST_PROOF_MASTER data
    public ArrayList<EstProofMasterGetterSetter> geEST_PROOF_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<EstProofMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM EST_PROOF_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    EstProofMasterGetterSetter sb = new EstProofMasterGetterSetter();


                    sb.setEST_PROOF_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("EST_PROOF_ID")));

                    sb.setEST_PROOF(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("EST_PROOF")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching EST_PROOF_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get IDENTIFICATION_TYPE_MASTER data
    public ArrayList<IdentificationTypeMasterGetterSetter> geIDENTIFICATION_TYPE_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<IdentificationTypeMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM IDENTIFICATION_TYPE_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    IdentificationTypeMasterGetterSetter sb = new IdentificationTypeMasterGetterSetter();


                    sb.setPROOF_TYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PROOF_TYPE_ID")));

                    sb.setPROOF_TYPE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PROOF_TYPE")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching IDENTIFICATION_TYPE_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    // get LEAD_CLOSURE_REASON_MASTER data
    public ArrayList<LeadClosureReasonMasterGetterSetter> geLEAD_CLOSURE_REASON_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<LeadClosureReasonMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM LEAD_CLOSURE_REASON_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    LeadClosureReasonMasterGetterSetter sb = new LeadClosureReasonMasterGetterSetter();


                    sb.setCREASON_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CREASON_ID")));

                    sb.setCLOSURE_REASON(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CLOSURE_REASON")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching LEAD_CLOSURE_REASON_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get STORETYPE_MASTER data
    public ArrayList<StoreTypeMasterGetterSetter> geSTORETYPE_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StoreTypeMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM STORETYPE_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreTypeMasterGetterSetter sb = new StoreTypeMasterGetterSetter();


                    sb.setSTORETYPE_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORETYPE_ID")));

                    sb.setSTORE_TYPE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_TYPE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching STORETYPE_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    // get RETAILER_REASON_MASTER data
    public ArrayList<ReteilerReasonMasterGetterSetter> geRETAILER_REASON_MASTERata() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<ReteilerReasonMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM RETAILER_REASON_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ReteilerReasonMasterGetterSetter sb = new ReteilerReasonMasterGetterSetter();


                    sb.setRETAILER_REASON_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("RETAILER_REASON_ID")));

                    sb.setRETAILER_REASON(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("RETAILER_REASON")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching RETAILER_REASON_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get REJECTION_REASON_AINTU data
    public ArrayList<RejectionReasonAintuGetterSetter> geREJECTION_REASON_AINTUData() {
        Log.d("FetchinggetCREJECTION_REASON_AINTUData--------------->Start<------------",
                "------------------");
        ArrayList<RejectionReasonAintuGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM REJECTION_REASON_AINTU", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    RejectionReasonAintuGetterSetter sb = new RejectionReasonAintuGetterSetter();


                    sb.setAREASON_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("AREASON_ID")));

                    sb.setAREASON(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("AREASON")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching RejectionReasonAintuGetterSetter!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get STORE_CATEGORY data
    public ArrayList<StoreCategoryGetterSetter> getSTORE_CATEGORYData() {
        Log.d("FetchinggetCREJECTION_REASON_AINTUData--------------->Start<------------",
                "------------------");
        ArrayList<StoreCategoryGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM STORE_CATEGORY", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreCategoryGetterSetter sb = new StoreCategoryGetterSetter();

                    sb.setCATEGORY_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_ID")));

                    sb.setCATEGORY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching RejectionReasonAintuGetterSetter!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    // get STATUS_MASTER data
    public ArrayList<StatusMasterGetterSetter> geSTATUS_MASTERData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StatusMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM STATUS_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StatusMasterGetterSetter sb = new StatusMasterGetterSetter();


                    sb.setSTATUS_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS_ID")));

                    sb.setSTATUS(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS")));
                    sb.setALLOW_REJECTION_AINTU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_REJECTION_AINTU")));
                    sb.setALLOW_KYC_PITCH_DATE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_KYC_PITCH_DATE")));
                    sb.setALLOW_REASON_REJECTION_RETAILER(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_REASON_REJECTION_RETAILER")));
                    sb.setALLOW_REASON_FOLLOWUP(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_REASON_FOLLOWUP")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching STATUS_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get STATUS_MASTER data
    public ArrayList<StatusMasterGetterSetter> geSTATUS_MASTER_LISTData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StatusMasterGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT 0 as STATUS_ID,'Select' as STATUS,0 as ALLOW_REJECTION_AINTU,0 as ALLOW_KYC_PITCH_DATE,0 as ALLOW_REASON_REJECTION_RETAILER,0 as ALLOW_REASON_FOLLOWUP   union all Select STATUS_ID,STATUS,ALLOW_REJECTION_AINTU,ALLOW_KYC_PITCH_DATE,ALLOW_REASON_REJECTION_RETAILER,ALLOW_REASON_FOLLOWUP  FROM STATUS_MASTER", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StatusMasterGetterSetter sb = new StatusMasterGetterSetter();


                    sb.setSTATUS_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS_ID")));

                    sb.setSTATUS(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS")));

                    sb.setALLOW_REJECTION_AINTU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_REJECTION_AINTU")));

                    sb.setALLOW_KYC_PITCH_DATE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_KYC_PITCH_DATE")));
                    sb.setALLOW_REASON_REJECTION_RETAILER(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_REASON_REJECTION_RETAILER")));

                    sb.setALLOW_REASON_FOLLOWUP(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ALLOW_REASON_FOLLOWUP")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching STATUS_MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get Store details data
    public StoreDetailsInsertGetterSetter getStoreDetailsData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        StoreDetailsInsertGetterSetter sb = new StoreDetailsInsertGetterSetter();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS +" WHERE "+CommonString.KEY_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {


                    sb.setStore_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));
                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setStore_address(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ADDRESS)));
                    sb.setStore_city(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY)));
                    sb.setCity_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY_CD)));
                    sb.setStore_pincode(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_PINCODE)));
                    sb.setStore_owner_person(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_NAME)));
                    sb.setStore_phone(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PHONE_NUMBER)));
                    sb.setStore_mobile(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MOBILE_NUMBER)));
                    sb.setUpload_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS)));
                    sb.setGeotag(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_GEO_TAG)));
                    sb.setStore_kyc(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_KYC)));


                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }


    // get Store calls data
    public CallsInsertGetterSetter getCallsData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        CallsInsertGetterSetter sb = new CallsInsertGetterSetter();
        Cursor dbcursor = null;


        try {
            //dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS+" S inner join "+CommonString.TABLE_COVERAGE_DATA+" C on S.Id=C.COMMON_ID" , null);

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_CALLS+" WHERE "+CommonString.COMMON_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {



                    sb.setCalls_person_meet(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PERSON_MET)));
                    sb.setCaals_remark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REMARK)));
                    sb.setCalls_visit_type_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_TYPE_CD)));
                    sb.setCalls_store_visitet_with_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_WITH_CD)));
                    sb.setCalls_status_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STATUS_CD)));
                    sb.setCalls_reson_for_rejection(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FORREJECTION)));
                    sb.setCalls_followup_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_FOLLOWUP_DATE)));
                    sb.setCalls_reson_for_rejection_retailer_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FORREJECTION_RETAILER_CD)));
                    sb.setCalls_reson_for_followup_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FOR_FOLLOWUP_CD)));
                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                   /* sb.setCalls_store_image(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));*/
                  /*  sb.setCalls_store_lat(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setCalls_store_long(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));*/

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }


    public CallsInsertGetterSetter getUploadCallsData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        CallsInsertGetterSetter sb = new CallsInsertGetterSetter();
        Cursor dbcursor = null;


        try {
            // dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS+" S inner join "+CommonString.TABLE_COVERAGE_DATA+" C on S.Id=C.COMMON_ID" , null);

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_CALLS+" S inner join "+CommonString.TABLE_COVERAGE_DATA+" C on S.COMMON_ID=C.COMMON_ID WHERE C."+CommonString.COMMON_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {


                    sb.setCalls_person_meet(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PERSON_MET)));
                    sb.setCaals_remark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REMARK)));
                    sb.setCalls_visit_type_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_TYPE_CD)));
                    sb.setCalls_reason_for_rejection_aintu(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FORREJECTION)));
                    sb.setCalls_store_visitet_with_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_WITH_CD)));
                    sb.setCalls_status_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STATUS_CD)));
                    sb.setCalls_reson_for_rejection(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FORREJECTION)));
                    sb.setCalls_followup_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_FOLLOWUP_DATE)));
                    sb.setCalls_reson_for_rejection_retailer_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FORREJECTION_RETAILER_CD)));
                    sb.setCalls_reson_for_followup_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_FOR_FOLLOWUP_CD)));
                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setCalls_store_image(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setCalls_store_lat(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setCalls_store_long(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }


    // get Store profile data
    public ProfileInsertGetterSetter getProfileData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ProfileInsertGetterSetter sb = new ProfileInsertGetterSetter();
        Cursor dbcursor = null;


        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_PROFILE+" WHERE "+CommonString.COMMON_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setProfile_store_format_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_FORMAT_CD)));
                    sb.setProfile_store_carpet_area_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CARPET_AREA_CD)));
                    sb.setProfile_freezer_chiller_available_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_FREEZER_CHILLER_AVAILABLE_CD)));
                    sb.setProfile_available_brand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_AVAILABLE_BRANDS_CD)));
                    sb.setProfile_type_of_entity_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TYPE_OF_ENTITY_CD)));

                    sb.setProfile_store_opening_time(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_OPENING_TIME)));
                    sb.setProfile_store_closing_time(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CLOSING_TIME)));
                    sb.setProfile_outlet_format_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OUTLENT_FORMAT_CD)));

                    sb.setProfile_billing_system_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_BILLING_SYSTEM_CD)));
                    sb.setProfile_outlet_tye_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OUTLET_TYPE_CD)));
                    sb.setProfile_number_of_billing_point_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_NUMBER_OF_BILLING_POINT_CD)));
                    sb.setProfile_name_of_pos(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_NAME_OF_POS)));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }



    // get profile Toggle data
    public ArrayList<StoreCategoryGetterSetter> getProfileToggleData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
       ArrayList<StoreCategoryGetterSetter> list=new ArrayList<>();
        Cursor dbcursor = null;


        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_PROFILE_TOGGLE+" WHERE "+CommonString.COMMON_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreCategoryGetterSetter sb = new StoreCategoryGetterSetter();

                    sb.setToggolevalue(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PROFILE_TOGGLE)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching STORE_CATEGORY!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }
    // get Store kyc data
    public KycInsertGetterSetter getkycData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        KycInsertGetterSetter sb = new KycInsertGetterSetter();
        Cursor dbcursor = null;


        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_KYC+" WHERE "+CommonString.COMMON_ID+"= '"+key_id+"'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setKyc_proof_of_entity_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PROOF_OF_ENTITY_ESTABILISHMENT_CD)));

                    sb.setKyc_entity_proof_number(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ENTITY_PROOF_NUMBER)));

                    sb.setKyc_store_email_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_EMAIL_ID)));
                    sb.setKyc_owner_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_NAME)));
                    sb.setKyc_owner_contact_number(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_CONTACT_NUMBER)));
                    sb.setKyc_gender_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_GENDER_CD)));
                    sb.setKyc_id_proof_type_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID_PROOF_TYPE_CD)));
                    sb.setKyc_id_proof_number(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID_PROOF_NUMBER)));
                  //  setKyc_address_proof_type_cd
                    sb.setKyc_address_proof_type(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ADDRESS_PROOF_TYPE)));
                    sb.setKyc_address_proof_type(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ADDRESS_PROOF_TYPE)));

                    sb.setKyc_address_proof(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ADDRESS_PROOF)));

                    sb.setKyc_image1(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE_ENTITY_PROOF_NO)));
                    sb.setKyc_image2(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE_ID_PROOF_NO)));
                    sb.setKyc_image3(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE_ADDRESS_PROOF_NO)));

                   sb.setKyc_image4(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE_CANCELLED_CHEQUE)));
                    sb.setKyc_image5(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE_BACK)));



                 /*   sb.setKyc_address_prooff_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ADDRESS_PROOF_CD)));
*/
                   /*  values.put(CommonString.KEY_IMAGE_CANCELLED_CHEQUE, data.getKyc_image4());
    values.put(CommonString.KEY_ADDRESS_PROOF_CD, data.getKyc_address_prooff_cd());*/

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }


    //get store data
    public ArrayList<StoreDetailsInsertGetterSetter> getStoreListData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StoreDetailsInsertGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
           // dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS+" WHERE "+CommonString.KEY_VISIT_DATE+ " <> '"+visit_date+"'", null);

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreDetailsInsertGetterSetter sb = new StoreDetailsInsertGetterSetter();

                    sb.setStore_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    sb.setKey_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));

                    sb.setStore_address(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ADDRESS)));
                    sb.setStore_city(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY_CD)));
                    sb.setStore_pincode(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_PINCODE)));
                    sb.setStore_owner_person(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_NAME)));
                    sb.setStore_phone(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PHONE_NUMBER)));
                    sb.setStore_mobile(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MOBILE_NUMBER)));
                    sb.setUpload_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS)));
                    sb.setStore_kyc(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_KYC)));
                  /*  sb.setGeotag(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_GEO_TAG)));
*/

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

//UPLOAD DATA
    public ArrayList<StoreDetailsInsertGetterSetter> getUploadStoreListData(String visit_date) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StoreDetailsInsertGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
             dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS+" WHERE "+CommonString.KEY_VISIT_DATE+ " = '"+visit_date+"'", null);

           // dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreDetailsInsertGetterSetter sb = new StoreDetailsInsertGetterSetter();

                    sb.setStore_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    sb.setKey_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));
                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));

                    sb.setStore_address(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ADDRESS)));
                    sb.setStore_city(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY_CD)));
                    sb.setStore_pincode(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_PINCODE)));
                    sb.setStore_owner_person(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_NAME)));
                    sb.setStore_phone(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PHONE_NUMBER)));
                    sb.setStore_mobile(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MOBILE_NUMBER)));
                    sb.setUpload_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS)));
                    sb.setGeotag(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_GEO_TAG)));
                    sb.setStore_kyc(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_KYC)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<StoreDetailsInsertGetterSetter> getStoreListPreviusUploadData(String visit_date) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StoreDetailsInsertGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS+" WHERE "+CommonString.KEY_VISIT_DATE+ " <> '"+visit_date+"'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreDetailsInsertGetterSetter sb = new StoreDetailsInsertGetterSetter();

                    sb.setStore_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    sb.setKey_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));

                    sb.setStore_address(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ADDRESS)));
                    sb.setStore_city(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY_CD)));
                    sb.setStore_pincode(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_PINCODE)));
                    sb.setStore_owner_person(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_NAME)));
                    sb.setStore_phone(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PHONE_NUMBER)));
                    sb.setStore_mobile(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MOBILE_NUMBER)));
                    sb.setUpload_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    public ArrayList<StoreDetailsInsertGetterSetter> getUploadStoreListData() {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        ArrayList<StoreDetailsInsertGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_STORE_DETAILS+" S inner join "+CommonString.TABLE_COVERAGE_DATA+" C on S.Id=C.COMMON_ID" , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreDetailsInsertGetterSetter sb = new StoreDetailsInsertGetterSetter();

                    sb.setStore_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    sb.setKey_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                    sb.setCity_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY_CD")));


                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));

                    sb.setStore_address(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ADDRESS)));
                    sb.setStore_city(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY_CD)));
                    sb.setStore_pincode(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_PINCODE)));
                    sb.setStore_owner_person(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OWNER_NAME)));
                    sb.setStore_phone(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PHONE_NUMBER)));
                    sb.setStore_mobile(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MOBILE_NUMBER)));
                    sb.setUpload_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_UPLOAD_STATUS)));
                    sb.setStore_image(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    public long InsertStoreDetailsData(StoreDetailsInsertGetterSetter data,String store_cd,String visit_date) {
        db.delete(CommonString.TABLE_INSERT_STORE_DETAILS, CommonString.KEY_ID + "='" + data.getKey_id() + "'", null);
        ContentValues values = new ContentValues();

        try {
            values.put(CommonString.KEY_UPLOAD_STATUS,data.getUpload_status());
            values.put(CommonString.KEY_VISIT_DATE,visit_date);
            values.put(CommonString.KEY_STORE_CD,store_cd);
            values.put(CommonString.KEY_STORE_NAME, data.getStore_name());
            values.put(CommonString.KEY_STORE_ADDRESS, data.getStore_address());
            values.put(CommonString.KEY_CITY_CD, data.getCity_cd());
            values.put(CommonString.KEY_CITY, data.getStore_city());
            values.put(CommonString.KEY_STORE_PINCODE, data.getStore_pincode());
            values.put(CommonString.KEY_STORE_STATE, data.getStore_state());
            values.put(CommonString.KEY_OWNER_NAME, data.getStore_owner_person());
            values.put(CommonString.KEY_PHONE_NUMBER, data.getStore_phone());
            values.put(CommonString.KEY_MOBILE_NUMBER, data.getStore_mobile());
            values.put(CommonString.KEY_GEO_TAG, data.getGeotag());
            values.put(CommonString.KEY_KYC, data.getStore_kyc());


            return db.insert(CommonString.TABLE_INSERT_STORE_DETAILS, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
        }
        return 0;
    }


    public long UpdateStoreDetailsData(StoreDetailsInsertGetterSetter data,String store_cd) {
        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_UPLOAD_STATUS,data.getUpload_status());
            values.put(CommonString.KEY_STORE_CD,store_cd);
            values.put(CommonString.KEY_STORE_NAME, data.getStore_name());
            values.put(CommonString.KEY_STORE_ADDRESS, data.getStore_address());
            values.put(CommonString.KEY_CITY_CD, data.getCity_cd());
            values.put(CommonString.KEY_CITY, data.getStore_city());
            values.put(CommonString.KEY_STORE_PINCODE, data.getStore_pincode());
            values.put(CommonString.KEY_STORE_STATE, data.getStore_state());
            values.put(CommonString.KEY_OWNER_NAME, data.getStore_owner_person());
            values.put(CommonString.KEY_PHONE_NUMBER, data.getStore_phone());
            values.put(CommonString.KEY_MOBILE_NUMBER, data.getStore_mobile());

            //return db.insert(CommonString.TABLE_INSERT_STORE_DETAILS, null, values);
            return db.update(CommonString.TABLE_INSERT_STORE_DETAILS, values, CommonString.KEY_ID + "='" +  data.getKey_id() + "'", null);

        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
        }
        return 0;
    }

    public long UpdateStoreDetailsUploadStatus(String key_id, String status) {
        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_UPLOAD_STATUS,status);

            db.update(CommonString.TABLE_INSERT_STORE_DETAILS, values, CommonString.KEY_ID + "='" +  key_id + "'", null);

        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
        }
        return 0;
    }

    public long InsertCallsData(CallsInsertGetterSetter data,String key_id) {
        db.delete(CommonString.TABLE_INSERT_CALLS, CommonString.COMMON_ID + "='" +key_id  + "'", null);
        ContentValues values = new ContentValues();
        long id=0;

        try {

            values.put(CommonString.COMMON_ID, key_id);
            values.put(CommonString.KEY_PERSON_MET, data.getCalls_person_meet());
            values.put(CommonString.KEY_REMARK, data.getCaals_remark());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisit_date());
            values.put(CommonString.KEY_VISIT_TYPE_CD, data.getCalls_visit_type_CD());
            values.put(CommonString.KEY_VISIT_WITH_CD, data.getCalls_store_visitet_with_CD());
            values.put(CommonString.KEY_STATUS_CD, data.getCalls_status_CD());
            values.put(CommonString.KEY_RESON_FORREJECTION, data.getCalls_reason_for_rejection_aintu());
            values.put(CommonString.KEY_FOLLOWUP_DATE, data.getCalls_followup_date());
            values.put(CommonString.KEY_RESON_FORREJECTION_RETAILER_CD, Integer.parseInt(data.getCalls_reson_for_rejection_retailer_CD()));
            values.put(CommonString.KEY_RESON_FOR_FOLLOWUP_CD, data.getCalls_reson_for_followup_CD());


            id= db.insert(CommonString.TABLE_INSERT_CALLS, null, values);
            return id;
        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
            return  -1;
        }
    }

    public long InsertProfileData(ProfileInsertGetterSetter data,String key_id) {
        db.delete(CommonString.TABLE_INSERT_PROFILE, CommonString.COMMON_ID + "='" +key_id  + "'", null);
        ContentValues values = new ContentValues();
        long id=0;

        try {
            values.put(CommonString.COMMON_ID, key_id);
            values.put(CommonString.KEY_STORE_FORMAT_CD, data.getProfile_store_format_cd());
            values.put(CommonString.KEY_STORE_CARPET_AREA_CD, data.getProfile_store_carpet_area_cd());
            values.put(CommonString.KEY_FREEZER_CHILLER_AVAILABLE_CD, data.getProfile_freezer_chiller_available_cd());
            values.put(CommonString.KEY_AVAILABLE_BRANDS_CD, data.getProfile_available_brand_cd());
            values.put(CommonString.KEY_TYPE_OF_ENTITY_CD, data.getProfile_type_of_entity_cd());
            values.put(CommonString.KEY_STORE_OPENING_TIME, data.getProfile_store_opening_time());
            values.put(CommonString.KEY_STORE_CLOSING_TIME, data.getProfile_store_closing_time());
            values.put(CommonString.KEY_OUTLENT_FORMAT_CD, data.getProfile_outlet_format_cd());
            values.put(CommonString.KEY_BILLING_SYSTEM_CD, data.getProfile_billing_system_cd());
            values.put(CommonString.KEY_OUTLET_TYPE_CD, data.getProfile_outlet_tye_cd());
            values.put(CommonString.KEY_NUMBER_OF_BILLING_POINT_CD, data.getProfile_number_of_billing_point_cd());
            values.put(CommonString.KEY_NAME_OF_POS, data.getProfile_name_of_pos());

            id= db.insert(CommonString.TABLE_INSERT_PROFILE, null, values);
            return id;
        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
            return -1;
        }

    }


    public long InsertProfileToggleData(ArrayList<StoreCategoryGetterSetter>  data,String key_id) {

        db.delete(CommonString.TABLE_INSERT_PROFILE_TOGGLE, CommonString.COMMON_ID + "='" +key_id  + "'", null);
        ContentValues values = new ContentValues();
        long id=0;
        try {
            for (int i=0;i<data.size();i++){
                values.put(CommonString.COMMON_ID, key_id);
                values.put(CommonString.KEY_PROFILE_TOGGLE, data.get(i).getToggolevalue());

                id= db.insert(CommonString.TABLE_INSERT_PROFILE_TOGGLE, null, values);
            }

            return id;
        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
            return -1;
        }

    }

    public long InsertKYCData(KycInsertGetterSetter data,String key_id) {

        db.delete(CommonString.TABLE_INSERT_KYC, CommonString.COMMON_ID + "='" +key_id  + "'", null);
        ContentValues values = new ContentValues();
        long id=0;
        try {
            values.put(CommonString.COMMON_ID, key_id);
            values.put(CommonString.KEY_PROOF_OF_ENTITY_ESTABILISHMENT_CD, data.getKyc_proof_of_entity_cd());
            values.put(CommonString.KEY_ENTITY_PROOF_NUMBER, data.getKyc_entity_proof_number());
            values.put(CommonString.KEY_STORE_EMAIL_ID, data.getKyc_store_email_id());
            values.put(CommonString.KEY_OWNER_NAME, data.getKyc_owner_name());
            values.put(CommonString.KEY_OWNER_CONTACT_NUMBER, data.getKyc_owner_contact_number());
            values.put(CommonString.KEY_GENDER_CD, data.getKyc_gender_cd());
            values.put(CommonString.KEY_ID_PROOF_TYPE_CD, data.getKyc_id_proof_type_cd());
            values.put(CommonString.KEY_ID_PROOF_NUMBER, data.getKyc_id_proof_number());
            values.put(CommonString.KEY_ADDRESS_PROOF_TYPE, data.getKyc_address_proof_type());
            values.put(CommonString.KEY_ADDRESS_PROOF, data.getKyc_address_proof());
            values.put(CommonString.KEY_IMAGE_ENTITY_PROOF_NO, data.getKyc_image1());
            values.put(CommonString.KEY_IMAGE_ID_PROOF_NO, data.getKyc_image2());
            values.put(CommonString.KEY_IMAGE_ADDRESS_PROOF_NO, data.getKyc_image3());
            values.put(CommonString.KEY_IMAGE_CANCELLED_CHEQUE, data.getKyc_image4());
            values.put(CommonString.KEY_IMAGE_BACK, data.getKyc_image5());


           /* values.put(CommonString.KEY_ADDRESS_PROOF_CD, data.getKyc_address_prooff_cd());*/


           /*  values.put(CommonString.KEY_IMAGE_CANCELLED_CHEQUE, data.getKyc_image4());
    values.put(CommonString.KEY_ADDRESS_PROOF_CD, data.getKyc_address_prooff_cd());*/

            id= db.insert(CommonString.TABLE_INSERT_KYC, null, values);
            return id;
        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
            return -1;
        }
    }

    public long InsertGEOTAGData(GeotaggingBeans data) {
        db.delete(CommonString.TABLE_INSERT_GEOTAG, CommonString.COMMON_ID + "='" +data.getCommon_id() + "'", null);
        ContentValues values = new ContentValues();
        long id=0;
        try {
            values.put(CommonString.KEY_STORE_CD, "0");
            values.put(CommonString.COMMON_ID, data.getCommon_id());
            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_STATUS, data.getStatus());

            id= db.insert(CommonString.TABLE_INSERT_GEOTAG, null, values);
            return id;
        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
            return -1;
        }
    }


    // get Store geotag data
    public GeotaggingBeans getGeoTagData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        GeotaggingBeans sb = new GeotaggingBeans();
        Cursor dbcursor = null;


        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_GEOTAG+" WHERE "+CommonString.COMMON_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setLatitude(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setLongitude(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STATUS)));


                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }

    public GeotaggingBeans getGeoTagUploadData(String key_id) {
        Log.d("FetchinggetCityMasterData--------------->Start<------------",
                "------------------");
        GeotaggingBeans sb = new GeotaggingBeans();
        Cursor dbcursor = null;


        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_GEOTAG+" S inner join "+CommonString.TABLE_INSERT_STORE_SEARCH+" C on S.Id=C.COMMON_ID WHERE C."+CommonString.COMMON_ID+" = "+key_id, null);

           // dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_GEOTAG+" WHERE "+CommonString.COMMON_ID+" = "+key_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setLatitude(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setLongitude(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STATUS)));
                    sb.setGeotag_data(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_GEO_TAG)));


                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching CITY MASTER!!!!!!!!!!!",
                    e.toString());
            return sb;
        }

        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return sb;
    }

    //update coverage status

    public void updateCoverageStatus(String id, String status) {

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_ID + "='" + id + "'", null);

        } catch (Exception e) {

            Log.d("Excep update checkout", e.toString());
        }

    }
    public void deletedRecordStoreDetails(String visit_date) {
        try {
            db.delete(CommonString.TABLE_INSERT_STORE_DETAILS, CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);
           // db.delete(CommonString.TABLE_INSERT_STORE_DETAILS, null, null);
        } catch (Exception e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }

}
