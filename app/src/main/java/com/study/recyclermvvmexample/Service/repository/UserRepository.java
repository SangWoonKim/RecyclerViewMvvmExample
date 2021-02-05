package com.study.recyclermvvmexample.Service.repository;

import android.os.AsyncTask;
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
    UserDTO user = new UserDTO();
    private static final RetrofitClient.SelectAPI selectApI = RetrofitClient.getApiService();
    private final MutableLiveData<ArrayList<UserDTO>> userDTO = new MutableLiveData<>();
    private RetrofitClient.SelectAPI selectAPI = RetrofitClient.getApiService();
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

    public void insert(UserDTO user){
        new InsertAsyncTask(this.user).execute(user);
    }

    public void update(UserDTO user, String updateUser){
        new UpdateAsyncTask(this.user,updateUser).execute(user);
    }

    public void delete(UserDTO user){
        new DeleteAsyncTask(this.user).execute(user);
    }


    private class InsertAsyncTask extends AsyncTask<UserDTO, Void, Void>{
        private UserDTO user;

        private InsertAsyncTask(UserDTO user){
            this.user = user;
        }

        @Override
        protected Void doInBackground(UserDTO... userDTOS) {
            //Call<UserDTO> insertCall = selectAPI.put_insert(user);
            //insertCall.enqueue(new ..
            //users.add(user);
            //UserDTO.setValue(users);
            return null;
        }
    }


    private class UpdateAsyncTask extends AsyncTask<UserDTO,Void,Void>{
        private UserDTO user;
        private String updateUser;

        public UpdateAsyncTask(UserDTO user,String updateUser) {
            this.user = user;
            this.updateUser = updateUser;
        }

        @Override
        protected Void doInBackground(UserDTO... userDTOS) {
            Call<UserDTO> updateCall = selectAPI.put_update(user.getNickname(),updateUser);
            updateCall.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    if(response.isSuccessful()){
                       // users.set(position,response.body());
                        //users = response.body();
                        userDTO.setValue(users);
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {

                }
            });
            return null;
        }
    }


    private class DeleteAsyncTask extends AsyncTask<UserDTO,Void,Void>{
        private UserDTO userDTO;

        public DeleteAsyncTask(UserDTO user) {
            this.userDTO = user;
        }

        @Override
        protected Void doInBackground(UserDTO... userDTOS) {
            return null;
        }
    }
}
