package aintu.cpm.com.aintu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import aintu.cpm.com.aintu.xmlGetterSetter.FailureGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.LoginGetterSetter;
import constent.CommonString;

import aintu.cpm.com.aintu.xmlHandlers.XMLHandlers;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_LOCATION=1;
    String app_ver;
    LoginGetterSetter lgs = null;
    static int counter = 1;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private static final int REQUEST_READ_CONTACTS = 0;
    String lat = "0.0";
    String lon = "0.0";
    // UI references.
    private AutoCompleteTextView museridView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String userid, password;
    private int versionCode;
    int eventType;
    Button museridSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tv_version = (TextView) findViewById(R.id.tv_version_code);

        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

            // login_version.setText("Parinaam Version " + app_ver);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        tv_version.setText("Version " + app_ver);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        // Set up the login form.
        museridView = (AutoCompleteTextView) findViewById(R.id.userid);
        mPasswordView = (EditText) findViewById(R.id.password);

      /*  museridView.setText("testmer");
        mPasswordView.setText("cpm123");*/
        checkgpsEnableDevice();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

            // tv_version.setText("Version " + app_ver);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        museridSignInButton = (Button) findViewById(R.id.user_login_button);
        museridSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkgpsEnableDevice()){
                    attemptLogin();
                }else {
                    Toast.makeText(LoginActivity.this,"Please Open Gps",Toast.LENGTH_SHORT).show();
                }

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // Create a Folder for Images
        File file = new File(Environment.getExternalStorageDirectory(), "Aintu_Images");
        if (!file.isDirectory()) {
            file.mkdir();
        }

    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(museridView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid userid, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
      /*  if (mAuthTask != null) {
            return;
        }
*/
        // Reset errors.
        museridView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        userid = museridView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid userid address.
        if (TextUtils.isEmpty(userid)) {
            museridView.setError(getString(R.string.error_field_required));
            focusView = museridView;
            cancel = true;
        } /*else if (!isuseridValid(userid)) {
            museridView.setError(getString(R.string.error_invalid_userid));
            focusView = museridView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else if(!isuseridValid(userid)){
            Snackbar.make(museridView,getString(R.string.error_incorrect_username),Snackbar.LENGTH_SHORT).show();
        }else if(!isPasswordValid(password)){
            Snackbar.make(museridView,getString(R.string.error_incorrect_password),Snackbar.LENGTH_SHORT).show();
        }else{
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            new AuthenticateTask().execute();
        }
    }

    private boolean isuseridValid(String userid) {
        //TODO: Replace this with your own logic

        boolean flag = true;

        String u_id = preferences.getString(CommonString.KEY_USERNAME, "");

        if (!u_id.equals("") && !userid.equals(u_id)) {
            flag = false;
        }

        return flag;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        boolean flag = true;

        String pw = preferences.getString(CommonString.KEY_PASSWORD, "");

        if(!pw.equals("") &&!password.equals(pw)){
            flag = false;
        }

        return flag;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle("Login");
            dialog.setMessage("Authenticating....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

                String userauth_xml = "[DATA]" + "[USER_DATA][USER_ID]"
                        + userid + "[/USER_ID]" + "[Password]" + password
                        + "[/Password]" + "[IN_TIME]" + getCurrentTime()
                        + "[/IN_TIME]" + "[LATITUDE]" + lat
                        + "[/LATITUDE]" + "[LONGITUDE]" + lon
                        + "[/LONGITUDE]" + "[APP_VERSION]" + app_ver
                        + "[/APP_VERSION]" + "[ATT_MODE]OnLine[/ATT_MODE]"
                        + "[/USER_DATA][/DATA]";


                SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_LOGIN);
                request.addProperty("onXML", userauth_xml);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_LOGIN, envelope);

                Object result = (Object) envelope.getResponse();
                System.out.println("result "+result);

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            showAlert(CommonString.MESSAGE_FAILURE);
                        }
                    });

                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            showAlert(CommonString.MESSAGE_FALSE);
                        }
                    });

                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_CHANGED)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            showAlert(CommonString.MESSAGE_CHANGED);
                        }
                    });

                } else {

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    System.out.println("eventType "+eventType);
                    final FailureGetterSetter failureGetterSetter = XMLHandlers
                            .failureXMLHandler(xpp, eventType);

                    if (failureGetterSetter.getStatus().equalsIgnoreCase(
                            CommonString.KEY_FAILURE)) {
                       /* final AlertMessage message = new AlertMessage(
                                LoginActivity.this, CommonString.METHOD_LOGIN
                                + failureGetterSetter.getErrorMsg(),
                                "login", null);*/
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                showAlert(CommonString.METHOD_LOGIN
                                        + failureGetterSetter.getErrorMsg());
                            }
                        });
                    } else {

                        try {
                            // For String source

                            xpp.setInput(new StringReader(result.toString()));
                            xpp.next();
                            eventType = xpp.getEventType();
                            lgs = XMLHandlers.loginXMLHandler(xpp, eventType);
                            System.out.println("lgs  "+lgs);

                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // PUT IN PREFERENCES
                        editor.putString(CommonString.KEY_USERNAME, userid);
                        editor.putString(CommonString.KEY_PASSWORD, password);
                        editor.putString(CommonString.KEY_VERSION, lgs.getAPP_VERSION());
                        editor.putString(CommonString.KEY_PATH, lgs.getAPP_PATH());
                        editor.putString(CommonString.KEY_DATE, lgs.getCURRENTDATE());
                     //  editor.putString(CommonString.KEY_DATE, "01/21/2017");

                        editor.commit();
                        return CommonString.KEY_SUCCESS;

                    }
                }

                return "";

            } catch (MalformedURLException e) {

            /*    final AlertMessage message = new AlertMessage(
                        LoginActivity.this, AlertMessage.MESSAGE_EXCEPTION,
                        "acra_login", e);*/

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        showAlert(CommonString.MESSAGE_EXCEPTION);
                    }
                });

            } catch (IOException e) {
               /* final AlertMessage message = new AlertMessage(
                        LoginActivity.this,
                        AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket_login", e);*/

                counter++;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (counter < 3) {
                            new AuthenticateTask().execute();
                        } else {
                            showAlert(CommonString.MESSAGE_SOCKETEXCEPTION);
                            counter = 1;
                        }
                    }
                });
            } catch (Exception e) {
              /*  final AlertMessage message = new AlertMessage(
                        LoginActivity.this, AlertMessage.MESSAGE_EXCEPTION,
                        "acra_login", e);*/
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        showAlert(CommonString.MESSAGE_EXCEPTION);
                    }
                });
            }
            return "";

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            if (result.equals(CommonString.KEY_SUCCESS)) {

                if (preferences.getString(CommonString.KEY_VERSION, "").equals(Integer.toString(versionCode))) {

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                    startActivity(intent);
                    finish();

                } else {

                    Intent intent = new Intent(getBaseContext(), AutoUpdateActivity.class);
                    startActivity(intent);
                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                    startActivity(intent);
                    finish();

                }
            }

            dialog.dismiss();
        }

    }

    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       /* Intent i = new Intent(activity, StorelistActivity.class);
                        activity.startActivity(i);
                        activity.finish();*/

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

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());

        if (preferences.getString(CommonString.KEY_LANGUAGE, "").equalsIgnoreCase(CommonString.KEY_LANGUAGE_ARABIC_KSA)) {
            cdate = arabicToenglish(cdate);
        }

        return cdate;
    }

    class AnswerData {

        public String question_id, answer_id, username, visit_date, right_answer;

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getAnswer_id() {
            return answer_id;
        }

        public void setAnswer_id(String answer_id) {
            this.answer_id = answer_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVisit_date() {
            return visit_date;
        }

        public void setVisit_date(String visit_date) {
            this.visit_date = visit_date;
        }

        public String getRight_answer() {
            return right_answer;
        }

        public void setRight_answer(String right_answer) {
            this.right_answer = right_answer;
        }
    }



    private boolean checkgpsEnableDevice()
    {
        boolean flag=true;
        if(!hasGPSDevice(LoginActivity.this)){
            Toast.makeText(LoginActivity.this,"Gps not Supported",Toast.LENGTH_SHORT).show();
        }
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this,"Gps not enabled",Toast.LENGTH_SHORT).show();
            enableLoc();
            flag=false;
        }else if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(LoginActivity.this)){
           // Toast.makeText(LoginActivity.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
            flag=true;
        }
       return flag;
    }


    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
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
                                status.startResolutionForResult(LoginActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }

    }
    @Override
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

    }

}
