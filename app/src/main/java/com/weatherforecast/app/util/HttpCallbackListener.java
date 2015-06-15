package com.weatherforecast.app.util;

/**
 * Created by chenmo on 2015/5/24.
 */
public interface  HttpCallbackListener {
       void onFinish(String response);
       void onError(Exception e);
}