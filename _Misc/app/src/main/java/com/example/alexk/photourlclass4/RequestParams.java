package com.example.alexk.photourlclass4;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by AlexK on 2/17/2018.
 */

public class RequestParams {

    private HashMap<String, String> params;
    private StringBuilder stringBuilder;

    public RequestParams() {
        params = new HashMap<>();
        stringBuilder = new StringBuilder();
    }

    public RequestParams addParameter(String key, String value){
        try {
            params.put(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this; //THIS Object value
    }

    public String getEncodedParameters(){

        for (String key:params.keySet()) {
            if(stringBuilder.length() >0){
                stringBuilder.append("&");
            }
            stringBuilder.append(key + "=" + params.get(key));
        }
        return stringBuilder.toString();
    }

    public String getEncodedUrl(String url){
        return url + "?" + getEncodedParameters();
    }
}
