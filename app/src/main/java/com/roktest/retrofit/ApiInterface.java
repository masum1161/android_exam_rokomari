package com.roktest.retrofit;

import com.roktest.account.RegistrationInfo;
import com.roktest.account.RegistrationReturn;
import com.roktest.login.LoginInfo;
import com.roktest.login.LoginReturn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

   @POST("auth/token/")
   Call<LoginReturn> appLogin(@Body LoginInfo details);

    @POST("auth/register/")
    Call<RegistrationReturn> registration(@Body RegistrationInfo details);

}


