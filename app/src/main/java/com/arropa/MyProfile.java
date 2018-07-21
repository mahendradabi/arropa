package com.arropa;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.arropa.models.CityList;
import com.arropa.models.Citydetail;
import com.arropa.models.MyResponse;
import com.arropa.models.ProfileDetails;
import com.arropa.models.StateList;
import com.arropa.models.Statedetail;
import com.arropa.models.UserDetailsModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.spinnerCity)
    Spinner etCity;
    @BindView(R.id.spinnerState)
    Spinner etState;
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

    List<String> state = new ArrayList<>();
    List<String> city = new ArrayList<>();
    PreferenceManger preferenceManger;

    UserDetailsModel userDetailsModel;
    ProgressDialog progressDialog;

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

        progressDialog = DialogWindow.showProgressDialog(MyProfile.this, "My Profile",
                "Update profile in process please wait...");


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
                if (isValidate()) {
                    if (etMobile.getText().length() != 10) {
                        etMobile.setError("Enter 10 digit number");
                        etMobile.requestFocus();
                    } else if (etPassword.getText().length() < 8) {
                        etPassword.setError("Password minimum 8 charcter required");
                        etPassword.requestFocus();
                    } else {
                        new Requestor(Constant.UPDATE_PROFILE, MyProfile.this)
                                .updateProfile(etName.getEditableText().toString(),
                                        etShopName.getText().toString(),
                                        etAddharCardno.getText().toString(),
                                        etShopAddress.getText().toString(),
                                        etResAddress.getText().toString(),
                                        etCity.getSelectedItem().toString(),
                                        etState.getSelectedItem().toString(),
                                        etPincode.getText().toString(),
                                        etPassword.getText().toString(),
                                        etMobile.getText().toString(),
                                        PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
                    }
                } else {
                    Utility.showToast(MyProfile.this, "All fields required.");
                }
            }
        });

        city.add("Select City");
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, city);
        etCity.setAdapter(arrayAdapter);
        state.add("Select State");
        arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner_textview, state);
        etState.setAdapter(arrayAdapter);

        new Requestor(Constant.GET_PROFILE, this)
                .getProfileDetails(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
        etState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    new Requestor(Constant.CITY_LIST, MyProfile.this)
                            .getCity(etState.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(etName.getText().toString()) ||
                TextUtils.isEmpty(etShopName.getText().toString()) ||
                TextUtils.isEmpty(etAddharCardno.getText().toString()) ||
                TextUtils.isEmpty(etPassword.getText().toString()) ||
                TextUtils.isEmpty(etPincode.getText().toString()) ||
                TextUtils.isEmpty(etResAddress.getText().toString()) ||
                TextUtils.isEmpty(etShopAddress.getText().toString()) ||
                TextUtils.isEmpty(etEmail.getText().toString()) ||
                TextUtils.isEmpty(etMobile.getText().toString())
                )

            return false;
        else if (etState.getSelectedItemPosition() == 0) {
            Utility.showToast(MyProfile.this, "Select state");
            return false;
        } else if (etCity.getSelectedItemPosition() == 0) {
            Utility.showToast(MyProfile.this, "Select city");
            return false;
        } else return true;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.UPDATE_PROFILE:
                MyResponse myResponse = (MyResponse) o;
                if (myResponse != null && myResponse.getMessage() != null)
                    Utility.showToast(MyProfile.this, myResponse.getMessage());
                break;

            case Constant.GET_PROFILE:
                ProfileDetails details = (ProfileDetails) o;
                if (details != null) {
                    List<UserDetailsModel> fevdetail = details.getFevdetail();
                    if (fevdetail != null && fevdetail.get(0) != null) {

                        userDetailsModel = fevdetail.get(0);
                        etName.setText(userDetailsModel.getVenName());
                        etEmail.setText(userDetailsModel.getVenEmail());
                        etAddharCardno.setText(userDetailsModel.getVenAdhaarNo());
                        // etCity.setText(userDetailsModel.getCity());
                        etShopAddress.setText(userDetailsModel.getShopAddress());
                        etShopName.setText(userDetailsModel.getVenShopname());
                        etPincode.setText(userDetailsModel.getPincode());

                        etMobile.setText(userDetailsModel.getVendermobile());
                        etResAddress.setText(userDetailsModel.getResidentialAddress());
                        etPassword.setText(userDetailsModel.getPassword());
                        new Requestor(Constant.STATE_LIST, MyProfile.this)
                                .getStates();
                    }
                }
                break;


            case Constant.CITY_LIST:
                CityList cityList = (CityList) o;

                if (cityList != null && cityList.getCitydetail() != null) {
                    city.clear();
                    city.add("Select City");
                    int index = 0;
                    int count = 1;
                    for (Citydetail citydetail : cityList.getCitydetail()) {
                        city.add(citydetail.getCityName());
                        if (userDetailsModel != null && userDetailsModel.getCity() != null)
                            if (userDetailsModel.getCity().equalsIgnoreCase(citydetail.getCityName()))
                                index = count;
                        count++;
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, city);
                    etCity.setAdapter(arrayAdapter);

                    if (index > 0) {
                        etCity.setSelection(index);

                    }


                }
                break;
            case Constant.STATE_LIST:
                StateList stateList = (StateList) o;
                if (stateList != null) {
                    List<Statedetail> statedetail = stateList.getStatedetail();
                    if (statedetail != null) {
                        state.clear();
                        state.add("Select State");
                        int index = 0;
                        int count = 1;
                        for (Statedetail stateNames : stateList.getStatedetail()) {
                            state.add(stateNames.getStateName());
                            if (userDetailsModel != null && userDetailsModel.getState() != null)
                                if (userDetailsModel.getState().equalsIgnoreCase(stateNames.getStateName()))
                                    index = count;
                            count++;
                        }


                        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, state);
                        etState.setAdapter(arrayAdapter);

                        if (index > 0) {
                            etState.setSelection(index);
                            new Requestor(Constant.CITY_LIST, MyProfile.this)
                                    .getCity(etState.getSelectedItem().toString());
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}
