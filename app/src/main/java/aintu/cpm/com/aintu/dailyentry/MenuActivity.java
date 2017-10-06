package aintu.cpm.com.aintu.dailyentry;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.CallsInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.GeotaggingBeans;
import aintu.cpm.com.aintu.GetterSetter.KycInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.ProfileInsertGetterSetter;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.MainActivity;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.RetailerEnrollmentFormActivity;
import aintu.cpm.com.aintu.RetailerVisitTrackerActivity;
import aintu.cpm.com.aintu.StoreIdentificationTrackerActivity;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StatusMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.VisitTypeMasterGetterSetter;
import constent.CommonString;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<VisitTypeMasterGetterSetter> visitTypeData = new ArrayList<>();
    String kycname;
    ArrayList<StatusMasterGetterSetter> statusData = new ArrayList<>();
    CallsInsertGetterSetter callsdata = new CallsInsertGetterSetter();
    StoreDetailsInsertGetterSetter storeDetailsdata = new StoreDetailsInsertGetterSetter();
    ProfileInsertGetterSetter profiledata = new ProfileInsertGetterSetter();
    KycInsertGetterSetter kycdata = new KycInsertGetterSetter();
    GeotaggingBeans geotagdata = new GeotaggingBeans();

    private Button store_identification_traker, retailer_enrollment_form;
    private ImageView store_details, calls, storeprodue, kyc, geotag;

    private Toolbar toolbar;
    String key_id;
    StoreDetailsInsertGetterSetter storeGetrSet;
    StoreSearchGetterSetter storeSearchGetrSet;
    ArrayList<StoreSearchGetterSetter> storelist = new ArrayList<>();


    AintuREDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getId();

        db = new AintuREDB(this);
        db.open();
        // storelist = db.getStoreSearchData();

        key_id = getIntent().getStringExtra(CommonString.KEY_ID);
        if (getIntent().getSerializableExtra(CommonString.KEY_OBJECT) != null) {
            storeGetrSet = (StoreDetailsInsertGetterSetter) getIntent().getSerializableExtra(CommonString.KEY_OBJECT);
        }


    }

    private void getId() {
        store_details = (ImageView) findViewById(R.id.img_store_details);
        calls = (ImageView) findViewById(R.id.img_calls);
        storeprodue = (ImageView) findViewById(R.id.img_store_profile);
        kyc = (ImageView) findViewById(R.id.img_kyc);
        geotag = (ImageView) findViewById(R.id.img_geotag);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        store_details.setOnClickListener(this);
        calls.setOnClickListener(this);
        storeprodue.setOnClickListener(this);
        kyc.setOnClickListener(this);
        geotag.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_store_details:
                Intent i1 = new Intent(MenuActivity.this, StoreDetailsActivity.class);
                i1.putExtra(CommonString.KEY_ID, key_id);
                i1.putExtra(CommonString.KEY_MODE, CommonString.KEY_FROM_MENU);
                startActivity(i1);
                break;
            case R.id.img_calls:
                Intent i2 = new Intent(MenuActivity.this, CallsActivity.class);
                i2.putExtra(CommonString.KEY_ID, key_id);
                startActivity(i2);
                break;
            case R.id.img_store_profile:
                Intent i3 = new Intent(MenuActivity.this, ProfileActivity.class);
                i3.putExtra(CommonString.KEY_ID, key_id);
                startActivity(i3);
                break;
            case R.id.img_kyc:
                Intent i4 = new Intent(MenuActivity.this, KycActivity.class);
                i4.putExtra(CommonString.KEY_ID, key_id);
                startActivity(i4);
                break;
            case R.id.img_geotag:
                Intent i5 = new Intent(MenuActivity.this, GeoTagActivity.class);
                i5.putExtra(CommonString.KEY_ID, key_id);
                startActivity(i5);
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

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Menu");

        db = new AintuREDB(this);
        db.open();
        callsdata = db.getCallsData(key_id);
        storeDetailsdata = db.getStoreDetailsData(key_id);
        kycname=storeDetailsdata.getStore_kyc();
        visitTypeData = db.geVISIT_TYPE_MASTERData(kycname);
        profiledata = db.getProfileData(key_id);
        kycdata = db.getkycData(key_id);
        geotagdata = db.getGeoTagData(key_id);
        //  geotagdata=db.getGeoTagUploadData(key_id);
        storelist = db.getStoreGEoTAgData(key_id);


        setCheckoutData();


        if (kycname.equalsIgnoreCase("Yes")) {

            storeprodue.setBackground(getResources().getDrawable(R.drawable.store_profile_gray));
            storeprodue.setEnabled(false);
            store_details.setBackground(getResources().getDrawable(R.drawable.store_details_gray));
            store_details.setEnabled(false);
            kyc.setBackground(getResources().getDrawable(R.drawable.kyc_gray));
            kyc.setEnabled(false);
            geotag.setBackground(getResources().getDrawable(R.drawable.geotag_gray));
            geotag.setEnabled(false);

            if (callsdata.getCalls_person_meet() != null) {
                calls.setBackground(getResources().getDrawable(R.drawable.calls_done));
                calls.setEnabled(false);
            }
        }
        else {

            if (callsdata.getCalls_person_meet() != null) {
                calls.setBackground(getResources().getDrawable(R.drawable.calls_done));
                calls.setEnabled(false);
            }
            if (storeDetailsdata.getStore_name() != null) {
                store_details.setBackground(getResources().getDrawable(R.drawable.store_details_done));
            }
            if (profiledata.getProfile_store_format_cd() != null) {
                storeprodue.setBackground(getResources().getDrawable(R.drawable.store_profile_done));
                storeprodue.setEnabled(false);
            }

            if (kycdata.getKyc_address_proof() != null) {
                kyc.setBackground(getResources().getDrawable(R.drawable.kyc_done));
                kyc.setEnabled(false);

            }

            if (storeDetailsdata.getGeotag() != null) {
                if (storeDetailsdata.getGeotag().equalsIgnoreCase("Y")) {
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                    geotag.setEnabled(false);
                } else {
                    if (geotagdata.getLatitude() != null) {
                        geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                        geotag.setEnabled(false);
                    } else {
                        geotag.setEnabled(true);
                        geotag.setBackground(getResources().getDrawable(R.drawable.geotag));
                    }
                }
            } else {
                if (geotagdata.getLatitude() != null) {
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                    geotag.setEnabled(false);
                } else {
                    geotag.setEnabled(true);
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag));
                }
            }


        }





        /*if (kycname.equalsIgnoreCase("Yes")) {
            geotag.setBackground(getResources().getDrawable(R.drawable.geotag_gray));
            geotag.setEnabled(false);
        }else {
            if (storeDetailsdata.getGeotag() != null) {
                if (storeDetailsdata.getGeotag().equalsIgnoreCase("Y")) {
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                    geotag.setEnabled(false);
                } else {
                    if (geotagdata.getLatitude() != null) {
                        geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                        geotag.setEnabled(false);
                    } else {
                        geotag.setEnabled(true);
                        geotag.setBackground(getResources().getDrawable(R.drawable.geotag));
                    }
                }
            } else {
                if (geotagdata.getLatitude() != null) {
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                    geotag.setEnabled(false);
                } else {
                    geotag.setEnabled(true);
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag));
                }
            }
        }
*/


       /* if (storeDetailsdata.getGeotag() != null) {
            if (storeDetailsdata.getGeotag().equalsIgnoreCase("Y")) {
                geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                geotag.setEnabled(false);
            } else {
                if (geotagdata.getLatitude() != null) {
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                    geotag.setEnabled(false);
                } else {
                    geotag.setEnabled(true);
                    geotag.setBackground(getResources().getDrawable(R.drawable.geotag));
                }
            }
        } else {
            if (geotagdata.getLatitude() != null) {
                geotag.setBackground(getResources().getDrawable(R.drawable.geotag_done));
                geotag.setEnabled(false);
            } else {
                geotag.setEnabled(true);
                geotag.setBackground(getResources().getDrawable(R.drawable.geotag));
            }
        }*/

    }

    public void setCheckoutData() {
        if (storeDetailsdata.getStore_name() != null && callsdata.getCalls_person_meet() != null) {

            if(callsdata.getCalls_status_CD().equalsIgnoreCase("1")) {
                if(kycdata.getKyc_store_email_id()!=null) {

                    db.UpdateStoreDetailsUploadStatus(key_id, CommonString.KEY_VALID);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please fill KYC details",Toast.LENGTH_LONG).show();
                }
            }
            else {
                db.UpdateStoreDetailsUploadStatus(key_id, CommonString.KEY_VALID);

            }
        }
    }


}
