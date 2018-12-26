package com.appersden.epharma;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("authentication")
    @FormUrlEncoded
    Call<RegisterResponse> checkuser(@Field("username") String email,
                                     @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<RegisterResponse> userRegistration(@Field("name") String firstname,
                                    @Field("username") String username,
                                    @Field("phone") String phone,
                                    @Field("email") String email,
                                    @Field("gender") String gender,
                                    @Field("password") String password,
                                    @Field("image") String image);


}
