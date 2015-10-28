package com.example.administrator.search;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class singlestream extends ActionBarActivity {
    Context context = this;
    private String TAG  = "Display Images";
    final ArrayList<String> imageURLs = new ArrayList<String>();
    final ArrayList<String> imagemore = new ArrayList<String>();
    String stream_name;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlestream);

        Intent intent=getIntent();
        stream_name=intent.getStringExtra("content");
        email=intent.getStringExtra("account");

        TextView names = (TextView) findViewById(R.id.streamname2);
        names.setText("Stream: "+stream_name);

        final String request_url = "http://enhanced-oxygen-107815.appspot.com/viewSingle?stream_name="+stream_name;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(request_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {

                final ArrayList<String> imageCaps = new ArrayList<String>();
                try {
                    JSONObject jObject = new JSONObject(new String(response));
                    JSONArray displayImages = jObject.getJSONArray("displayImages");
                    JSONArray displayCaption = jObject.getJSONArray("imageCaptionList");

                    for (int i = 0; i < displayImages.length(); i++) {
                        imageURLs.add(displayImages.getString(i));
                        imageCaps.add(displayCaption.getString(i));
                        System.out.println(displayImages.getString(i));
                    }
                    imagemore.addAll(imageURLs);
                    GridView gridview = (GridView) findViewById(R.id.gridview2);
                    gridview.setAdapter(new ImageAdapter(context, imageURLs));
                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            Toast.makeText(context, imageCaps.get(position), Toast.LENGTH_SHORT).show();

                            Dialog imageDialog = new Dialog(context);
                            imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            imageDialog.setContentView(R.layout.thumbnail);
                            ImageView image = (ImageView) imageDialog.findViewById(R.id.thumbnail_IMAGEVIEW);

                            Picasso.with(context).load(imageURLs.get(position)).into(image);

                            imageDialog.show();
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
            if (imageURLs.size() >= 16) {
                return 16;
            } else {
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
                imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            Picasso.with(mContext).load(imageURLs.get(position)).into(imageView);
            return imageView;
        }

    }
    public void morepicture(View view) {
        GridView gridview = (GridView) findViewById(R.id.gridview2);

        if (imagemore.size()>16) {
            for(int i=1;i<17;i++) {
                if(imagemore.size()>0) {
                    imagemore.remove(0);
                }
            }
            gridview.setAdapter(new ImageAdapter(context, imagemore));
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

//                            Toast.makeText(context, imageCaps.get(position), Toast.LENGTH_SHORT).show();

                    Dialog imageDialog = new Dialog(context);
                    imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    imageDialog.setContentView(R.layout.thumbnail);
                    ImageView image = (ImageView) imageDialog.findViewById(R.id.thumbnail_IMAGEVIEW);

                    Picasso.with(context).load(imagemore.get(position)).into(image);

                    imageDialog.show();
                }
            });
        }
        else{
            Toast.makeText(context,"no more pictures",Toast.LENGTH_SHORT).show();
        }
    }
    public void tostreams(View view) {
        Intent intent= new Intent(this, MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("account", email);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toupload(View view) {

        Intent intent = new Intent(this,ImageUpload.class);

        Bundle bundle=new Bundle();
        bundle.putString("content", stream_name);
        bundle.putString("account", email);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

