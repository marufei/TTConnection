package com.ttrm.ttconnection.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.activity.LoginActivity;
import com.ttrm.ttconnection.entity.BaseBean;
import com.ttrm.ttconnection.http.HttpAddress;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class VolleyUtils {
    private  RequestQueue requestQueue;
    private  Context context;

    public  RequestQueue getRequestQueue(){
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
    public void Post(String url, final Map<String,String> map, final Class cls){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpAddress.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonData(response,cls);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        getRequestQueue().add(stringRequest);
    }
    public void Get(String url){
        StringRequest stringRequest=new StringRequest(HttpAddress.BASE_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getRequestQueue().add(stringRequest);
    }

    private void jsonData(String response,Class cls) {
        try{
            JSONObject jsonObject=new JSONObject(response);
            int errorCode=jsonObject.getInt("errorCode");
            String errorMsg=jsonObject.getString("errorMsg");
            if(errorCode==1){
                Object mData = new GsonBuilder()
                        .setPrettyPrinting()
                        .disableHtmlEscaping()
                        .create().fromJson(response, cls);


            }else {
                MyUtils.showToast(context,errorMsg);
            }
        }catch (Exception e){

        }
    }
}
