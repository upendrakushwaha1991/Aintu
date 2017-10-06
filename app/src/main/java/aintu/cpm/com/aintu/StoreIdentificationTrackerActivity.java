package aintu.cpm.com.aintu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

public class StoreIdentificationTrackerActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText retailer_store_name,retailer_complete_address,retailer_contect_number;
    private Spinner retailer_store_format_sppiner,retailer_store_carpet_sppiner,retailer_freezer_sppiner,retailer_available_sppiner;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private  String str_retailer_store_name,str_retailer_complete_address,str_retailer_contect_number;
    boolean validation = true;

    String[] storevisited = {"Select","Cold Call","Brand refrence","Accompanied by brand representative"};
    String[] storeformat = {"Select","Open","Closed","Semi-closed"};
    String[] freezerchilleravailable = {"Select","YES","NO"};
    String[] Multiselected = {"Select","Sensodyne,Colgate Total/Charol,Close-Up Deep Fresh","Colgate/Listerine Mouthwash","Fructis/Loreal Oil n Cream","Body Wash-Nivea, Nivea, Palmolive,Pears"};
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_identification_tracker);
        getId();
    }
    private  void  getId(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        retailer_store_name=(EditText)findViewById(R.id.retailer_store_name);
        retailer_complete_address=(EditText)findViewById(R.id.retailer_complete_address);
        retailer_contect_number=(EditText)findViewById(R.id.retailer_contect_number);

        retailer_store_format_sppiner=(Spinner)findViewById(R.id.retailer_store_format_sppiner);
        retailer_store_carpet_sppiner=(Spinner)findViewById(R.id.retailer_store_carpet_sppiner);
        retailer_freezer_sppiner=(Spinner)findViewById(R.id.retailer_freezer_sppiner);
        retailer_available_sppiner=(Spinner)findViewById(R.id.retailer_available_sppiner);

        fab=(FloatingActionButton) findViewById(R.id.fab);

      /*  retailer_store_format_sppiner.setOnClickListener(this);
        retailer_store_carpet_sppiner.setOnClickListener(this);
        retailer_freezer_sppiner.setOnClickListener(this);
        retailer_available_sppiner.setOnClickListener(this);*/
        fab.setOnClickListener(this);
        setSppiner();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.fab:
                if (ValidationMethod()){
                    Intent i1=new Intent(StoreIdentificationTrackerActivity.this,MenuActivity.class);
                    startActivity(i1);
                    break;
                }

         /*   case R.id.retailer_store_carpet_sppiner:

                break;
            case R.id.retailer_freezer_sppiner:

                break;
            case R.id.retailer_available_sppiner:

                break;*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Store Identification Tracker");
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


        return validation;
    }
    public void  setSppiner(){

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, storeformat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_store_format_sppiner.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, storevisited);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_store_carpet_sppiner.setAdapter(aa1);

        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, freezerchilleravailable);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_freezer_sppiner.setAdapter(aa3);

        ArrayAdapter aa4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Multiselected);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        retailer_available_sppiner.setAdapter(aa4);

        retailer_store_format_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_store_carpet_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        retailer_freezer_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();

                // customerDetails.setSupervisorname(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        retailer_available_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
