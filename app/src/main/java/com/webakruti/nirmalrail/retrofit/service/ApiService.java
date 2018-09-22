package com.webakruti.nirmalrail.retrofit.service;

import com.webakruti.nirmalrail.model.UserResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Registration API
    // http://nirmalrail.webakruti.in/api/registration?name=pallavi&mobile=9561665846&password=9561665846
    @POST(ApiConstants.REG_API)
    Call<UserResponse> registration(@Query("name") String name,
                                    @Query("mobile") String mobileNo,
                                    @Query("password") String password);

    // Login API
    //http://nirmalrail.webakruti.in/api/login?mobile=9561665846&password=9561665846&otp=123456
    @POST(ApiConstants.LOGIN_API)
    Call<UserResponse> login(@Query("mobile") String mobileNo,
                             @Query("password") String password,
                             @Query("otp") String otp);


}
