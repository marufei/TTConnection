package com.ttrm.ttconnection.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.MyUtils;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaRufei
 * time on 2017/8/21
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button register_btn_code;
    private TimeCount time;
    private String TAG="RegisterFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, null);
        time = new TimeCount(60 * 1000, 1000);
        initViews();
        return view;
    }

    private void initViews() {
        register_btn_code = (Button) view.findViewById(R.id.register_btn_code);
        register_btn_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_code:
                MyUtils.Loge(TAG,"点击了按钮");
                getSms();
                break;
        }
    }

    /**
     * 短信验证码倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            register_btn_code.setText("获取验证码");
            register_btn_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            register_btn_code.setClickable(false);//防止重复点击
            register_btn_code.setText(millisUntilFinished / 1000 + "s之后重发");
        }
    }

    /**
     * 获取短信验证码
     */
    private void getSms() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://116.62.195.53/tt/index.php/Api" + HttpAddress.GET_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    JSONObject jsonObject=new JSONObject();
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
                        time.start();// 开始计时
                    }else {
                        MyUtils.showToast(getActivity(),errorMsg);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(),"请检查网络设置");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("phone", "13213580912");
                map.put("timeStamp",String.valueOf(new Date().getTime()/1000));

                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
