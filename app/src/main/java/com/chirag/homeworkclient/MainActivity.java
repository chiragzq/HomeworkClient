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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment loginFragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(loginFragment, "login_frag")
                .commit();
    }

    public void tryLogin(String username, String password) {
        findViewById(R.id.login_button).setEnabled(false);
        Log.i("debug", "Username: " + username + " Password: " + password);
        new LoginTask().execute(username, password);

    }

    public void openLoading() {
        LoadingFragment loadingFragment = new LoadingFragment();
        LoginFragment loginFragmentloginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(loginFragmentloginFragment, loadingFragment);
        transaction.addToBackStack(null);

        /*Fragment loginFragment = getSupportFragmentManager().findFragmentByTag("login_frag");
        Fragment loadingFragment = LoadingFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .remove(loginFragment)
                .add(loadingFragment, "Loading")
                .commit();*/
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
}
