package com.study.recyclermvvmexample.Service.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.study.recyclermvvmexample.Service.Connection.RetrofitClient;
import com.study.recyclermvvmexample.Service.Vo.UserDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final String TAG = getClass().getSimpleName();
    ArrayList<UserDTO> users = new ArrayList<>();
    private static final RetrofitClient.SelectAPI selectApI = RetrofitClient.getApiService();
    private final MutableLiveData<ArrayList<UserDTO>> userDTO = new MutableLiveData<>();

    private static UserRepository userRepository;

    public static UserRepository getInstance(){
        if (userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public MutableLiveData<ArrayList<UserDTO>> getUserDTO(){
        Call<ArrayList<UserDTO>> callUser = selectApI.get_All();
        callUser.enqueue(new Callback<ArrayList<UserDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDTO>> call, Response<ArrayList<UserDTO>> response) {
                Log.d(TAG,"에러"+response.code());
                if (response.isSuccessful()) {
                    users = response.body();
                    userDTO.setValue(users);
                }else{
                    Log.d(TAG, "상태코드" + response.code());
                    Log.d(TAG, "에러메세지" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserDTO>> call, Throwable t) {
                Log.d(TAG, "에러메세지:" + t.getMessage());
            }
        });

        return userDTO;
    }
}
