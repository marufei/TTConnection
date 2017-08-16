package com.ttrm.ttconnection.http;

import android.util.Log;

import com.ttrm.ttconnection.util.HttpApi;
import com.ttrm.ttconnection.util.MyUtils;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by marufei
 * time on 2017/8/16
 */

public class HttpRequests {
    private static String baseUrl = "http://116.62.195.53/tt/index.php/Api/";
    private static HttpRequests instance = null;
    private HttpApi httpService;

    public static HttpRequests getInstance() {
        if (instance == null) {
            synchronized (HttpRequests.class) {
                if (instance == null) {
                    instance = new HttpRequests();
                }
            }
        }
        return instance;
    }

    private HttpRequests() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpApi.class);
    }

    public Observable post(String path, Map<String, String> map) {
        try {
            Observable<JSONObject> observable;
            if (path.split("/").length > 1) {
                String root = path.split("/")[0];
                path = path.split("/")[1];
                if (map != null)
                    observable = httpService.post(root, path, map);
                else
                    observable = httpService.post(root, path);
            } else if (map != null)
                observable = httpService.post(path, map);
            else
                observable = httpService.post(path);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            return observable;
        } catch (Exception e) {
            MyUtils.Loge("error", e.getMessage());
            return null;
        }
    }

    public Observable get(String path, Map<String, String> map) {
        try {
            Observable<JSONObject> observable;
            if (map != null) {
                observable = httpService.get(path, map);
            } else {
                observable = httpService.get(path);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            return observable;
        } catch (Exception e) {
            MyUtils.Loge("lawliex", e.getMessage());
            return null;
        }
    }
}
