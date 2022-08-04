package com.smartcheck.smartcheck;


import com.smartcheck.smartcheck.ModalClass.DataForRegsistration;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("registration")

        //on below line we are creating a method to post our data.
    Call<DataForRegsistration> createPost(@Body DataForRegsistration dataModal);
}
