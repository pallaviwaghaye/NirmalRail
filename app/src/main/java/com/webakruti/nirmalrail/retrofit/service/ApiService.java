package com.webakruti.nirmalrail.retrofit.service;

import com.webakruti.nirmalrail.model.OTPResponse;
import com.webakruti.nirmalrail.model.RailwayCategoryResponse;
import com.webakruti.nirmalrail.model.RegistrationResponse;
import com.webakruti.nirmalrail.model.UserResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Registration API
    // http://nirmalrail.webakruti.in/api/registration?name=pallavi&mobile=9561665846&password=9561665846
    @POST(ApiConstants.REG_API)
    Call<RegistrationResponse> registration(@Query("name") String name,
                                            @Query("mobile") String mobileNo,
                                            @Query("password") String password);

    // Login API
    //http://nirmalrail.webakruti.in/api/login?mobile=9561665846&password=9561665846&otp=123456
    @POST(ApiConstants.LOGIN_API)
    Call<UserResponse> login(@Query("mobile") String mobileNo,
                             @Query("password") String password,
                             @Query("otp") String otp);


    // OTP API
    //http://nirmalrail.webakruti.in/api/login?mobile=9561665846&password=9561665846&otp=123456
    @POST(ApiConstants.OTP_VERIFICATION)
    Call<OTPResponse> otpVerification(@Query("mobile") String mobileNo);


    // GET RAILWAY CATEGORY API
    //http://nirmalrail.webakruti.in/api/login?mobile=9561665846&password=9561665846&otp=123456
    @POST(ApiConstants.GET_RAILWAY_CATEGORY)
    Call<RailwayCategoryResponse> getServices(@Header("Authorization") String header);


}
