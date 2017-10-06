package aintu.cpm.com.aintu.dailyentry;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.ProfileInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.xmlGetterSetter.BillingTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.BrandMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.EntityTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.RejectionReasonAintuGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreCategoryGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreFormateMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitTypeMasterGetterSetter;
import constent.CommonString;

public class ProfileActivity extends AppCompatActivity implements  View.OnClickListener{
    private LinearLayout lin_pos;
   // ArrayList<StoreDetailsInsertGetterSetter> store_list = new ArrayList<>();
    ArrayList<StoreCategoryGetterSetter> store_list = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText profile_opening_time,profile_closing_time,profile_store_carpet,profile_name_of_pos;
    private int mYear, mMonth, mDay, mHour, mMinute, mHour1, mMinute1;
    String str_profile_opening_time="",str_profile_closing_time="",str_profile_store_carpet,str_profile_name_of_pos;
    private TextView icon_timeopening,icon_timeclosing;
    String ste_icon_timeopening,str_icon_timeclosing;
    private Typeface font;
    private Spinner profile_store_format_sppiner,profile_freezer_sppiner,profile_available_sppiner;
    String spinner_profile_store_format_sppiner,spinner_profile_store_carpet_sppiner,spinner_profile_profile_freezer_sppiner,
            spinner_profile_profile_available_sppiner;

    private Spinner profile_type_entity_sppiner,profile_outletformat_sppiner,profile_billingsystem_sppiner,
            profile_outlettype_sppiner,profile_billing_point_sppiner;
    String spinner_profile_type_entity_sppiner,spinner_profile_outletformat_sppiner,spinner_profile_billingsystem_sppiner,
            spinner_profile_outlettype_sppiner,spinner_profile_billing_point_sppiner;

    private Toolbar toolbar;
    private FloatingActionButton fab;

    String[] freezerchilleravailable = {"Select","YES","NO"};
    String[] outletformat = {"Select","Open","Closed","Semi-closed"};
    String[] billingcount = {"Select","1","2","3","4","5","6","7","8","9","10"};

    ProfileInsertGetterSetter profileInsertGetterSetter;
  //  StoreCategoryGetterSetter storeCategoryGetterSetter;
    ArrayList<StoreCategoryGetterSetter>storeCategoryGetterSetter=new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    private String date, store_intime;
    String user_type;
    AintuREDB db;
    String key_id;


    ArrayList<StoreFormateMasterGetterSetter> storeFormateData = new ArrayList<>();
    ArrayList<BrandMasterGetterSetter> availableBrandData = new ArrayList<>();
    ArrayList<EntityTypeMasterGetterSetter> typeOfEntityData = new ArrayList<>();
    ArrayList<BillingTypeMasterGetterSetter> billingSystemData = new ArrayList<>();
    ArrayList<StoreTypeMasterGetterSetter> outletTypeData = new ArrayList<>();

    private ArrayAdapter<CharSequence> store_formate_adapter;
    private ArrayAdapter<CharSequence> available_brand_adapter;
    private ArrayAdapter<CharSequence> type_of_entity_adapter;
    private ArrayAdapter<CharSequence> billing_system_adapter;
    private ArrayAdapter<CharSequence> outlet_type_adapter;

    String store_format_name,store_format_id,store_carmet_area_name,store_carmet_area_id, frezzer_chiller_adapet_name,
            frezzer_chiller_adapet_id,available_brand_name,available_brand_id,type_of_entity_name,type_of_entity_id,billing_system_name,
            billing_system_id,outlet_type_name,outlet_type_id,number_of_billing_point_name,number_of_billing_point_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        key_id=getIntent().getStringExtra(CommonString.KEY_ID);
        db=new AintuREDB(this);
        db.open();
        getId();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        user_type = preferences.getString(CommonString.KEY_USERNAME, null);
        editor = preferences.edit();

    }
    private  void  getId(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        profile_opening_time=(EditText)findViewById(R.id.profile_opening_time);
        profile_closing_time=(EditText)findViewById(R.id.profile_closing_time);
        profile_name_of_pos=(EditText)findViewById(R.id.profile_name_of_pos);
        lin_pos=(LinearLayout)findViewById(R.id.lin_pos);

        profile_store_format_sppiner=(Spinner)findViewById(R.id.profile_store_format_sppiner);
        profile_store_carpet=(EditText) findViewById(R.id.profile_store_carpet);
        profile_freezer_sppiner=(Spinner)findViewById(R.id.profile_freezer_sppiner);
        profile_available_sppiner=(Spinner)findViewById(R.id.profile_available_sppiner);
        recyclerView=(RecyclerView) findViewById(R.id.drawer_layout_recycle);
        profile_type_entity_sppiner=(Spinner)findViewById(R.id.profile_type_entity_sppiner);
        profile_outletformat_sppiner=(Spinner)findViewById(R.id.profile_outletformat_sppiner);
        profile_billingsystem_sppiner=(Spinner)findViewById(R.id.profile_billingsystem_sppiner);
        profile_outlettype_sppiner=(Spinner)findViewById(R.id.profile_outlettype_sppiner);
        profile_billing_point_sppiner=(Spinner)findViewById(R.id.profile_billing_point_sppiner);
        icon_timeopening=(TextView)findViewById(R.id.icon_timeopening);
        icon_timeclosing=(TextView)findViewById(R.id.icon_timeclosing);

        font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        icon_timeopening.setTypeface(font);
        icon_timeclosing.setTypeface(font);

        fab=(FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);
        icon_timeopening.setOnClickListener(this);
        icon_timeclosing.setOnClickListener(this);

        profileInsertGetterSetter=new ProfileInsertGetterSetter();
        setSppiner();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.fab:


                if (validation()) {

                    storeDetailsSaveData();

                }

                break;
            case R.id.icon_timeopening:

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String  min= "0"+String.valueOf(minute);
                                min = min.substring(min.length() - 2);

                                String  hours= "0"+String.valueOf(hourOfDay);
                                hours = hours.substring(hours.length() - 2);

                                str_profile_opening_time=(hours + ":" + min);
                                profile_opening_time.setText(str_profile_opening_time);
                                profile_closing_time.setText("");
                            }
                        }, mHour, mMinute, false);

                timePickerDialog.show();
                break;
            case R.id.icon_timeclosing:

                if (str_profile_opening_time.equals("")){

                    Snackbar.make(fab, "Please Select Opening Time", Snackbar.LENGTH_SHORT).show();
                }else {

                    final Calendar c1 = Calendar.getInstance();
                    mHour1 = c1.get(Calendar.HOUR_OF_DAY);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog1 = new TimePickerDialog(this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                               /* str_profile_closing_time=(hourOfDay + ":" + (30-minute));*/
                                    try {
                                    String  min1= "0"+String.valueOf(minute);
                                    min1 = min1.substring(min1.length() - 2);

                                    String  hours1= "0"+String.valueOf(hourOfDay);
                                    hours1 = hours1.substring(hours1.length() - 2);

                                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                    String timeString = str_profile_opening_time;

                                        Date date = formatter.parse(timeString);
                                        str_profile_closing_time=(hours1 + ":" + min1);
                                        Date date2= formatter.parse(str_profile_closing_time);
                                        if (date.getTime() > date2.getTime()){
                                            Snackbar.make(fab, "Closing Time should not be less than Opening Time", Snackbar.LENGTH_SHORT).show();

                                        }
                                        else {

                                            profile_closing_time.setText(str_profile_closing_time);
                                        }


                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    //  str_profile_closing_time=(hourOfDay + ":" + minute);


                                }
                            }, mHour1, mMinute1, false);
                    //    timePickerDialog1.setIs24HourView(true);

                    timePickerDialog1.show();

                }
                // Get Current Time


                break;



        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Profile");
        try {
            db.open();
            store_list=   db.getSTORE_CATEGORYData();
            if (store_list.size()>0) {

                ValueAdapterList adapterList=new ValueAdapterList(this,store_list);
                recyclerView.setAdapter(adapterList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    public void setSppiner(){

        storeFormateData = db.geSTORE_FORMAT_MASTERData();
        availableBrandData=db.geBRAND_MASTERData();
        typeOfEntityData=db.geENTITY_TYPE_MASTERData();
        billingSystemData=db.geBILLING_TYPE_MASTERData();

        outletTypeData=db.geSTORETYPE_MASTERData();



        store_formate_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        store_formate_adapter.add("Select Store Format");
        for (int i = 0; i < storeFormateData.size(); i++) {
            store_formate_adapter.add(storeFormateData.get(i).getSTORE_FORMAT().get(0));
        }
        profile_store_format_sppiner.setAdapter(store_formate_adapter);



        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, freezerchilleravailable);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_freezer_sppiner.setAdapter(aa3);



        available_brand_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        available_brand_adapter.add("Select Store Format");
        for (int i = 0; i < availableBrandData.size(); i++) {
            available_brand_adapter.add(availableBrandData.get(i).getBRAND().get(0));
        }
        profile_available_sppiner.setAdapter(available_brand_adapter);



        type_of_entity_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        type_of_entity_adapter.add("Select Type Of Entity");
        for (int i = 0; i < typeOfEntityData.size(); i++) {
            type_of_entity_adapter.add(typeOfEntityData.get(i).getENTITY_TYPE().get(0));
        }
        profile_type_entity_sppiner.setAdapter(type_of_entity_adapter);

        ArrayAdapter aa6 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, outletformat);
        aa6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_outletformat_sppiner.setAdapter(aa6);

        billing_system_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
      //  billing_system_adapter.add("Select Billing System");
        for (int i = 0; i < billingSystemData.size(); i++) {
            billing_system_adapter.add(billingSystemData.get(i).getBILLING_TYPE().get(0));
        }
        profile_billingsystem_sppiner.setAdapter(billing_system_adapter);


        outlet_type_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        outlet_type_adapter.add("Select Outlet Type");
        for (int i = 0; i < outletTypeData.size(); i++) {
            outlet_type_adapter.add(outletTypeData.get(i).getSTORE_TYPE().get(0));
        }
        profile_outlettype_sppiner.setAdapter(outlet_type_adapter);


        ArrayAdapter aa9 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, billingcount);
        aa9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_billing_point_sppiner.setAdapter(aa9);


        profile_store_format_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    store_format_name = storeFormateData.get(position-1).getSTORE_FORMAT().get(0);
                    store_format_id = storeFormateData.get(position-1).getFORMAT_TYPE_ID().get(0);
                }
                else {
                    store_format_id="0";
                    store_format_name="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        profile_freezer_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    spinner_profile_profile_freezer_sppiner= String.valueOf(parent.getSelectedItemPosition());
                    profileInsertGetterSetter.setProfile_freezer_chiller_available_cd(spinner_profile_store_format_sppiner);
                }
                else {
                    profileInsertGetterSetter.setProfile_freezer_chiller_available_cd("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profile_available_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    available_brand_name = availableBrandData.get(position-1).getBRAND().get(0);
                    available_brand_id = availableBrandData.get(position-1).getBRAND_ID().get(0);
                }
                else {
                    available_brand_id="0";
                    available_brand_name="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profile_type_entity_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    type_of_entity_name = typeOfEntityData.get(position-1).getENTITY_TYPE().get(0);
                    type_of_entity_id = typeOfEntityData.get(position-1).getETYPE_ID().get(0);
                }
                else {
                    type_of_entity_id="0";
                    type_of_entity_name="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profile_billingsystem_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               /* if (position != 0) {
                    billing_system_name = billingSystemData.get(position-1).getBILLING_TYPE().get(0);
                    billing_system_id = billingSystemData.get(position-1).getBTYPE_ID().get(0);
                }
                else {
                    billing_system_id="0";
                    billing_system_name="";
                }*/
                if (position != 0) {
                    billing_system_name = billingSystemData.get(position).getBILLING_TYPE().get(0);
                    billing_system_id = billingSystemData.get(position).getBTYPE_ID().get(0);
                    switch (billingSystemData.get(position).getBTYPE_ID().get(0)){
                        case "0":
                            lin_pos.setVisibility(View.VISIBLE);

                            break;
                        case "1":
                            lin_pos.setVisibility(View.VISIBLE);

                            break;
                        case "2":
                            lin_pos.setVisibility(View.GONE);

                            break;
                    }
                }
                else {
                    billing_system_id="0";
                    billing_system_name="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profile_outletformat_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    spinner_profile_outletformat_sppiner= String.valueOf(parent.getSelectedItemPosition());
                    profileInsertGetterSetter.setProfile_outlet_format_cd(spinner_profile_store_format_sppiner);
                }
                else {
                    profileInsertGetterSetter.setProfile_outlet_format_cd("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profile_outlettype_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    outlet_type_name = outletTypeData.get(position-1).getSTORE_TYPE().get(0);
                    outlet_type_id = outletTypeData.get(position-1).getSTORETYPE_ID().get(0);
                }
                else {
                    outlet_type_id="0";
                    outlet_type_name="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profile_billing_point_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    spinner_profile_billing_point_sppiner= String.valueOf(parent.getSelectedItemPosition());
                    profileInsertGetterSetter.setProfile_number_of_billing_point_cd(spinner_profile_store_format_sppiner);
                }
                else {
                    profileInsertGetterSetter.setProfile_number_of_billing_point_cd("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void storeDetailsSaveData() {

        profileInsertGetterSetter.setProfile_store_format_cd(store_format_id);
        profileInsertGetterSetter.setProfile_store_format(store_format_name);
        profileInsertGetterSetter.setProfile_store_carpet_area_cd(str_profile_store_carpet);
        profileInsertGetterSetter.setProfile_name_of_pos(str_profile_name_of_pos);
        profileInsertGetterSetter.setProfile_freezer_chiller_available_cd(spinner_profile_profile_freezer_sppiner);
        profileInsertGetterSetter.setProfile_available_brand_cd(available_brand_id);
        profileInsertGetterSetter.setProfile_available_brand(available_brand_name);
        profileInsertGetterSetter.setProfile_type_of_entity_cd(type_of_entity_id);
        profileInsertGetterSetter.setProfile_type_of_entity(type_of_entity_name);
        profileInsertGetterSetter.setProfile_store_opening_time(str_profile_opening_time);
        profileInsertGetterSetter.setProfile_store_closing_time(str_profile_closing_time);
        profileInsertGetterSetter.setProfile_outlet_format_cd(spinner_profile_outletformat_sppiner);
        profileInsertGetterSetter.setProfile_billing_system_cd(billing_system_id);
        profileInsertGetterSetter.setProfile_billing_system(billing_system_name);
        profileInsertGetterSetter.setProfile_outlet_tye_cd(outlet_type_id);
        profileInsertGetterSetter.setProfile_outlet_tye(outlet_type_name);
        profileInsertGetterSetter.setProfile_number_of_billing_point_cd(spinner_profile_billing_point_sppiner);

        db.open();


        long id = db.InsertProfileData(profileInsertGetterSetter,key_id);
        System.out.println("id" + id);

        if (id > 0) {
            long id1 = db.InsertProfileToggleData(store_list,key_id);
            if (id1 > 0){
                Snackbar.make(fab, "Data saved successfully", Snackbar.LENGTH_SHORT).show();
                finish();
            }else {
                Snackbar.make(fab, "Data not saved", Snackbar.LENGTH_SHORT).show();
            }
            Snackbar.make(fab, "Data Inserted saved Profile", Snackbar.LENGTH_SHORT).show();
            finish();
        } else {
            Snackbar.make(fab, "Profile Data not saved", Snackbar.LENGTH_SHORT).show();
        }

    }

    public  boolean validation(){

        str_profile_opening_time=profile_opening_time.getText().toString().replaceAll("[&^<>{}'$]", " ");
        str_profile_closing_time=profile_closing_time.getText().toString().replaceAll("[&^<>{}'$]", " ");
        str_profile_store_carpet= profile_store_carpet.getText().toString().replaceAll("[&^<>{}'$]", " ");
        str_profile_name_of_pos= profile_name_of_pos.getText().toString().replaceAll("[&^<>{}'$]", " ");
        boolean  value=true;
        if (profile_store_format_sppiner.getSelectedItemPosition()==0)
        {
            value=false;
            showMessage("Please Select Store Format");
        }
        else if (profile_store_carpet.getText().toString().isEmpty()){
            value=false;
            showMessage("Please Enter Store Carpet Area");
        }
        else if (profile_freezer_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Freezer Chiller Available");
        }
       /* else if (profile_available_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Available Brands");
        }*/
        else if (profile_type_entity_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Type of Entity");
        }

        else  if (profile_opening_time.getText().toString().isEmpty())
        {
            value=false;
            showMessage("Please Enter Store Opening Time");
        }
        else if (profile_closing_time.getText().toString().isEmpty())
        {
            value=false;
            showMessage("Please Enter Store Closing Time");
        }
//profile_outlettype_sppiner
        else if (profile_outlettype_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Outlet Format");
        }

        else if (profile_billingsystem_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Billing System");
        }

        //str_profile_name_of_pos
    /* else if (profile_name_of_pos.getText().toString().isEmpty()){
            value=false;
            showMessage("Please Enter name of POS");
        }*/

        else if (profile_billing_point_sppiner.getSelectedItemPosition()==0){
            value=false;
            showMessage("Please Select Number of Billing Points");
        } else {


            value =true;

        }
        return  value;

    }
    public void showMessage(String message){

        Snackbar.make(fab,message,Snackbar.LENGTH_SHORT).show();

    }



    public class ValueAdapterList extends RecyclerView.Adapter<ProfileActivity.ValueAdapterList.MyViewHolder> {

        private LayoutInflater inflator;
        List<StoreCategoryGetterSetter> data = Collections.emptyList();

        public ValueAdapterList(Context context, List<StoreCategoryGetterSetter> data) {

            inflator = LayoutInflater.from(context);
            this.data = data;

        }

        @Override
        public ProfileActivity.ValueAdapterList.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

            View view = inflator.inflate(R.layout.profilelist, parent, false);

            ProfileActivity.ValueAdapterList.MyViewHolder holder = new ProfileActivity.ValueAdapterList.MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final ProfileActivity.ValueAdapterList.MyViewHolder viewHolder, final int position) {

            final StoreCategoryGetterSetter current = data.get(position);

            viewHolder.txt.setText(current.getCATEGORY().get(0));

            viewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        viewHolder.toggleButton.setChecked(true);
                        current.setToggolevalue("1");
                    }
                    else {
                        viewHolder.toggleButton.setChecked(false);
                        current.setToggolevalue("0");
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txt;
            ToggleButton toggleButton;

            public MyViewHolder(View itemView) {
                super(itemView);

                txt = (TextView) itemView.findViewById(R.id.vagetable);
                toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButton);

            }
        }

    }

    public boolean CheckNetAvailability() {

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network
            connected = true;
        }
        return connected;
    }


}
