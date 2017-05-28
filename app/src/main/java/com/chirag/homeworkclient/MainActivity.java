package com.chirag.homeworkclient;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {
    NetworkManager mNetworkManager;
    MainClickListener mMainClickListener;
    DataManager mDataManager;

    LoginFragment mLoginFragment;
    //CalenderFragment mCalenderFragment;
    DayFragment mDayFragment;
    CalendarFragment mCalendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataManager = new DataManager();

        mMainClickListener = new MainClickListener();
        mLoginFragment = LoginFragment.newInstance(mMainClickListener, mDataManager);
        /*mCalenderFragment = CalenderFragment.newInstance(mMainClickListener, new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                openDay(year, month, day);
            }
        });*/
        mCalendarFragment = CalendarFragment.newInstance(mMainClickListener, mDataManager);
        mCalendarFragment.setOnDayClickedListener(new CalendarFragment.OnDayClickedListener() {
            @Override
            public void onDayClicked(int year, int month, int day) {
                openDay(year, month, day);
            }
        });
        mDayFragment = DayFragment.newInstance(mMainClickListener, mDataManager);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, mCalendarFragment, "cal2_frag")
                .commit();
        mNetworkManager = new NetworkManager();
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
        String username = ((EditText)findViewById(R.id.username_et)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_et)).getText().toString();
        mLoginFragment.login(username, password, new LoginFragment.OnLoginListener() {
            @Override
            public void onLogin(String sessionId, String token) {
                boolean success = mNetworkManager.handleLogin(sessionId, token);
                if (success) {
                    openCalender();
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
                default:
                    Log.i("Test", "Clicked id " + v.getId());
            }
        }
    }

}
