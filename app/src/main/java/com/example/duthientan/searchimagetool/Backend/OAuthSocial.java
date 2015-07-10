package com.example.duthientan.searchimagetool.Backend;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Du Thien Tan on 7/9/2015.
 */
public class OAuthSocial {
    private OAuthService mService;
    private Token requestToken;
    private URL mURL;
    private Verifier verifier;
    private Token accessToken;

    public void setVerifier(Verifier verifier) {
        this.verifier = verifier;
        accessToken = mService.getAccessToken(requestToken, verifier);
    }

    public OAuthSocial(Api api, String apiKey, String apiSecret){
        mService = new ServiceBuilder()
                .provider(api)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
        requestToken = mService.getRequestToken();
        try {
            mURL = new URL(mService.getAuthorizationUrl(requestToken));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Response getResponeseUrl(String url){
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        mService.signRequest(accessToken, request);
        return request.send();
    }

}
