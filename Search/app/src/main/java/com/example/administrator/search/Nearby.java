package com.example.administrator.search;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.sort;

public class Nearby extends ActionBarActivity {
    Context context = this;
    Double latitude;
    Double longitude;
    String email;
    ArrayList<String> locations;
    ArrayList<String> images;
    ArrayList<String> streamnames;
    ArrayList<String> more_url = new ArrayList<String>();
    ArrayList<String> distants =new ArrayList<String>();
    ArrayList<String> more_dis =new ArrayList<String>();
    Location current= new Location("0.0,0.0");
    Location imgloc= new Location("0.0,0.0");
    ArrayList<String> sortedimg =new ArrayList<String>();
    ArrayList<String> more_stream = new ArrayList<String>();
    ArrayList<String> sortedstream = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        Intent intent = getIntent();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        latitude = intent.getDoubleExtra("latitude",0.0);
        longitude = intent.getDoubleExtra("longitude", 0.0);
        locations = intent.getStringArrayListExtra("locations");
        streamnames = intent.getStringArrayListExtra("streamnames");
        email = intent.getStringExtra("account");
//        Log.d("location8", locations.get(7));
        images = intent.getStringArrayListExtra("images");
        current.setLatitude(latitude);
        current.setLongitude(longitude);
        ArrayList<nbimages> imgs = new ArrayList<nbimages>();

        for(int i= 0; i<images.size();i++){
            String loc = locations.get(i);
            if (!loc.equals("null")) {
//                Log.d("nearby", loc);
                loc = loc.split("\\(")[1];
                Log.d("nearby", loc);
                loc = loc.split("\\)")[0];
                Log.d("nearby", loc);
                String[] latlong = loc.split("\\%");
                Double lati = Double.parseDouble(latlong[0]);
                Double longi = Double.parseDouble(latlong[1]);
                Log.d("lat", lati.toString());
                Log.d("long", longi.toString());
                imgloc.setLongitude(longi);
                imgloc.setLatitude(lati);
                nbimages img = new nbimages("",0,"");
                img.dis = imgloc.distanceTo(current);
                img.url = images.get(i);
                img.streamname = streamnames.get(i);
                Log.d("urls",img.url);
                imgs.add(img);
            }
        }
        Collections.sort(imgs);
        for(int i= 0; i<imgs.size();i++) {
//            Log.d("urls",imgs.get(i).url);
            sortedimg.add(imgs.get(i).url);
            more_url.add(imgs.get(i).url);
            sortedstream.add(imgs.get(i).streamname);
            more_stream.add(imgs.get(i).streamname);
            int d =(int)imgs.get(i).dis;
            String dd;
            if(d>1000){
                d=(int)d/1000;
                dd = Integer.toString(d)+"km";
            }
            else{
                dd = Integer.toString((int)imgs.get(i).dis)+"m";
            }
            distants.add(dd);
            more_dis.add(dd);
        }
//        more_url.addAll(sortedimg);
        if (sortedimg != null) {
            gridview.setAdapter(new ImageAdapter(context, sortedimg,distants));

        }
        else{
            Toast.makeText(context, "no result", Toast.LENGTH_SHORT).show();
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent=new Intent(Nearby.this,singlestream.class);
                Bundle bundle=new Bundle();
                bundle.putString("content", sortedstream.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private class nbimages implements Comparable<nbimages>{
        String url;
        float dis;
        String streamname;
        nbimages(String url_,float dis_,String streamname_){
            this.url = url_;
            this.dis = dis_;
            this.streamname=streamname_;
        }

        @Override
        public int compareTo(nbimages other){
            if (this.dis > other.dis) return 1;
            else if (this.dis < other.dis) return -1;
            else return 0;
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> imageURLs;
        private ArrayList<String> distants;

        public ImageAdapter(Context c, ArrayList<String> imageURLs,ArrayList<String> distants) {
            mContext = c;
            this.imageURLs = imageURLs;
            this.distants = distants;
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
            // TODO Auto-generated method stub
            View v;
            if(convertView==null){
                LayoutInflater li = getLayoutInflater();
                v = li.inflate(R.layout.name, null);
                TextView tv = (TextView)v.findViewById(R.id.icon_text);
                tv.setText(distants.get(position));
                ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
                LinearLayout ll=(LinearLayout)v.findViewById(R.id.widget44);
                ll.setLayoutParams(new GridView.LayoutParams(215, 215));
                iv.setLayoutParams(new LinearLayout.LayoutParams(170,170));

                Picasso.with(mContext).load(imageURLs.get(position)).into(iv);

            }
            else
            {
                v = convertView;
            }
            return v;
        }

    }

    public void morepic(View view){
        GridView gridview = (GridView) findViewById(R.id.gridview);

        if (more_url.size()>16) {
            for(int i=1;i<17;i++) {
                if(more_url.size()>0) {
                    more_url.remove(0);
                    more_stream.remove(0);
                    more_dis.remove(0);
                }
            }
            gridview.setAdapter(new ImageAdapter(context, more_url,more_dis));

        }
        else{
            Toast.makeText(context,"no more pictures",Toast.LENGTH_SHORT).show();
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent=new Intent(Nearby.this,singlestream.class);
                Bundle bundle=new Bundle();
                bundle.putString("content", more_stream.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void tostreams(View view){
        Intent intent= new Intent(this, MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("account", email);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
