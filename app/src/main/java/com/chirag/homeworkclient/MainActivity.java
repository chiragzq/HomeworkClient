package com.chirag.homeworkclient;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends FragmentActivity {
    MainClickListener mMainClickListener;
    LoginFragment mLoginFragment;
    CalenderFragment mCalenderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainClickListener = new MainClickListener();

        mLoginFragment = LoginFragment.newInstance(mMainClickListener);
        mCalenderFragment = CalenderFragment.newInstance(mMainClickListener);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, mLoginFragment, "login_frag")
                .commit();

    }

    public void tryLogin(String username, String password) {
        findViewById(R.id.login_button).setEnabled(false);
        Log.i("debug", "Username: " + username + " Password: " + password);
        new LoginTask().execute(username, password);

    }

    public void openCalender() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, mCalenderFragment);
        transaction.commit();
    }

    public static void readStream(InputStream in) throws IOException {
        byte[] contents = new byte[1024];

        int bytesRead = 0;
        String strFileContents = "";
        while((bytesRead = in.read(contents)) != -1) {
            strFileContents += new String(contents, 0, bytesRead);
        }
        Log.i("debug", strFileContents);

    }
    public class LoginTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            try {
                URL url = new URL("https://webappsca.pcrsoft.com/Clue/SC-Student-Portal-Login-LDAP/8464?returnUrl=https%3a%2f%2fwebappsca.pcrsoft.com%2fClue%2fSC-Assignments-End-Date-Range%2f7536");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestProperty("Host","webappsca.pcrsoft.com");
                conn.setRequestProperty("Connection","keep-alive");
                conn.setRequestProperty("Cache-Control","max-age=0");
                conn.setRequestProperty("Origin","http://webappsca.pcrsoft.com");
                conn.setRequestProperty("Upgrade-Insecure-Requests","1");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestProperty("Referer","https://webappsca.pcrsoft.com/Clue/SC-Student-Portal-Login-LDAP/8464?returnUrl=https%3a%2f%2fwebappsca.pcrsoft.com%2fClue%2fSC-Assignments-End-Date-Range%2f7536");
                conn.setRequestProperty("Accept-Encoding","gzip, deflate, br");
                conn.setRequestProperty("Accept-Language","en-US,en;q=0.8,zh-TW;q=0.6,zh;q=0.4");
                conn.setRequestProperty("Cookie","ASP.NET_SessionId=ngkhlzpa4ly4r4nblc5qiol2; .ASPXAUTH=1F941E7566B69CD83349A260B3C22C5C1ED7E6AD51A0C83BFA12DE311F8678F163634D0C5F9C66502BFDD3B35D3C82A804193EDD2845FC88F5C554E68E10C3528FEEA6ED5DC9733DFE934AE27A5D74EA2D38B53D; _ga=GA1.3.900066248.1480825810; _gat=1; pcrSchool=Harker; WebSiteApplication=97");
                
                try {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    readStream(in);
                } finally {
                    conn.disconnect();
                }
            } catch(IOException e) {

            }

            return username.equals("Hi") && password.equals("Hi");
        }

        protected void onPostExecute(Boolean success) {
            if (success) {
                openCalender();
            } else {
                mLoginFragment.setLoading(false);
                findViewById(R.id.login_failed_message).setVisibility(View.VISIBLE);
            }
        }
    }

    public void onLoginClicked() {
        String username = ((EditText)findViewById(R.id.username_et)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_et)).getText().toString();
        tryLogin(username,password);

        mLoginFragment.setLoading(true);
    }

    public void onLogoutClicked() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, mLoginFragment);
        transaction.commit();
    }

    private class MainClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.login_button:
                    onLoginClicked();
                    break;
                case R.id.logout_button:
                    onLogoutClicked();
                    break;
                default:
                    Log.i("Test", "Clicked id " + v.getId());
            }
        }
    }

}
