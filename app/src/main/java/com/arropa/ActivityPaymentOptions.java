package com.arropa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.arropa.models.CartList;
import com.arropa.models.CartModel;
import com.arropa.models.MyResponse;
import com.arropa.models.PayAmountModel;
import com.arropa.models.PayModel;
import com.arropa.payment.PayMentGateWay;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;

import java.util.List;

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
    @BindView(R.id.total)
    TextView tvTotal;

    @BindView(R.id.tv_total_pay)
    AppCompatTextView totalPay;
    @BindView(R.id.tv_total_credit)
    AppCompatTextView totalcredit;
    @BindView(R.id.tv_total_order)
    AppCompatTextView totalOrder;


    ProgressDialog dialog;
    String totalAmount;

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
        confirmPyament.setEnabled(false);

        dialog = DialogWindow.showProgressDialog(ActivityPaymentOptions.this, "Payment", "Order in process please wait...");
        dialog.setCancelable(false);
        dialog.show();

        getCartSize();

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
                } else if (paytmWallet.isChecked())
                    Utility.showToast(ActivityPaymentOptions.this, "Paytm wallet not supported yet.");
                else Utility.showToast(ActivityPaymentOptions.this, "Please select payment option");
            }
        });
    }

    private void placeOrder() {
        dialog.show();
        new Requestor(Constant.PLACE_ORDER, ActivityPaymentOptions.this)
                .placeOrder(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }

    private void getCartSize() {
        new Requestor(Constant.GET_CART_LIST, this).finalPayment(
                PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID)
        );
    }


    private void openPayment() {

        if (totalAmount != null) {
            PreferenceManger manger = PreferenceManger.getPreferenceManger();
            Intent intent = new Intent(ActivityPaymentOptions.this, PayMentGateWay.class);
            intent.putExtra("FIRST_NAME", manger.getString(PrefKeys.USERNAME));
            intent.putExtra("PHONE_NUMBER", manger.getString(PrefKeys.MOBILE));
            intent.putExtra("EMAIL_ADDRESS", manger.getString(PrefKeys.EMAIL));
            intent.putExtra("RECHARGE_AMT", String.valueOf(totalAmount));
            startActivity(intent);
        }
    }

    @Override
    public void success(Object o, int code) {
        dialog.dismiss();
        switch (code) {
            case Constant.PLACE_ORDER:
                MyResponse response = (MyResponse) o;
                if (response != null) {
                    if (response.isStatus()) {
                        if (totalAmount != null) {
                            try {
                                double v = Double.parseDouble(totalAmount);
                                if (v > 0) {
                                    Utility.showToast(ActivityPaymentOptions.this, response.getMessage());
                                    openPayment();
                                    finish();
                                } else finish();
                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                    Utility.showToast(ActivityPaymentOptions.this, response.getMessage());
                }

                break;
            case Constant.GET_CART_LIST:
                PayAmountModel model = (PayAmountModel) o;
                if (model != null) {
                    PayModel details = model.getDetails();
                    if (details != null) {
                        confirmPyament.setEnabled(true);
                        totalcredit.setText(Constant.CURRENCY + " " + details.getTotalCreaditamount());
                        totalOrder.setText(Constant.CURRENCY + " " + details.getTotalOrderttlamount());
                        totalPay.setText(Constant.CURRENCY + " " + details.getTotalPayamount());
                        tvTotal.setText(Constant.CURRENCY + " " + details.getTotalPayamount());
                        totalAmount = String.valueOf(details.getTotalPayamount());
                    }
                }

              /*  CartList cartList = (CartList) o;
                if (cartList != null && cartList.isStatus()) {
                    totalAmount = cartList.getTotal();
                    tvTotal.setText(Constant.CURRENCY+" "+totalAmount);

                }*/
                break;

        }
    }

    @Override
    public void error(Object o, int code) {
        dialog.dismiss();

    }
}
