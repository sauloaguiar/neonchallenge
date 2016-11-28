package com.sauloaguiar.neonapplication.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sauloaguiar.neonapplication.data.Transaction;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sauloaguiar on 11/27/16.
 */

/**
 * https://square.github.io/retrofit/
 */
public class NeonEndpoint {

    // Trailing slash is needed
    public static final String BASE_URL = "http://processoseletivoneon.azurewebsites.net/";

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build();

    public static NeonEndpointInterface getEndpoint(){
        return retrofit.create(NeonEndpointInterface.class);
    }

    public interface NeonEndpointInterface {

        @GET("GetTransfers")
        Call<List<Transaction>> getTransactions(@Query("token") String token);

        @GET("GenerateToken")
        Call<String> getToken(@Query("nome") String name, @Query("email") String email);

        @FormUrlEncoded
        @POST("SendMoney")
        Call<Boolean> sendMoney(@Field("ClienteId") String clientID, @Field("token") String token, @Field("valor") double value);
    }
}

