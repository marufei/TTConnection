package com.ttrm.ttconnection.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Dictionary;
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

public class Dialogshow1 extends Dialog implements View.OnClickListener, OnWheelChangedListener {
    private Activity activity;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private OnBtnlistenner onBtnlistenner;
    /**
     * 城市ID
     */
    private String mCurrentCityCode;


    public void setOnBtnlistenner(OnBtnlistenner onBtnlistenner) {
        this.onBtnlistenner = onBtnlistenner;
    }
    //  ????
    /**
     * 省的集合
     */
    protected String[] mProvinceDatas;

    /**
     * key-市code value-市
     */
    private Map<String, String> mCitisZipMap = new HashMap<>();

    /**
     * key-省 value - 市id集合
     */
    private Map<String, String[]> mCitisCodesMap = new HashMap<>();
    /**
     * key - 省 value - 市集合
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区集合
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key-市id，values-区id集合
     */
    private Map<String, String[]> mDistrictCodesMap = new HashMap<>();

    /**
     * key - ?? values - ???
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 省
     */
    protected String mCurrentProviceName;
    /**
     * 市
     */
    protected String mCurrentCityName;
    /**
     * 区，县
     */
    protected String mCurrentDistrictName = "";

    /**
     * 区县的编码
     */
    protected String mCurrentZipCode = "";
    private String TAG = "Dialogshow";
    private Button dialog_select_cancel;
    private Button dialog_select_sure;

    public Dialogshow1(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_address);
        MyUtils.Loge(TAG, "??");
        setUpViews();
        setUpListener();
        setUpData();
        setViewLocation();
        setCanceledOnTouchOutside(false);//??????
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        dialog_select_cancel = (Button) findViewById(R.id.dialog_select_cancel);
        dialog_select_sure = (Button) findViewById(R.id.dialog_select_sure);
        dialog_select_cancel.setOnClickListener(this);
        dialog_select_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_select_cancel:
                dismiss();
                break;
            case R.id.dialog_select_sure:
                LocationAddActivity.location = mCurrentProviceName + "-" + mCurrentCityName + "-" + mCurrentDistrictName;
                if (!TextUtils.isEmpty(mCurrentCityCode)) {
                    LocationAddActivity.cityCode = mCurrentCityCode;
                }
                MyUtils.Loge(TAG, "------location:" + LocationAddActivity.location + "------cityCode:" + LocationAddActivity.cityCode);
                onBtnlistenner.onSure();
                dismiss();
                break;
        }
    }

    private void setUpListener() {
        // ???change???
        mViewProvince.addChangingListener(this);
        // ???change???
        mViewCity.addChangingListener(this);
        // ???change???
        mViewDistrict.addChangingListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, mProvinceDatas));
        // ?????????????
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
            MyUtils.Loge(TAG, "onChanged----mCurrentDistrictName:" + mCurrentDistrictName);
            mCurrentZipCode = getKey(mZipcodeDatasMap, mCurrentDistrictName);
        }
    }

    private void updateAreas() {
        MyUtils.Loge(TAG, "进入--updateAreas方法--");
        int pCurrent = mViewCity.getCurrentItem();
        MyUtils.Loge(TAG, "输出--------pCurrent:" + pCurrent);
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        String[] codes = mDistrictCodesMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = areas[0];

        mCurrentCityCode=getKey(mCitisZipMap,mCurrentCityName);

        MyUtils.Loge(TAG,"updateAreas-----城市code:"+mCurrentCityCode);
        mCurrentZipCode = getKey(mZipcodeDatasMap, mCurrentDistrictName);


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

        mCurrentCityCode=getKey(mCitisZipMap,cities[0]);
        MyUtils.Loge(TAG,"updateCities-----城市code:"+mCurrentCityCode);
        updateAreas();
    }

    /**
     * ??dialog??????
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // ??????
        onWindowAttributesChanged(lp);
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = activity.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // ???????????xml?????????
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // ????xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // ?????????????????
            provinceList = handler.getDataList();
            //*/ ????????????????????
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    mCurrentCityCode=cityList.get(0).getZipcode();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    MyUtils.Loge(TAG, "initProvinceDatas-------mCurrentDistrictName:" + mCurrentDistrictName);
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // ???????????????
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
//                MyUtils.Loge(TAG,"CityModel:"+provinceList.get(i).getZipcode()+"--name:"+provinceList.get(i).getName());
                String[] cityNames = new String[cityList.size()];
                String[] cityCodes = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // ?????????????????????
                    cityNames[j] = cityList.get(j).getName();
                    cityCodes[j] = cityList.get(j).getZipcode();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    String[] distrinctCodeArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];

//                    CityModel cityModel = new CityModel(cityList.get(j).getZipcode(), cityList.get(j).getName());
                    MyUtils.Loge(TAG,"城市key:"+cityList.get(j).getZipcode()+"---城市value:"+cityList.get(j).getName());
                    mCitisZipMap.put(cityList.get(j).getZipcode(), cityList.get(j).getName());
                    for (int k = 0; k < districtList.size(); k++) {
                        // ????????????????/???????
//                        MyUtils.Loge(TAG, "key:" + districtList.get(k).getZipcode() + "--value:" + districtList.get(k).getName());
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getZipcode(), districtList.get(k).getName());
                        // ??/?????????????mZipcodeDatasMap
//                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getZipcode(), districtList.get(k).getName());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                        distrinctCodeArray[k] = districtModel.getZipcode();
                    }
                    // ??-??/????????????mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    mDistrictCodesMap.put(cityNames[j], distrinctCodeArray);
                }
                // ?-????????????mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
                mCitisCodesMap.put(provinceList.get(i).getName(), cityCodes);

            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    public interface OnBtnlistenner {
        public void onSure();
    }

    //根据map的value获取map的key
    private String getKey(Map<String, String> map, String value) {
        String key = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                key = entry.getKey();
            }
        }
        return key;
    }


}
