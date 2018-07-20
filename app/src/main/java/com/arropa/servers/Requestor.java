package com.arropa.servers;


import com.arropa.models.CityList;
import com.arropa.models.LoginModel;
import com.arropa.models.MyResponse;
import com.arropa.models.ProductList;
import com.arropa.models.ProfileDetails;
import com.arropa.models.StateList;
import com.arropa.models.Statedetail;

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
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

    public void getProductList(String url) {
        apis.getProductList(url)
                .enqueue(new Callback<ProductList>() {
                    @Override
                    public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<ProductList> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

    public void addFavorite(String userId,String pid) {
        apis.addFavoriteProduct(userId,pid)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

    public void addCartProduct(String userId,String pid) {
        apis.addtocart(userId,pid)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

    public void getFavoriteList(String userId)
    {
        apis.getFavoriteList(userId)
                .enqueue(new Callback<ProductList>() {
                    @Override
                    public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<ProductList> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }


    public void getCartList(String userID)
    {
        apis.getCartList(userID)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

    public void removeFavorite(String userId,String pid)
    {
        apis.removeFavorite(userId,pid)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.code() == 200 && response.body() != null)
                            serverResponse.success(response.body(), code);
                        else serverResponse.error("Error", code);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        serverResponse.error(t.getMessage(), code);
                    }
                });
    }

    public void getStates()
    {
        apis.getStateList().enqueue(new Callback<StateList>() {
            @Override
            public void onResponse(Call<StateList> call, Response<StateList> response) {
                if (response.code() == 200 && response.body() != null)
                    serverResponse.success(response.body(), code);
                else serverResponse.error("Error", code);
            }

            @Override
            public void onFailure(Call<StateList> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getCity(String state)
    {
        apis.getCity(state).enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if (response.code() == 200 && response.body() != null)
                    serverResponse.success(response.body(), code);
                else serverResponse.error("Error", code);
            }

            @Override
            public void onFailure(Call<CityList> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void getProfileDetails(String userid)
    {
        apis.getProfileDetails(userid).enqueue(new Callback<ProfileDetails>() {
            @Override
            public void onResponse(Call<ProfileDetails> call, Response<ProfileDetails> response) {
                if (response.code() == 200 && response.body() != null)
                    serverResponse.success(response.body(), code);
                else serverResponse.error("Error", code);
            }

            @Override
            public void onFailure(Call<ProfileDetails> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }

    public void updateProfile(String name,  String shopName, String addhar, String shopaddress,
                              String resAddress, String city, String state, String pincode, String password,
                              String vendermobile,String uid)
    {
        apis.updateProfile(name,shopName,addhar,shopaddress,resAddress,city,state,
                pincode,password,vendermobile,uid).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200 && response.body() != null)
                    serverResponse.success(response.body(), code);
                else serverResponse.error("Error", code);
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                serverResponse.error(t.getMessage(), code);
            }
        });
    }
}
