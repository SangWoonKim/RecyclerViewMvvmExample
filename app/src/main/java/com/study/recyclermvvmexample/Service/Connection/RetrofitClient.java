package com.study.recyclermvvmexample.Service.Connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.recyclermvvmexample.Service.Vo.UserDTO;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RetrofitClient {
    public static final String BASE_URL ="";

    public static SelectAPI getApiService(){return getInstance().create(SelectAPI.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public interface SelectAPI{
        @GET("index")
        Call<ArrayList<UserDTO>> get_All();

        @GET("index/{id}")
        Call<UserDTO>get_Select(@Path("id") String id);

        @PUT("update/{nickname}/{nicknameParam}")
        Call<UserDTO>put_update(@Path("nickname")String searchName,@Path("nicknameParam")String updateName);

        @DELETE("delete/{nickname}")
        Call<UserDTO> delete_Delete(@Path("nickname") String deleteName);
    }
}
