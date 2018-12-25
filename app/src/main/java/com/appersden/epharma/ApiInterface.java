package com.appersden.epharma;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<RegisterResponse> checkuser(@Field("username") String username,
                                     @Field("password") String password);

    @POST("signup")
    @FormUrlEncoded
    Call<RegisterResponse> userRegistration(@Field("firstname") String firstname,
                                    @Field("lastname") String lastname,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("confpassword") String confpassword,
                                    @Field("website") String website,
                                    @Field("company") String company,
                                    @Field("phone") String phone,
                                    @Field("address") String address,
                                    @Field("type") int type);

}
