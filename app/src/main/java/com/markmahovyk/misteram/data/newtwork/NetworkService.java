package com.markmahovyk.misteram.data.newtwork;

import com.markmahovyk.misteram.model.ActiveTasks;
import com.markmahovyk.misteram.model.ResponseEndPoint;
import com.markmahovyk.misteram.model.ResponseRegister;
import com.markmahovyk.misteram.model.ResponseSingIn;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @POST("/business-app/api/couriers/app")
    Call<ResponseRegister> register(
            @Header("authToken") String token,
            @Query("v") String v,
            @Query("type") String type ,
            @Query("deviceId") String deviceId,
            @Query("notificationToken") String notificationToken,
            @Query("notificationProvider") String notificationProvider);


    @POST("/business-app/api/couriers/account/sign-in")
    Call<ResponseSingIn> singIn(
            @Header("App-Token") String token,
            @Query("username") String username,
            @Query("password") String password);


    @GET("/business-app/api/couriers/tasks/active")
    Call<ArrayList<ActiveTasks>> getActiveTask(
            @Header("App-Token") String appToken,
            @Header("App-Auth-Token") String authAppToken);


    @POST("/business-app/api/couriers/order/{orderId}/process-action")
    Call<ResponseEndPoint> endPoint(
            @Header("App-Token") String appToken,
            @Header("App-Auth-Token") String authAppToken,
            @Path("orderId") String orderId,
            @Query("status") String type);
}

