package com.markmahovyk.misteram.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.ResponseSingIn;
import com.markmahovyk.misteram.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordView;
    private View singInLayout;
    private View arrowLoginImageView;
    private ProgressBar loginProgressBar;
    private TextView errorSingInTv;
    private View singInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authorizationUser();


        singInLayout = findViewById(R.id.singInLayout);

        arrowLoginImageView = findViewById(R.id.arrowLoginImageView);

        loginProgressBar = (ProgressBar) findViewById(R.id.loadProgress);

        errorSingInTv = (TextView) findViewById(R.id.errorSingInTv);

        usernameEditText = (EditText) findViewById(R.id.login);

        passwordView = (EditText) findViewById(R.id.password);
        passwordView.setOnKeyListener(this);

        singInButton = findViewById(R.id.sign_in_button);
        singInButton.setOnClickListener(this);

        singInLayout.setOnClickListener(this);
    }

    private void authorizationUser() {
        if (SharePreference.getAppAuthToken(this) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }


    private void attemptLogin() {
        if (isEmptyField()) {
            return;
        }

        sendLoggingRequest();
    }

    private boolean isEmptyField() {
        if (usernameEditText.getText().length() == 0 || passwordView.getText().length() == 0) {
            errorSingInTv.setText("Поля должны быть не пустыми");
            inputStage();
            return true;
        }
        return false;
    }

    private void sendLoggingRequest() {
        App.getApi()
                .singIn(SharePreference.getTokenApp(this),
                        usernameEditText.getText().toString(),
                        passwordView.getText().toString())
                .enqueue(new Callback<ResponseSingIn>() {
                    @Override
                    public void onResponse(Call<ResponseSingIn> call, Response<ResponseSingIn> response) {

                        if (response != null && response.body() != null) {

                            SharePreference.setTokenAppAuthToken(LoginActivity.this, response.body().getAuthToken());
                            SharePreference.setUsername(LoginActivity.this, usernameEditText.getText().toString());

                            authorizationUser();
                        } else {
                            inputStage();

                            errorSingInTv.setText(getString(R.string.wrongDataInput));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSingIn> call, Throwable t) {

                        inputStage();

                        ConnectivityManager connectivityManager =
                                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                                != NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                                        != NetworkInfo.State.CONNECTED) {

                            errorSingInTv.setText(R.string.noConnection);
                        }
                    }
                });
    }


    private void inputStage() {
        singInButton.setBackgroundResource(R.color.colorDefaultSingInButton);
        loginProgressBar.setIndeterminate(false);
        loginProgressBar.setVisibility(View.GONE);
        arrowLoginImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            loginStage();
            hideKeyBord();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            loginStage();
            hideKeyBord();
        }
        hideKeyBord();
    }

    private void hideKeyBord(){
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);

        if (getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    private void loginStage() {
        singInButton.setBackgroundResource(R.color.colorLoginSingInButton);
        arrowLoginImageView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.VISIBLE);
        loginProgressBar.setIndeterminate(true);
        attemptLogin();
    }
}

