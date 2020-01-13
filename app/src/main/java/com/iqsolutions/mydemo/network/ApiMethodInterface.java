package com.iqsolutions.mydemo.network;

import com.iqsolutions.mydemo.pojo.ProductResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;



public interface ApiMethodInterface {

    @GET(ApiUrlConstant.json_url)
    Call<ProductResponse> getAllData();

}
