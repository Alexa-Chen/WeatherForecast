package com.weatherforecast.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenmo on 2015/5/24.
 */
public class WeatherForecastOpenHelper  extends SQLiteOpenHelper {
    /*
    *    ��һ������������䶨��ɳ��� Ȼ����onCreate()������ȥִ�д���
    * */

    /*
    * province �������
    * */
    public  static final  String CREATE_PROVINCE ="create table Province("
            + "id integer primary key autoincrement, "
            + "province_name text, "
            + "province_code text)";
    /*
    *     city�� �������
    * */

    public static  final  String CREATE_CITY = "create table City("
            + "id integer primary key autoincrement, "
            + "city_name text, "
            + "city_code text, "
            + "province_id integer)";

    /*
    *   Country�� �������
    * */

    public  static  final  String CREATE_COUNTRY = "create table Country("
            + "id integer primary key autoincrement, "
            + "country_name text, "
            + "country_code text, "
            + "city_id integer)";


    public WeatherForecastOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PROVINCE); // ����province��
        db.execSQL(CREATE_CITY);  // ���� city��
        db.execSQL(CREATE_COUNTRY); // ���� country��
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


