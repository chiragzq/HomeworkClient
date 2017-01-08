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

public class MainActivity extends FragmentActivity {
    MainClickListener mMainClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainClickListener = new MainClickListener();

        Fragment loginFragment = LoginFragment.newInstance(mMainClickListener);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, loginFragment, "login_frag")
                .commit();

    }

    public void tryLogin(String username, String password) {
        findViewById(R.id.login_button).setEnabled(false);
        Log.i("debug", "Username: " + username + " Password: " + password);
        new LoginTask().execute(username, password);

    }

    public void openLoading() {
        LoadingFragment loadingFragment = new LoadingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, loadingFragment);
        transaction.commit();
    }

    public class LoginTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            //TODO Make the network call to actually login

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        protected void onPostExecute(Boolean success) {
            Log.i("debug", "Logged in? " + success);
            findViewById(R.id.login_button).setEnabled(true);
            if (success) {
                openLoading();
            }
        }
    }

    public void onLoginClicked(){
        String username = ((EditText)findViewById(R.id.username_et)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_et)).getText().toString();
        tryLogin(username,password);
    }
    private class MainClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.login_button:
                    onLoginClicked();
                    break;
                default:
                    Log.i("Test", "Clicked id " + v.getId());
            }
        }
    }
}
