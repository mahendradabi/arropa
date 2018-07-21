package com.arropa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arropa.models.CityList;
import com.arropa.models.Citydetail;
import com.arropa.models.MyResponse;
import com.arropa.models.StateList;
import com.arropa.models.Statedetail;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;
import com.arropa.utils.Validator;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.creataccount)
    Button creataccount;

    ProgressDialog progressDialog;
    List<String> state = new ArrayList<>();
    List<String> stateid = new ArrayList<>();
    List<String> city = new ArrayList<>();


    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                    if (!Validator.isEmailValid(etEmail.getText().toString())) {
                        etEmail.setError("enter valid email address");
                        etEmail.requestFocus();
                    } else if (etAddharCardno.length() != 12) {
                        etAddharCardno.setError("Enter 12 digit addhar number");
                        etAddharCardno.requestFocus();
                    } else if (etMobile.getText().length() != 10) {
                        etMobile.setError("Enter 10 digit number");
                        etMobile.requestFocus();
                    } else if (etPassword.getText().length() < 8) {
                        etPassword.setError("Password minimum 8 charcter required");
                        etPassword.requestFocus();
                    } else {
                        progressDialog.show();
                        new Requestor(Constant.REGISTER_CODE, RegisterActivity.this)
                                .userRegister(etName.getText().toString(),
                                        etEmail.getText().toString(), etShopName.getText().toString(),
                                        etAddharCardno.getText().toString(), etShopAddress.getText().toString(),
                                        etResAddress.getText().toString(), etCity.getSelectedItem().toString(),
                                        etState.getSelectedItem().toString(), etPincode.getText().toString(), etPassword.getText().toString(),
                                        etMobile.getText().toString());
                    }
                } else
                    Toast.makeText(getApplicationContext(), "All field are required", Toast.LENGTH_SHORT).show();
            }
        });

        new Requestor(Constant.STATE_LIST,RegisterActivity.this)
                .getStates();

        city.add("Select City");
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, city);
        etCity.setAdapter(arrayAdapter);
        state.add("Select State");
       ArrayAdapter  stateAdpter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, state);
        etState.setAdapter(stateAdpter);

        etState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                    new Requestor(Constant.CITY_LIST,RegisterActivity.this)
                            .getCity(stateid.get(position));
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
            Utility.showToast(RegisterActivity.this, "Select state");
            return false;
        } else if (etCity.getSelectedItemPosition() == 0) {
            Utility.showToast(RegisterActivity.this, "Select city");
            return false;
        } else return true;
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
                        onBackPressed();
                    }
                    Utility.showToast(RegisterActivity.this, response.getMessage());

                }
                break;

            case Constant.CITY_LIST:
                CityList cityList = (CityList) o;

                if (cityList != null&&cityList.getCitydetail()!=null) {
                    city.clear();
                    city.add("Select City");
                    for (Citydetail citydetail : cityList.getCitydetail()) {
                        city.add(citydetail.getCityName());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, city);
                    etCity.setAdapter(arrayAdapter);


                }
                break;
            case Constant.STATE_LIST:
                StateList stateList = (StateList) o;
                if (stateList != null) {
                    List<Statedetail> statedetail = stateList.getStatedetail();
                    if (statedetail != null) {
                        state.clear();
                        stateid.clear();
                        state.add("Select State");
                        stateid.add("0");
                        for (Statedetail stateNames : stateList.getStatedetail()) {
                            state.add(stateNames.getName());
                            stateid.add(stateNames.getId());
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_textview, state);
                        etState.setAdapter(arrayAdapter);


                    }

                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
