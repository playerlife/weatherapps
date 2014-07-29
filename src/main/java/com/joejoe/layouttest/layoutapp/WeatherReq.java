package com.joejoe.layouttest.layoutapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.os.Handler;


public class WeatherReq {


    private Context context;
    private Handler handler;
    ProgressDialog pd1;

    public WeatherReq(Context c, Handler h) {
        this.context = c;
        this.handler = h;
    }

    public void requestWeatherData(String url) {
        RequestQueue mQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        parseXml(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                hideDialog();
            }
        }
        );


        showDialog();

        mQueue.add(stringRequest);
    }

    private void showDialog() {

        pd1 = new ProgressDialog(context);
        // 设置对话框的标题
        pd1.setTitle("正在加载中");
        // 设置对话框显示的内容
        pd1.setMessage("正在加载中，请等待...");

        pd1.show(); //②
    }

    private void hideDialog() {
        pd1.hide();
    }


    private void parseXml(String xml) {

        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        ArrayList<WeatherBean> weathers = new ArrayList<WeatherBean>();
        WeatherBean weather = null;
        try {
            parser.setInput(stream, "utf-8");
            int eventType = parser.getEventType();

            while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if (parser.getName().equals("city")) {

                            weather = new WeatherBean();

                            String quName = parser.getAttributeValue(null, "quName");
                            String cityname = parser.getAttributeValue(null, "cityname");
                            String stateDetailed = parser.getAttributeValue(null, "stateDetailed");
                            String tem1 = parser.getAttributeValue(null, "tem1");
                            String tem2 = parser.getAttributeValue(null, "tem2");
                            String windState = parser.getAttributeValue(null, "windState");

                            weather.setQuName(quName);
                            weather.setCityname(cityname);
                            weather.setStateDetailed(stateDetailed);
                            weather.setTem1(tem1);
                            weather.setTem2(tem2);
                            weather.setWindState(windState);
                            weathers.add(weather);
                        }
                        break;
                }

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = new Message();
        msg.what = 200;
        msg.obj = weathers;
        handler.sendMessage(msg);

    }

}
