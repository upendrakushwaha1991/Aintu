package aintu.cpm.com.aintu.util;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import com.squareup.okhttp.RequestBody;


import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


/**
 * Created by upendrak on 16-05-2017.
 */

public interface PostApi {

    @POST("Uploadimages")
    Call<String> getUploadImage(@Body RequestBody request);



}
