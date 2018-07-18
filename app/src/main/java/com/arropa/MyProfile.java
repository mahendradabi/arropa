package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfile extends MyAbstractActivity implements ServerResponse {


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

    @BindView(R.id.updateProfile)
    Button updateProfile;


    PreferenceManger preferenceManger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("My Profile");
        showBackButton();

        ButterKnife.bind(this);

        etEmail.setKeyListener(null);
        etName.setKeyListener(null);
        etAddharCardno.setKeyListener(null);

        preferenceManger = PreferenceManger.getPreferenceManger();
        if (preferenceManger != null) {
            etName.setText(preferenceManger.getString(PrefKeys.USERNAME));
            etEmail.setText(preferenceManger.getString(PrefKeys.EMAIL));

        }

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate())
                {
                    Utility.showToast(MyProfile.this,"Prifle udpate test");
                }
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
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {

    }

    @Override
    public void error(Object o, int code) {

    }
}
