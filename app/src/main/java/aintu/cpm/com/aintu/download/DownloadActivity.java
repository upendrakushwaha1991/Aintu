package aintu.cpm.com.aintu.download;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.R;
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
import aintu.cpm.com.aintu.xmlGetterSetter.StoreTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.TableBean;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitedWithMasterGetterSetter;
import aintu.cpm.com.aintu.xmlHandlers.XMLHandlers;
import constent.CommonString;

public class DownloadActivity extends AppCompatActivity {
    Data data;
    int eventType;
    AintuREDB db;
    String userId;

    // JourneyPlanGetterSetter jcpgettersetter;
    VisitTypeMasterGetterSetter visitTypeMasterGetterSetter;
    VisitedWithMasterGetterSetter visitedWithMasterGetterSetter;
    StatusMasterGetterSetter statusMasterGetterSetter;
    ReteilerReasonMasterGetterSetter reteilerReasonMasterGetterSetter;
    FolloowupReasonMasterGetterSetter folloowupReasonMasterGetterSetter;
    StoreFormateMasterGetterSetter storeFormateMasterGetterSetter;
    BrandMasterGetterSetter brandMasterGetterSetter;
    StoreTypeMasterGetterSetter storeTypeMasterGetterSetter;
    LeadClosureReasonMasterGetterSetter leadClosureReasonMasterGetterSetter;
    EntityTypeMasterGetterSetter entityTypeMasterGetterSetter;
    BillingTypeMasterGetterSetter billingTypeMasterGetterSetter;
    EstProofMasterGetterSetter estProofMasterGetterSetter;
    IdentificationTypeMasterGetterSetter identificationTypeMasterGetterSetter;
    CityMasterGetterSetter cityMasterGetterSetter;
    RejectionReasonAintuGetterSetter rejectionReasonAintuGetterSetter;
    StoreCategoryGetterSetter storeCategoryGetterSetter;


    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private SharedPreferences preferences = null;
    Toolbar toolbar;
    String str;
    boolean ResultFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

      /*  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        db = new AintuREDB(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);

        new UploadTask(DownloadActivity.this).execute();
    }
    class Data {
        int value;
        String name;
    }
    private  class  UploadTask extends AsyncTask<Void, Data, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.custom_dialog_progress);

            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            //VISIT_TYPE_MASTER
            try {
                String resultHttp = "";
                data = new Data();

                data.value = 5;
                data.name = "VISIT TYPE MASTER " + getResources().getString(R.string.download_data);
                publishProgress(data);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "VISIT_TYPE_MASTER");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result = envelope.getResponse();

                if (result.toString() != null) {
                    //InputStream stream = new ByteArrayInputStream(result.toString().getBytes("UTF-8"));

                    xpp.setInput(new StringReader(result.toString()));
                    // xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    // xpp.setInput(stream,"UTF-8");
                    xpp.next();
                    eventType = xpp.getEventType();

                    visitTypeMasterGetterSetter = XMLHandlers.VISITTYPEMASTERXMLHandler(xpp, eventType);

                    if (visitTypeMasterGetterSetter.getVISIT_TYPE_ID().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String visittypemasterTable = visitTypeMasterGetterSetter.getTable_VISIT_TYPE_MASTER();
                        TableBean.setVisitTypeMaster(visittypemasterTable);
                    } else {
                        return "VISIT_TYPE_MASTER";
                    }

                    data.value = 10;
                    data.name = "VISIT TYPE" + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                // VISITED_WITH_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "VISITED_WITH_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    visitedWithMasterGetterSetter = XMLHandlers.VISITEDWITHMASTERXMLHandler(xpp, eventType);
                    if (visitedWithMasterGetterSetter.getWVISIT_ID().size() > 0) {
                        String visitedWithMaster = visitedWithMasterGetterSetter.getTable_VISITED_WITH_MASTER();
                        if (visitedWithMaster != null) {
                            resultHttp = CommonString.KEY_SUCCESS;
                            TableBean.setVisitedWithMaster(visitedWithMaster);
                        }
                    } else {
                        return "VISITED_WITH_MASTER";
                    }

                    data.value = 15;
                    data.name = "VISITED_WITH" + getResources().getString(R.string.download_data);
                }
                publishProgress(data);


                // STATUS_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "STATUS_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    statusMasterGetterSetter = XMLHandlers.STATUS_MASTERXMLHandler(xpp, eventType);
                    if (statusMasterGetterSetter.getSTATUS_ID().size() > 0) {
                        String statusMaster = statusMasterGetterSetter.getTable_STATUS_MASTER();
                        if (statusMaster != null) {
                            resultHttp = CommonString.KEY_SUCCESS;
                            TableBean.setStatusMaster(statusMaster);
                        }
                    } else {
                        return "STATUS_MASTER";
                    }

                    data.value = 20;
                    data.name = "STATUS_MASTER" + getResources().getString(R.string.download_data);
                }
                publishProgress(data);


                // RETAILER_REASON_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "RETAILER_REASON_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    reteilerReasonMasterGetterSetter = XMLHandlers.RETAILER_REASON_MASTERXMLHandler(xpp, eventType);
                    if (reteilerReasonMasterGetterSetter.getRETAILER_REASON_ID().size() > 0) {
                        String retailer_reason_master = reteilerReasonMasterGetterSetter.getTable_RETAILER_REASON_MASTER();
                        if (retailer_reason_master != null) {
                            resultHttp = CommonString.KEY_SUCCESS;
                            TableBean.setRetailerReasonMaster(retailer_reason_master);
                        }
                    } else {
                        return "RETAILER_REASON_MASTER";
                    }
                    data.value = 25;
                    data.name = "RETAILER_REASON" + getResources().getString(R.string.download_data);
                }
                publishProgress(data);


                // FOLLOWUP_REASON_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "FOLLOWUP_REASON_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    folloowupReasonMasterGetterSetter = XMLHandlers.FOLLOWUP_REASON_MASTERXMLHandler(xpp, eventType);
                    if (folloowupReasonMasterGetterSetter.getFREASON_ID().size() > 0) {
                        String followup_reason_master = folloowupReasonMasterGetterSetter.getTable_FOLLOWUP_REASON_MASTER();
                        if (followup_reason_master != null) {
                            resultHttp = CommonString.KEY_SUCCESS;
                            TableBean.setFollowupReasonMaster(followup_reason_master);
                        }
                    } else {
                        return "CATEGORY_MASTER";
                    }
                    data.value = 30;
                    data.name = "CATEGORY_MASTER" + getResources().getString(R.string.download_data);
                }
                publishProgress(data);


                // STORE_FORMAT_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "STORE_FORMAT_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    storeFormateMasterGetterSetter = XMLHandlers.STORE_FORMAT_MASTERXMLHandler(xpp, eventType);
                    if (storeFormateMasterGetterSetter.getFORMAT_TYPE_ID().size() > 0) {
                        String display_table = storeFormateMasterGetterSetter.getTable_STORE_FORMAT_MASTER();
                        if (display_table != null) {
                            resultHttp = CommonString.KEY_SUCCESS;
                            TableBean.setStoreFormatMaster(display_table);
                        }
                    } else {
                        return "STORE_FORMAT_MASTER";
                    }
                    data.value = 35;
                    data.name = "STORE_FORMAT" + getResources().getString(R.string.download_data);
                }
                publishProgress(data);

                // BRAND_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "BRAND_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    brandMasterGetterSetter = XMLHandlers.BRAND_MASTERXMLHandler(xpp, eventType);

                    String stocktable = brandMasterGetterSetter.getTable_BRAND_MASTER();
                    if (stocktable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setBrandMaster(stocktable);
                    }

                    if (brandMasterGetterSetter.getBRAND_ID().size() > 0) {
                        data.value = 40;
                        data.name = "BRAND_ID" + getResources().getString(R.string.download_data);
                    } else {
                        return "BRAND_ID";
                    }

                }
                publishProgress(data);


                // STORETYPE_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "STORETYPE_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    storeTypeMasterGetterSetter = XMLHandlers.STORETYPE_MASTERXMLHandler(xpp, eventType);

                    String t2ptable = storeTypeMasterGetterSetter.getTable_STORETYPE_MASTER();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setStoretypeMaster(t2ptable);
                    }

                    if (storeTypeMasterGetterSetter.getSTORETYPE_ID().size() > 0) {
                        data.value = 45;
                        data.name = "STORETYPE_MASTER" + getResources().getString(R.string.download_data);

                    } else {
                        return "STORETYPE_MASTER";
                    }

                }
                publishProgress(data);

                // LEAD_CLOSURE_REASON_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "LEAD_CLOSURE_REASON_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    leadClosureReasonMasterGetterSetter = XMLHandlers.LEAD_CLOSURE_REASON_MASTERXMLHandler(xpp, eventType);

                    String leadclosure = leadClosureReasonMasterGetterSetter.getTable_LEAD_CLOSURE_REASON_MASTER();
                    if (leadclosure != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setLeadClosureReasonMaster(leadclosure);
                    }

                    if (leadClosureReasonMasterGetterSetter.getCREASON_ID().size() > 0) {
                        data.value = 50;
                        data.name = "LEAD_CLOSURE" + getResources().getString(R.string.download_data);

                    } else {
                        return "LEAD_CLOSURE_REASON_MASTER";
                    }

                }
                publishProgress(data);

                // ENTITY_TYPE_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "ENTITY_TYPE_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    entityTypeMasterGetterSetter = XMLHandlers.ENTITY_TYPE_MASTERXMLHandler(xpp, eventType);

                    String t2ptable = entityTypeMasterGetterSetter.getTable_ENTITY_TYPE_MASTER();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setEntityTypeMaster(t2ptable);
                    }

                    if (entityTypeMasterGetterSetter.getETYPE_ID().size() > 0) {
                        data.value = 55;
                        data.name = "ENTITY_TYPE" + getResources().getString(R.string.download_data);

                    } else {
                        return "ENTITY_TYPE_MASTER";
                    }

                }
                publishProgress(data);

                // BILLING_TYPE_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "BILLING_TYPE_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    billingTypeMasterGetterSetter = XMLHandlers.BILLING_TYPE_MASTERHandler(xpp, eventType);

                    String t2ptable = billingTypeMasterGetterSetter.getTable_BILLING_TYPE_MASTER();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setBillingTypeMaster(t2ptable);
                    }

                    if (billingTypeMasterGetterSetter.getBTYPE_ID().size() > 0) {
                        data.value = 60;
                        data.name = "BILLING_TYPE" + getResources().getString(R.string.download_data);

                    } else {
                        return "BILLING_TYPE_MASTER";
                    }

                }
                publishProgress(data);

                // EST_PROOF_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "EST_PROOF_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    estProofMasterGetterSetter = XMLHandlers.EST_PROOF_MASTERXMLHandler(xpp, eventType);

                    String t2ptable = estProofMasterGetterSetter.getTable_EST_PROOF_MASTER();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setEstProofMaster(t2ptable);
                    }

                    if (estProofMasterGetterSetter.getEST_PROOF_ID().size() > 0) {
                        data.value = 65;
                        data.name = "EST_PROOF" + getResources().getString(R.string.download_data);

                    } else {
                        return "EST_PROOF_MASTER";
                    }

                }
                publishProgress(data);

                // IDENTIFICATION_TYPE_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "IDENTIFICATION_TYPE_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    identificationTypeMasterGetterSetter = XMLHandlers.IDENTIFICATION_TYPE_MASTERXMLHandler(xpp, eventType);

                    String t2ptable = identificationTypeMasterGetterSetter.getTable_IDENTIFICATION_TYPE_MASTER();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setIdentificationTypeMaster(t2ptable);
                    }

                    if (identificationTypeMasterGetterSetter.getPROOF_TYPE_ID().size() > 0) {
                        data.value = 70;
                        data.name = "IDENTIFICATION" + getResources().getString(R.string.download_data);

                    } else {
                        return "IDENTIFICATION_TYPE_MASTER";
                    }

                }
                publishProgress(data);



                // CITY_MASTER
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "CITY_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    cityMasterGetterSetter = XMLHandlers.CITY_MASTERHandler(xpp, eventType);

                    String t2ptable = cityMasterGetterSetter.getTable_CITY_MASTER();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setCityMaster(t2ptable);
                    }

                    if (cityMasterGetterSetter.getCITY_CD().size() > 0) {
                        data.value = 80;
                        data.name = "IDENTIFICATION" + getResources().getString(R.string.download_data);

                    } else {
                        return "IDENTIFICATION_TYPE_MASTER";
                    }

                }
                publishProgress(data);

                // REJECTION_REASON_AINTU
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "REJECTION_REASON_AINTU");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    rejectionReasonAintuGetterSetter = XMLHandlers.REJECTION_REASON_AINTUHandler(xpp, eventType);

                    String t2ptable = rejectionReasonAintuGetterSetter.getTable_REJECTION_REASON_AINTU();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setRejectionReasonAintu(t2ptable);
                    }

                    if (rejectionReasonAintuGetterSetter.getAREASON_ID().size() > 0) {
                        data.value = 85;
                        data.name = "REJECTION" + getResources().getString(R.string.download_data);

                    } else {
                        return "REJECTION_REASON_AINTU";
                    }

                }
                publishProgress(data);

                // STORE_CATEGORY
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", userId);
                request.addProperty("Type", "STORE_CATEGORY");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                   storeCategoryGetterSetter = XMLHandlers.STORE_CATEGORYHandler(xpp, eventType);

                    String t2ptable = storeCategoryGetterSetter.getTable_STORE_CATEGORY();
                    if (t2ptable != null) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        TableBean.setStoreCategory(t2ptable);
                    }

                    if (storeCategoryGetterSetter.getCATEGORY_ID().size() > 0) {
                        data.value = 90;
                        data.name = "STORE_CATEGORY" + getResources().getString(R.string.download_data);

                    } else {
                        return "STORE_CATEGORY";
                    }

                }
                    publishProgress(data);


                db.open();
                db.InsertVISIT_TYPE_MASTER(visitTypeMasterGetterSetter);
                db.InsertVISITED_WITH_MASTER(visitedWithMasterGetterSetter);
                db.InsertSTATUS_MASTER(statusMasterGetterSetter);
                db.InsertRETAILER_REASON_MASTER(reteilerReasonMasterGetterSetter);
                db.InsertFOLLOWUP_REASON_MASTER(folloowupReasonMasterGetterSetter);
                db.InsertSTORE_FORMAT_MASTER(storeFormateMasterGetterSetter);
                db.InsertBRAND_MASTER(brandMasterGetterSetter);
                db.InsertSTORETYPE_MASTER(storeTypeMasterGetterSetter);
                db.InsertLEAD_CLOSURE_REASON_MASTER(leadClosureReasonMasterGetterSetter);
                db.InsertENTITY_TYPE_MASTER(entityTypeMasterGetterSetter);
                db.InsertBILLING_TYPE_MASTER(billingTypeMasterGetterSetter);
                db.InsertEST_PROOF_MASTER(estProofMasterGetterSetter);
                db.InsertIDENTIFICATION_TYPE_MASTER(identificationTypeMasterGetterSetter);
                db.InsertCITY_MASTER(cityMasterGetterSetter);

                db.InsertREJECTION_REASON_AINTU(rejectionReasonAintuGetterSetter);
                db.InsertSTORE_CATEGORY(storeCategoryGetterSetter);


            } catch (MalformedURLException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (InterruptedIOException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;

            } catch (IOException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (XmlPullParserException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_XmlPull;
                return CommonString.MESSAGE_XmlPull;
            } catch (Exception e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;

                return CommonString.MESSAGE_EXCEPTION;
            }

            if (ResultFlag) {
                return "";
            } else {
                return str;
            }
        }
        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub

            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equalsIgnoreCase("")) {
                dialog.dismiss();

                showAlert(getString(R.string.data_downloaded_successfully));
            } else {
                dialog.dismiss();
                showAlert(getString(R.string.datanotfound) + " " + s);
            }
        }
        public void showAlert(String str) {

            AlertDialog.Builder builder = new AlertDialog.Builder(DownloadActivity.this);
            builder.setTitle("Parinaam");
            builder.setMessage(str).setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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

    }
   /* @Override
    protected void onResume() {
        super.onResume();

        toolbar.setTitle(getString(R.string.address));
    }*/

}
