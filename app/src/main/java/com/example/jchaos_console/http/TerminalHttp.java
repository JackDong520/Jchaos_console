package com.example.jchaos_console.http;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TerminalHttp implements Httpimpl {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String sendGetHttp(String url) {
        String jsonString = new String();
        OkHttpClient client = new OkHttpClient();
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();

        //Request request = reqBuild.url(urlBuilder.build()).addHeader("Authorization", "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGl0eSI6IjExMDk2NDgxMzlAcXEuY29tIiwiaWF0IjoxNTYyNDkyMjI5LCJuYmYiOjE1NjI0OTIyMjksImV4cCI6MTU2MjUzNTQyOX0.-C3jv2zFy0akFrHUUntuHVPa7DDgIYzYeeSmdwi1HTw").build();
        Request request = reqBuild.url(urlBuilder.build()).build();
        try (Response response = client.newCall(request).execute()) {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonString.replace("-", "_");
        //System.out.println(jsonString);

        return jsonString;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String sendPostHtpp(String url, Map paras) {
        String jsonString = null;
        OkHttpClient client = new OkHttpClient();
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();
        Iterator entries = paras.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            urlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        Request request = reqBuild.url(urlBuilder.build()).build();
        try (Response response = client.newCall(request).execute()) {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(jsonString);
        return jsonString;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String getOsInfo(String terminalID) {
        Map<String, String> map = new HashMap<>();
        map.put("terminalid", terminalID);
        return sendPostHtpp("http://10.59.13.137:8080/getgetos", map);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getAllTerminalInfo() {
        return sendGetHttp("http://10.59.13.137:8080/getallterminalid");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String sendRunNmap(String terminalID) {
        Map<String, String> map = new HashMap<>();
        map.put("terminalid", terminalID);
        return sendPostHtpp("http://10.59.13.137:8080/runnmap", map);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) {
        TerminalHttp terminalHttp = new TerminalHttp();
        // System.out.println(terminalHttp.getAllTerminalInfo());

        System.out.println(terminalHttp.getOsInfo("09799182"));

    }
}
