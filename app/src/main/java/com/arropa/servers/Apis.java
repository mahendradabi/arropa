package com.arropa.servers;

/*
 * all method declare to access the api form backend side
 * */

import com.arropa.models.CityList;
import com.arropa.models.Citydetail;
import com.arropa.models.LoginModel;
import com.arropa.models.LoginUserDetails;
import com.arropa.models.MyResponse;
import com.arropa.models.ProductList;
import com.arropa.models.ProfileDetails;
import com.arropa.models.StateList;
import com.arropa.models.Statedetail;

import java.sql.Struct;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<MyResponse> addFavoriteProduct(@Field("ven_id") String id, @Field("prd_id") String pid);

    @FormUrlEncoded
    @POST(Constant.PATH + "Addtocart")
    Call<MyResponse> addtocart(@Field("ven_id") String id, @Field("product_id") String pid);

    @FormUrlEncoded
    @POST(Constant.PATH + "product")
    Call<MyResponse> getCartList(@Field("ven_id") String id);

    @FormUrlEncoded
    @POST(Constant.PATH + "favorite")
    Call<ProductList> getFavoriteList(@Field("p_id") String id);

    @FormUrlEncoded
    @POST(Constant.PATH + "removefevorite")
    Call<MyResponse> removeFavorite(@Field("ven_id") String uid, @Field("prd_id") String pid);

    @GET(Constant.PATH + "state")
    Call<StateList> getStateList();

    @FormUrlEncoded
    @POST(Constant.PATH + "city")
    Call<CityList> getCity(@Field("state") String name);

    @FormUrlEncoded
    @POST(Constant.PATH+"Profilefetch")
    Call<ProfileDetails> getProfileDetails(@Field("ven_id") String userId);



    @FormUrlEncoded
    @POST(Constant.PATH + "updateprofile")
    Call<MyResponse> updateProfile(@Field("vendername") String vendername,
                              @Field("Shopname") String Shopname,
                              @Field("School_Contact_Us") String addhar,
                              @Field("shopaddress") String shopaddress,
                              @Field("resAddress") String resAddress,
                              @Field("City") String City,
                              @Field("state") String state,
                              @Field("pincode") String pincode,
                              @Field("password") String password,
                              @Field("vendermobile") String vendermobile,
                              @Field("ven_id") String userid
    );


    @Multipart
    @POST(Constant.PATH + "dfsfa")
    Call<MyResponse> uploadPhoto(@Part("userid") RequestBody userId,
                                          @Part MultipartBody.Part photo);


}
