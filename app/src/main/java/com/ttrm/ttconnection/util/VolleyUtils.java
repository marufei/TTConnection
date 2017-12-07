package com.ttrm.ttconnection.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
    private static VolleyUtils volleyUtils;
    private RequestQueue mRequestQueue;

    private static Context mCtx;
    private static final int TIME_OUT = 10 * 1000;//设置超时时间

    private VolleyUtils(Context mCtx) {
        this.mCtx = mCtx;
        mRequestQueue = getRequestQueue();
    }

    public static void setTimeOut(StringRequest stringRequest) {
        if (stringRequest != null) {
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);

    }

    public static synchronized VolleyUtils getInstance(Context context){

        if(volleyUtils == null){

            volleyUtils=new VolleyUtils(context);

        }

        return volleyUtils;

    }
}
