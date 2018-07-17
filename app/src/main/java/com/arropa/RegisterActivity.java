package com.arropa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arropa.models.MyResponse;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;
import com.arropa.utils.Validator;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements ServerResponse {
    @BindView(R.id.tvSingIn)
    TextView tvSignIn;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etShopName)
    EditText etShopName;
    @BindView(R.id.etCarNo)
    EditText etAddharCardno;
    @BindView(R.id.etShopAddress)
    EditText etShopAddress;
    @BindView(R.id.etResAddress)
    EditText etResAddress;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.etState)
    EditText etState;
    @BindView(R.id.etPincode)
    EditText etPincode;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etMobile)
    EditText etMobile;

    @BindView(R.id.creataccount)
    Button creataccount;

    ProgressDialog progressDialog;

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceManger.getPreferenceManger().getString(PrefKeys.EMAIL) != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        progressDialog = DialogWindow.showProgressDialog(RegisterActivity.this, "Register",
                "Registration in process please wait...");

        creataccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    if (Validator.isEmailValid(etEmail.getText().toString())) {
                        progressDialog.show();
                        new Requestor(Constant.REGISTER_CODE, RegisterActivity.this)
                                .userRegister(etName.getText().toString(),
                                        etEmail.getText().toString(), etShopName.getText().toString(),
                                        etAddharCardno.getText().toString(), etShopAddress.getText().toString(),
                                        etResAddress.getText().toString(), etCity.getText().toString(),
                                        etState.getText().toString(), etPincode.getText().toString(), etPassword.getText().toString(),
                                        etMobile.getText().toString());
                    } else {
                        etEmail.setError("enter valid email address");
                        etEmail.requestFocus();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(etName.getText().toString()) ||
                TextUtils.isEmpty(etShopName.getText().toString()) ||
                TextUtils.isEmpty(etAddharCardno.getText().toString()) ||
                TextUtils.isEmpty(etCity.getText().toString()) ||
                TextUtils.isEmpty(etPassword.getText().toString()) ||
                TextUtils.isEmpty(etPincode.getText().toString()) ||
                TextUtils.isEmpty(etResAddress.getText().toString()) ||
                TextUtils.isEmpty(etShopAddress.getText().toString()) ||
                TextUtils.isEmpty(etState.getText().toString()) ||
                TextUtils.isEmpty(etEmail.getText().toString()) ||
                TextUtils.isEmpty(etMobile.getText().toString())

                )

            return false;
        else return true;
    }

    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
        switch (code) {
            case Constant.REGISTER_CODE:
                MyResponse response = (MyResponse) o;
                if (response != null) {
                    if (response.isStatus()) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }

                    Utility.showToast(RegisterActivity.this, response.getMessage());

                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {
        progressDialog.dismiss();
    }
}
