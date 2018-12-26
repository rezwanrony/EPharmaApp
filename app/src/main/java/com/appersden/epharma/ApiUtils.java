package com.appersden.epharma;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://173.82.105.191:7000/customer/";

    public static ApiInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
