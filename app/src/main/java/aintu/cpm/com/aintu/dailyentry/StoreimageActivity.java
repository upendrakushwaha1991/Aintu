package aintu.cpm.com.aintu.dailyentry;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.CoverageBean;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.LoginActivity;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.xmlGetterSetter.FailureGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;
import aintu.cpm.com.aintu.xmlHandlers.FailureXMLHandler;
import constent.CommonString;


/**
 * Created by ashishc on 31-05-2016.
 */
public class StoreimageActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_LOCATION=1;

    String gallery_package = "";
    Uri outputFileUri;

    ImageView img_cam, img_clicked;
    Button btn_save;
    private Dialog dialog;
    private TextView percentage, message;
    private ProgressBar pb;
    private FailureGetterSetter failureGetterSetter = null;
    String _pathforcheck, _path, str;

    String store_id, visit_date, username, intime, date, _UserId;
    private SharedPreferences preferences;
    AlertDialog alert;
    String img_str, strflag;
    /*  private GSKOrangeDB database;*/
    private AintuREDB database;
    String key_id;

    double lat, lon;
    GoogleApiClient mGoogleApiClient;
    ArrayList<CoverageBean> coverage_list;
    Toolbar toolbar;
    boolean ResultFlag = true;
    ArrayList<CoverageBean> coverage = new ArrayList<CoverageBean>();

    LocationManager locationManager;
    boolean enabled;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 500; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 5; // 10 meters
    private Location mLastLocation;

    private static final String TAG = StoreimageActivity.class.getSimpleName();
    String mode;

    StoreSearchGetterSetter storeSearchGetterSetter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeimage);

        mode = getIntent().getStringExtra(CommonString.KEY_MODE);
        storeSearchGetterSetter = (StoreSearchGetterSetter) getIntent().getSerializableExtra(CommonString.KEY_OBJECT);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img_cam = (ImageView) findViewById(R.id.img_selfie);
        img_clicked = (ImageView) findViewById(R.id.img_cam_selfie);

        btn_save = (Button) findViewById(R.id.btn_save_selfie);
        //store_id = preferences.getString(CommonString.KEY_STORE_ID, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");


        str = CommonString.FILE_PATH;

        database = new AintuREDB(this);
        database.open();

        coverage_list = database.getCoverageData(date);

        img_cam.setOnClickListener(this);
        img_clicked.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    StoreimageActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle(getResources().getString(R.string.gps));

            // Setting Dialog Message
            alertDialog.setMessage(getResources().getString(R.string.gpsebale));

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton(getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton(getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event

                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();

        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
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

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.notsuppoted)
                        , Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void startLocationUpdates() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();

            }
        }


        // if (mRequestingLocationUpdates) {
        startLocationUpdates();
        // }

        // startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }


    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        // AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onBackPressed() {
        /*Intent i = new Intent(this, DailyEntryScreen.class);
        startActivity(i);*/

        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.img_cam_selfie:
                String store_cd;
                if (mode.equalsIgnoreCase(CommonString.KEY_FROM_JCP_STORE)){
                    store_cd=storeSearchGetterSetter.getSTORE_ID().get(0);
                }else {
                    store_cd="0";
                }
                _pathforcheck = store_cd + "Store_Images" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";

                _path = CommonString.FILE_PATH + _pathforcheck;

                intime = getCurrentTime();

                startCameraActivity();

                break;

            case R.id.btn_save_selfie:

                if (img_str != null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(StoreimageActivity.this);
                    builder.setMessage(getResources().getString(R.string.title_activity_save_data))
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                                    if (mode.equalsIgnoreCase(CommonString.KEY_FROM_JCP_STORE)){
                                        StoreDetailsInsertGetterSetter store=new StoreDetailsInsertGetterSetter(storeSearchGetterSetter);

                                        long key_id = database.InsertStoreDetailsData(store,storeSearchGetterSetter.getSTORE_ID().get(0),visit_date);

                                        CoverageBean cdata = new CoverageBean();
                                        cdata.setStore_id(storeSearchGetterSetter.getSTORE_ID().get(0));
                                        cdata.setVisit_date(visit_date);
                                        cdata.setUser_id(username);
                                        cdata.setIn_time(intime);
                                        cdata.setReason("");
                                        cdata.setReason_id("0");
                                        cdata.setLattitude(lat+"");
                                        cdata.setLongitude(lon+"");
                                        cdata.setImage(img_str);
                                        cdata.setRemark("");
                                        cdata.setCoverage_status(CommonString.KEY_INVALID);
                                     /*   cdata.setCheckOut_Image("");*/

                                        database.InsertCoverageData(cdata,key_id+"");

                                        Intent in = new Intent(StoreimageActivity.this, MenuActivity.class);
                                        in.putExtra(CommonString.KEY_ID,key_id+"");
                                        in.putExtra(CommonString.KEY_MODE,CommonString.KEY_FROM_JCP_STORE);
                                        startActivity(in);
                                        finish();

                                    }else {
                                        CoverageBean cdata = new CoverageBean();
                                        cdata.setStore_id("0");
                                        cdata.setVisit_date(visit_date);
                                        cdata.setUser_id(username);
                                        cdata.setIn_time(intime);
                                        cdata.setReason("");
                                        cdata.setReason_id("0");
                                        cdata.setLattitude(lat+"");
                                        cdata.setLongitude(lon+"");
                                        cdata.setImage(img_str);
                                        cdata.setRemark("");
                                        cdata.setCoverage_status(CommonString.KEY_INVALID);
                                     /*   cdata.setCheckOut_Image("");*/

                                        Intent in = new Intent(StoreimageActivity.this, StoreDetailsActivity.class);
                                        in.putExtra(CommonString.KEY_COVERAGE_STATUS,cdata);
                                        in.putExtra(CommonString.KEY_MODE,CommonString.KEY_FROM_ADD_STORE);
                                        finish();
                                        startActivity(in);
                                    }



                                    //  database.updateCheckoutStatus(store_id, CommonString.KEY_INVALID);




                                    // new StoreimageActivity.GeoTagUpload(StoreimageActivity.this).execute();

                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    alert = builder.create();
                    alert.show();

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.clickimage), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    protected void startCameraActivity() {
        try {

            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
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
                    if (packag.equalsIgnoreCase("Gallery") || packag.equalsIgnoreCase("Galeri") || packag.equalsIgnoreCase("الاستوديو")) {
                        gallery_package = list.get(n).packageName;
                    }

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (packag.equalsIgnoreCase("Camera") || packag.equalsIgnoreCase("Kamera") || packag.equalsIgnoreCase("الكاميرا")) {
                            defaultCameraPackage = list.get(n).packageName;
                            break;
                        }
                    } else {

                        if (packag.equalsIgnoreCase("Camera") || packag.equalsIgnoreCase("Kamera") || packag.equalsIgnoreCase("الكاميرا")) {

                            defaultCameraPackage = list.get(n).packageName;
                            break;
                        }
                    }
                }
            }

            //com.android.gallery3d

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
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {

            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {
                        Bitmap bmp = BitmapFactory.decodeFile(str + _pathforcheck);
                        img_cam.setImageBitmap(bmp);

                        img_clicked.setVisibility(View.GONE);
                        img_cam.setVisibility(View.VISIBLE);

                        img_str = _pathforcheck;
                        _pathforcheck = "";
                    }
                }

            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_CANCELED: {


                    }

                    break;
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:mmm");
        String cdate = formatter.format(m_cal.getTime());

      /*  if (preferences.getString(CommonString.KEY_LANGUAGE, "").equalsIgnoreCase(CommonString.KEY_LANGUAGE_ARABIC_KSA)) {
            cdate = arabicToenglish(cdate);
        }else if (preferences.getString(CommonString.KEY_LANGUAGE, "").equalsIgnoreCase(CommonString.KEY_LANGUAGE_ARABIC_UAE)) {
            cdate = arabicToenglish(cdate);
        }*/

        return cdate;
    }



    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(R.string.title_activity_store_image);

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() ) {
            startLocationUpdates();
            enableLoc();
        }

    }

    /*protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }*/


    @Override
    public void onLocationChanged(Location location) {

    }


/*
    public class GeoTagUpload extends AsyncTask<Void, Void, String> {

        private Context context;

        GeoTagUpload(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle(getResources().getString(R.string.dialog_title));
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                AintuREDB db = new AintuREDB(StoreimageActivity.this);
                db.open();

                coverage = db.getCoverageWithStoreID_Data(store_id);

                // uploading Geotag

                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();


                String current_xml = "";

                if (coverage.size() > 0) {

                    for (int i = 0; i < coverage.size(); i++) {


                        String onXML = "[Coverage_Intime][USER_ID]"
                                + _UserId
                                + "[/USER_ID]"
                                + "[STORE_ID]"
                                + coverage.get(i).getStoreId()
                                + "[/STORE_ID]"
                                + "[VISIT_DATE]"
                                + coverage.get(i).getVisitDate()
                                + "[/VISIT_DATE]"
                                + "[IN_TIME]"
                                + coverage.get(i).getInTime()
                                + "[/IN_TIME]"
                                + "[LATITUDE]"
                                + coverage.get(i).getLatitude()
                                + "[/LATITUDE]"
                                + "[LONGITUDE ]"
                                + coverage.get(i).getLongitude()
                                + "[/LONGITUDE ]"
                                + "[REASON_ID]"
                                + coverage.get(i).getReasonid()
                                + "[/REASON_ID]"
                                + "[REMARK]"
                                + coverage.get(i).getReason()
                                + "[/REMARK][/Coverage_Intime]";

                        current_xml = current_xml + onXML;


                    }

                    current_xml = "[DATA]" + current_xml
                            + "[/DATA]";

                    SoapObject request = new SoapObject(CommonString.NAMESPACE,
                            CommonString.METHOD_UPLOAD_CURRENT_DATA);
                    //request.addProperty("MID", "0");
                    // request.addProperty("KEYS", "CURRENT_DATA");
                    // request.addProperty("USERNAME", username);

                    request.addProperty("onXML", current_xml);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);

                    HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                    androidHttpTransport.call(CommonString.SOAP_ACTION_UPLOAD_CURRRENT_DATA, envelope);
                    Object result = (Object) envelope.getResponse();

                    if (result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {


                    } else {

                        if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                            return CommonString.METHOD_UPLOAD_CURRENT_DATA;
                        }

                        // for failure
                        FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
                        xmlR.setContentHandler(failureXMLHandler);

                        InputSource is = new InputSource();
                        is.setCharacterStream(new StringReader(result
                                .toString()));
                        xmlR.parse(is);

                        failureGetterSetter = failureXMLHandler
                                .getFailureGetterSetter();

                        if (failureGetterSetter.getStatus().equalsIgnoreCase(
                                CommonString.KEY_FAILURE)) {
                            return CommonString.METHOD_UPLOAD_CURRENT_DATA + ","
                                    + failureGetterSetter.getErrorMsg();

                        } else {

                        }
                    }
                }


                return CommonString.KEY_SUCCESS;

            } catch (MalformedURLException e) {

                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;

            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;

            } catch (InterruptedIOException e) {

                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;


            } catch (IOException e) {

                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;

            } catch (XmlPullParserException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_XmlPull;

            } catch (Exception e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;

            }

            if (ResultFlag) {
                return CommonString.KEY_SUCCESS;

            } else {

                return strflag;
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();

            if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                dialog.dismiss();

                Intent in = new Intent(StoreimageActivity.this, MenuActivity.class);
                startActivity(in);
                finish();


                //showAlert(getString(R.string.data_downloaded_successfully));
            } else {

                AintuREDB db = new AintuREDB(StoreimageActivity.this);
                db.open();

                dialog.dismiss();
                db.deleteTableWithStoreID(store_id);

                showAlert(getString(R.string.datanotfound) + " " + result);
            }
        }

    }
*/

    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(StoreimageActivity.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private static String arabicToenglish(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }





    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(StoreimageActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }

    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_CANCELED: {

                        finish();
                    }

                    default:
                    {
                        break;
                    }
                }
                break;
        }

    }*/
}

