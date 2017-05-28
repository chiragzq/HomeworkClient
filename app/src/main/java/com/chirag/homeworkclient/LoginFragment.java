package com.chirag.homeworkclient;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class LoginFragment extends Fragment {
    static final String loginUrl = "https://webappsca.pcrsoft.com/Clue/SC-Student-Portal-Login-LDAP/8464";

    View.OnClickListener mClickListener;
    DataManager mDataManager;
    CustomWebViewClient mClient;
    WebView mWebView;

    public LoginFragment() {
    }

    // TODO: Rename and change type
    public static LoginFragment newInstance(View.OnClickListener listener, DataManager dataManager) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mClickListener = listener;
        fragment.mDataManager = dataManager;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.login_button).setOnClickListener(mClickListener);
        mClient = new CustomWebViewClient();
        mWebView = (WebView) view.findViewById(R.id.login_webview);
        mWebView.setWebViewClient(mClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        reset();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void setLoading(boolean loading) {
        getView().findViewById(R.id.login_button).setEnabled(!loading);
        getView().findViewById(R.id.password_et).setEnabled(!loading);
        getView().findViewById(R.id.username_et).setEnabled(!loading);
        getView().findViewById(R.id.login_progress).setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    public void reset() {
        getView().findViewById(R.id.login_failed_message).setVisibility(View.INVISIBLE);

        ((EditText)getView().findViewById(R.id.username_et)).setText("");
        ((EditText)getView().findViewById(R.id.password_et)).setText("");
    }

    public void autoLogin(OnLoginListener callback) {
        String cookies = CookieManager.getInstance().getCookie(loginUrl);
        String token;
        String sessionId;
        try {
            token = cookies.split(".ASPXAUTH=")[1].split(";")[0];
            sessionId = cookies.split("SessionId=")[1].split(";")[0];
        } catch (Exception e) {
            token = "";
            sessionId = "";
        }
        callback.onLogin(sessionId, token);
    }

    public void login(String username, String password, OnLoginListener callback) {
        logout();
        mClient.username = username;
        mClient.password = password;
        mClient.callback = callback;
        mWebView.loadUrl(loginUrl);
    }

    public void logout() {
        CookieManager.getInstance().removeAllCookies(null);
    }

    interface OnLoginListener {
        void onLogin(String sessionId, String token);
    }

    private class CustomWebViewClient extends WebViewClient {
        boolean alreadyTried = false;
        String username = "";
        String password = "";
        OnLoginListener callback = null;
        void login(WebView view, String username, String password) {
            String command =
                    "document.getElementById(\"ctl00_ctl00_baseContent_baseContent_flashTop_ctl00_Login1_UserName\").value=\""+username+"\";" +
                    "document.getElementById(\"ctl00_ctl00_baseContent_baseContent_flashTop_ctl00_Login1_Password\").value=\""+password+"\";" +
                    "document.getElementById(\"ctl00_ctl00_baseContent_baseContent_flashTop_ctl00_Login1_LoginButton\").click();";
            view.evaluateJavascript(command, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.e("WEBDEBUG", "Result (should be this): " + value);
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading (WebView view, WebResourceRequest request) {
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!alreadyTried) {
                login(view, username, password);
                alreadyTried = true;
            } else {
                autoLogin(callback);
                alreadyTried = false;
            }
        }
    }
}
