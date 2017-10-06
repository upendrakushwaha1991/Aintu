package aintu.cpm.com.aintu.dailyentry;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.CoverageBean;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.RetailerVisitTrackerActivity;
import aintu.cpm.com.aintu.util.Utilities;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;
import constent.CommonString;

public class StoreDetailsActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    ArrayList<CityMasterGetterSetter> reasondata = new ArrayList<CityMasterGetterSetter>();
    //CityMasterGetterSetter cityMasterDetails;
    String cityname,cityid;
    private ArrayAdapter<CharSequence> city_adapter;
    private Spinner store_details_city;
    private EditText store_name,store_address,store_details_pincode,store_details_state,store_owner_person,
            store_phone,store_mobile,store_geotag;
    private String strstore_name,strstore_address,strstore_details_city,strstore_details_pincode,strstore_details_state,strstore_owner_person,
            strstore_phone,strstore_mobile,strstore_geotag;

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    private String date, store_intime;
    String user_type;
    AintuREDB db;
    StoreDetailsInsertGetterSetter storeDetailsInsertGetterSetter;
    String key_id,mode;
    CoverageBean coverageBean;
    StoreDetailsInsertGetterSetter sd;
    String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        key_id=getIntent().getStringExtra(CommonString.KEY_ID);
        mode = getIntent().getStringExtra(CommonString.KEY_MODE);
        coverageBean = (CoverageBean) getIntent().getSerializableExtra(CommonString.KEY_COVERAGE_STATUS);
        getId();


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        user_type = preferences.getString(CommonString.KEY_USERNAME, null);
        editor = preferences.edit();
        db=new AintuREDB(this);
        db.open();
        reasondata = db.getCityMasterData();

        city_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        city_adapter.add("Select City");

        for (int i = 0; i < reasondata.size(); i++) {
            city_adapter.add(reasondata.get(i).getCITY().get(0));

        }

        store_details_city.setAdapter(city_adapter);

        city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        store_details_city.setOnItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Store Details");
        if (mode.equalsIgnoreCase(CommonString.KEY_FROM_JCP_STORE) ||mode.equalsIgnoreCase(CommonString.KEY_FROM_MENU)){
            sd = db.getStoreDetailsData(key_id);
            if (!sd.getStore_name().equals("")){

                store_name.setText(sd.getStore_name());
                store_address.setText(sd.getStore_address());
                store_details_pincode.setText(sd.getStore_pincode());
                store_details_state.setText(sd.getStore_state());
                store_owner_person.setText(sd.getStore_owner_person());
                store_mobile.setText(sd.getStore_mobile());
                store_phone.setText(sd.getStore_phone());
                store_id=sd.getStore_cd();



                int pos=0;
                for (int i=0;i<reasondata.size();i++){
                    if (sd.getCity_cd().equals(reasondata.get(i).getCITY_CD().get(0))){
                        pos=i+1;
                        break;
                    }
                }
                store_details_city.setSelection(pos);
                //need to set city spinner selection
                // store_details_city.set
            }
        }

    }

    private void getId(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        store_name=(EditText)findViewById(R.id.store_name);
        store_address=(EditText)findViewById(R.id.store_address);
        store_details_city=(Spinner) findViewById(R.id.store_details_city);
        store_details_pincode=(EditText)findViewById(R.id.store_details_pincode);
        store_details_state=(EditText)findViewById(R.id.store_details_state);
        store_owner_person=(EditText)findViewById(R.id.store_owner_person);
        store_phone=(EditText)findViewById(R.id.store_phone);
        store_mobile=(EditText)findViewById(R.id.store_mobile);
        store_geotag=(EditText)findViewById(R.id.store_geotag);

        fab.setOnClickListener(this);
        storeDetailsInsertGetterSetter = new StoreDetailsInsertGetterSetter();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (validation()) {

                    storeDetailsSaveData();
                }

                break;
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


    public void storeDetailsSaveData() {
        storeDetailsInsertGetterSetter.setStore_name(strstore_name);
        storeDetailsInsertGetterSetter.setStore_address(strstore_address);
        storeDetailsInsertGetterSetter.setStore_city(cityname);
        storeDetailsInsertGetterSetter.setCity_cd(cityid);
        storeDetailsInsertGetterSetter.setStore_state(strstore_details_state);
        storeDetailsInsertGetterSetter.setStore_owner_person(strstore_owner_person);
        storeDetailsInsertGetterSetter.setStore_phone(strstore_phone);
        storeDetailsInsertGetterSetter.setStore_mobile(strstore_mobile);
        storeDetailsInsertGetterSetter.setStore_pincode(strstore_details_pincode);
        storeDetailsInsertGetterSetter.setKey_id(key_id);
        storeDetailsInsertGetterSetter.setUpload_status("N");

        db.open();

        if (mode.equals(CommonString.KEY_FROM_ADD_STORE)){
            store_id="0";

        }
        long id;
        if (mode.equals(CommonString.KEY_FROM_MENU)){
            id = db.UpdateStoreDetailsData(storeDetailsInsertGetterSetter,store_id);
        }else {
            id = db.InsertStoreDetailsData(storeDetailsInsertGetterSetter,store_id,date);
        }

        System.out.println("id" + id);
        if (id > 0) {

            if (mode.equals(CommonString.KEY_FROM_ADD_STORE)){
                db.InsertCoverageData(coverageBean,id+"");

                Intent i1 = new Intent(StoreDetailsActivity.this, MenuActivity.class);
                i1.putExtra(CommonString.KEY_ID,id+"");
                startActivity(i1);
                finish();
            }

            Snackbar.make(fab, "Data Inserted saved Store Details", Snackbar.LENGTH_SHORT).show();
            finish();
        } else {
            Snackbar.make(fab, "Data not saved", Snackbar.LENGTH_SHORT).show();
        }

    }

    public  boolean validation(){

        boolean  value=true;
        if (store_name.getText().toString().isEmpty())
        {
            value=false;
            showMessage("Please Enter Store Name");
        }
        else if (store_address.getText().toString().isEmpty()){
            value=false;
            showMessage("Please Enter Store Address");
        }
        else if (store_details_city.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select City");
        }
        else if (store_details_pincode.getText().toString().isEmpty()){
            value=false;
            showMessage("Please Enter Valid Pincode");
        }
        else if (store_owner_person.getText().toString().isEmpty()){
            value=false;
            showMessage("Please Enter Store Owner Person");
        }
        else if (store_phone.length()<10){
            value=false;
            showMessage("Please Enter Valid Phone");
        }
        else if (store_mobile.length()<10){
            value=false;
            showMessage("Please Enter Valid Mobile");
        }
        else {
            strstore_name=store_name.getText().toString();
            strstore_address=store_address.getText().toString();
            strstore_details_pincode=store_details_pincode.getText().toString();
            strstore_details_state=store_details_state.getText().toString();
            strstore_owner_person=store_owner_person.getText().toString();
            strstore_phone=store_phone.getText().toString();
            strstore_mobile=store_mobile.getText().toString();
            value =true;
        }

        return  value;

    }

    public void showMessage(String message){

        Snackbar.make(fab,message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.store_details_city:
                if (position != 0) {
                    cityname = reasondata.get(position-1).getCITY().get(0);
                    cityid = reasondata.get(position-1).getCITY_CD().get(0);
                    break;
                }
                else {
                    cityid="0";
                    cityname="";
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
