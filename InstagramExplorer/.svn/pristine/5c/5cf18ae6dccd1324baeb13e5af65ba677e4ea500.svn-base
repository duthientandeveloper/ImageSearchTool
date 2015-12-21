package com.example.duthientan.searchimagetool.Backend;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Du Thien Tan on 7/23/2015.
 */
public class Instagam {
    URL url;
    List<URL> mURLs;
    String key;
    String access_token;
    public Instagam(){};
    public Instagam(String key, String access_token) {
        this.key = key;
        this.access_token = access_token;
    }

    public List<String> getMediaCounts() {
        final List<String> mediaCounts = new ArrayList<>();
        System.out.println(key+"_"+access_token);
        final String url = "https://api.instagram.com/v1/tags/search?q=" + key + "&access_token=" + access_token;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                StringBuilder builder = new StringBuilder();
                try {
                    HttpResponse response = client.execute(httpGet);
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        builder.append(line);
                    }
                    try {
                        JSONObject object = new JSONObject(builder.toString());
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject o = array.getJSONObject(i);
                            mediaCounts.add(o.optString("media_count"));
                            System.out.println(o.optString("media_count"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mediaCounts;
    }

    public List<URL> getmURLs(final List<String> media,boolean authen) {
        final List<URL> mURLs = new ArrayList<>();
                for (String s : media) {
                    String tmp;
                    if(authen)
                        tmp = "https://api.instagram.com/v1/media/" + s + "?access_token=" + access_token;
                    else
                    tmp="https://api.instagram.com/v1/media/" + s + "?client_id=167d7ebc732548bb9b9b0c33c727d572";
                    System.out.println(tmp);
                    final String url =tmp;
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    StringBuilder builder = new StringBuilder();
                    try {
                        HttpResponse response = client.execute(httpGet);
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        if (response.getStatusLine().getStatusCode() == 200) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder.append(line);
                            }
                        } else builder=null;
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(builder!=null){
                            JSONObject object = new JSONObject(builder.toString());
                            JSONObject array = object.getJSONObject("data");
                            JSONObject arrayImage = array.optJSONObject("images");
                            JSONObject jsonObject = arrayImage.getJSONObject("standard_resolution");
                            mURLs.add(new URL(jsonObject.optString("url")));
                            System.out.println(jsonObject.optString("url"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                        }
                    });
                    thread.start();
                    if(s.equals(media.get(media.size()-1))){
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }



        return mURLs;
    }
}
