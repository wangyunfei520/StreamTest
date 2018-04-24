package cn.wyf.streamtest.di.api;


import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * user request interface
 */

public interface RequestApiService {


    @GET("person/getAllperson")
    Observable<JsonElement> getAllperson();

    @POST("person/addPerson2")
    Observable<JsonElement> addPerson(@Body Map<String, String> params);

    @FormUrlEncoded
    @Headers("Accept-Encoding: application/json")
    @POST("home/test3")
    Observable<JsonElement> test(@Field("username") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("course/test1")
    Observable<JsonElement> getCoursebyId(@Field("courseId") String courseId, @Field("courseName") String courseName);
}
