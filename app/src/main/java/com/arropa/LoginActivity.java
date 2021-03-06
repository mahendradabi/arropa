package com.arropa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arropa.models.LoginModel;
import com.arropa.models.UserDetailsModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;
import com.arropa.utils.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements ServerResponse {

    @BindView(R.id.tvForgotPassword)
    AppCompatTextView tvForgotPassword;

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etEmail)
    EditText etEmail;
    ProgressDialog progressDialog;
    @BindView(R.id.termsConditions)
    AppCompatTextView termsConditions;

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    AppCompatTextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (PreferenceManger.getPreferenceManger().getString(PrefKeys.EMAIL) != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        ButterKnife.bind(this);

        progressDialog = DialogWindow.showProgressDialog(LoginActivity.this, "Login",
                "Login in process please wait...");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    if (Validator.isEmailValid(etEmail.getText().toString())) {
                        progressDialog.show();
                        new Requestor(Constant.LOGIN_CODE, LoginActivity.this)
                                .userLogin(etEmail.getText().toString(), etPassword.getText().toString());
                    } else {
                        etEmail.setError("enter valid email address");
                        etEmail.requestFocus();
                    }
                } else {
                    Utility.showToast(LoginActivity.this, "All fields required");
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWindow.showForgotDialog(LoginActivity.this).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ReadPrivacy.class);
                intent.putExtra("url",1);
                startActivity(intent);
            }
        });
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter email address");
            etEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return false;
        } else return true;

    }

    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
        switch (code) {
            case Constant.LOGIN_CODE:
                LoginModel response = (LoginModel) o;
                if (response != null) {
                    if (response.isStatus()) {
                        if (response.getUserdetail() != null && response.getUserdetail().getUserdetails() != null) {

                            UserDetailsModel userdetails = response.getUserdetail().getUserdetails();
                            PreferenceManger preferenceManger = PreferenceManger.getPreferenceManger();
                            preferenceManger.setString(PrefKeys.USERID,userdetails.getVenId());
                            preferenceManger.setString(PrefKeys.EMAIL, userdetails.getVenEmail());
                            preferenceManger.setString(PrefKeys.USERNAME, userdetails.getVenName());
                            preferenceManger.setString(PrefKeys.MOBILE,userdetails.getVendermobile());

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    }

                    Utility.showToast(LoginActivity.this, response.getMessage());

                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {
    progressDialog.dismiss();
    }
}
