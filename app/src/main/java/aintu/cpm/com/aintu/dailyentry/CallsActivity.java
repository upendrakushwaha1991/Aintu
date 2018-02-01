package aintu.cpm.com.aintu.dailyentry;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.CallsInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.FolloowupReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.RejectionReasonAintuGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.ReteilerReasonMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StatusMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitedWithMasterGetterSetter;
import constent.CommonString;

public class CallsActivity extends AppCompatActivity implements View.OnClickListener {
    StoreDetailsInsertGetterSetter storeDetailsdata = new StoreDetailsInsertGetterSetter();
    String kycname;
   // StoreDetailsInsertGetterSetter storeDetailsInsertGetterSetter;
    private LinearLayout reason_for_rejection_aintu, kyc_pitch_followup_date, reason_for_rejection_by_retailer, reason_for_followup,
            linear_visit_type,linear_store_visitwith,linear_status;
    private EditText retailer_person_meet,retailer_remark;
    private String strretailer_person_meet,strretailer_person_remark;

    private Spinner retailer_vist_sppiner, retailer_store_visit_sppiner, retailer_status_sppiner, retailer_reason_sppiner,
            retailer_reson_followup_sppiner, retailer_reson_aintu;
    private TextView icon_date, reportingdate;
    private FloatingActionButton fab;
    private Typeface font;
    private Toolbar toolbar;
    String datePaid;
    int a = 0, b = 0;
    String Store_Name = "", Store_Address = "", Store_Contact_Number = "", Store_Owner_Name = "", Store_Contact_person = "";

    CallsInsertGetterSetter callsInsertGetterSetter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    private String date, store_intime;
    String user_type;
    AintuREDB db;
    String spinnerVisitType, spinnerStoreVisitedWith, spinnerStatus, spinnerRejectionForRetailer, spinnerReasonForFollowup;
    String key_id;

    ArrayList<VisitTypeMasterGetterSetter> visitTypeData = new ArrayList<>();
    ArrayList<VisitedWithMasterGetterSetter> storeVisitedWitheData = new ArrayList<>();
    ArrayList<StatusMasterGetterSetter> statusData = new ArrayList<>();
    ArrayList<ReteilerReasonMasterGetterSetter> reasonForRejectionByRetailerData = new ArrayList<>();
    ArrayList<FolloowupReasonMasterGetterSetter> reasonForFollowupData = new ArrayList<>();
    ArrayList<RejectionReasonAintuGetterSetter> rejectionReasonAintuData = new ArrayList<>();


    private ArrayAdapter<CharSequence> vist_type_adapter;
    private ArrayAdapter<CharSequence> store_visited_adapter;
    private ArrayAdapter<CharSequence> status_adapet;
    private ArrayAdapter<CharSequence> reason_for_rejection_adapter;
    private ArrayAdapter<CharSequence> reason_for_followup_adapter;
    private ArrayAdapter<CharSequence> rejection_reason_for_aintu_adapter;
    String cityname, cityid;
    String store_visit_name, store_visit_id, status_name, status_id="0", reason_for_rejection_name,
            reason_for_rejection_id, reason_for_followup_name, reason_for_followup_id, rejection_reson_aintu_name, rejection_reson_aintu_id;

    String store_ALLOW_REJECTION_AINTU,store_ALLOW_KYC_PITCH_DATE,store_ALLOW_REASON_REJECTION_RETAILER,store_ALLOW_REASON_FOLLOWUP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);

        key_id = getIntent().getStringExtra(CommonString.KEY_ID);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        user_type = preferences.getString(CommonString.KEY_USERNAME, null);
        editor = preferences.edit();
        db = new AintuREDB(this);
        db.open();
        storeDetailsdata = db.getStoreDetailsData(key_id);
        kycname=storeDetailsdata.getStore_kyc();
        getId();


    }

    private void getId() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        retailer_person_meet = (EditText) findViewById(R.id.retailer_person_meet);
        retailer_remark = (EditText) findViewById(R.id.retailer_remark);
        retailer_reson_aintu = (Spinner) findViewById(R.id.retailer_reson_aintu);

        icon_date = (TextView) findViewById(R.id.icon_date);
        reportingdate = (TextView) findViewById(R.id.reportingdate);

        retailer_vist_sppiner = (Spinner) findViewById(R.id.retailer_vist_sppiner);
        retailer_store_visit_sppiner = (Spinner) findViewById(R.id.retailer_store_visit_sppiner);
        retailer_status_sppiner = (Spinner) findViewById(R.id.retailer_status_sppiner);
        retailer_reason_sppiner = (Spinner) findViewById(R.id.retailer_reason_sppiner);
        retailer_reson_followup_sppiner = (Spinner) findViewById(R.id.retailer_reson_followup_sppiner);

        reason_for_rejection_aintu = (LinearLayout) findViewById(R.id.reason_for_rejection_aintu);
        kyc_pitch_followup_date = (LinearLayout) findViewById(R.id.kyc_pitch_followup_date);
        reason_for_rejection_by_retailer = (LinearLayout) findViewById(R.id.reason_for_rejection_by_retailer);
        reason_for_followup = (LinearLayout) findViewById(R.id.reason_for_followup);
        linear_visit_type = (LinearLayout) findViewById(R.id.linear_visit_type);
        linear_store_visitwith = (LinearLayout) findViewById(R.id.linear_store_visitwith);
        linear_status = (LinearLayout) findViewById(R.id.linear_status);


        font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        icon_date.setTypeface(font);

//get current date format
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());
        datePaid = formattedDate;
        System.out.println("Current=> " + datePaid);
        reportingdate.setText(datePaid);
        icon_date.setOnClickListener(this);
        fab.setOnClickListener(this);
        callsInsertGetterSetter = new CallsInsertGetterSetter();
        setSppiner();
        if (kycname.equalsIgnoreCase("yes")) {
            reason_for_rejection_aintu.setVisibility(View.GONE);
            kyc_pitch_followup_date.setVisibility(View.GONE);
            reason_for_rejection_by_retailer.setVisibility(View.GONE);
            reason_for_followup.setVisibility(View.GONE);
            linear_store_visitwith.setVisibility(View.GONE);
            linear_status.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_date:
                a++;
                b = 0;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                break;

            case R.id.fab:
              //  Relationship Call
           // String ="Relationship Call";
                if (validation(kycname)) {

                    storeDetailsSaveData();
                }

                break;
        }

    }

    @SuppressLint("ValidFragment")
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            final Calendar calendar1 = Calendar.getInstance();
            int year = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, mm, dd);
            DatePicker dp = datePickerDialog.getDatePicker();
            calendar.add(Calendar.DATE, 60);
            dp.setMaxDate(calendar.getTimeInMillis());
            dp.setMinDate(calendar1.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {

            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            datePaid = year + "/" + month + "/" + day;
            if ((day < 10 && month < 10)) {

                reportingdate.setText("0" + month + "/" + "0" + day + "/" + year);

            } else if (day > 10 && month < 10) {
                reportingdate.setText("0" + month + "/" + day + "/" + year);
            } else if (day < 10 && month > 10) {
                reportingdate.setText(month + "/" + "0" + day + "/" + year);
            } else {
                reportingdate.setText(month + "/" + day + "/" + year);
                System.out.println("<<<<<<<<<" + reportingdate);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Calls");

    }

    private void setResponseData() {
        Intent i1 = getIntent();
        if (i1 != null) {
            Store_Name = getIntent().getStringExtra(CommonString.KEY_STORE_NAME);
            Store_Contact_person = getIntent().getStringExtra(CommonString.KEY_CONTECT_PERSON);
            retailer_person_meet.setText(Store_Contact_person);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {

            finish();


            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        return super.onOptionsItemSelected(item);
    }

    public void setSppiner() {

        visitTypeData = db.geVISIT_TYPE_MASTERData(kycname);
        storeVisitedWitheData = db.geVISITED_WITH_MASTERData();
        statusData = db.geSTATUS_MASTER_LISTData();
        reasonForRejectionByRetailerData = db.geRETAILER_REASON_MASTERata();
        reasonForFollowupData = db.geFOLLOWUP_REASON_MASTERData();
        rejectionReasonAintuData = db.geREJECTION_REASON_AINTUData();


        vist_type_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        vist_type_adapter.add("Select Visit Type");
        for (int i = 0; i < visitTypeData.size(); i++) {
            vist_type_adapter.add(visitTypeData.get(i).getVISIT_TYPE().get(0));
        }
        retailer_vist_sppiner.setAdapter(vist_type_adapter);


        store_visited_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        store_visited_adapter.add("Select Visit Type");
        for (int i = 0; i < storeVisitedWitheData.size(); i++) {
            store_visited_adapter.add(storeVisitedWitheData.get(i).getVISITED_WITH().get(0));
        }
        retailer_store_visit_sppiner.setAdapter(store_visited_adapter);


        status_adapet = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);

        for (int i = 0; i < statusData.size(); i++) {
            status_adapet.add(statusData.get(i).getSTATUS().get(0));
        }
        retailer_status_sppiner.setAdapter(status_adapet);


        reason_for_rejection_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        reason_for_rejection_adapter.add("Select Reason for Rejection By Retailer");
        for (int i = 0; i < reasonForRejectionByRetailerData.size(); i++) {
            reason_for_rejection_adapter.add(reasonForRejectionByRetailerData.get(i).getRETAILER_REASON().get(0));
        }
        retailer_reason_sppiner.setAdapter(reason_for_rejection_adapter);

        reason_for_followup_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        reason_for_followup_adapter.add("Select Reason for Followup");
        for (int i = 0; i < reasonForFollowupData.size(); i++) {
            reason_for_followup_adapter.add(reasonForFollowupData.get(i).getFREASON().get(0));
        }
        retailer_reson_followup_sppiner.setAdapter(reason_for_followup_adapter);

        rejection_reason_for_aintu_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        rejection_reason_for_aintu_adapter.add("Select Rejection Reason for Aintu");
        for (int i = 0; i < rejectionReasonAintuData.size(); i++) {
            rejection_reason_for_aintu_adapter.add(rejectionReasonAintuData.get(i).getAREASON().get(0));
        }
        retailer_reson_aintu.setAdapter(rejection_reason_for_aintu_adapter);

        retailer_reson_aintu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    rejection_reson_aintu_name = rejectionReasonAintuData.get(position - 1).getAREASON().get(0);
                    rejection_reson_aintu_id = rejectionReasonAintuData.get(position - 1).getAREASON_ID().get(0);
                } else {
                    rejection_reson_aintu_id = "0";
                    rejection_reson_aintu_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        retailer_vist_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              /*  if (position != 0) {
                    cityname = visitTypeData.get(position - 1).getVISIT_TYPE().get(0);
                    cityid = visitTypeData.get(position - 1).getVISIT_TYPE_ID().get(0);
                    cityname="Relationship Call";
                } else {
                    cityid = "0";
                    cityname = "";
                }*/
                if (position != 0) {
                    cityname = visitTypeData.get(position - 1).getVISIT_TYPE().get(0);
                    cityid = visitTypeData.get(position - 1).getVISIT_TYPE_ID().get(0);
                   // cityname="Relationship Call";
                    if (cityname.equalsIgnoreCase("Relationship Call")){

                        reason_for_rejection_aintu.setVisibility(View.GONE);
                        kyc_pitch_followup_date.setVisibility(View.GONE);
                        reason_for_rejection_by_retailer.setVisibility(View.GONE);
                        reason_for_followup.setVisibility(View.GONE);
                        linear_store_visitwith.setVisibility(View.GONE);
                        linear_status.setVisibility(View.GONE);

                        retailer_remark.setText("");
                        reportingdate.setText("");
                        datePaid="";

                        reason_for_rejection_id="0";
                        store_visit_id="0";
                        status_id="0";
                        rejection_reson_aintu_id="0";
                        reason_for_followup_id="0";


                    }
                } else {
                    cityid = "0";
                    cityname = "";
                   /* reason_for_rejection_aintu.setVisibility(View.VISIBLE);
                    kyc_pitch_followup_date.setVisibility(View.VISIBLE);
                    reason_for_rejection_by_retailer.setVisibility(View.VISIBLE);
                    reason_for_followup.setVisibility(View.VISIBLE);
                    linear_store_visitwith.setVisibility(View.VISIBLE);
                    linear_status.setVisibility(View.VISIBLE);
*/
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_store_visit_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    store_visit_name = storeVisitedWitheData.get(position - 1).getVISITED_WITH().get(0);
                    store_visit_id = storeVisitedWitheData.get(position - 1).getWVISIT_ID().get(0);
                } else {
                    store_visit_id = "0";
                    store_visit_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        retailer_status_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    status_name = statusData.get(position).getSTATUS().get(0);
                    status_id = statusData.get(position).getSTATUS_ID().get(0);
                    store_ALLOW_REJECTION_AINTU = statusData.get(position).getALLOW_REJECTION_AINTU().get(0);
                    store_ALLOW_KYC_PITCH_DATE = statusData.get(position).getALLOW_KYC_PITCH_DATE().get(0);
                    store_ALLOW_REASON_REJECTION_RETAILER = statusData.get(position).getALLOW_REASON_REJECTION_RETAILER().get(0);
                    store_ALLOW_REASON_FOLLOWUP = statusData.get(position).getALLOW_REASON_FOLLOWUP().get(0);

                    if (store_ALLOW_REJECTION_AINTU.equals("1")){
                        reason_for_rejection_aintu.setVisibility(View.VISIBLE);

                    }else {
                        reason_for_rejection_aintu.setVisibility(View.GONE);

                    }
                    if (store_ALLOW_KYC_PITCH_DATE.equals("1")){

                        kyc_pitch_followup_date.setVisibility(View.VISIBLE);

                    }else {

                        kyc_pitch_followup_date.setVisibility(View.GONE);

                    }
                    if (store_ALLOW_REASON_REJECTION_RETAILER.equals("1")){

                        reason_for_rejection_by_retailer.setVisibility(View.VISIBLE);

                    }else {

                        reason_for_rejection_by_retailer.setVisibility(View.GONE);

                    }
                    if (store_ALLOW_REASON_FOLLOWUP.equals("1")){

                        reason_for_followup.setVisibility(View.VISIBLE);

                    }else {

                        reason_for_followup.setVisibility(View.GONE);
                    }

                } else {
                    status_id = "0";
                    store_ALLOW_REJECTION_AINTU="0";
                    store_ALLOW_KYC_PITCH_DATE="0";
                    store_ALLOW_REASON_REJECTION_RETAILER="0";
                    store_ALLOW_REASON_FOLLOWUP="0";


                    status_name = "";
                    reason_for_rejection_aintu.setVisibility(View.VISIBLE);
                    kyc_pitch_followup_date.setVisibility(View.VISIBLE);
                    reason_for_rejection_by_retailer.setVisibility(View.VISIBLE);
                    reason_for_followup.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_reason_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position != 0) {
                    reason_for_rejection_name = reasonForRejectionByRetailerData.get(position - 1).getRETAILER_REASON().get(0);
                    reason_for_rejection_id = reasonForRejectionByRetailerData.get(position - 1).getRETAILER_REASON_ID().get(0);
                } else {
                    reason_for_rejection_id = "0";
                    reason_for_rejection_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        retailer_reson_followup_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    reason_for_followup_name = reasonForFollowupData.get(position - 1).getFREASON().get(0);
                    reason_for_followup_id = reasonForFollowupData.get(position - 1).getFREASON_ID().get(0);
                } else {
                    reason_for_followup_id = "0";
                    reason_for_followup_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void storeDetailsSaveData() {

        callsInsertGetterSetter.setCalls_person_meet(strretailer_person_meet);
        callsInsertGetterSetter.setCaals_remark(strretailer_person_remark);
        callsInsertGetterSetter.setCalls_visit_type_CD(cityid);
        callsInsertGetterSetter.setCalls_visit_type(cityname);
        callsInsertGetterSetter.setCalls_store_visitet_with_CD(store_visit_id);
        callsInsertGetterSetter.setCalls_store_visitet_with(store_visit_name);
        callsInsertGetterSetter.setCalls_status_CD(status_id);
        callsInsertGetterSetter.setCalls_status(status_name);
        callsInsertGetterSetter.setCalls_reason_for_rejection_aintu(rejection_reson_aintu_id);
        callsInsertGetterSetter.setCalls_reson_for_rejection(rejection_reson_aintu_id);
        callsInsertGetterSetter.setCalls_followup_date(date);
        callsInsertGetterSetter.setCalls_reson_for_rejection_retailer_CD(reason_for_rejection_id);
        callsInsertGetterSetter.setCalls_reson_for_rejection_retailer(reason_for_rejection_name);
        callsInsertGetterSetter.setCalls_reson_for_followup_CD(reason_for_followup_id);
        callsInsertGetterSetter.setCalls_reson_for_followup(reason_for_followup_name);

        db.open();

      /*  long id = db.InsertCallsData(callsInsertGetterSetter);*/
        long id = db.InsertCallsData(callsInsertGetterSetter, key_id);
        System.out.println("id" + id);
        if (id > 0) {


            Snackbar.make(fab, "Data Inserted saved Calls", Snackbar.LENGTH_SHORT).show();
            finish();
        } else {
            Snackbar.make(fab, "Data not saved", Snackbar.LENGTH_SHORT).show();
        }

    }

    public boolean validation(String visittype) {

        boolean value = true;
        if (visittype.equalsIgnoreCase("Yes")){
             value = true;
            if (retailer_person_meet.getText().toString().isEmpty()) {
                value = false;
                showMessage("Please Enter Store Meet");
            } else if (retailer_vist_sppiner.getSelectedItemPosition() == 0) {
                value = false;
                showMessage("Please Select Visit Type");
            }
            else if (retailer_remark.getText().toString().isEmpty()) {
                value = false;
                showMessage("Please Select remark");
            }
            else {
                strretailer_person_meet = retailer_person_meet.getText().toString().replaceAll("[&^<>{}'$]", " ");
                strretailer_person_remark= retailer_remark.getText().toString().replaceAll("[&^<>{}'$]", " ");
                // strretailer_reson_aintu=retailer_reson_aintu.getText().toString();

                value = true;

            }
        }else {
             value = true;
            if (retailer_person_meet.getText().toString().isEmpty()) {
                value = false;
                showMessage("Please Enter Store Meet");
            } else if (retailer_vist_sppiner.getSelectedItemPosition() == 0) {
                value = false;
                showMessage("Please Select Visit Type");
            } else if (retailer_store_visit_sppiner.getSelectedItemPosition() == 0) {
                value = false;
                showMessage("Please Select Visited With");
            } else if (retailer_status_sppiner.getSelectedItemPosition() == 0) {
                value = false;
                showMessage("Please Select Status");
            }
            else if (retailer_remark.getText().toString().isEmpty()) {
                value = false;
                showMessage("Please Select remark");
            }
         /*   else if (retailer_reson_aintu.getSelectedItemPosition()==0){
                value=false;
                showMessage("Please Select Rejection Reason Aintu");
            }*/

            else {
                strretailer_person_meet = retailer_person_meet.getText().toString().replaceAll("[&^<>{}'$]", " ");
                strretailer_person_remark= retailer_remark.getText().toString().replaceAll("[&^<>{}'$]", " ");

                value = true;

            }
        }
      /*  boolean value = true;
        if (retailer_person_meet.getText().toString().isEmpty()) {
            value = false;
            showMessage("Please Enter Store Meet");
        } else if (retailer_vist_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Visit Type");
        }*//* else if (retailer_store_visit_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Visited With");
        } else if (retailer_status_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Status");
        }*/
       /* else if (retailer_reson_aintu.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Rejection Reason Aintu");
        }*/


       /* else if (retailer_reson_aintu.getText().toString().isEmpty())
        {
            value=false;
            showMessage("Please Enter Reason for Rejection by Aintu");
        }
        else if (retailer_reason_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Reason for Rejection by Retailer");
        }
        else if (retailer_reson_followup_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Reason for Followup");
        }*/
      /*  else {
            strretailer_person_meet = retailer_person_meet.getText().toString().replaceAll("[&^<>{}'$]", " ");
            strretailer_person_remark= retailer_remark.getText().toString().replaceAll("[&^<>{}'$]", " ");
            // strretailer_reson_aintu=retailer_reson_aintu.getText().toString();

            value = true;

        }*/
        return value;

    }


    public boolean validation1() {

        boolean value = true;
        if (retailer_person_meet.getText().toString().isEmpty()) {
            value = false;
            showMessage("Please Enter Store Meet");
        } else if (retailer_vist_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Visit Type");
        } else if (retailer_store_visit_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Visited With");
        } else if (retailer_status_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Status");
        }
        else if (retailer_reson_aintu.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Rejection Reason Aintu");
        }


       /* else if (retailer_reson_aintu.getText().toString().isEmpty())
        {
            value=false;
            showMessage("Please Enter Reason for Rejection by Aintu");
        }
        else if (retailer_reason_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Reason for Rejection by Retailer");
        }
        else if (retailer_reson_followup_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Reason for Followup");
        }*/

        return value;

    }

    public void showMessage(String message) {

        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show();

    }

}
