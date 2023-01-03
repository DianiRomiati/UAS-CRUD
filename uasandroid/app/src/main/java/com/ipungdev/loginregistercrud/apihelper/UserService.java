package com.ipungdev.loginregistercrud.apihelper;


import com.ipungdev.loginregistercrud.models.ProjectResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @FormUrlEncoded
    @POST("login")
    public Call<ResponseBody> loginRequest(@Field("email") String email,
                                           @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    public Call<ResponseBody> registerRequest(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
            );


    //Call<List<Task>> getTasks(@Header("Content-Range") String contentRange);
    //Call<ProjectResponse> getProyek(@Header("Authorization") String tokenkey);
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("proyeks")
    Call<ProjectResponse> getProyek(@Header("Authorization") String auth);

    
    @FormUrlEncoded
    @POST("proyeks")
    public Call<ResponseBody> createRequest(
            @Field("nama") String nama,
            @Field("intro") String intro,
            @Field("lokasi") String lokasi,
            @Field("harga") String harga
    );


}
