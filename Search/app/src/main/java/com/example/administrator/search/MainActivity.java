package com.example.administrator.search;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.*;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    Context context = this;
    //    //-----location
//    protected static final String TAG = "location-updates-sample";
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;

    //    protected Button mStartUpdatesButton;
//    protected Button mStopUpdatesButton;
//    protected TextView mLastUpdateTimeTextView;
//    protected TextView mLatitudeTextView;
//    protected TextView mLoc;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    protected String mNoget;
    protected Double mLatitude=0.0;
    protected Double mLongitude=0.0;
    protected Boolean mRequestingLocationUpdates;

    protected String mLastUpdateTime;
    private String TAG  = "Display Images";
    String accountName;
    String sub;
    final ArrayList<String> imageURLs = new ArrayList<String>();
    final ArrayList<String> namelist = new ArrayList<String>();
    final ArrayList<String> location = new ArrayList<String>();
    final ArrayList<String> images = new ArrayList<String>();
    final ArrayList<String> streamnames = new ArrayList<String>();

//    public final static String SEARCH_NAME = "com.example.administrator.search.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentstream = getIntent();
        accountName = intentstream.getStringExtra("account");
        sub= intentstream.getStringExtra("sub");
        System.out.println(accountName);
        if (sub==null){
            sub="0";
        }
        System.out.println(sub);

        //----location
        // Locate the UI widgets.
//        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
//        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
//        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
//        mLoc = (TextView) findViewById(R.id.loc);
//        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);

        // Set labels.
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);
        mNoget = getResources().getString(R.string.noget);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        updateValuesFromBundle(savedInstanceState);
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        buildGoogleApiClient();

        //------

        final String request_url = "http://enhanced-oxygen-107815.appspot.com/viewAllPhotos?user_id="+accountName;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(request_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {


                try {

                    JSONObject jObject = new JSONObject(new String(response));
                    JSONArray displayImages;
                    JSONArray namelists;
                    if (sub.equals("1")) {
                        displayImages = jObject.getJSONArray("suburl");
                        namelists = jObject.getJSONArray("subnamelist");
                    }
                    else{
                        displayImages = jObject.getJSONArray("displayImages");
                        namelists = jObject.getJSONArray("namelist");
                    }
//                    JSONObject jObject = new JSONObject(new String(response));
//                    JSONArray displayImages = jObject.getJSONArray("displayImages");
//                    JSONArray namelists = jObject.getJSONArray("namelist");
                    JSONArray locations = jObject.getJSONArray("location");
                    JSONArray imageurl = jObject.getJSONArray("imageurl");
                    JSONArray streamname = jObject.getJSONArray("streamname");

                    for (int i = 0; i < displayImages.length(); i++) {
                        namelist.add(namelists.getString(i));
                        imageURLs.add(displayImages.getString(i));
//                        System.out.println(displayImages.getString(i));
//                        System.out.println(namelists.getString(i));
                    }
                    for (int i = 0; i < imageurl.length(); i++) {
                        location.add(locations.getString(i));
                        images.add(imageurl.getString(i));
                        streamnames.add(streamname.getString(i));
//                        System.out.println(imageurl.getString(i));
//                        System.out.println(locations.getString(i));
                    }
                    GridView gridview = (GridView) findViewById(R.id.gridview);
                    gridview.setAdapter(new ImageAdapter(context, imageURLs));


                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            Toast.makeText(context, namelist.get(position), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, singlestream.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("content", namelist.get(position));
                            bundle.putString("account", accountName);
                            intent.putExtras(bundle);
                            startActivity(intent);
//
//                            Dialog imageDialog = new Dialog(context);
//                            imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            imageDialog.setContentView(R.layout.thumbnail);
//                            ImageView image = (ImageView) imageDialog.findViewById(R.id.thumbnail_IMAGEVIEW);
//
//                            Picasso.with(context).load(imageURLs.get(position)).into(image);
//
//                            imageDialog.show();
                        }
                    });
                } catch (JSONException j) {
                    System.out.println("JSON Error");
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] errorResponse, Throwable e) {
                Log.e(TAG, "There was a problem in retrieving the url : " + e.toString());
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> imageURLs;


        public ImageAdapter(Context c, ArrayList<String> imageURLs) {
            mContext = c;
            this.imageURLs = imageURLs;

        }

        public int getCount() {
            if(imageURLs.size()>=16) {
                return 16;
            }
            else{
                return imageURLs.size();
            }
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);

                imageView.setLayoutParams(new GridView.LayoutParams(230, 230));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;

            }

            Picasso.with(mContext).load(imageURLs.get(position)).into(imageView);

            return imageView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Searchname(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        if(message.equals("")){
            Toast.makeText(this, "Please enter keywords",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this,Search.class);
            Bundle bundle1 = new Bundle();
            Bundle bundle2 = new Bundle();
            Bundle bundle3 = new Bundle();
            bundle1.putStringArrayList("streamname", namelist);
            bundle2.putStringArrayList("coverimage", imageURLs);
            bundle1.putString("account", accountName);
            bundle3.putString("searchname", message);
            intent.putExtras(bundle3);
            intent.putExtras(bundle1);
            intent.putExtras(bundle2);
            startActivity(intent);
        }
    }
    //-------------------location
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
//                setButtonsEnabledState();
            }
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            Toast.makeText(this, "updatefrombundle",Toast.LENGTH_SHORT).show();
            updateUI();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

//    public void startUpdatesButtonHandler(View view) {
//        if (!mRequestingLocationUpdates) {
//            mRequestingLocationUpdates = true;
//            setButtonsEnabledState();
//            startLocationUpdates();
//        }
//    }

//    public void stopUpdatesButtonHandler(View view) {
//        if (mRequestingLocationUpdates) {
//            mRequestingLocationUpdates = false;
//            setButtonsEnabledState();
//            stopLocationUpdates();
//        }
//    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }
//    private void setButtonsEnabledState() {
//        if (mRequestingLocationUpdates) {
//            mStartUpdatesButton.setEnabled(false);
//            mStopUpdatesButton.setEnabled(true);
//        } else {
//            mStartUpdatesButton.setEnabled(true);
//            mStopUpdatesButton.setEnabled(false);
//        }
//    }

    private void updateUI() {
        mLatitude = mCurrentLocation.getLatitude();
        mLongitude= mCurrentLocation.getLongitude();
//        mLoc.setText(mLatitude.toString()+" " + mLongitude.toString());
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();

        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
//-----------------------------------------------------------------------------------------------------Modified
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            if (mCurrentLocation != null) {
                Toast.makeText(this, "onconnected",Toast.LENGTH_SHORT).show();
                updateUI();
            }
            else{
                Toast.makeText(this, "nolocation",Toast.LENGTH_SHORT).show();
            }
        }

//        while (mCurrentLocation == null) {
//            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//            if (mCurrentLocation != null) {
//                updateUI();
//            }
//        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        Toast.makeText(this, "locationchanged",Toast.LENGTH_SHORT).show();
        updateUI();
        Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void nearby(View view){
        Intent intent = new Intent(this,Nearby.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", mLatitude);
        bundle.putDouble("longitude", mLongitude);
//        Log.d("location8",location.get(7));
        bundle.putStringArrayList("locations", location);
        bundle.putStringArrayList("streamnames", streamnames);
        bundle.putStringArrayList("images", images);
        bundle.putString("account", accountName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void sub(View view) {
        Intent intent = new Intent(this,MainActivity.class);

        Bundle bundle=new Bundle();
        bundle.putString("sub", "1");
        bundle.putString("account", accountName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}