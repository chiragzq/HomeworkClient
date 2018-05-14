package com.chirag.homeworkclient;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {
    NetworkManager mNetworkManager;
    MainClickListener mMainClickListener;
    DataManager mDataManager;
    LoginFragment mLoginFragment;
    //CalenderFragment mCalenderFragment;
    DayFragment mDayFragment;
    CalendarFragment mCalendarFragment;
    String mCookie1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetworkManager = new NetworkManager();
        mDataManager = new DataManager(mNetworkManager, this);
        mMainClickListener = new MainClickListener();
        mLoginFragment = LoginFragment.newInstance(mMainClickListener, mDataManager);

        mCalendarFragment = CalendarFragment.newInstance(mMainClickListener, mDataManager);
        mCalendarFragment.setOnDayClickedListener(new CalendarFragment.OnDayClickedListener() {
            @Override
            public void onDayClicked(int year, int month, int day) {
                openDay(year, month, day);
            }
        });
        mDayFragment = DayFragment.newInstance(mMainClickListener, mDataManager);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, mLoginFragment, "login_frag")
                .commit();
    }

    public void openCalender() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, mCalendarFragment);
        transaction.commit();
    }

    public void openDay(int year, int month, int day) {
        mDayFragment.setDate(year,month,day);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, mDayFragment).addToBackStack("day");
        transaction.commit();
    }

    public void onLoginClicked() {
        final String username = ((EditText)findViewById(R.id.username_et)).getText().toString();
        final String password = ((EditText)findViewById(R.id.password_et)).getText().toString();
        mLoginFragment.login(username, password, new LoginFragment.OnLoginListener() {
            @Override
            public void onLogin(String sessionId, String token) {
                boolean success = mNetworkManager.handleLogin(sessionId, token);
                if (success) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mDataManager.downloadAssignments();
                            openCalender();
                        }
                    }).start();
                } else {
                    mLoginFragment.setLoading(false);
                    findViewById(R.id.login_failed_message).setVisibility(View.VISIBLE);
                }
            }
        });

        mLoginFragment.setLoading(true);
    }

    public void onLogoutClicked() {
        mLoginFragment.logout();
        mNetworkManager.clearLogin();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, mLoginFragment);
        transaction.commit();
    }

    private class MainClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button:
                    onLoginClicked();
                    break;
                case R.id.logout_button:
                    onLogoutClicked();
                    break;
                case R.id.done_button:
                    try {
                        mDataManager.toggleDone((Assignment)v.getTag());
                        mDayFragment.updateList();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Log.i("Test", "Clicked id " + v.getId());
            }
        }
    }

}
