package com.example.duthientan.searchimagetool.Backend;

import android.util.Log;

import com.example.duthientan.searchimagetool.TabHost.Px500;
import com.example.duthientan.searchimagetool.Utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.Px500Api;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Du Thien Tan on 7/9/2015.
 */
public abstract class OAuthSocial implements Serializable {

    private static OAuthService mService;
    private static Token requestTokenFlickr;
    private static Token requestTokenTwitter;
    private static Token requestTokenPx;
    private static URL mURLFlick;
    private static URL mURLTwitter;
    private static URL mURLPx;
    private static Verifier verifierFlick;
    private static Verifier verifierTwitter;
    private static Verifier verifierPx;
    private static Token accessTokenFlick;
    private static Token accessTokenTwitter;
    private static Token accessTokenPx;

    public static URL getURL(String social) {
        switch (social) {
            case "flickr":
                return mURLFlick;
            case "twitter":
                return mURLTwitter;
            case "px":
                return mURLPx;
            default:
                return null;
        }
    }

    public static void setVerifier(Verifier verifier, String social) {
        switch (social) {
            case "flickr":
                OAuthSocial.verifierFlick = verifier;
                accessTokenFlick = mService.getAccessToken(requestTokenFlickr, verifier);
                break;
            case "twitter":
                OAuthSocial.verifierTwitter = verifier;
                accessTokenTwitter = mService.getAccessToken(requestTokenTwitter, verifier);
                break;
            case "px":
                OAuthSocial.verifierPx = verifier;
                accessTokenPx = mService.getAccessToken(requestTokenPx, verifier);
                break;
        }

    }

    public static void startOAuthSocial(String api) {
        switch (api) {
            case "flickr":
                mService = new ServiceBuilder()
                        .provider(FlickrApi.class)
                        .apiKey(ApiSocial.FLICKR_KEY)
                        .apiSecret(ApiSocial.FLICKR_SECRET_KEY)
                        .callback("https://www.google.com")
                        .build();
                requestTokenFlickr = mService.getRequestToken();
                try {
                    mURLFlick = new URL(mService.getAuthorizationUrl(requestTokenFlickr));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "twitter":
                mService = new ServiceBuilder()
                        .provider(TwitterApi.class)
                        .apiKey(ApiSocial.TWITTER_KEY)
                        .apiSecret(ApiSocial.TWITTER_SECRET_KEY)
                        .callback("https://www.google.com")
                        .build();
                requestTokenTwitter = mService.getRequestToken();
                try {
                    mURLTwitter = new URL(mService.getAuthorizationUrl(requestTokenTwitter));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "px":
                mService = new ServiceBuilder()
                        .provider(Px500Api.class)
                        .apiKey(ApiSocial.PX500_KEY)
                        .apiSecret(ApiSocial.PX500_SECRET_KEY)
                        .callback("https://www.google.com")
                        .build();
                requestTokenPx = mService.getRequestToken();
                try {
                    mURLPx = new URL(mService.getAuthorizationUrl(requestTokenPx));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public static List<URL> getResponeseUrl(final String social, final String key) {
        List<URL> result = new ArrayList<>();
        do {
            if (social == "flickr") {
                final URL[] urls = new URL[100];
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.flickr.com/services/rest/");
                            mService.signRequest(accessTokenFlick, request);
                            request.addQuerystringParameter("method", "flickr.photos.search");
                            request.addQuerystringParameter("text", key);
                            Response response = request.send();
                            int i = 0;
                            List<URL> tmp = new ArrayList<URL>();
                            Document document = Jsoup.parseBodyFragment(response.getBody());
                            for (Element element : document.select("photo")) {
                                urls[i] = new URL("https://farm" + element.attr("farm") + ".staticflickr.com/" + element.attr("server") + "/" + element.attr("id") + "_" + element.attr("secret") + ".jpg");
                                i++;
                            }
                        } catch (MalformedURLException e) {
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
                result = Arrays.asList(urls);
            }

            if(social=="twitter"){
                final List<URL> tmp = new ArrayList<>();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("SSSSSSSSSSSSSSSSSSS");
                            OAuthRequest requestt = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/search/tweets.json");
                            requestt.addQuerystringParameter("q", key);
                            requestt.addQuerystringParameter("count", "100");
                            requestt.addQuerystringParameter("filter", "images");
                            mService.signRequest(accessTokenTwitter, requestt);
                            Response responset = requestt.send();
                            JSONObject jsonObject = new JSONObject(responset.getBody());
                            JSONArray jsonArrayStatues = jsonObject.optJSONArray("statuses");
                            int lenght = jsonArrayStatues.length();
                            int count = 0;
                            String[] url = new String[100];
                            for (int i1 = 0; i1 < lenght; i1++) {
                                JSONObject jsonStatues = jsonArrayStatues.getJSONObject(i1);
                                if (jsonStatues.has("entities")) {
                                    JSONObject jsonEntities = jsonStatues.getJSONObject("entities");
                                    if (jsonEntities.has("media")) {
                                        JSONArray jsonArrayMedia = jsonEntities.getJSONArray("media");
                                        JSONObject jsonMedia = jsonArrayMedia.getJSONObject(0);
                                        url[count] = jsonMedia.optString("media_url_https");
                                        count++;
                                    }
                                }
                            }
                            for (String s : url) {
                                System.out.println(s);
                                if (!tmp.contains(s))
                                    tmp.add(new URL(s));
                            }
                            tmp.remove(null);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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
                result =tmp;
                for(URL url:result){
                    System.out.println(url.toString());
                }
            }
        } while (false);




        return result;
    }

}
