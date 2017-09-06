package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 兑换码
 */
public class RedeemActivity extends BaseActivity implements View.OnClickListener {

    private EditText redeem_et;
    private Button redeem_change;
    private String TAG = "RedeemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        ActivityUtil.add(this);
        initViews();
    }

    private void initViews() {
        setToolBar("兑换码");
        redeem_et = (EditText) findViewById(R.id.redeem_et);
        redeem_change = (Button) findViewById(R.id.redeem_change);
        redeem_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.redeem_change:
                if (TextUtils.isEmpty(redeem_et.getText().toString().trim())) {
                    MyUtils.showToast(this, "请填写兑换码");
                    return;
                }
                redeem();
                break;
        }
    }

    /**
     * 兑换
     */
    private void redeem() {
        String url = HttpAddress.BASE_URL + HttpAddress.TO_DH;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        MyUtils.showToast(RedeemActivity.this, errorMsg);
                        finish();
                    } else {
                        MyUtils.showToast(RedeemActivity.this, errorMsg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RedeemActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("code", redeem_et.getText().toString().trim());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
