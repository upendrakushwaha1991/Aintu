package aintu.cpm.com.aintu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import aintu.cpm.com.aintu.dailyentry.MenuActivity;
import aintu.cpm.com.aintu.util.Utilities;

public class RetailerEnrollmentFormActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText retailer_store_name,retailer_complete_address,retailer_locality,retailer_opening_time,retailer_closing_time,
            retailer_store_phonenumber, retailer_entity_proofno,retailer_store_emailid,retailer_gps_location,retailer_owner_name,
            retailer_owner_contactnoctno,retailer_id_proofno, retailer_address_proof_type,retailer_address_proof;
    private Spinner retailer_type_entity_sppiner,retailer_lead_closure_sppiner,retailer_outletformat_sppiner,retailer_billingsystem_sppiner,
            retailer_outlettype_sppiner,retailer_billing_point_sppiner,retailer_proof_entiity_sppiner,
            retailer_gender_sppiner,retailer_id_proof_sppiner;
    private String str_retailer_store_name,str_retailer_complete_address,str_retailer_locality,str_retailer_opening_time,str_retailer_closing_time,
            str_retailer_store_phonenumber,str_retailer_entity_proofno,str_retailer_store_emailid,str_retailer_gps_location,str_retailer_owner_name,
            str_retailer_owner_contactnoctno,str_retailer_id_proofno,str_retailer_address_proof_type,str_retailer_address_proof;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    String[] userNames = {"Select","Male","Female","Other"};

//typeofentiity leadclosurereson outletformat billingsystem outlettype numberofnillingpoint idprooftype
    String[] typeofentiity = {"Select","Properietorship","Partnership","Pvt Ltd","Limited Liability Company"};
    String[] leadclosurereson = {"Select","Cold Call","Company List","Interoduction dorm Brand Representative","Referral from another retailer","Other: [Need text field]"};
    String[] outletformat = {"Select","Open","Closed","Semi-closed"};
   // String[] billingsystem = {"Select","Supermarket","General Store(Only Packaged Food Including Atta, oil)","Chemist","Liquor"};
    String[] outlettype = {"Select","1","2","3","4","5"};
    String[] proofofentiity = {"Select","Shop $ EStablishment License","Seles Tax Certificate","Company Certificate(Pvt Ltd only)"};
    String[] idprooftype = {"Select","PAN Card","Adhar Card","Voter ID","Passport","Driving License","Ration Card"};

    String[] billingsystem = {"Select","POS","NON-POS"};
    String gender;
    boolean validation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_enrollment_form);
        getId();
    }
    private  void  getId(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setBackgroundColor(Color.parseColor("#808080"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab=(FloatingActionButton)findViewById(R.id.fab);

        retailer_store_name=(EditText)findViewById(R.id.retailer_store_name);
        retailer_complete_address=(EditText)findViewById(R.id.retailer_complete_address);
        retailer_locality=(EditText)findViewById(R.id.retailer_locality);
        retailer_opening_time=(EditText)findViewById(R.id.retailer_opening_time);
        retailer_closing_time=(EditText)findViewById(R.id.retailer_closing_time);
        retailer_store_phonenumber=(EditText)findViewById(R.id.retailer_store_phonenumber);
        retailer_entity_proofno=(EditText)findViewById(R.id.retailer_entity_proofno);
        retailer_store_emailid=(EditText)findViewById(R.id.retailer_store_emailid);
        retailer_gps_location=(EditText)findViewById(R.id.retailer_gps_location);
        retailer_owner_name=(EditText)findViewById(R.id.retailer_owner_name);
        retailer_owner_contactnoctno=(EditText)findViewById(R.id.retailer_owner_contactnoctno);
        retailer_id_proofno=(EditText)findViewById(R.id.retailer_id_proofno);
        retailer_address_proof_type=(EditText)findViewById(R.id.retailer_address_proof_type);
        retailer_address_proof=(EditText)findViewById(R.id.retailer_address_proof);

        retailer_type_entity_sppiner=(Spinner)findViewById(R.id.retailer_type_entity_sppiner);
        retailer_lead_closure_sppiner=(Spinner)findViewById(R.id.retailer_lead_closure_sppiner);
        retailer_outletformat_sppiner=(Spinner)findViewById(R.id.retailer_outletformat_sppiner);
        retailer_billingsystem_sppiner=(Spinner)findViewById(R.id.retailer_billingsystem_sppiner);
        retailer_outlettype_sppiner=(Spinner)findViewById(R.id.retailer_outlettype_sppiner);
        retailer_billing_point_sppiner=(Spinner)findViewById(R.id.retailer_billing_point_sppiner);
        retailer_proof_entiity_sppiner=(Spinner)findViewById(R.id.retailer_proof_entiity_sppiner);
        retailer_gender_sppiner=(Spinner)findViewById(R.id.retailer_gender_sppiner);
        retailer_id_proof_sppiner=(Spinner)findViewById(R.id.retailer_id_proof_sppiner);
        setSppiner();

       /* ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_gender_sppiner.setAdapter(aa);

        fab.setOnClickListener(this);

        retailer_gender_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

     /*   retailer_type_entity_sppiner.setOnClickListener(this);
        retailer_lead_closure_sppiner.setOnClickListener(this);
        retailer_outletformat_sppiner.setOnClickListener(this);
        retailer_billingsystem_sppiner.setOnClickListener(this);
        retailer_outlettype_sppiner.setOnClickListener(this);
        retailer_billing_point_sppiner.setOnClickListener(this);
        retailer_proof_entiity_sppiner.setOnClickListener(this);
        retailer_gender_sppiner.setOnClickListener(this);
        retailer_id_proof_sppiner.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if(ValidationMethod()){
                    if (Utilities.isConnectingToInternet(RetailerEnrollmentFormActivity.this)) {

                        Intent i1=new Intent(RetailerEnrollmentFormActivity.this,MenuActivity.class);
                        startActivity(i1);
                        finish();
                    } else {
                        Snackbar.make(fab, "Internet Not Connected", Snackbar.LENGTH_SHORT).show();
                    }

                  /*  Intent i1=new Intent(RetailerEnrollmentFormActivity.this,MainActivity.class);
                    startActivity(i1);
                    finish();*/
                }

                break;

          /*  case R.id.retailer_type_entity_sppiner:

                break;

            case R.id.retailer_lead_closure_sppiner:

                break;
            case R.id.retailer_outletformat_sppiner:

                break;
            case R.id.retailer_billingsystem_sppiner:

                break;
            case R.id.retailer_outlettype_sppiner:

                break;
            case R.id.retailer_billing_point_sppiner:

                break;
            case R.id.retailer_proof_entiity_sppiner:

                break;
            case R.id.retailer_gender_sppiner:

                break;
            case R.id.retailer_id_proof_sppiner:

                break;*/

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Retailer Enrollment");

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
    private boolean ValidationMethod() {

        str_retailer_store_name = retailer_store_name.getText().toString().trim();
        str_retailer_complete_address = retailer_complete_address.getText().toString().trim();
        str_retailer_locality = retailer_locality.getText().toString().trim();
        str_retailer_opening_time = retailer_opening_time.getText().toString().trim();
        str_retailer_closing_time = retailer_closing_time.getText().toString().trim();
        str_retailer_store_phonenumber = retailer_store_phonenumber.getText().toString().trim();
        str_retailer_entity_proofno = retailer_entity_proofno.getText().toString().trim();
        str_retailer_store_emailid = retailer_store_emailid.getText().toString().trim();
        str_retailer_gps_location = retailer_gps_location.getText().toString().trim();
        str_retailer_owner_name = retailer_owner_name.getText().toString().trim();
        str_retailer_owner_contactnoctno = retailer_owner_contactnoctno.getText().toString().trim();
        str_retailer_id_proofno = retailer_id_proofno.getText().toString().trim();
        str_retailer_address_proof_type = retailer_address_proof_type.getText().toString().trim();
        str_retailer_address_proof = retailer_address_proof.getText().toString().trim();






        return validation;
    }
    public void  setSppiner(){
        //
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_gender_sppiner.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeofentiity);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_type_entity_sppiner.setAdapter(aa1);

        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leadclosurereson);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_lead_closure_sppiner.setAdapter(aa3);

        ArrayAdapter aa4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, outletformat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_outletformat_sppiner.setAdapter(aa4);

        ArrayAdapter aa5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, billingsystem);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_billingsystem_sppiner.setAdapter(aa5);

        ArrayAdapter aa6 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, outlettype);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_outlettype_sppiner.setAdapter(aa6);

        ArrayAdapter aa7 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, billingsystem);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_billing_point_sppiner.setAdapter(aa7);

        ArrayAdapter aa8 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, proofofentiity);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_proof_entiity_sppiner.setAdapter(aa8);

        ArrayAdapter aa9 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, idprooftype);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_id_proof_sppiner.setAdapter(aa9);

        fab.setOnClickListener(this);

        retailer_gender_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_type_entity_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        retailer_lead_closure_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_outletformat_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        retailer_billingsystem_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //6
        retailer_outlettype_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_billing_point_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_proof_entiity_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        retailer_id_proof_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
