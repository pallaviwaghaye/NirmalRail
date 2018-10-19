package com.webakruti.nirmalrail.retrofit.service;

import com.webakruti.nirmalrail.model.AdminLoginSuccess;
import com.webakruti.nirmalrail.model.AdmintGetComplaintResponse;
import com.webakruti.nirmalrail.model.ColonyResponse;
import com.webakruti.nirmalrail.model.EventImageResponse;
import com.webakruti.nirmalrail.model.MyRequestStatusResponse;
import com.webakruti.nirmalrail.model.OTPResponse;
import com.webakruti.nirmalrail.model.RailwayCategoryResponse;
import com.webakruti.nirmalrail.model.RegistrationResponse;
import com.webakruti.nirmalrail.model.SaveComplaintResponse;
import com.webakruti.nirmalrail.model.SendRequestFormResponse;
import com.webakruti.nirmalrail.model.TechnologyImageResponse;
import com.webakruti.nirmalrail.model.UserResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST(ApiConstants.GET_RAILWAY_CATEGORY)
    Call<RailwayCategoryResponse> getServices(@Header("Authorization") String header);


    @POST(ApiConstants.GET_COLONIES)
    Call<ColonyResponse> getColony(@Header("Authorization") String header);


    @POST(ApiConstants.GET_TECHNOLOGIES)
    Call<TechnologyImageResponse> getTechnlogiy(@Header("Authorization") String header);

    @POST(ApiConstants.GET_EVENTS)
    Call<EventImageResponse> getEvents(@Header("Authorization") String header);


    // Send Request Complaint
   /* @Multipart
    @POST(ApiConstants.SAVE_COMPLAINT)
    Call<SaveComplaintResponse> uploadImage(@Header("Authorization") String header,
                                            @Part MultipartBody.Part baseImage,
                                            @Part("description") RequestBody description,
                                            @Part("service_id") RequestBody serviceId,
                                            @Part("station_id") RequestBody stationId
    );*/


    @POST(ApiConstants.GET_STATION_PLATFORM)
    Call<SendRequestFormResponse> getStationPlatform(@Header("Authorization") String header,
                                                     @Query("service_id") String serviceId);


    @POST(ApiConstants.GET_MY_REQUEST_STATUS)
    Call<MyRequestStatusResponse> getMyRequestStatus(@Header("Authorization") String header);



    // Station, Places, PF
    @Multipart
    @POST(ApiConstants.SAVE_COMPLAINT)
    Call<SaveComplaintResponse> uploadImage_Station_Places_PF(
            @Header("Authorization") String header,
            @Part MultipartBody.Part baseImage,
            @Part("description") RequestBody description,
            @Part("service_id") RequestBody serviceId,
            @Part("station_id") RequestBody stationId,
            @Part("at_platform") RequestBody platform
    );


    // Station and Places
    @Multipart
    @POST(ApiConstants.SAVE_COMPLAINT)
    Call<SaveComplaintResponse> uploadImage_Station_Places(
            @Header("Authorization") String header,
            @Part MultipartBody.Part baseImage,
            @Part("description") RequestBody description,
            @Part("service_id") RequestBody serviceId,
            @Part("station_id") RequestBody stationId);

    // Station and PF
    @Multipart
    @POST(ApiConstants.SAVE_COMPLAINT)
    Call<SaveComplaintResponse> uploadImage_Station_PF(
            @Header("Authorization") String header,
            @Part MultipartBody.Part baseImage,
            @Part("description") RequestBody description,
            @Part("service_id") RequestBody serviceId,
            @Part("station_id") RequestBody stationId,
            @Part("at_platform") RequestBody platform
    );


    // Station and Category Service
    @Multipart
    @POST(ApiConstants.SAVE_COMPLAINT)
    Call<SaveComplaintResponse> uploadImage_Station_Category(
            @Header("Authorization") String header,
            @Part MultipartBody.Part baseImage,
            @Part("description") RequestBody description,
            @Part("service_id") RequestBody serviceId,
            @Part("station_id") RequestBody stationId);


    @Multipart
    @POST(ApiConstants.SAVE_COMPLAINT_COLONY)
    Call<SaveComplaintResponse> uploadColonyRequest(
            @Header("Authorization") String header,
            @Part MultipartBody.Part baseImage,
            @Part("colony_id") RequestBody colonyId,
            @Part("description") RequestBody description,
            @Part("address") RequestBody address
    );


    // --------------------ADMIN APIS-------------------------

    @POST(ApiConstants.ADMIN_LOGIN_API)
    Call<AdminLoginSuccess> adminLogin(@Query("email") String emailId,
                                       @Query("password") String password);


    @POST(ApiConstants.GET_ADMIN_REQUEST_STATUS)
    Call<MyRequestStatusResponse> getAdminRequestStatus(@Header("Authorization") String header,
                                                        @Query("status") String status);

    @POST(ApiConstants.ADMIN_GET_COMPLAINT_BY_ID)
    Call<AdmintGetComplaintResponse> getAdminComplaintById(@Header("Authorization") String header,
                                                        @Query("id") String id);


    @Multipart
    @POST(ApiConstants.ADMIN_UPDATE_COMPLAINT_UPLOAD_IMAGE)
    Call<SaveComplaintResponse> uploadAdminComplaintUpdaate(
            @Header("Authorization") String header,
            @Part("id") RequestBody id,
            @Part("status") RequestBody status,
            @Part("comment") RequestBody comment,
            @Part MultipartBody.Part baseImage);


}
