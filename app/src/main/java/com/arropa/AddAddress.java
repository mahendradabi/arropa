package com.arropa;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.arropa.models.AddressModel;
import com.arropa.payment.PayMentGateWay;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.google.gson.Gson;


public class AddAddress extends AppCompatActivity {

PreferenceManger managerInstance;
    LinearLayout root;
    RelativeLayout saved, form;

    AppCompatButton add_address, btn_continue;

    AppCompatTextView change_address, landmark_city, username, full_address, tv_mobile;

    EditText zipcode, address, landmark, city, state, fname, lname, mobile;

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
        landmark = (EditText) findViewById(R.id.landmark);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        mobile = (EditText) findViewById(R.id.mobile);

        landmark_city = (AppCompatTextView) findViewById(R.id.tv_landmark_city);
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
                Gson gson = new Gson();
                AddressModel addressModel = new AddressModel();
                addressModel.setZip_code(zipcode.getText().toString());
                addressModel.setAddress(address.getText().toString());
                addressModel.setLandmark(landmark.getText().toString());
                addressModel.setCity(city.getText().toString());
                addressModel.setState(state.getText().toString());
                addressModel.setFname(fname.getText().toString());
                addressModel.setLname(lname.getText().toString());
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
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayment();
            }
        });
    }

    private void updateUi() {
        if (managerInstance != null && managerInstance.getString(PrefKeys.ADDRESS) != null) {
            AddressModel addressModel = new Gson().fromJson(managerInstance.getString(PrefKeys.ADDRESS), AddressModel.class);
            if (addressModel != null) {
                zipcode.setText(addressModel.getZip_code());
                address.setText(addressModel.getAddress());
                landmark.setText(addressModel.getLandmark());
                city.setText(addressModel.getCity());
                state.setText(addressModel.getState());
                fname.setText(addressModel.getFname());
                lname.setText(addressModel.getLname());
                mobile.setText(addressModel.getMobile());


                landmark_city.setText(addressModel.getLandmark() + " - " + addressModel.getCity());
                username.setText(addressModel.getFname() + " " + addressModel.getLname());
                full_address.setText(addressModel.getAddress() + ", "
                        + addressModel.getLandmark() + ", " + addressModel.getCity() + " " + addressModel.getState() + " " + addressModel.getZip_code());
                tv_mobile.setText(addressModel.getMobile());
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    private void openPayment()
    {

        Intent intent = new Intent(AddAddress.this, PayMentGateWay.class);
        intent.putExtra("FIRST_NAME","Arropa testing Payment");
        intent.putExtra("PHONE_NUMBER","1234567890");
        intent.putExtra("EMAIL_ADDRESS","test@gmail.com");
        intent.putExtra("RECHARGE_AMT",String.valueOf(100));
        startActivity(intent);
    }
}
