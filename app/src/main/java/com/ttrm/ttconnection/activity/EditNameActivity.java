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
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 修改昵称
 */
public class EditNameActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_name_et;
    private Button edit_name_btn;
    private String TAG="EditNameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        initViews();
    }

    private void initViews() {
        edit_name_et=(EditText)findViewById(R.id.edit_name_et);
        edit_name_btn=(Button)findViewById(R.id.edit_name_btn);
        edit_name_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_name_btn:
                if(TextUtils.isEmpty(edit_name_et.getText().toString().trim())){
                    MyUtils.showToast(EditNameActivity.this,"请输入昵称");
                    return;
                }
                editName();
                break;
        }
    }
    /**
     * 修改昵称
     */
    public void editName(){
        String url= HttpAddress.BASE_URL+HttpAddress.EDIT_NAME;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        finish();
                    }
                    String errorMsg=jsonObject.getString("errorMsg");
                    MyUtils.showToast(EditNameActivity.this,errorMsg);
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(EditNameActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("nickname",edit_name_et.getText().toString().trim());
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(EditNameActivity.this).add(stringRequest);
    }
}
