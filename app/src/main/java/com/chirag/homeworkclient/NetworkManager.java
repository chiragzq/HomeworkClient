package com.chirag.homeworkclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class handles any network requests to pull data from the website
 */
public class NetworkManager {
    private static final String loginUrl = "https://webappsca.pcrsoft.com/Clue/SC-Student-Portal-Login-LDAP/8464";
    private static final String assignmentUrl = "https://webappsca.pcrsoft.com/Clue/SC-Assignments-End-Date-Range/7536";
    private static final String otherUrl = "https://webappsca.pcrsoft.com/Clue/SC-Assignments---Start-Date/8425";
    private String mSessionId;
    private String mToken;

    public NetworkManager() {

    }

    boolean handleLogin(String sessionId, String token) {
        mSessionId = sessionId;
        mToken = token;
        if(mToken.equals("")) {
            Log.e("WEBDEBUG", "FAILED TO LOGIN");
        } else {
            Log.e("WEBDEBUG", "Successful login: " + sessionId + " " + token);
        }
        return !mToken.equals("");
    }

    void clearLogin() {
        mSessionId = "";
        mToken = "";
    }

    private HttpsURLConnection createConnection(String url, String method) throws Exception {
        URL url_ = new URL(url);
        HttpsURLConnection client = (HttpsURLConnection) url_.openConnection();
        client.setDoOutput(true);
        client.setRequestMethod(method);
        return client;
    }

    private void setHeaders(HttpURLConnection client, String sessionId, int contentLength, String referer, String token) {
        client.setRequestProperty("Connection", "keep-alive");
        client.setRequestProperty("Cache-Control", "max-age=0");
        client.setRequestProperty("Origin", "https://webappsca.pcrsoft.com");
        client.setRequestProperty("Host", "webappsca.pcrsoft.com");
        client.setRequestProperty("Upgrade-Insecure-Requests", "1");
        client.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        client.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        client.setRequestProperty("DNT", "1");
        client.setRequestProperty("Referer", referer);
        client.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        if (sessionId != "") {
            client.setRequestProperty("Cookie", "ASP.NET_SessionId=" + sessionId + "; path=/; pcrSchool=Harker; WebSiteApplication=97; .ASPXAUTH=" + token + ";");
        }
    }

    public void getAssignmentPage() {
        try {
            HttpsURLConnection client = createConnection(assignmentUrl, "GET");
            setHeaders(client, mSessionId, 0, otherUrl, "");
            client.connect();

            int responseCode = client.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + otherUrl);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            //System.out.println(response.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
