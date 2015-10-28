package com.example.administrator.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Search extends ActionBarActivity {
    Context context = this;
    ArrayList<String> streamnames = new ArrayList<String>();
    ArrayList<String> coverimage = new ArrayList<String>();
    String searchname = new String();
    String email;
    ArrayList<String> search_res = new ArrayList<String>();
    ArrayList<String> search_more = new ArrayList<String>();
    ArrayList<String> search_more_name = new ArrayList<String>();
    ArrayList<String> search_url = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        EditText mEdText = (EditText) findViewById(R.id.searchname);
        TextView mtext = (TextView) findViewById(R.id.numresult);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        searchname = intent.getStringExtra("searchname");
        streamnames = intent.getStringArrayListExtra("streamname");
        coverimage = intent.getStringArrayListExtra("coverimage");
        email=intent.getStringExtra("account");
        for (int i = 0;i<streamnames.size();i++){
            if (Pattern.matches(".*" + searchname + ".*", streamnames.get(i))){
                search_res.add(streamnames.get(i));
                search_url.add(coverimage.get(i));
            }
        }
        search_more.addAll(search_url);
        search_more_name.addAll(search_res);
        mEdText.setText(searchname);
        if (search_res != null) {
            gridview.setAdapter(new ImageAdapter(context, search_url,search_res));
        }
        else{
            Toast.makeText(context,"no result",Toast.LENGTH_SHORT).show();
        }
        int len = search_res.size();
        mtext.setText(Integer.toString(len)+" result for \""+searchname+"\"");
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            Intent intent=new Intent(Search.this,singlestream.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("content", search_res.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> imageURLs;
        private ArrayList<String> streamname;

        public ImageAdapter(Context c, ArrayList<String> imageURLs, ArrayList<String> streamname) {
            mContext = c;
            this.imageURLs = imageURLs;
            this.streamname=streamname;
        }

        public int getCount() {
            if(imageURLs.size()>=8) {
                return 8;
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
                tv.setText(streamname.get(position));
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
    public void Searchnames(View view) {
        Intent intent = new Intent(this,Search.class);
        EditText editText = (EditText) findViewById(R.id.searchname);
        String message = editText.getText().toString();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        bundle1.putStringArrayList("streamname", streamnames);
        bundle2.putStringArrayList("coverimage", coverimage);
        bundle3.putString("searchname", message);
        intent.putExtras(bundle1);
        intent.putExtras(bundle2);
        intent.putExtras(bundle3);
        startActivity(intent);
    }
    public void moreres(View view){
        GridView gridview = (GridView) findViewById(R.id.gridview);

        if (search_more.size()>8) {
            for(int i=1;i<9;i++) {
                if(search_more.size()>0) {
                    search_more.remove(0);
                    search_more_name.remove(0);
                }
            }
            gridview.setAdapter(new ImageAdapter(context, search_more,search_more_name));
        }
        else{
            Toast.makeText(context,"no more results",Toast.LENGTH_SHORT).show();
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent=new Intent(Search.this,singlestream.class);
                Bundle bundle=new Bundle();
                bundle.putString("content", search_more_name.get(position));
                bundle.putString("account", email);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

