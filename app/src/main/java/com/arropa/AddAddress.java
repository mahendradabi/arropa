package com.arropa;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.arropa.models.AddressModel;
import com.arropa.models.ProfileDetails;
import com.arropa.models.UserDetailsModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.google.gson.Gson;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AddAddress extends AppCompatActivity implements ServerResponse {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    PreferenceManger managerInstance;
    LinearLayout root;
    RelativeLayout saved, form;

    AppCompatButton add_address, btn_continue;

    AppCompatTextView change_address, username, full_address, tv_mobile;

    EditText zipcode, address, city, state, fname, mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shipment");


        zipcode = (EditText) findViewById(R.id.zipcode);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        fname = (EditText) findViewById(R.id.fname);
        mobile = (EditText) findViewById(R.id.mobile);

        username = (AppCompatTextView) findViewById(R.id.tv_username);
        full_address = (AppCompatTextView) findViewById(R.id.tv_full_address);
        tv_mobile = (AppCompatTextView) findViewById(R.id.tv_mobile);

        change_address = (AppCompatTextView) findViewById(R.id.change_address);

        add_address = (AppCompatButton) findViewById(R.id.add_address);
        btn_continue = (AppCompatButton) findViewById(R.id.btn_continue);

        saved = (RelativeLayout) findViewById(R.id.rl_saved_address);
        form = (RelativeLayout) findViewById(R.id.rl_address_form);

        root = (LinearLayout) findViewById(R.id.ll_container);


        managerInstance = PreferenceManger.getPreferenceManger();

        if (managerInstance != null && managerInstance.getString(PrefKeys.ADDRESS) != null) {
            saved.setVisibility(View.VISIBLE);
            form.setVisibility(View.GONE);
        } else {
            form.setVisibility(View.VISIBLE);
            saved.setVisibility(View.GONE);
        }

        updateUi();

        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    TransitionManager.beginDelayedTransition(root);
                saved.setVisibility(View.GONE);
                form.setVisibility(View.VISIBLE);
                updateUi();
            }
        });


        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllValidate()) {
                    Gson gson = new Gson();
                    AddressModel addressModel = new AddressModel();
                    addressModel.setZip_code(zipcode.getText().toString());
                    addressModel.setAddress(address.getText().toString());
                    addressModel.setCity(city.getText().toString());
                    addressModel.setState(state.getText().toString());
                    addressModel.setFname(fname.getText().toString());
                    addressModel.setMobile(mobile.getText().toString());
                    String s = gson.toJson(addressModel);
                    if (s != null) {
                        managerInstance.setString(PrefKeys.ADDRESS, s);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                            TransitionManager.beginDelayedTransition(root);
                        form.setVisibility(View.GONE);
                        saved.setVisibility(View.VISIBLE);
                        updateUi();

                    }
                }
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAddress.this,ActivityPaymentOptions.class));
                finish();
            }
        });
    }

    private boolean isAllValidate() {
        if (TextUtils.isEmpty(zipcode.getText().toString())) {
            zipcode.setError("enter zipcode");
            zipcode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            address.setError("enter address");
            address.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(city.getText().toString())) {
            city.setError("enter city");
            city.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(state.getText().toString())) {
            state.setError("enter state");
            state.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fname.getText().toString())) {
            fname.setError("enter your name");
            fname.requestFocus();
            return false;
        }
         else if (TextUtils.isEmpty(mobile.getText().toString())) {
            mobile.setError("enter mobile number");
            mobile.requestFocus();
            return false;
        } else if (mobile.getText().toString().length() != 10) {
            mobile.setError("enter 10 digit valid mobile number");
            mobile.requestFocus();
            return false;
        } else
            return true;

    }

    private void updateUi() {
        if (managerInstance != null && managerInstance.getString(PrefKeys.ADDRESS) != null) {
            AddressModel addressModel = new Gson().fromJson(managerInstance.getString(PrefKeys.ADDRESS), AddressModel.class);
            if (addressModel != null) {
                zipcode.setText(addressModel.getZip_code());
                address.setText(addressModel.getAddress());
                city.setText(addressModel.getCity());
                state.setText(addressModel.getState());
                fname.setText(addressModel.getFname());
                mobile.setText(addressModel.getMobile());


                username.setText(addressModel.getFname());
                full_address.setText(addressModel.getAddress() + ", "
                       + ", " + addressModel.getCity() + " " + addressModel.getState() + " " + addressModel.getZip_code());
                tv_mobile.setText(addressModel.getMobile());
            }
        } else {
            new Requestor(Constant.GET_PROFILE, AddAddress.this)
                    .getProfileDetails(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }



    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.GET_PROFILE:
                ProfileDetails details = (ProfileDetails) o;
                if (details != null) {
                    List<UserDetailsModel> fevdetail = details.getFevdetail();
                    if (fevdetail != null && fevdetail.get(0) != null) {
                        UserDetailsModel userDetailsModel = fevdetail.get(0);
                        if (userDetailsModel != null) {
                            zipcode.setText(userDetailsModel.getPincode());
                            address.setText(userDetailsModel.getResidentialAddress());
                            state.setText(userDetailsModel.getState());
                            city.setText(userDetailsModel.getCity());
                            mobile.setText(userDetailsModel.getVendermobile());
                            fname.setText(userDetailsModel.getVenName());
                        }
                    }
                    break;
                }
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}
