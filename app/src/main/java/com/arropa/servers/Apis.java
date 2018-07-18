package com.arropa.servers;

/*
 * all method declare to access the api form backend side
 * */

import com.arropa.models.LoginModel;
import com.arropa.models.LoginUserDetails;
import com.arropa.models.MyResponse;
import com.arropa.models.ProductList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Apis {

    @FormUrlEncoded
    @POST(Constant.PATH + "register")
    Call<MyResponse> register(@Field("vendername") String vendername,
                              @Field("venderemail") String venderemail,
                              @Field("Shopname") String Shopname,
                              @Field("School_Contact_Us") String addhar,
                              @Field("shopaddress") String shopaddress,
                              @Field("resAddress") String resAddress,
                              @Field("City") String City,
                              @Field("state") String state,
                              @Field("pincode") String pincode,
                              @Field("password") String password,
                              @Field("vendermobile") String vendermobile
    );

    @FormUrlEncoded
    @POST(Constant.PATH + "u_signin")
    Call<LoginModel> login(@Field("venderemail") String venderemail,
                           @Field("password") String password);

    @GET
    Call<ProductList> getProductList(@Url String url);

    @FormUrlEncoded
    @POST(Constant.PATH + "fevinsrt")
    Call<MyResponse> addFavoriteProduct(@Field("ven_id") String id,@Field("prd_id") String pid);

    @FormUrlEncoded
    @POST(Constant.PATH + "product")
    Call<MyResponse> getCartList(@Field("ven_id") String id);

    @FormUrlEncoded
    @POST(Constant.PATH + "favorite")
    Call<MyResponse> getFavoriteList(@Field("ven_id") String id);
}
