package com.weatherforecast.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.weatherforecast.app.model.City;
import com.weatherforecast.app.model.County;
import com.weatherforecast.app.model.Province;
import com.weatherforecast.app.model.WeatherForecastDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chenmo on 2015/5/24.
 */
public class Utility {
    /*
    * �����ʹ�����������ص�ʡ������
    * */
    public synchronized  static  boolean handleProvincesResponse(WeatherForecastDB weatherForecastDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces =response.split(",");
            if(allProvinces!= null && allProvinces.length>0){
                for(String p: allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);

                    // ���������������ݴ洢��province��
                    weatherForecastDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /*
    *   �����ʹ�����������ص��м�����
    * */

    public static  boolean handleCitiesResponse(WeatherForecastDB weatherForecastDB,String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities!=null&&allCities.length>0){
                for(String c: allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    // ���������������ݴ洢��city��
                    weatherForecastDB.saveCity(city);
                }
                return  true;
            }
        }
        return  false;
    }
    /*
    * �����ʹ�������������ؼ�����
    * */
    public  static  boolean handleCountiesResponse(WeatherForecastDB weatherForecastDB,String response,int cityId){

        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length>0){
                for (String c:allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    // ���������������ݴ洢��County��
                    weatherForecastDB.saveCounty(county);
                }
                return  true;
            }
        }
        return  false;
    }

    /*
       �������������ص�JSON���� �����������������ݿ�洢������
     */
    public static  void handleWeatherResponse(Context context, String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherInfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");

            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*
       �����������ص�����������Ϣ�洢��SharedPreferences�ļ���
     */

    public static  void saveWeatherInfo(Context context,String cityName,String weatherCode,
                                        String temp1, String temp2, String weatherDesp,String publishTime){

        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy��M��d��", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("punlish_Time", publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}




