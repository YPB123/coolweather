package com.example.coolweather.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coolweather.R;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.config.StringKey;
import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.fragments
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/8 18:06
 * @describe:
 **/
public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private Context context;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> citiesList;
    private List<County> countiesList;

    //选中的
    private Province selectProvince;
    private City selectCity;
    private County selectCounty;

    //选中的级别
    private int currentLevel;

    private TextView mTitleTv;
    private Button mBtBack;
    private ListView mDataLv;
    private LinearLayout mLayoutLl;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);

        mTitleTv = view.findViewById(R.id.tv_title);
        mBtBack = view.findViewById(R.id.bt_back);
        mDataLv = view.findViewById(R.id.lv_data);
        mLayoutLl = view.findViewById(R.id.ll_layout);

        this.context = view.getContext();
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, dataList);
        mDataLv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentLevel == LEVEL_PROVINCE) {
                    selectProvince = provinceList.get(position);
                    queryCites();

                } else if (currentLevel == LEVEL_CITY) {
                    selectCity = citiesList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = countiesList.get(position).getWeatherId();
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weather_id", weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        mBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCites();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces() {
        mTitleTv.setText("中国");
        mBtBack.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            mDataLv.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServe(address, StringKey.KEY_PROVINCE);
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCites() {
        mTitleTv.setText(selectProvince.getProvinceName());
        mBtBack.setVisibility(View.VISIBLE);
        citiesList = DataSupport.where("provinceId = ?", String.valueOf(selectProvince.getId())).find(City.class);
        if (citiesList.size() > 0) {
            dataList.clear();
            for (City city : citiesList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            mDataLv.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else {
            int provinceCode = selectProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServe(address, StringKey.KEY_CITY);
        }
    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties() {
        mTitleTv.setText(selectCity.getCityName());
        mBtBack.setVisibility(View.VISIBLE);
        countiesList = DataSupport.where("cityId = ?", String.valueOf(selectCity.getId())).find(County.class);
        if (countiesList.size() > 0) {
            dataList.clear();
            for (County county : countiesList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            mDataLv.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            int provinceCode = selectProvince.getProvinceCode();
            int cityCode = selectCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode +"/"+ cityCode;
            queryFromServe(address, StringKey.KEY_COUNTY);
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据
     */
    private void queryFromServe(String address, final String type) {
        showProgressBar();
        Log.d("Http", "Url---->"+ address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("TAG",e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressBar();
                        Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("Http", "---> "+ responseText);
                boolean result = false;
                if (StringKey.KEY_PROVINCE.equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if (StringKey.KEY_CITY.equals(type)) {
                    result = Utility.handCityResponse(responseText, selectProvince.getId());
                } else if (StringKey.KEY_COUNTY.equals(type)) {
                    result = Utility.handCountyResponse(responseText, selectCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressBar();
                            if (StringKey.KEY_PROVINCE.equals(type)) {
                                queryProvinces();
                            } else if (StringKey.KEY_CITY.equals(type)) {
                                queryCites();
                            } else if (StringKey.KEY_COUNTY.equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showProgressBar() {
        if (mProgressBar == null) {
            createProgressBar();
        }
        mProgressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void closeProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void createProgressBar() {
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyle);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.gravity = Gravity.CENTER;
        mProgressBar.setLayoutParams(params);
        mLayoutLl.addView(mProgressBar);
    }
}
