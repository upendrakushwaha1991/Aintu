package aintu.cpm.com.aintu.dailyentry;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.KycInsertGetterSetter;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.util.Utilities;
import aintu.cpm.com.aintu.xmlGetterSetter.BrandMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.EstProofMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.IdentificationTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreFormateMasterGetterSetter;
import constent.CommonString;

public class KycActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner address_proof_proof_sppiner;
    private ImageView kyc_imageentity, kyc_id_proof_no, kyc_adddress_proof, address_proof_image,back_image;
    private EditText kyc_entity_proofno, kyc_store_emailid, retailer_gps_location, kyc_owner_name,
            kyc_owner_contactnoctno, kyc_id_proofno, kyc_address_proof;

    String str_kyc_entity_proofno, kyc_kyc_store_emailid, kyc_retailer_gps_location, kyc_kyc_owner_name, kyc_kyc_owner_contactnoctno, kyc_kyc_id_proofno,
            kyc_kyc_address_proof_type_name, kyc_kyc_address_proof_type_id, kyc_kyc_address_proof;
    private Spinner kyc_proof_entiity_sppiner, kyc_gender_sppiner, kyc_id_proof_sppiner, kyc_address_proof_type;
    String spinner_kyc_proof_entiity_sppiner, spinner_kyc_kyc_gender_sppiner, spinner_kyc_kyc_id_proof_sppiner,spinner_kyc_address_proof_sppiner;
    private TextView picproofnumber, picpidroofnumber, picpaddressproof;


    private FloatingActionButton fab;
    private Toolbar toolbar;
    String[] userNames = {"Select", "Male", "Female", "Other"};
    String[] proofofentiity = {"Select", "Shop $ EStablishment License", "Seles Tax Certificate", "Company Certificate(Pvt Ltd only)"};
    String[] idprooftype = {"Select", "PAN Card", "Adhar Card", "Voter ID", "Passport", "Driving License", "Ration Card"};
    String gender;
    boolean validation = true;

    Uri outputFileUri;
    String _pathforcheck = "", _path, str, str1;
    String _pathforcheck_id_proof_number = "", _path_proof_number;
    String _pathforcheck_address_proof = "", _path_address_prof;
    String _pathforcheck_cancelled = "", _path_cancelled;
    String _pathforcheck_back = "", _path_back;


    String gallery_package = "";
    String currentdate, currentuserid, image_name = "", image_name_id_proof_number = "", image_name_address_proof_number = "", image_name_cancelled = "",image_name_back;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    KycInsertGetterSetter kycInsertGetterSetter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    private String date, store_intime;
    String user_type;
    AintuREDB db;
    String key_id;

    private ArrayAdapter<CharSequence> proof_of_entity_adapter;
    private ArrayAdapter<CharSequence> id_proof_type_adapter;
    private ArrayAdapter<CharSequence> address_proof_type_adapter;
    String proof_of_entity_name, proof_of_entity_id, id_proof_type_name, id_proof_type_id,id_address_proof_id,id_address_proof_name;

    ArrayList<EstProofMasterGetterSetter> estProofMasterData = new ArrayList<>();
    ArrayList<IdentificationTypeMasterGetterSetter> identificationTypeMasterData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        key_id = getIntent().getStringExtra(CommonString.KEY_ID);

        db = new AintuREDB(this);
        db.open();

        getId();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentdate = preferences.getString(CommonString.KEY_DATE, null);
        currentuserid = preferences.getString(CommonString.KEY_USERNAME, null);
        editor = preferences.edit();

        str1 = CommonString.FILE_PATH;
       /* _pathforcheck = "AintuStore" + "Image" + currentdate.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
        _path = CommonString.FILE_PATH + _pathforcheck;*/

    }

    private void getId() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        kyc_entity_proofno = (EditText) findViewById(R.id.kyc_entity_proofno);
        kyc_store_emailid = (EditText) findViewById(R.id.kyc_store_emailid);
        retailer_gps_location = (EditText) findViewById(R.id.retailer_gps_location);
        kyc_owner_name = (EditText) findViewById(R.id.kyc_owner_name);
        kyc_owner_contactnoctno = (EditText) findViewById(R.id.kyc_owner_contactnoctno);
        kyc_id_proofno = (EditText) findViewById(R.id.kyc_id_proofno);
        kyc_address_proof_type = (Spinner) findViewById(R.id.kyc_address_proof_type);
        kyc_address_proof = (EditText) findViewById(R.id.kyc_address_proof);

        picproofnumber = (TextView) findViewById(R.id.picproofnumber);
      //  picpidroofnumber = (TextView) findViewById(R.id.picpidroofnumber);
        picpaddressproof = (TextView) findViewById(R.id.picpaddressproof);

        kyc_proof_entiity_sppiner = (Spinner) findViewById(R.id.kyc_proof_entiity_sppiner);
        kyc_gender_sppiner = (Spinner) findViewById(R.id.kyc_gender_sppiner);
        kyc_id_proof_sppiner = (Spinner) findViewById(R.id.kyc_id_proof_sppiner);
        address_proof_proof_sppiner = (Spinner) findViewById(R.id.address_proof_proof_sppiner);

        kyc_imageentity = (ImageView) findViewById(R.id.kyc_imageentity);
        kyc_id_proof_no = (ImageView) findViewById(R.id.kyc_id_proof_no);
        kyc_adddress_proof = (ImageView) findViewById(R.id.kyc_adddress_proof);
        address_proof_image = (ImageView) findViewById(R.id.address_proof_image);
        back_image= (ImageView) findViewById(R.id.back_image);

        fab.setOnClickListener(this);
        picproofnumber.setOnClickListener(this);
      //  picpidroofnumber.setOnClickListener(this);
        picpaddressproof.setOnClickListener(this);

        kyc_imageentity.setOnClickListener(this);
        kyc_id_proof_no.setOnClickListener(this);
        kyc_adddress_proof.setOnClickListener(this);
        address_proof_image.setOnClickListener(this);
        back_image.setOnClickListener(this);

        kycInsertGetterSetter = new KycInsertGetterSetter();
        setSppiner();

    }

    public void setSppiner() {

        estProofMasterData = db.geEST_PROOF_MASTERData();
        identificationTypeMasterData = db.geIDENTIFICATION_TYPE_MASTERData();

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        kyc_gender_sppiner.setAdapter(aa);


        proof_of_entity_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        proof_of_entity_adapter.add("Select Proof Of Entity");
        for (int i = 0; i < estProofMasterData.size(); i++) {
            proof_of_entity_adapter.add(estProofMasterData.get(i).getEST_PROOF().get(0));
        }
        kyc_proof_entiity_sppiner.setAdapter(proof_of_entity_adapter);


        id_proof_type_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        id_proof_type_adapter.add("Select Id Proof Type");
        for (int i = 0; i < identificationTypeMasterData.size(); i++) {
            id_proof_type_adapter.add(identificationTypeMasterData.get(i).getPROOF_TYPE().get(0));
        }
        kyc_id_proof_sppiner.setAdapter(id_proof_type_adapter);


        id_proof_type_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        id_proof_type_adapter.add("Select Address Proof");
        for (int i = 0; i < identificationTypeMasterData.size(); i++) {
            id_proof_type_adapter.add(identificationTypeMasterData.get(i).getPROOF_TYPE().get(0));
        }
        address_proof_proof_sppiner.setAdapter(id_proof_type_adapter);





        address_proof_type_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        address_proof_type_adapter.add("Select Address Proof Type");
        for (int i = 0; i < identificationTypeMasterData.size(); i++) {
            address_proof_type_adapter.add(identificationTypeMasterData.get(i).getPROOF_TYPE().get(0));
        }
        kyc_address_proof_type.setAdapter(address_proof_type_adapter);


        kyc_gender_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //  spinner_kyc_kyc_gender_sppiner= String.valueOf(parent.getSelectedItemPosition());
                    spinner_kyc_kyc_gender_sppiner = parent.getItemAtPosition(position).toString();
                    //  spinner_kyc_kyc_gender_sppiner= String.valueOf(parent.getSelectedItemPosition());

                    kycInsertGetterSetter.setKyc_gender_cd(spinner_kyc_kyc_gender_sppiner);
                } else {
                    kycInsertGetterSetter.setKyc_gender_cd("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kyc_proof_entiity_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    proof_of_entity_name = estProofMasterData.get(position - 1).getEST_PROOF().get(0);
                    proof_of_entity_id = estProofMasterData.get(position - 1).getEST_PROOF_ID().get(0);
                } else {
                    proof_of_entity_id = "0";
                    proof_of_entity_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kyc_id_proof_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    id_proof_type_name = identificationTypeMasterData.get(position - 1).getPROOF_TYPE().get(0);
                    id_proof_type_id = identificationTypeMasterData.get(position - 1).getPROOF_TYPE_ID().get(0);
                } else {
                    id_proof_type_id = "0";
                    id_proof_type_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        address_proof_proof_sppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    id_address_proof_name = identificationTypeMasterData.get(position - 1).getPROOF_TYPE().get(0);
                    id_address_proof_id = identificationTypeMasterData.get(position - 1).getPROOF_TYPE_ID().get(0);
                } else {
                    id_address_proof_id = "0";
                    id_address_proof_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kyc_address_proof_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    kyc_kyc_address_proof_type_name = identificationTypeMasterData.get(position - 1).getPROOF_TYPE().get(0);
                    kyc_kyc_address_proof_type_id = identificationTypeMasterData.get(position - 1).getPROOF_TYPE_ID().get(0);
                } else {
                    id_proof_type_id = "0";
                    id_proof_type_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (validation()) {

                    storeDetailsSaveData();

                }

                break;


            case R.id.kyc_imageentity:

                _pathforcheck = "KYC_Entity_proof_number"
                        + "Image" + currentdate.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;

                startCameraActivity(0, _path);

                break;
            case R.id.kyc_id_proof_no:


                _pathforcheck_id_proof_number = "KYC_Id_Proof_number"
                        + "Image" + currentdate.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
                _path_proof_number = CommonString.FILE_PATH + _pathforcheck_id_proof_number;
                startCameraActivity(1, _path_proof_number);


                break;

            case R.id.kyc_adddress_proof:

                _pathforcheck_address_proof = "KYC_Address_proof"
                        + "Image" + currentdate.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
                _path_address_prof = CommonString.FILE_PATH + _pathforcheck_address_proof;
                startCameraActivity(2, _path_address_prof);


                break;
            case R.id.address_proof_image:

                _pathforcheck_cancelled = "KYC_Cancelled_Cheque"
                        + "Image" + currentdate.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
                _path_cancelled = CommonString.FILE_PATH + _pathforcheck_cancelled;
                startCameraActivity(3, _path_cancelled);

            case R.id.back_image:

                _pathforcheck_back = "KYC_Back"
                        + "Image" + currentdate.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
                _path_back = CommonString.FILE_PATH + _pathforcheck_back;
                startCameraActivity(4, _path_back);


                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("KYC");

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


    protected void startCameraActivity(int request_code, String img_path) {
        try {

            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(img_path);
            outputFileUri = Uri.fromFile(file);

            String defaultCameraPackage = "";
            final PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
            for (int n = 0; n < list.size(); n++) {
                if ((list.get(n).flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    Log.e("TAG", "Installed Applications  : " + list.get(n).loadLabel(packageManager).toString());
                    Log.e("TAG", "package name  : " + list.get(n).packageName);

                    //temp value in case camera is gallery app above jellybean
                    String packag = list.get(n).loadLabel(packageManager).toString();
                    if (packag.equalsIgnoreCase("Gallery") || packag.equalsIgnoreCase("Galeri") || packag.equalsIgnoreCase("Ø§Ù„Ø§Ø³ØªÙˆØ¯ÙŠÙˆ")) {
                        // gallery_package = list.get(n).packageName;
                    }

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (packag.equalsIgnoreCase("Camera") || packag.equalsIgnoreCase("Kamera") || packag.equalsIgnoreCase("Ø§Ù„ÙƒØ§Ù…ÙŠØ±Ø§")) {
                            defaultCameraPackage = list.get(n).packageName;
                            break;
                        }
                    } else {

                        if (packag.equalsIgnoreCase("Camera") || packag.equalsIgnoreCase("Kamera") || packag.equalsIgnoreCase("Ø§Ù„ÙƒØ§Ù…ÙŠØ±Ø§")) {

                            defaultCameraPackage = list.get(n).packageName;
                            break;
                        }
                    }
                }
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent.setPackage(defaultCameraPackage);
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {

            e.printStackTrace();

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent.setPackage(gallery_package);

            startActivityForResult(intent, 0);

        } catch (Exception e) {
            // Log( e, "Error creating temp file for HTC Desire HD" );
            System.out.println("Error creating temp file for HTC Desire HD" + e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str1 + _pathforcheck).exists()) {

                        //Bitmap bmp = BitmapFactory.decodeFile(str + _pathforcheck);
                        image_name = _pathforcheck;
                        System.out.println("image_name" + image_name);

                        kyc_imageentity.setBackgroundResource(R.drawable.cam_icon_done);
                    }
                }
                //   kyc_id_proof_no,d;
            case 1:
                if (_pathforcheck_id_proof_number != null && !_pathforcheck_id_proof_number.equals("")) {
                    if (new File(str1 + _pathforcheck_id_proof_number).exists()) {

                        //Bitmap bmp = BitmapFactory.decodeFile(str + _pathforcheck_id_proof_number);
                        image_name_id_proof_number = _pathforcheck_id_proof_number;
                        System.out.println("image_name" + image_name_id_proof_number);

                        kyc_id_proof_no.setBackgroundResource(R.drawable.cam_icon_done);
                        //  textname.setText("Camera Capture");
                    }
                }
            case 2:
                if (_pathforcheck_address_proof != null && !_pathforcheck_address_proof.equals("")) {
                    if (new File(str1 + _pathforcheck_address_proof).exists()) {

                        //Bitmap bmp = BitmapFactory.decodeFile(str + _pathforcheck_address_proof);
                        image_name_address_proof_number = _pathforcheck_address_proof;
                        System.out.println("image_name" + image_name_address_proof_number);

                        kyc_adddress_proof.setBackgroundResource(R.drawable.cam_icon_done);

                    }
                }

            case 3:
                if (_pathforcheck_cancelled != null && !_pathforcheck_cancelled.equals("")) {
                    if (new File(str1 + _pathforcheck_cancelled).exists()) {

                        image_name_cancelled = _pathforcheck_cancelled;
                        address_proof_image.setBackgroundResource(R.drawable.cam_icon_done);

                    }
                }
            case 4:
             //   String _pathforcheck_back = "", _path_back;

                if (_pathforcheck_back != null && !_pathforcheck_back.equals("")) {
                    if (new File(str1 + _pathforcheck_back).exists()) {

                        image_name_back = _pathforcheck_back;
                        back_image.setBackgroundResource(R.drawable.cam_icon_done);

                    }
                }
                break;
        }
        //  super.onActivityResult(requestCode, resultCode, data);
    }


    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:mmm");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;

    }

    public void storeDetailsSaveData() {
        kycInsertGetterSetter.setKyc_proof_of_entity_cd(proof_of_entity_id);
        kycInsertGetterSetter.setKyc_proof_of_entity(proof_of_entity_name);
        kycInsertGetterSetter.setKyc_entity_proof_number(str_kyc_entity_proofno);
        kycInsertGetterSetter.setKyc_store_email_id(kyc_kyc_store_emailid);
        kycInsertGetterSetter.setKyc_owner_name(kyc_kyc_owner_name);
        kycInsertGetterSetter.setKyc_owner_contact_number(kyc_kyc_owner_contactnoctno);
        kycInsertGetterSetter.setKyc_gender_cd(spinner_kyc_kyc_gender_sppiner);
        kycInsertGetterSetter.setKyc_id_proof_type_cd(id_proof_type_id);
        kycInsertGetterSetter.setKyc_id_proof_type(id_proof_type_name);
        kycInsertGetterSetter.setKyc_id_proof_number(kyc_kyc_id_proofno);
        kycInsertGetterSetter.setKyc_address_proof_type(kyc_kyc_address_proof_type_id);
        kycInsertGetterSetter.setKyc_address_proof_type_cd(kyc_kyc_address_proof_type_name);
        kycInsertGetterSetter.setKyc_address_proof(kyc_kyc_address_proof);
        kycInsertGetterSetter.setKyc_image1(image_name);
        kycInsertGetterSetter.setKyc_image2(image_name_id_proof_number);
        kycInsertGetterSetter.setKyc_image3(image_name_address_proof_number);
        kycInsertGetterSetter.setKyc_image4(image_name_cancelled);
        kycInsertGetterSetter.setKyc_image5(image_name_back);

       // kycInsertGetterSetter.setKyc_address_prooff(id_address_proof_id);


       /* + KEY_ADDRESS_PROOF_CD + " INTEGER,"
                + KEY_IMAGE_ADDRESS_PROOF_NO + " VARCHAR"*/
        db.open();

        long id = db.InsertKYCData(kycInsertGetterSetter, key_id);
        System.out.println("id" + id);

        if (id > 0) {

            Snackbar.make(fab, "Data Inserted saved Kyc", Snackbar.LENGTH_SHORT).show();
            finish();
        } else {
            Snackbar.make(fab, "Data not saved", Snackbar.LENGTH_SHORT).show();
        }

    }


    public boolean validation() {

        str_kyc_entity_proofno = kyc_entity_proofno.getText().toString().replaceAll("[&^<>{}'$]", " ");
        kyc_kyc_store_emailid = kyc_store_emailid.getText().toString().replaceAll("[&^<>{}'$]", " ");
        kyc_kyc_owner_name = kyc_owner_name.getText().toString().replaceAll("[&^<>{}'$]", " ");
        kyc_kyc_owner_contactnoctno = kyc_owner_contactnoctno.getText().toString().replaceAll("[&^<>{}'$]", " ");
        kyc_kyc_id_proofno = kyc_id_proofno.getText().toString().replaceAll("[&^<>{}'$]", " ");
       //  kyc_kyc_address_proof_type=kyc_address_proof_type.getText().toString();
        kyc_kyc_address_proof = kyc_address_proof.getText().toString().replaceAll("[&^<>{}'$]", " ");
        boolean value = true;
        if (kyc_proof_entiity_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Proof of Entity");
        } else if (kyc_entity_proofno.getText().toString().isEmpty()) {
            value = false;
            showMessage("Please Enter Entity Proof Number");
        }
        /*else if (!Utilities.isValid(stremailaddress)) {

            validation = false;
            fragmentDialogFragmentToShowMessages = new FragmentDialogFragmentToShowMessages(MainActivity.this, "PARINAAM", "Enter valid Email ID");
            fragmentDialogFragmentToShowMessages.show(getSupportFragmentManager(), "");
        }*/
        // if (kyc_store_emailid.getText().toString().isEmpty())
        //  if (!Utilities.isValid(kyc_kyc_store_emailid))
        else if (!Utilities.isValid(kyc_kyc_store_emailid)) {
            value = false;
            showMessage("Please Enter valid Store Email Id");
        }

       /* else  if (kyc_owner_name.getText().toString().isEmpty())
        {
            value=false;
            showMessage("Please Enter Owner Name");
        }
*/
        else if (kyc_owner_contactnoctno.length() < 10) {
            value = false;
            showMessage("Please Enter Valid Owner Contact Number");
        } else if (kyc_gender_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Gender");
        } else if (kyc_id_proof_sppiner.getSelectedItemPosition() == 0) {
            value = false;
            showMessage("Please Select Id Proof Type");
        }
        else if (kyc_address_proof_type.getSelectedItemPosition()==0) {
            value=false;
            showMessage("Please Address Proof Type");
        }
        else if (kyc_id_proofno.getText().toString().isEmpty()) {
            value = false;
            showMessage("Please Enter Id Proof Number");
        }
        else if (kyc_address_proof.getText().toString().isEmpty()) {
            value=false;
            showMessage("Please Enter Address Proof");
        }
        else if (image_name.isEmpty()) {
            value = false;
            showMessage("Please Add Entity Proof Number Photo");
        } else if (image_name_id_proof_number.isEmpty()) {
            value = false;
            showMessage("Please Add Front Photo");
        }
        else if (image_name_cancelled.isEmpty()) {
            value=false;
            showMessage("Please Add Cancelled Cheque Photo");
        }
        else if (image_name_address_proof_number.isEmpty()) {
            value = false;
            showMessage("Please Add address Proof Photo");
        }
        else if (image_name_back.isEmpty()) {
            value=false;
            showMessage("Please Add Back Photo");
        }

        else {

           /* str_kyc_entity_proofno=kyc_entity_proofno.getText().toString();
            kyc_kyc_store_emailid=kyc_store_emailid.getText().toString();
            kyc_kyc_owner_name=kyc_owner_name.getText().toString();
            kyc_kyc_owner_contactnoctno=kyc_owner_contactnoctno.getText().toString();
            kyc_kyc_id_proofno=kyc_id_proofno.getText().toString();
            // kyc_kyc_address_proof_type=kyc_address_proof_type.getText().toString();
            kyc_kyc_address_proof=kyc_address_proof.getText().toString();*/

            value = true;

        }
        return value;

    }

    public void showMessage(String message) {

        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show();

    }


}
