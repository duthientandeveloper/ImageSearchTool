package com.example.duthientan.searchimagetool.Backend;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Du Thien Tan on 7/7/2015.
 */
public class Wiki_backend {

    private String key = "";

    public Map<String, String> getMap() {
        this.wikiLink(key);
        return map;
    }

    private Map<String,String> map = new HashMap<>();

    public String getKey() {
        return key;
    }

    public Wiki_backend(String key){
        this.key = key;
    }

    private void wikiLink(String key){
        final String url = "https://en.wikipedia.org/w/index.php?title=Special%3ASearch&profile=default&search="+key+"&fulltext=Search";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    org.jsoup.nodes.Document document = Jsoup.connect(url).get();
                    Elements elements = document.select("a");
                    for (Element element:elements){
                        if (element.toString().contains("span class=\"searchmatch\"")){
                            map.put(element.attr("title"),"https://en.wikipedia.org"+element.attr("href"));
                        }
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
    }


}
