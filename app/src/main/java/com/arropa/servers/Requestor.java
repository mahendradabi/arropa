package com.arropa.servers;


import com.arropa.models.LoginModel;
import com.arropa.models.MyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by xyz on 03-01-2018.
 */

public class Requestor {
    private int code;
    ServerResponse serverResponse;
    public static Apis apis;

    public Requestor(int code, ServerResponse serverResponse) {
        this.code = code;
        this.serverResponse = serverResponse;
        this.apis = RetrofitClient.getMyClient().create(Apis.class);
    }


    public void userLogin(String email, String password) {
        apis.login(email, password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);

                    }
                });
    }


    public void userRegister(String name, String email, String shopName, String addhar, String shopaddress,
                             String resAddress, String city, String state, String pincode, String password,
                             String vendermobile) {

        apis.register(name, email, shopaddress, addhar, shopaddress, resAddress, city, state, pincode, password, vendermobile)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
    }


}
