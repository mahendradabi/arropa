package com.arropa.servers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xyz on 11-12-2017.
 */

public class RetrofitClient {
    public static Retrofit retrofit = null;


    public static Retrofit getMyClient() {
        if (retrofit == null) {
         /*   OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "$2y$08$8dS/O4m9PF2f6pe4uaHE6eCMQnqxxF6gnIu4RxV3l.JlwUKeqEbf6").build();
                    return chain.proceed(request);
                }
            });*/
            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Constant.HOST_URL).build();
        }
        return retrofit;
    }


}
