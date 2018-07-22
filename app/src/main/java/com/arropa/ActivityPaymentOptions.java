package com.arropa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.arropa.models.MyResponse;
import com.arropa.payment.PayMentGateWay;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPaymentOptions extends MyAbstractActivity implements ServerResponse {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.confirmPayment)
    AppCompatButton confirmPyament;
    @BindView(R.id.payMoney)
    CheckBox payMoney;
    @BindView(R.id.paytmWallet)
    CheckBox paytmWallet;

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);
        initViews();
        initListeners();

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        setToolbar(toolbar);
        showBackButton();
        setTitle("Payment");

        dialog = DialogWindow.showProgressDialog(ActivityPaymentOptions.this, "Payment", "Order in process please wait...");
        dialog.setCancelable(false);


    }

    @Override
    public void initListeners() {
        payMoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    paytmWallet.setChecked(false);


            }
        });

        paytmWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    payMoney.setChecked(false);
            }
        });

        confirmPyament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payMoney.isChecked()) {
                    placeOrder();
                }
                else if (paytmWallet.isChecked())
                    Utility.showToast(ActivityPaymentOptions.this,"Paytm wallet not supported yet.");
                else Utility.showToast(ActivityPaymentOptions.this,"Please select payment option");
            }
        });
    }

    private void placeOrder() {
        dialog.show();
        new Requestor(Constant.PLACE_ORDER, ActivityPaymentOptions.this)
        .placeOrder(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }

    private void openPayment() {

        Intent intent = new Intent(ActivityPaymentOptions.this, PayMentGateWay.class);
        intent.putExtra("FIRST_NAME", "Arropa testing Payment");
        intent.putExtra("PHONE_NUMBER", "1234567890");
        intent.putExtra("EMAIL_ADDRESS", "test@gmail.com");
        intent.putExtra("RECHARGE_AMT", String.valueOf(100));
        startActivity(intent);
    }

    @Override
    public void success(Object o, int code) {
        dialog.dismiss();
        switch (code) {
            case Constant.PLACE_ORDER:
                MyResponse response = (MyResponse) o;
                if (response != null) {
                    if (response.isStatus())
                    {
                        openPayment();
                        finish();
                    }

                    Utility.showToast(ActivityPaymentOptions.this, response.getMessage());
                }

                break;
        }
    }

    @Override
    public void error(Object o, int code) {
        dialog.dismiss();

    }
}
