package com.joejoe.layouttest.layoutapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;


/**
 * Created by joejoe on 2014/7/29.
 */
public class WeatherAPP extends ActionBarActivity {

    private ListView list_view;
    private RelativeLayout detail;
    Animation leftIn, leftOut, rightIn, rightOut;
    ViewFlipper viewFlipper;

    TextView quName, cityname, stateDetailed, tem1, tem2, windState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        new WeatherReq(this, handler).requestWeatherData(getString(R.string.weather));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        viewFlipper.addView(getLayoutInflater().inflate(R.layout.list_view, null));
        viewFlipper.addView(getLayoutInflater().inflate(R.layout.details, null));


        list_view = (ListView) viewFlipper.findViewById(R.id.list_view);
        detail = (RelativeLayout) viewFlipper.findViewById(R.id.detail);


        leftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOut = AnimationUtils.loadAnimation(this, R.anim.left_out);

        rightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOut = AnimationUtils.loadAnimation(this, R.anim.right_out);


        quName = (TextView) viewFlipper.findViewById(R.id.quName);
        cityname = (TextView) viewFlipper.findViewById(R.id.cityname);
        stateDetailed = (TextView) viewFlipper.findViewById(R.id.stateDetailed);
        tem1 = (TextView) viewFlipper.findViewById(R.id.tem1);
        tem2 = (TextView) viewFlipper.findViewById(R.id.tem2);
        windState = (TextView) viewFlipper.findViewById(R.id.windState);


        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(rightIn);
                viewFlipper.setOutAnimation(rightOut);
                viewFlipper.showPrevious();
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                ArrayList<WeatherBean> list = (ArrayList<WeatherBean>) msg.obj;
                Log.d("list", String.valueOf(list));
                showWeathers(list);
            }
        }

    };

    private void showWeathers(ArrayList<WeatherBean> list) {


        MyAdapter adapter = new MyAdapter(list);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WeatherBean weather = (WeatherBean) view.getTag();

                String quName = weather.getQuName();
                String cityname = weather.getCityname();
                String stateDetailed = weather.getStateDetailed();
                String tem1 = weather.getTem1();
                String tem2 = weather.getTem2();
                String windState = weather.getWindState();

                WeatherAPP.this.quName.setText(quName);
                WeatherAPP.this.cityname.setText(cityname);
                WeatherAPP.this.stateDetailed.setText(stateDetailed);
                WeatherAPP.this.tem1.setText(tem1);
                WeatherAPP.this.tem2.setText(tem2);
                WeatherAPP.this.windState.setText(windState);



                viewFlipper.setInAnimation(leftIn);
                viewFlipper.setOutAnimation(leftOut);
                viewFlipper.showNext();//向右滑动
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        ArrayList<WeatherBean> list = null;

        public MyAdapter(ArrayList<WeatherBean> ls) {
            super();
            this.list = ls;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = null;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item, null);
            } else {
                view = convertView;
            }


            TextView title = (TextView) view.findViewById(R.id.title);

            title.setText(list.get(position).getCityname());
            view.setTag(list.get(position));

            return view;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }
    }


}
