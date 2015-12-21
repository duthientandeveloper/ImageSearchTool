package com.example.duthientan.searchimagetool.Backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Du Thien Tan on 7/30/2015.
 */
public class NonAuthention {

    public static List<URL> getResponeseUrl(String social, final String key) {
        do {
            if (social.equals("px")) {
                List<URL> result = new ArrayList<>();
                final List<URL> mListURl = new ArrayList<>();
                final StringBuffer response = new StringBuffer();
                for (int i = 1; i < 6; i++) {
                    final int[] num = new int[1];
                    num[0] = i;
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int tmp = num[0];
                                URL url = new URL("https://500px.com/search?utf8=%E2%9C%93&page=" + tmp + "&q=" + key + "&type=photos&order=score");
                                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                                connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                                connection.setRequestProperty("Connection", "keep-alive");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String readLine = "";

                                while ((readLine = reader.readLine()) != null) {
                                    response.append(readLine);
                                }
                                reader.close();
                                Document body = Jsoup.parse(response.toString());
                                for (Element element : body.select("div.photo")) {
                                    mListURl.add(new URL(element.select("img").attr("src")));
                                    System.out.println(element.select("img").attr("src"));
                                }

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        if (i == 5)
                            thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (URL url : mListURl) {
                    if (!result.contains(url))
                        result.add(url);
                }
                return result;
            }

            if (social.equals("flickr")) {
                final List<URL> mListURl = new ArrayList<>();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://www.flickr.com/search/?text=" + key);
                            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                            connection.setRequestProperty("Connection", "keep-alive");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String readLine = "";

                            while ((readLine = reader.readLine()) != null) {
                                if (readLine.contains("img.src=")) {
                                    mListURl.add(new URL("https://" + readLine.substring(16, readLine.length() - 2)));
                                }
                            }
                            reader.close();
                        } catch (MalformedURLException e) {
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
                return mListURl;
            }
            if(social.equals("instargram")){
                System.out.println("Hello");
                final List<String> mediaCounts = new ArrayList<>();
                final StringBuffer response = new StringBuffer();
                final String client_id = "167d7ebc732548bb9b9b0c33c727d572";
                Thread thread =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://api.instagram.com/v1/tags/search?q=" + key + "&client_id=" + client_id);
                            System.out.println(url.toString());
                            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                            connection.setRequestProperty("Connection", "keep-alive");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String readLine = "";
                            while ((readLine = reader.readLine()) != null) {
                                System.out.println(readLine);
                                response.append(readLine);
                            }
                            reader.close();
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray array = object.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject o = array.getJSONObject(i);
                                    mediaCounts.add(o.optString("media_count"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (MalformedURLException e) {
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
                for(String s : mediaCounts){
                    System.out.println(s);
                }
                Instagam instagam = new Instagam();
                return instagam.getmURLs(mediaCounts,false);
            }
        } while (false);


        return null;
    }
}
