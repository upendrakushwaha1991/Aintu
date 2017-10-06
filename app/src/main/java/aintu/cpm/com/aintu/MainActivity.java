package aintu.cpm.com.aintu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.dailyentry.SearchListActivity;
import aintu.cpm.com.aintu.dailyentry.StoreListActivity;
import aintu.cpm.com.aintu.download.DownloadActivity;
import aintu.cpm.com.aintu.upload.PreviousDataUploadActivity;
import aintu.cpm.com.aintu.upload.UploadActivity;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import constent.CommonString;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<CityMasterGetterSetter> citymaster = new ArrayList<CityMasterGetterSetter>();
    AintuREDB db;
    CityMasterGetterSetter cityMasterDetails;

    WebView webView;
    ImageView imageView;
    String date;
    private SharedPreferences preferences = null;
    //GSKOrangeDB db;
    String user_name;
    // ArrayList<StoreBean> storelist = new ArrayList<StoreBean>();
    View headerView;

    //  ArrayList<CoverageBean> coverageList;

    String error_msg;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new AintuREDB(this);
        db.open();
      /*
        citymaster = db.getCityMasterData();*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setBackgroundColor(Color.parseColor("#808080"));


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        imageView = (ImageView) findViewById(R.id.img_main);
        webView = (WebView) findViewById(R.id.webview);

        String url = preferences.getString(CommonString.KEY_NOTICE_BOARD_LINK, "");
        user_name = preferences.getString(CommonString.KEY_USERNAME, null);
        //user_type = preferences.getString(CommonString.KEY_USER_TYPE, null);

        //  db = new GSKOrangeDB(MainActivity.this);
        //  db.open();

        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);

        if (!url.equals("")) {

            webView.loadUrl(url);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);

        TextView tv_username = (TextView) headerView.findViewById(R.id.nav_user_name);
        //tv_usertype = (TextView) headerView.findViewById(R.id.nav_user_type);

        tv_username.setText(user_name);
        //tv_usertype.setText(user_type);
        navigationView.addHeaderView(headerView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_route_plan) {

            db = new AintuREDB(this);
            db.open();
            citymaster = db.getCityMasterData();
            if (citymaster.size() > 0) {
                Intent startDownload = new Intent(this, StoreListActivity.class);
                startActivity(startDownload);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            } else {

                Snackbar.make(webView, "Please Download Data First", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }


           /* db.open();
            reasondata = db.getCityMasterData();

            if (checkNetIsAvailable()) {
                reasondata = db.getCityMasterData();
               // storelist = db.getStoreData(date);

                if (reasondata.size() == 0) {

                    Snackbar.make(webView, R.string.title_store_list_download_data, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    //  Toast.makeText(getBaseContext(), "Please Download Data First", Toast.LENGTH_LONG).show();
                } else {

                    if (coverageList.size() == 0) {
                        Snackbar.make(webView, R.string.no_data_for_upload, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    } else {
                        if (isStoreCheckedIn() && isValid()) {

                            Intent i = new Intent(getBaseContext(), StoreListActivity.class);
                            startActivity(i);

                            //finish();
                        } else {
                            Snackbar.make(webView, error_msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }

                    }

                }

            } else {

                Snackbar.make(webView, getResources().getString(R.string.nonetwork), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                //  Toast.makeText(getApplicationContext(), "No Network Available", Toast.LENGTH_SHORT).show();
            }*/


/*
            Intent startDownload = new Intent(this, StoreListActivity.class);
            startActivity(startDownload);

            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);*//*


            // Handle the camera action
*/
        } else if (id == R.id.nav_download) {

           /* db.open();
            if(db.getStoreListPreviusUploadData(date).size()>0){
                Intent in = new Intent(getApplicationContext(), PreviousDataUploadActivity.class);
                startActivity(in);

            }else {
                Intent in = new Intent(getApplicationContext(), DownloadActivity.class);
                startActivity(in);
            }*/
            if (checkNetIsAvailable()) {

                if (db.getStoreListPreviusUploadData(date).size()>0) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Parinaam");
                    builder.setMessage(getResources().getString(R.string.previous_data_upload)).setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent in = new Intent(getApplicationContext(), PreviousDataUploadActivity.class);
                                    startActivity(in);
                                    //finish();
                                }
                            });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    Intent in1 = new Intent(getApplicationContext(), DownloadActivity.class);
                    startActivity(in1);
                }
            } else {
                Snackbar.make(webView, getResources().getString(R.string.nonetwork), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }


        } else if (id == R.id.search_data) {
            showExportDialog();

           /* Intent in = new Intent(getApplicationContext(), SearchListActivity.class);
            startActivity(in);*/


        } else if (id == R.id.nav_exit) {
            //  finish();
            ActivityCompat.finishAffinity(this);

            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        } else if (id == R.id.nav_upload) {
            db.open();
            if (checkNetIsAvailable()) {

                ArrayList<StoreDetailsInsertGetterSetter> storelist = db.getUploadStoreListData(date);
                if (storelist.size() > 0) {

                    boolean flag = false;
                    boolean flag2 = false;
                    for (int i = 0; i < storelist.size(); i++) {

                        if (!storelist.get(i).getUpload_status().equals(CommonString.KEY_U)) {
                            flag = true;
                            if(flag)
                            {
                                if (storelist.get(i).getUpload_status().equals(CommonString.KEY_C)||storelist.get(i).getUpload_status().equals(CommonString.KEY_D)) {
                                    flag2 = true;
                                    break;
                                }
                            }

                        }
                    }

                    if (flag) {
                        if (flag2) {
                            Intent in = new Intent(getApplicationContext(), UploadActivity.class);
                            startActivity(in);
                        }
                        else {
                            Snackbar.make(webView, "Please Checkout From Store", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }
                    }else {
                        Snackbar.make(webView, "All Store Data Uploaded", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }


                } else {
                    Snackbar.make(webView, "No Store For Upload", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }

            } else {

                Snackbar.make(webView, getResources().getString(R.string.nonetwork), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
           /* progress.setVisibility(View.GONE);
            WebViewActivity.this.progress.setProgress(100);*/
            imageView.setVisibility(View.INVISIBLE);
            webView.setVisibility(View.VISIBLE);
            super.onPageFinished(view, url);
            view.clearCache(true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
           /* progress.setVisibility(View.VISIBLE);
            WebViewActivity.this.progress.setProgress(0);*/
            super.onPageStarted(view, url, favicon);
        }

    }

    public void showExportDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(R.string.Areyou_sure_take_backup)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("resource")
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            File file = new File(Environment
                                    .getExternalStorageDirectory(),
                                    "Aintu_backup");
                            if (!file.isDirectory()) {
                                file.mkdir();
                            }

                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();

                            if (sd.canWrite()) {
                                long date = System.currentTimeMillis();

                                SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yy");
                                String dateString = sdf.format(date);

                                String currentDBPath = "//data//aintu.cpm.com.aintu//databases//" + AintuREDB.DATABASE_NAME;
                                String backupDBPath = "Aintu_Database_backup" + dateString.replace('/', '-');

                                String path = Environment.getExternalStorageDirectory().getPath() + "/Aintu_backup";

                                File currentDB = new File(data, currentDBPath);
                                File backupDB = new File(path, backupDBPath);

                                Snackbar.make(webView, "Database Exported Successfully", Snackbar.LENGTH_SHORT).show();
                                //Toast.makeText(MainActivity.this, getString(R.string.data_exported_successfully), Toast.LENGTH_SHORT).show();

                                if (currentDB.exists()) {
                                    @SuppressWarnings("resource")
                                    FileChannel src = new FileInputStream(currentDB).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    public boolean checkNetIsAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Main Menu");

    }*/
}
