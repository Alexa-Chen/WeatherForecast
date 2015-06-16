package com.weatherforecast.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.weatherforecast.app.db.WeatherForecastOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmo on 2015/5/24.
 */
public class WeatherForecastDB {
    /*
    * 数据库名
    * */

    public  static  final String DB_NAME = "weather_forecast";
    /*
    *  数据库版本
    * */
    public  static  final  int VERSION = 1;
    private  static  WeatherForecastDB weatherForecastDB;
    private SQLiteDatabase db;

    /*
    *  将构造方法私有化
    * */
    private  WeatherForecastDB(Context context){
        WeatherForecastOpenHelper dbHelper = new WeatherForecastOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /*
    * 获取 WeatherForecastDB 实例
    * */
    public  synchronized  static WeatherForecastDB getInstance(Context context) {
        if (weatherForecastDB == null) {
            weatherForecastDB = new WeatherForecastDB(context);
        }
        return weatherForecastDB;
    }

    /*
    * 将 province 实例存储到数据库
    * */
    public  void saveProvince(Province province){
        if(province == null) return;
        Log.i("saveProvince", province.getProvinceName() + ":" + province.getProvinceCode());
        ContentValues values = new ContentValues();
        values.put("province_name",province.getProvinceName());
        values.put("province_code", province.getProvinceCode());
        db.insert("province",null,values);
    }
    /*
    * 从数据库读取全国所有的省份信息
    * */

    public List<Province> loadProvinces(){
        List<Province> list =new ArrayList<Province>();
        Cursor cursor = db.query("province",null,null,null,null,null,null);
        Log.i("loadProvinces", "count: " + cursor.getCount());
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                Log.i("loadProvinces", province.getProvinceCode() + ":" + province.getProvinceName());
                list.add(province);
            }  while (cursor.moveToNext());
        }
        return list;
    }

    /*
    *   将city 实例存储到数据库
    * */

    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    /*
    * 从数据库读取某省下所有的城市信息
    * */
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /*
    * 将county 实例存储到数据库
    * */
    public  void  saveCounty(County county){
        if(county == null) return;
        ContentValues values = new ContentValues();
        values.put("county_name",county.getCountyName());
        values.put("county_code",county.getCountyCode());
        values.put("city_id",county.getCityId());
        db.insert("County", null, values);
        Log.i("saveCounty", county.getCityId() + "/" + county.getCountyCode() + ":" + county.getCountyName() + " Saved");
    }

    /*
    * 从数据库中读取某城市下所有县的信息
    * */

    public  List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor;
        try {
            cursor = db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        } catch(SQLiteException e) {
            return list;
        }
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
