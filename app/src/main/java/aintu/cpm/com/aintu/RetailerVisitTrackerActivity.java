package aintu.cpm.com.aintu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import aintu.cpm.com.aintu.dailyentry.MenuActivity;
import constent.CommonString;

public class RetailerVisitTrackerActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText retailer_fild_name,retailer_store_name,retailer_store_address,
            retailer_contact_person,retailer_owner_name,retailer_person_meet,retailer_reson_aintu;
    private Spinner retailer_vist_sppiner,retailer_store_visit_sppiner,retailer_status_sppiner,retailer_reason_sppiner,
            retailer_reson_followup_sppiner;
    private TextView icon_date,reportingdate;
    private FloatingActionButton fab;
    private Typeface font;
    private Toolbar toolbar;
    String datePaid;
    private SharedPreferences.Editor editor = null;
    private SharedPreferences preferences;
    private String date, store_intime;
    private String reportdate;;
    private String str_retailer_fild_name, str_retailer_store_name, str_retailer_store_address, str_retailer_contact_person, str_retailer_owner_name,
            str_retailer_person_meet, str_retailer_reson_aintu;
    String user_type;
    int a = 0, b = 0;
    String Store_Name = "",Store_Address="",Store_Contact_Number = "",Store_Owner_Name="",Store_Contact_person="";
    boolean validation = true;

    String[] visittype = {"Select","New Visit","Follow-up 1","Follow 2","Follow-up 2 with supervisor"};
    String[] storevisited = {"Select","Cold Call","Brand refrence","Accompanied by brand representative"};
    String[] status = {"Select","Closed - KYC Collected","Not Interested(outright rejection)","rejection by Aintu"};
    String[] resonforrejectionretailer = {"Select","No reason given","Don't like online cos","Too much effort required","Happy with current business"};
    String[] reasonforfollowup = {"Select","Decision maker not avilable","Retailer Busy","Needs time to think","KYC documents not available"};
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_visit_tracker);

        getId();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");

        editor = preferences.edit();
        user_type = preferences.getString(CommonString.KEY_USERNAME, null);

        setResponseData();

    }
    private  void getId(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  toolbar.setBackgroundColor(Color.parseColor("#808080"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        retailer_fild_name=(EditText)findViewById(R.id.retailer_fild_name);
        retailer_store_name=(EditText)findViewById(R.id.retailer_store_name);
        retailer_store_address=(EditText)findViewById(R.id.retailer_store_address);
        retailer_contact_person=(EditText)findViewById(R.id.retailer_contact_person);
        retailer_owner_name=(EditText)findViewById(R.id.retailer_owner_name);
        retailer_person_meet=(EditText)findViewById(R.id.retailer_person_meet);
        retailer_reson_aintu=(EditText)findViewById(R.id.retailer_reson_aintu);

        icon_date=(TextView)findViewById(R.id.icon_date);
        reportingdate=(TextView)findViewById(R.id.reportingdate);

        retailer_vist_sppiner=(Spinner)findViewById(R.id.retailer_vist_sppiner);
        retailer_store_visit_sppiner=(Spinner)findViewById(R.id.retailer_store_visit_sppiner);
        retailer_status_sppiner=(Spinner)findViewById(R.id.retailer_status_sppiner);
        retailer_reason_sppiner=(Spinner)findViewById(R.id.retailer_reason_sppiner);
        retailer_reson_followup_sppiner=(Spinner)findViewById(R.id.retailer_reson_followup_sppiner);



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
        setSppiner();
       /* retailer_vist_sppiner.setOnClickListener(this);
        retailer_store_visit_sppiner.setOnClickListener(this);
        retailer_status_sppiner.setOnClickListener(this);
        retailer_reason_sppiner.setOnClickListener(this);
        retailer_reson_followup_sppiner.setOnClickListener(this);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon_date:
                a++;
                b = 0;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                break;

            case R.id.fab:




          /*  case R.id.retailer_vist_sppiner:

                break;
            case R.id.retailer_store_visit_sppiner:

                break;
            case R.id.retailer_status_sppiner:

                break;
            case R.id.retailer_reason_sppiner:

                break;
            case R.id.retailer_reson_followup_sppiner:

                break;*/
        }

    }

    @SuppressLint("ValidFragment")
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, mm, dd);
            DatePicker dp = datePickerDialog.getDatePicker();
            dp.setMaxDate(calendar.getTimeInMillis());
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
        toolbar.setTitle("Retailer Visit Tracker");

    }
    private void setResponseData(){
        Intent i1=getIntent();
        if(i1!=null){
            Store_Name= getIntent().getStringExtra(CommonString.KEY_STORE_NAME);
            Store_Address= getIntent().getStringExtra(CommonString.KEY_STORE_ADDRESS);
            Store_Contact_Number= getIntent().getStringExtra(CommonString.KEY_CONTACT_NUMBER);
            Store_Owner_Name= getIntent().getStringExtra(CommonString.KEY_OWNER_NAME);
            Store_Contact_person= getIntent().getStringExtra(CommonString.KEY_CONTECT_PERSON);

            retailer_store_name.setText(Store_Name);
            retailer_store_address.setText(Store_Address);
            retailer_contact_person.setText(Store_Contact_Number);
            retailer_owner_name.setText(Store_Owner_Name);
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

            // NavUtils.navigateUpFromSameTask(this);
            finish();


            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        return super.onOptionsItemSelected(item);
    }



    public void  setSppiner(){
        //

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, visittype);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_vist_sppiner.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, storevisited);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_store_visit_sppiner.setAdapter(aa1);

        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_status_sppiner.setAdapter(aa3);

        ArrayAdapter aa4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, resonforrejectionretailer);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_reason_sppiner.setAdapter(aa4);

        ArrayAdapter aa5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, reasonforfollowup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_reson_followup_sppiner.setAdapter(aa5);

        retailer_vist_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_store_visit_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //new
        retailer_status_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_reason_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        retailer_reson_followup_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
