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
import com.ttrm.ttconnection.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 提现
 */
public class WithdrawCashActivity extends BaseActivity implements View.OnClickListener {

    private EditText cash_account;
    private EditText cash_name;
    private EditText cash_fee;
    private Button cash_commit;
    private String TAG = "WithdrawCashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash);
        ActivityUtil.add(this);
        initViews();
        setMenuBtn("明细",this,WithdrawCashInfoActivity.class);
    }

    private void initViews() {
        setToolBar("提现");
        cash_account = (EditText) findViewById(R.id.cash_account);
        cash_name = (EditText) findViewById(R.id.cash_name);
        cash_fee = (EditText) findViewById(R.id.cash_fee);
        cash_commit = (Button) findViewById(R.id.cash_commit);
        cash_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cash_commit:
                if (TextUtils.isEmpty(cash_account.getText().toString().trim())) {
                    MyUtils.showToast(this, "请填写账号");
                    return;
                }
                if (TextUtils.isEmpty(cash_name.getText().toString().trim())) {
                    MyUtils.showToast(this, "请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(cash_fee.getText().toString().trim())) {

                    MyUtils.showToast(this, "请填写提现金额");
                    return;
                } else {
                    double d_fee = Double.valueOf(cash_fee.getText().toString().trim());
                    if (d_fee < 10d) {
                        MyUtils.showToast(this, "提现金额不能小于10元");
                        return;
                    }
                }
                commit();

                break;
        }
    }

    /**
     * 提现
     */
    private void commit() {
        String url = HttpAddress.BASE_URL + HttpAddress.TO_CASH;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        MyUtils.showToast(WithdrawCashActivity.this, errorMsg);
                        finish();
                    } else if(errorCode==40001){
                        ActivityUtil.toLogin(WithdrawCashActivity.this, errorCode);
                    }else {
                        MyUtils.showToast(WithdrawCashActivity.this, errorMsg);
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(WithdrawCashActivity.this, "网路有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("fee", cash_fee.getText().toString().trim());
                map.put("aliaccount", cash_account.getText().toString().trim());
                map.put("name", cash_name.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
