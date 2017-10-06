package aintu.cpm.com.aintu.upload;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.CallsInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.CoverageBean;
import aintu.cpm.com.aintu.GetterSetter.GeotaggingBeans;
import aintu.cpm.com.aintu.GetterSetter.KycInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.ProfileInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.uploadImage.UploadImageWithRetrofit;
import aintu.cpm.com.aintu.xmlGetterSetter.FailureGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreCategoryGetterSetter;
import aintu.cpm.com.aintu.xmlHandlers.FailureXMLHandler;
import constent.CommonString;


public class PreviousDataUploadActivity extends AppCompatActivity {
    AintuREDB db;
    ArrayList<CoverageBean> coverageList;
    String date, userId, app_version;
    /* StoreBean storeData;*/
    String datacheck = "";
    String[] words;
    String validity;
    int mid;
    String errormsg = "", Path;
    Data data;
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private FailureGetterSetter failureGetterSetter = null;
    private SharedPreferences preferences;
    private int factor, k = 0;
    Object result = "";
    Toolbar toolbar;

    ArrayList<StoreDetailsInsertGetterSetter> storedata;
    CallsInsertGetterSetter callsdata;
    ProfileInsertGetterSetter profiledata;
    ArrayList<StoreCategoryGetterSetter> storetoggledata=new ArrayList<>();
    GeotaggingBeans geotagdata;
    KycInsertGetterSetter kycdata;
    CoverageBean coveragedata;
    String key_id;
    int uploadImgCount,totalImag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        key_id = getIntent().getStringExtra(CommonString.KEY_ID);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        date = preferences.getString(CommonString.KEY_DATE, null);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        app_version = preferences.getString(CommonString.KEY_VERSION, null);

        db = new AintuREDB(this);
        db.open();

        Path = CommonString.FILE_PATH;

        //start upload
        new UploadTask(this).execute();
    }



    class Data {
        int value;
        String name;
    }

    private class UploadTask extends AsyncTask<Void, Data, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle(getString(R.string.uploaddata));
            dialog.setCancelable(false);
            dialog.show();

            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub

            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                data = new Data();
                coverageList = db.getCoverageData(date);

                if (coverageList.size() > 0) {
                    if (coverageList.size() == 1) {
                        factor = 50;
                    } else {
                        factor = 100 / (coverageList.size());
                    }
                }
                storedata = db.getUploadStoreListData();

                for (int i = 0; i < storedata.size(); i++) {


                    if (!storedata.get(i).getUpload_status().equals(CommonString.KEY_U)) {

                        //storedata upload
                        String store_details = "",calls_details="",profile_data="",kyc_data="",geo_tag="",toggle="";
                        String onXML = "";
                        callsdata = db.getUploadCallsData(storedata.get(i).getKey_id());
                        profiledata=db.getProfileData(storedata.get(i).getKey_id());
                        storetoggledata=db.getProfileToggleData(storedata.get(i).getKey_id());
                        kycdata=db.getkycData(storedata.get(i).getKey_id());
                        geotagdata=db.getGeoTagData(storedata.get(i).getKey_id());

                        if (storedata.get(i).getStore_name()!=null) {

                            store_details = "[COMMON_ID]" + storedata.get(i).getKey_id() + "[/COMMON_ID]"
                                    + "[USER_ID]" + userId + "[/USER_ID]"
                                    + "[STORE_NAME]"
                                    +  storedata.get(i).getStore_name()
                                    + "[/STORE_NAME]"
                                    + "[STORE_CD]"
                                    + storedata.get(i).getStore_cd()
                                    + "[/STORE_CD]"
                                    + "[STORE_ADDRESS]"
                                    + storedata.get(i).getStore_address()
                                    + "[/STORE_ADDRESS]"
                                    + "[CITY_CD]"
                                    + storedata.get(i).getCity_cd()
                                    + "[/CITY_CD]"
                                    + "[STORE_PINCODE]" +
                                    storedata.get(i).getStore_pincode()
                                    + "[/STORE_PINCODE]"
                                    + "[OWNER_NAME]"
                                    + storedata.get(i).getStore_owner_person()
                                    + "[/OWNER_NAME]"
                                    + "[PHONE_NUMBER]"
                                    + storedata.get(i).getStore_phone()
                                    + "[/PHONE_NUMBER]"
                                    + "[MOBILE_NUMBER]"
                                    + storedata.get(i).getStore_mobile()
                                    + "[/MOBILE_NUMBER]"
                                    + "[UPLOAD_STATUS]"
                                    + storedata.get(i).getUpload_status()
                                    + "[/UPLOAD_STATUS]"
                                    + "[VISIT_DATE]"
                                    + storedata.get(i).getVisit_date()
                                    + "[/VISIT_DATE]";
                        }

                        if (callsdata.getCalls_followup_date()!=null) {

                            calls_details = "[COMMON_ID]" + storedata.get(i).getKey_id() + "[/COMMON_ID]"
                                    + "[PERSON_MET]" + callsdata.getCalls_person_meet() + "[/PERSON_MET]"
                                    + "[REMARK]" + callsdata.getCaals_remark() + "[/REMARK]"
                                    + "[VISIT_TYPE_CD]"
                                    +  callsdata.getCalls_visit_type_CD()
                                    + "[/VISIT_TYPE_CD]"
                                    + "[USER_ID]" + userId + "[/USER_ID]"
                                    + "[VISIT_WITH_CD]"
                                    + callsdata.getCalls_store_visitet_with_CD()
                                    + "[/VISIT_WITH_CD]"
                                    + "[STATUS_CD]"
                                    + callsdata.getCalls_status_CD()
                                    + "[/STATUS_CD]"
                                    + "[REASON_FOR_RJECTION_AINTU_CD]"
                                    + callsdata.getCalls_reason_for_rejection_aintu()
                                    + "[/REASON_FOR_RJECTION_AINTU_CD]"
                                    + "[FOLLOWUP_DATE]" +
                                    callsdata.getCalls_followup_date()
                                    + "[/FOLLOWUP_DATE]"
                                    + "[REASON_FOR_REJECTION_RETAILER_CD]"
                                    + callsdata.getCalls_reson_for_rejection_retailer_CD()
                                    + "[/REASON_FOR_REJECTION_RETAILER_CD]"
                                    + "[VISIT_DATE]"
                                    + date
                                    + "[/VISIT_DATE]"
                                    + "[REASON_FOR_FOLLOWUP_CD]"
                                    + callsdata.getCalls_reson_for_followup_CD()
                                    + "[/REASON_FOR_FOLLOWUP_CD]"
                                    + "[STORE_IMAGE]"
                                    + callsdata.getCalls_store_image()
                                    + "[/STORE_IMAGE]"
                                    + "[STORE_LATITUDE]"
                                    + callsdata.getCalls_store_lat()
                                    + "[/STORE_LATITUDE]"
                                    + "[STORE_LANGUAGE]"
                                    + callsdata.getCalls_store_long()
                                    + "[/STORE_LANGUAGE]";

                        }
                        String FinalToggleXML="";
                        if (storetoggledata.size()>0 && storetoggledata.get(i).getToggolevalue()!=null) {

                            for (int j=0;j<storetoggledata.size();j++){
                                toggle="";
                                toggle = "[CATEGORY]"
                                        +"[COMMON_ID]" + storedata.get(i).getKey_id()
                                        + "[/COMMON_ID]"
                                        + "[CATEGORY_CD]" + profiledata.getProfile_store_format_cd()
                                        + "[/CATEGORY_CD]"
                                        + "[USER_ID]" + userId + "[/USER_ID]"
                                        + "[TOGGLEVALUE]" + storetoggledata.get(j).getToggolevalue()
                                        + "[/TOGGLEVALUE]"
                                        +"[/CATEGORY]";
                                FinalToggleXML=FinalToggleXML+toggle;
                            }

                        }

                        if (profiledata.getProfile_store_closing_time()!=null) {

                            profile_data =
                                    "[COMMON_ID]" + storedata.get(i).getKey_id() + "[/COMMON_ID]"
                                            + "[STORE_FORMAT_CD]" +
                                            profiledata.getProfile_store_format_cd()
                                            + "[/STORE_FORMAT_CD]"
                                            + "[USER_ID]" + userId + "[/USER_ID]"
                                            + "[STORE_CARPET_AREA]"
                                            +  profiledata.getProfile_store_carpet_area_cd()
                                            + "[/STORE_CARPET_AREA]"
                                            + "[FREEZER_CHILLER_AVAILABLE_CD]"
                                            + profiledata.getProfile_freezer_chiller_available_cd()
                                            + "[/FREEZER_CHILLER_AVAILABLE_CD]"
                                            + "[TYPE_OF_ENTITY_CD]"
                                            + profiledata.getProfile_type_of_entity_cd()
                                            + "[/TYPE_OF_ENTITY_CD]"
                                            + "[STORE_OPENING_TIME]" +
                                            profiledata.getProfile_store_opening_time()
                                            + "[/STORE_OPENING_TIME]"
                                            + "[STORE_CLOSING_TIME]"
                                            + profiledata.getProfile_store_closing_time()
                                            + "[/STORE_CLOSING_TIME]"
                                            + "[BILLING_SYSTEM_CD]"
                                            + profiledata.getProfile_billing_system_cd()
                                            + "[/BILLING_SYSTEM_CD]"
                                            + "[NAME_OF_POS]"
                                            + profiledata.getProfile_name_of_pos()
                                            + "[/NAME_OF_POS]"
                                            + "[OUTLET_TYPE_CD]"
                                            + profiledata.getProfile_outlet_tye_cd()
                                            + "[/OUTLET_TYPE_CD]"
                                            + "[NUMBER_OF_BILLING_POINT_CD]"
                                            + profiledata.getProfile_number_of_billing_point_cd()
                                            + "[/NUMBER_OF_BILLING_POINT_CD]"
                                            + "[CATEGORYDATA]"
                                            + FinalToggleXML
                                            + "[/CATEGORYDATA]";

                        }

                        if (kycdata.getKyc_store_email_id()!=null) {

                            kyc_data = "[COMMON_ID]" + storedata.get(i).getKey_id() + "[/COMMON_ID]"
                                    + "[STORE_EMAIL_ID]"
                                    + kycdata.getKyc_store_email_id()
                                    + "[/STORE_EMAIL_ID]"
                                    + "[USER_ID]" + userId + "[/USER_ID]"
                                    + "[PROOF_OF_ENTITY_ESTABILISHMENT_CD]"
                                    +  kycdata.getKyc_proof_of_entity_cd()
                                    + "[/PROOF_OF_ENTITY_ESTABILISHMENT_CD]"
                                    + "[ENTITY_PROOF_NUMBER]"
                                    + kycdata.getKyc_entity_proof_number()
                                    + "[/ENTITY_PROOF_NUMBER]"
                                    + "[IMAGE_ENTITY_PROOF_NO]"
                                    + kycdata.getKyc_image1()
                                    + "[/IMAGE_ENTITY_PROOF_NO]"
                                    + "[OWNER_CONTACT_NUMBER]"
                                    + kycdata.getKyc_owner_contact_number()
                                    + "[/OWNER_CONTACT_NUMBER]"
                                    + "[GENDER_CD]" +
                                    kycdata.getKyc_gender_cd()
                                    + "[/GENDER_CD]"
                                    + "[ID_PROOF_TYPE_CD]"
                                    + kycdata.getKyc_id_proof_type_cd()
                                    + "[/ID_PROOF_TYPE_CD]"
                                    + "[ID_PROOF_NUMBER]"
                                    + kycdata.getKyc_id_proof_number()
                                    + "[/ID_PROOF_NUMBER]"
                                    + "[IMAGE_ID_PROOF_NO]"
                                    + kycdata.getKyc_image2()
                                    + "[/IMAGE_ID_PROOF_NO]"
                                    + "[IMAGE_BACK]"
                                    + kycdata.getKyc_image5()
                                    + "[/IMAGE_BACK]"
                                    + "[ADDRESS_PROOF_TYPE_CD]"
                                    + kycdata.getKyc_address_proof_type()
                                    + "[/ADDRESS_PROOF_TYPE_CD]"
                                    + "[IMAGE_CANCELLED_CHEQUE]"
                                    + kycdata.getKyc_image4()
                                    + "[/IMAGE_CANCELLED_CHEQUE]"
                                    + "[ADDRESS_PROOF]"
                                    + kycdata.getKyc_address_proof()
                                    + "[/ADDRESS_PROOF]"
                                    + "[IMAGE_ADDRESS_PROOF_NO]"
                                    + kycdata.getKyc_image3()
                                    + "[/IMAGE_ADDRESS_PROOF_NO]";
                                           /* + "[/IMAGE_ID_PROOF_NO]"
                                            + "[ADDRESS_PROOF_TYPE_CD]"
                                            + kycdata.getKyc_address_proof_type()
                                            + "[/ADDRESS_PROOF_TYPE_CD]"
                                            + "[ADDRESS_PROOF]"
                                            + kycdata.getKyc_address_proof()
                                            + "[/ADDRESS_PROOF]"
                                            + "[IMAGE_ADDRESS_PROOF_NO]"
                                            + kycdata.getKyc_image3()
                                            + "[/IMAGE_ADDRESS_PROOF_NO]"*/
                        }

                        if (geotagdata.getLatitude()!=null) {

                            geo_tag = "[COMMON_ID]" + storedata.get(i).getKey_id() + "[/COMMON_ID]"
                                    + "[USER_ID]" + userId + "[/USER_ID]"
                                    + "[LATITUDE]"
                                    + geotagdata.getLatitude() + "[/LATITUDE]"
                                    + "[LONGITUDE]"
                                    +  geotagdata.getLongitude()
                                    + "[/LONGITUDE]"
                                    + "[IMAGE]"
                                    + geotagdata.getImage()
                                    + "[/IMAGE]"
                                    + "[STATUS]"
                                    + geotagdata.getStatus()
                                    + "[/STATUS]";

                        }

                        String onXml="[DATA]"
                                +"[STORE_DETAILS]"+
                                store_details
                                +"[/STORE_DETAILS]"
                                +"[CALLS]"+
                                calls_details
                                +"[/CALLS]"
                                +"[PROFILE]"+
                                profile_data
                                +"[/PROFILE]"
                                +"[KYC]"+
                                kyc_data
                                +"[/KYC]"
                                +"[GEOTAG]"+
                                geo_tag
                                +"[/GEOTAG]"
                                + "[/DATA]";

                        SoapObject   request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_UPLOAD_STOCK_XML_DATA);
                        request.addProperty("XMLDATA", onXml);

                        if (storedata.get(i).getStore_cd().equals("0")){
                            request.addProperty("KEYS", "STORE_DATA_ADDED");
                        }else {
                            request.addProperty("KEYS", "STORE_DATA_EXISTED");
                        }

                        request.addProperty("USERNAME", userId);
                        request.addProperty("MID", mid);

                        SoapSerializationEnvelope  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(request);

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                        androidHttpTransport.call(CommonString.SOAP_ACTION + CommonString.METHOD_UPLOAD_STOCK_XML_DATA, envelope);

                        result = envelope.getResponse();
                        data.value = 15;
                        data.name = "STORE DATA UPLOAD";
                        publishProgress(data);

                        if (result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                            db.UpdateStoreDetailsUploadStatus(storedata.get(i).getKey_id(),CommonString.KEY_D);
                        }

                        data.value = 100;
                        publishProgress(data);
                    }
                }
//image upload

                storedata = db.getUploadStoreListData();

                for (int i = 0; i < storedata.size(); i++) {

                    if (storedata.get(i).getUpload_status().equalsIgnoreCase("D")) {
                        //region Upload image with retrofit 23-05-2017
                        uploadImgCount = 0;
                        totalImag = 0;
                        File f = new File(CommonString.FILE_PATH);
                        totalImag = f.listFiles().length;
                        if (totalImag > 0) {
                            UploadImageWithRetrofit uploadRetro2 = new UploadImageWithRetrofit(context);
                            uploadImgCount = uploadRetro2.UploadImagesAndData();
                            if (uploadImgCount == -1) {
                                throw new IOException();
                            } else if (uploadImgCount == -2) {
                                throw new Exception();
                            }
                        }

                        if (is95uploaded(uploadImgCount, totalImag)) {

                            Object result = (Object)CommonString.KEY_SUCCESS;

                            if (result.toString().equalsIgnoreCase(
                                    CommonString.KEY_SUCCESS)) {
                                db.open();
                                db.deleteTableWithCommonID(storedata.get(i).getKey_id());
                                db.deleteStoreDetailTable();
                               // db.UpdateStoreDetailsUploadStatus(storedata.get(i).getKey_id(),CommonString.KEY_U);
                            }

                        }

                    }

                }



            } catch (HttpResponseException e1) {
                e1.printStackTrace();
            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
            } catch (XmlPullParserException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
                return  CommonString.MESSAGE_SOCKETEXCEPTION;

            }
            catch (Exception e) {
                e.printStackTrace();
                return CommonString.MESSAGE_EXCEPTION;
            }

            return result.toString();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();
            if (result.contains(CommonString.KEY_SUCCESS)) {
                //db.deleteAllTables();
                showAlert(getString(R.string.upload_data_msg));
                //showAlert(getString(R.string.menu_upload_data));
            } else {
                showAlert(getString(R.string.error) + result.toString());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(getString(R.string.title_activity_upload));
    }


    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(PreviousDataUploadActivity.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       /* Intent i = new Intent(activity, StorelistActivity.class);
                        activity.startActivity(i);
                        activity.finish();*/
                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    boolean is95uploaded(int upload, int total) {
        boolean result = true;
        if (total != 0) {
            int percent = ((upload / total) * 100);
            if (percent < 95) {
                result = false;
            }
        }
        return result;
    }

}