package com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.view;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.utils.cascade.CascadeListener;
import com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.model.CityModel;
import com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.model.DistrictModel;
import com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.model.ProvinceModel;
import com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.service.XmlParserHandler;
import com.shinc.duobaohui.utils.cascade.kankan.wheel.widget.OnWheelChangedListener;
import com.shinc.duobaohui.utils.cascade.kankan.wheel.widget.WheelView;
import com.shinc.duobaohui.utils.cascade.kankan.wheel.widget.adapters.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@SuppressWarnings("ALL")
public class CascadeViewLayout extends RelativeLayout implements OnClickListener, OnWheelChangedListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;
    private Context mActivity;
    private CascadeListener cascadeListener;
    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName = "";
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName = "";
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    public CascadeViewLayout(Context context) {
        super(context);
        mActivity = context;
        onCreate();
    }

    public CascadeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
        onCreate();
    }

    public CascadeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = context;
        onCreate();
    }


    protected void onCreate() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cascade_layout, this);
        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(mActivity, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        updateDistrict(0);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
            showSelectedResult();
        } else if (wheel == mViewCity) {
            updateAreas();
            showSelectedResult();
        } else if (wheel == mViewDistrict) {
//            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
//            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            updateDistrict(newValue);
            showSelectedResult();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateDistrict(int newValue) {
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);

    }

//    /**
//     * 根据当前的市，更新区WheelView的信息
//     */
//    private void updateDistrict(int newValue) {
//        int pCurrent = mViewDistrict.getCurrentItem();
//        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[pCurrent];
//        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
//        if (areas == null) {
//            areas = new String[]{""};
//        }
//        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mActivity, areas));
//        mViewDistrict.setCurrentItem(newValue);
//    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(mActivity, areas));
        mViewDistrict.setCurrentItem(0);
        updateDistrict(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(mActivity, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            default:
                break;
        }
    }

    public void setCascadeListener(CascadeListener cascadeListener) {
        this.cascadeListener = cascadeListener;
    }

    private void showSelectedResult() {
        cascadeListener.getCascadeValue(mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName + "," + mCurrentZipCode);
    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = mActivity.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    if (districtList != null && !districtList.isEmpty()) {
                        mCurrentDistrictName = districtList.get(0).getName();
                        mCurrentZipCode = districtList.get(0).getZipcode();
                    }
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}
