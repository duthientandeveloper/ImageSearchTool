package com.example.duthientan.searchimagetool.Backend;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Du Thien Tan on 7/3/2015.
 */
public class GoogleImageUrl {
    public String url = "";
    public String key = "";
    public GoogleImageUrl(String url){
        this.url = url;
    }

    public String pageCode(){
        final String[] pageCode = {""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements elements =  document.getElementsByTag("a");
                    for (Element element :elements){
                        if(element.toString().contains("Visually similar images")){
                            pageCode[0] = element.attr("href");
                            break;
                        }
                        if (element.toString().contains("<a class=\"_gUb\" href=")){
                            if (element.ownText().contains("logo"));
                            key = element.ownText().replace("logo","");
                        }
                    }
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
        return pageCode[0];
    }

    public String getUrlGG(String url){
        String result = "";
        result = url.replace(";","&");
        return result;
    }

        public List<String> getLinksImage(final String url){
        final List<String> list = new ArrayList<String>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36")
                            .referrer("https://www.google.com/")
                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                            .get();
                    Elements elements =  document.getElementsByTag("img");
                    for (Element element:elements){
                        if(element.toString().contains("data-src=\"https://encrypted-tbn3.gstatic.com/images?q")){
                                list.add(element.attr("data-src"));
                        }
                    }
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

        return list;
    }

}
