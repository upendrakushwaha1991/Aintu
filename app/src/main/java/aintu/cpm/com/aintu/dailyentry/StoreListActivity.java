package aintu.cpm.com.aintu.dailyentry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aintu.cpm.com.aintu.Database.AintuREDB;
import aintu.cpm.com.aintu.GetterSetter.StoreDetailsInsertGetterSetter;
import aintu.cpm.com.aintu.R;
import aintu.cpm.com.aintu.RetailerVisitTrackerActivity;
import aintu.cpm.com.aintu.StoreIdentificationTrackerActivity;
import aintu.cpm.com.aintu.download.DownloadActivity;
import aintu.cpm.com.aintu.xmlGetterSetter.CityMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.JourneyPlanGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreSearchGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.StoreTypeMasterGetterSetter;
import aintu.cpm.com.aintu.xmlGetterSetter.TableBean;
import aintu.cpm.com.aintu.xmlHandlers.XMLHandlers;
import constent.CommonString;

public class StoreListActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    ArrayList<CityMasterGetterSetter> reasondata = new ArrayList<CityMasterGetterSetter>();
    ArrayList<StoreDetailsInsertGetterSetter> store_list = new ArrayList<>();
    private  TextView close_dialog_button;
    private LinearLayout no_data_lay;
    private FloatingActionButton fab;
    private  TextView nodatasearch;
    private EditText search_store_character,search_store_number;
    private Spinner search_store_city;
    private Button search_store;
    private Toolbar toolbar;
    ArrayList<StoreSearchGetterSetter> storelist=new ArrayList<>();
    private SharedPreferences preferences;
    private String date, store_intime;
    private Typeface font;
    String store_cd;
    String user_type;
    AintuREDB db;
    Context context;
    // StoreListActivity.ValueAdapter adapter;
    RecyclerView recyclerView,drawer_layout_recycle_popup;
    private SharedPreferences.Editor editor = null;
    String store_id;
    boolean ResultFlag = true;
    int eventType;
    String str,cityname="",cityid="0";

    private ProgressDialog dialog;
    private Dialog dialog1;

    String storeSearch;
    StoreSearchGetterSetter storeSearchGetterSetter;
    private ArrayAdapter<CharSequence> city_adapter;
    CityMasterGetterSetter cityMasterDetails;
    String search_store_char="",search_store_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        getId();
        context=this;

        db = new AintuREDB(this);
        db.open();
        reasondata = db.getCityMasterData();


        city_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);

        city_adapter.add("Select City");

        for (int i = 0; i < reasondata.size(); i++) {
            city_adapter.add(reasondata.get(i).getCITY().get(0));
        }

        search_store_city.setAdapter(city_adapter);

        city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        search_store_city.setOnItemSelectedListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        user_type = preferences.getString(CommonString.KEY_USERNAME, null);
        editor = preferences.edit();

    }
    private  void getId(){
        no_data_lay=(LinearLayout)findViewById(R.id.no_data_lay);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab=(FloatingActionButton)findViewById(R.id.fab) ;
        search_store_character=(EditText)findViewById(R.id.search_store_character);
        search_store_number=(EditText)findViewById(R.id.search_store_number);
        search_store_city=(Spinner) findViewById(R.id.search_store_city);
        search_store=(Button)findViewById(R.id.search_store);

        font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //  ValueAdapterList
        recyclerView = (RecyclerView) findViewById(R.id.drawer_layout_recycle);
        fab.setOnClickListener(this);
        search_store.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });

        cityMasterDetails = new CityMasterGetterSetter();
        cityMasterDetails.setCITY_CD("");

    }

    public  void  setStoreListSearchData(){

        try {
            storelist = db.getStoreSearchData();
            if (storelist.size() > 0) {

                drawer_layout_recycle_popup.setAdapter(new  ValueAdapter(this,storelist));
                drawer_layout_recycle_popup.setLayoutManager(new LinearLayoutManager(this));

            } else {
                no_data_lay.setVisibility(View.VISIBLE);

                //  drawer_layout_recycle_popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Daily Entry");

        try {
            db.open();
            store_list=   db.getUploadStoreListData(date);
            if (store_list.size()>0) {

                ValueAdapterList adapterList=new  ValueAdapterList(this,store_list);
                recyclerView.setAdapter(adapterList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* else {

        db.deletedRecordStoreDetails(date);
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:

                if (isStoreCheckedIn()){
                    Snackbar.make(fab, "Please Checkout from current store", Snackbar.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(StoreListActivity.this, StoreimageActivity.class);
                    StoreDetailsInsertGetterSetter storelist=null;
                    i.putExtra(CommonString.KEY_OBJECT,storelist);
                    i.putExtra(CommonString.KEY_MODE,CommonString.KEY_FROM_ADD_STORE);
                    startActivity(i);
                }
                break;


            case R.id.search_store:

                if (isStoreCheckedIn()){
                    Snackbar.make(fab, "Please Checkout from current store", Snackbar.LENGTH_SHORT).show();
                }else {
                    storeSearch="";
                    search_store_char=search_store_character.getText().toString().trim();
                    search_store_no=search_store_number.getText().toString().trim();

                    if (search_store_char.isEmpty()){

                        if (search_store_no.isEmpty()){
                            Snackbar.make(fab, "Please fill store Name or Mobile Number", Snackbar.LENGTH_SHORT).show();
                        }else {
                            new UploadTask(StoreListActivity.this).execute();
                        }
                    }
                    else {
                        if (cityname.isEmpty()){
                            Snackbar.make(fab, "Please Select City", Snackbar.LENGTH_SHORT).show();
                        }else {
                            new UploadTask(StoreListActivity.this).execute();
                        }
                    }

                }
                  break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.search_store_city:
                if (position != 0) {
                    cityname = reasondata.get(position - 1).getCITY().get(0);
                    cityid = reasondata.get(position - 1).getCITY_CD().get(0);
                    cityMasterDetails.setCITY_CD(cityid);

                    break;
                }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class ValueAdapter extends RecyclerView.Adapter<StoreListActivity.ValueAdapter.MyViewHolder> {

        private LayoutInflater inflator;
        List<StoreSearchGetterSetter> data = Collections.emptyList();

        public ValueAdapter(Context context, List<StoreSearchGetterSetter> data) {

            inflator = LayoutInflater.from(context);
            this.data = data;

        }

        @Override
        public StoreListActivity.ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

            View view = inflator.inflate(R.layout.storeviewlist, parent, false);

            StoreListActivity.ValueAdapter.MyViewHolder holder = new StoreListActivity.ValueAdapter.MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final StoreListActivity.ValueAdapter.MyViewHolder viewHolder, final int position) {

            final StoreSearchGetterSetter current = data.get(position);

            viewHolder.txt.setText(current.getSTORE_NAME().get(0));
            viewHolder.address.setText(current.getSTORE_ADDRESS().get(0));

            viewHolder.relativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    search_store_city.setSelection(0);
                    search_store_character.setText("");
                    search_store_number.setText("");

                    Intent i = new Intent(StoreListActivity.this, StoreimageActivity.class);
                    i.putExtra(CommonString.KEY_OBJECT,current);
                    i.putExtra(CommonString.KEY_MODE,CommonString.KEY_FROM_JCP_STORE);
                    isStoreAlreadyDone(current.getSTORE_ID().get(0));
                    startActivity(i);
                    dialog1.dismiss();
                }
            });

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

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txt, address;
            ImageView icon;
            RelativeLayout relativelayout;
            ImageView imageview;
            Button chkbtn;
            CardView Cardbtn;

            public MyViewHolder(View itemView) {
                super(itemView);

                txt = (TextView) itemView.findViewById(R.id.storelistviewxml_storename);
                address = (TextView) itemView.findViewById(R.id.storelistviewxml_storeaddress);

                relativelayout = (RelativeLayout) itemView.findViewById(R.id.storenamelistview_layout);
                //imageview = (ImageView) itemView.findViewById(R.id.imageView2);
                imageview = (ImageView) itemView.findViewById(R.id.storelistviewxml_storeico);
                chkbtn = (Button) itemView.findViewById(R.id.chkout);
                Cardbtn = (CardView) itemView.findViewById(R.id.card_view);

            }
        }

    }


    public class ValueAdapterList extends RecyclerView.Adapter<ValueAdapterList.MyViewHolder> {

        private LayoutInflater inflator;
        List<StoreDetailsInsertGetterSetter> data = Collections.emptyList();

        public ValueAdapterList(Context context, List<StoreDetailsInsertGetterSetter> data) {

            inflator = LayoutInflater.from(context);
            this.data = data;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

            View view = inflator.inflate(R.layout.storeviewlist, parent, false);

            MyViewHolder holder = new  MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

            final StoreDetailsInsertGetterSetter current = data.get(position);

            viewHolder.txt.setText(current.getStore_name());
            viewHolder.address.setText(current.getStore_address());

            viewHolder.relativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(current.getUpload_status().equals(CommonString.KEY_U)){
                        Snackbar.make(fab, "Data and Image already uploaded", Snackbar.LENGTH_SHORT).show();

                    }else if(current.getUpload_status().equals(CommonString.KEY_D)){
                        Snackbar.make(fab, "Data already uploaded", Snackbar.LENGTH_SHORT).show();

                    }
                    else if (current.getUpload_status().equals(CommonString.KEY_C)){
                        Snackbar.make(fab, "Store already Checkout", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(StoreListActivity.this, MenuActivity.class);
                        i.putExtra(CommonString.KEY_OBJECT,current);
                        i.putExtra(CommonString.KEY_ID,current.getKey_id());
                        i.putExtra(CommonString.KEY_MODE,CommonString.KEY_FROM_JCP_STORE);
                        startActivity(i);
                    }

                }
            });

            if(current.getUpload_status().equals(CommonString.KEY_U)){
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
            }
            else if (current.getUpload_status().equals(CommonString.KEY_C)){
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
                viewHolder.imageview.setBackground(getDrawable(R.mipmap.exclamation));

            }
            else if (current.getUpload_status().equals(CommonString.KEY_D)){
                viewHolder.imageview.setVisibility(View.VISIBLE);
                viewHolder.chkbtn.setVisibility(View.INVISIBLE);
                viewHolder.imageview.setBackground(getDrawable(R.mipmap.tick_d));

            }
            else if (current.getUpload_status().equals(CommonString.KEY_VALID)){


                viewHolder.imageview.setVisibility(View.INVISIBLE);
                viewHolder.chkbtn.setVisibility(View.VISIBLE);
                viewHolder.chkbtn.setBackground(getDrawable(R.mipmap.checkout));
            }
            viewHolder.chkbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.UpdateStoreDetailsUploadStatus(current.getKey_id(),CommonString.KEY_C);
                    store_list=   db.getUploadStoreListData(date);
                    if (store_list.size()>0) {

                        ValueAdapterList adapterList=new  ValueAdapterList(context,store_list);
                        recyclerView.setAdapter(adapterList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                }
            });
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

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txt, address;
            ImageView icon;
            RelativeLayout relativelayout;
            ImageView imageview;
            Button chkbtn;
            CardView Cardbtn;

            public MyViewHolder(View itemView) {
                super(itemView);

                txt = (TextView) itemView.findViewById(R.id.storelistviewxml_storename);
                address = (TextView) itemView.findViewById(R.id.storelistviewxml_storeaddress);

                relativelayout = (RelativeLayout) itemView.findViewById(R.id.storenamelistview_layout);
                //imageview = (ImageView) itemView.findViewById(R.id.imageView2);
                imageview = (ImageView) itemView.findViewById(R.id.storelistviewxml_storeico);
                chkbtn = (Button) itemView.findViewById(R.id.chkout);
                Cardbtn = (CardView) itemView.findViewById(R.id.card_view);



            }
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


    private  class  UploadTask extends AsyncTask<Void, Void, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(StoreListActivity.this,"Processing","Please wait...",false,false);
            dialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                String resultHttp = "";


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_STORE_SEARCH);

                request.addProperty("Storename", search_store_char);
                request.addProperty("city", cityid);
                request.addProperty("mobile", search_store_no);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.TEM_PURI, envelope);

                String store_search="";

                Object result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    storeSearchGetterSetter = XMLHandlers.STORESEARCHXMLHandler(xpp, eventType);

                    if (storeSearchGetterSetter.getSTORE_ID().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;

                       /* store_search= storeSearchGetterSetter.getTable_STORE_SEARCH();
                        TableBean.setStoreSearch(store_search);*/
                    } else {
                        //    return "STORE_SEARCH";
                        return "STORE_SEARCH";
                    }

                }

                db = new AintuREDB(StoreListActivity.this);
                db.open();
                db.createTable(CommonString.CREATE_TABLE_STORE_SEARCH);
                db.InsertStoreSearch(storeSearchGetterSetter);


            } catch (MalformedURLException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (InterruptedIOException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;

            } catch (IOException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (XmlPullParserException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_XmlPull;
                return CommonString.MESSAGE_XmlPull;
            } catch (Exception e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;

                return CommonString.MESSAGE_EXCEPTION;
            }

            if (ResultFlag) {
                return "";
            } else {
                return str;
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equalsIgnoreCase("")) {
                searchStorePopup();
                dialog.dismiss();
                //  setStoreListData();
                // showAlert(getString(R.string.data_downloaded_successfully));
            } else {
                // nodatasearch.setVisibility(View.VISIBLE);
                dialog.dismiss();
                showAlert("No store found according to provided information, please add the store.");
            }
        }
        public void showAlert(String str) {

            AlertDialog.Builder builder = new AlertDialog.Builder(StoreListActivity.this);
            builder.setTitle("Parinaam");
            builder.setMessage(str).setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void searchStorePopup() {
        dialog1 = new Dialog(StoreListActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.search_store_popup);
        close_dialog_button=(TextView)dialog1.findViewById(R.id.close_button);
        drawer_layout_recycle_popup = (RecyclerView)dialog1.findViewById(R.id.drawer_layout_recycle_popup);
        nodatasearch=(TextView)dialog1.findViewById(R.id.nodatasearch);

        close_dialog_button.setTypeface(font);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog1.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        wlp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog1.setCanceledOnTouchOutside(true);
        dialog1.show();
        setStoreListSearchData();

        close_dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });

    }

    public boolean isStoreCheckedIn(){
        boolean flag=false;
        for (int i=0;i<store_list.size();i++ ){
            if (store_list.get(i).getUpload_status().equals(CommonString.KEY_VALID)||
                    store_list.get(i).getUpload_status().equals("N")){
                flag =true;
                break;
            }
        }
        return flag;

    }

    public  boolean isStoreAlreadyDone(String storecd){
        boolean flag=true;
        for (int i=0;i<store_list.size();i++ ){
            if (store_list.get(i).getCity_cd().equalsIgnoreCase(storecd)){
                flag =false;
                break;
            }
        }

    return flag;
    }

}
