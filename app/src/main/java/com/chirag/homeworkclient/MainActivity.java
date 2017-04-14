package com.chirag.homeworkclient;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.sql.Date;


public class MainActivity extends FragmentActivity {
    MainClickListener mMainClickListener;
    DataManager mDataManager;

    LoginFragment mLoginFragment;
    CalenderFragment mCalenderFragment;
    DayFragment mDayFragment;
    CalendarFragment2 mCalendarFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataManager = new DataManager();

        mMainClickListener = new MainClickListener();
        mLoginFragment = LoginFragment.newInstance(mMainClickListener, mDataManager);
        mCalenderFragment = CalenderFragment.newInstance(mMainClickListener, new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                openDay(year, month, day);
            }
        });
        mCalendarFragment2 = CalendarFragment2.newInstance(mMainClickListener, mDataManager);
        mCalendarFragment2.setOnDayClickedListener(new CalendarFragment2.OnDayClickedListener() {
            @Override
            public void onDayClicked(int year, int month, int day) {
                openDay(year, month, day);
            }
        });
        mDayFragment = DayFragment.newInstance(mMainClickListener, mDataManager);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, mCalendarFragment2, "cal2_frag")
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

    public void openDay(int year, int month, int day) {
        mDayFragment.setDate(year,month,day);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, mDayFragment).addToBackStack("day");
        transaction.commit();
    }

    public class LoginTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];


            if(username.equals("22pranavg")) {
                return false;
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
