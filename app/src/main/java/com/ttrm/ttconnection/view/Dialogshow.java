package com.ttrm.ttconnection.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.activity.LocationAddActivity;
import com.ttrm.ttconnection.entity.CityModel;
import com.ttrm.ttconnection.entity.DistrictModel;
import com.ttrm.ttconnection.entity.ProvinceModel;
import com.ttrm.ttconnection.servlet.XmlParserHandler;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.view.citywheel.OnWheelChangedListener;
import com.ttrm.ttconnection.view.citywheel.WheelView;
import com.ttrm.ttconnection.view.citywheel.adapters.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by MaRufei
 * time on 2017/8/23
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public  class Dialogshow extends Dialog implements View.OnClickListener,OnWheelChangedListener {
    private Activity activity;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private OnBtnlistenner onBtnlistenner;

    public void setOnBtnlistenner(OnBtnlistenner onBtnlistenner) {
        this.onBtnlistenner = onBtnlistenner;
    }
    //  地区数据
    /**
     * ����ʡ
     */
    protected String[] mProvinceDatas;
    /**
     * key - ʡ value - ��
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - �� values - ��
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - �� values - �ʱ�
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * ��ǰʡ������
     */
    protected String mCurrentProviceName;
    /**
     * ��ǰ�е�����
     */
    protected String mCurrentCityName;
    /**
     * ��ǰ��������
     */
    protected String mCurrentDistrictName ="";

    /**
     * ��ǰ������������
     */
    protected String mCurrentZipCode ="";
    private String TAG="Dialogshow";
    private Button dialog_select_cancel;
    private Button dialog_select_sure;

    public Dialogshow(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_address);
        MyUtils.Loge(TAG,"进入");
        setUpViews();
        setUpListener();
        setUpData();
        setViewLocation();
        setCanceledOnTouchOutside(false);//外部点击取消
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        dialog_select_cancel=(Button)findViewById(R.id.dialog_select_cancel);
        dialog_select_sure=(Button)findViewById(R.id.dialog_select_sure);
        dialog_select_cancel.setOnClickListener(this);
        dialog_select_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_select_cancel:
                dismiss();
                break;
            case R.id.dialog_select_sure:
                LocationAddActivity.location=mCurrentProviceName+"-"+mCurrentCityName+"-"+mCurrentDistrictName;
                onBtnlistenner.onSure();
                dismiss();
                break;
        }
    }

    private void setUpListener() {
        // ���change�¼�
        mViewProvince.addChangingListener(this);
        // ���change�¼�
        mViewCity.addChangingListener(this);
        // ���change�¼�
        mViewDistrict.addChangingListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, mProvinceDatas));
        // ���ÿɼ���Ŀ����
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, areas));
        mViewDistrict.setCurrentItem(0);
    }
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(activity, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height =ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }
    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = activity.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // ����һ������xml�Ĺ�������
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // ����xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // ��ȡ��������������
            provinceList = handler.getDataList();
            //*/ ��ʼ��Ĭ��ѡ�е�ʡ���С���
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // ��������ʡ������
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // ����ʡ����������е�����
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        // ����������������/�ص�����
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // ��-��/�ص����ݣ����浽mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // ʡ-�е����ݣ����浽mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
    public interface OnBtnlistenner{
        public void onSure();
    }
}
