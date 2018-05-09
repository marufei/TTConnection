package com.ttrm.ttconnection.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.MainVpAdapter;
import com.ttrm.ttconnection.fragment.HomeFragment;
import com.ttrm.ttconnection.fragment.MineFragment;
import com.ttrm.ttconnection.fragment.ZsFragment;
import com.ttrm.ttconnection.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends CheckPermissionsActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener{


    private long exitTime = 0l;
    private ViewPager main_vp;
    private RadioGroup rg_bottom;
    private RadioButton rb0;
    private RadioButton rb1;
    private RadioButton rb2;
    private HomeFragment fragment1;
    private ZsFragment fragment2;
    private MineFragment fragment3;
    private List<Fragment> listFragnet=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActivityUtil.add(this);
        initView();
        initEvent();
        setPermission();
    }

    private void initEvent() {
        //设置主页第一个分页被选中
        rb0.setSelected(true);
        rb0.setChecked(true);
        fragment1 = new HomeFragment();
        fragment2 = new ZsFragment();
        fragment3 = new MineFragment();
        listFragnet.add(fragment1);
        listFragnet.add(fragment2);
        listFragnet.add(fragment3);

        rg_bottom.setOnCheckedChangeListener(this);
        main_vp.setAdapter(new MainVpAdapter(getSupportFragmentManager()));
        main_vp.setCurrentItem(0);
        main_vp.addOnPageChangeListener(this);
    }

    private void initView() {
        main_vp = (ViewPager) findViewById(R.id.main_vp);
        rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);
        rb0 = (RadioButton) findViewById(R.id.rb0);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb0:
                main_vp.setCurrentItem(0);
                break;
            case R.id.rb1:
                main_vp.setCurrentItem(1);
                break;
            case R.id.rb2:
                main_vp.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (main_vp.getCurrentItem()) {
                case 0:
                    rb0.setChecked(true);
                    break;
                case 1:
                    rb1.setChecked(true);
                    break;
                case 2:
                    rb2.setChecked(true);
                    break;
            }
        }
    }

    /**
     * 返回键的监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                ActivityUtil.exitAll();
                finish();
                System.exit(0);
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }
}
