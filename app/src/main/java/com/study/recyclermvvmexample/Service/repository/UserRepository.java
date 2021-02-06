package com.study.recyclermvvmexample.Service.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.study.recyclermvvmexample.Service.Connection.RetrofitClient;
import com.study.recyclermvvmexample.Service.Vo.UserDTO;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final String TAG = getClass().getSimpleName();
    private static ArrayList<UserDTO> users;
    String user;
    UserDTO userParam = new UserDTO();
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
        new InsertAsyncTask(this.userParam).execute(user);
    }

    public void update(String user, String updateUser,int position){
        new UpdateAsyncTask(user,updateUser,position).execute(user);
    }

    public void delete(String user){
        new DeleteAsyncTask(user).execute(user);
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

    //굳이 deprecated인 AsyncTask를 쓸 필요도 없을 뿐더러 적은 데이터를 보내는 행동이라
    //비동기적 방식을 사용하지 않아도 되나 그냥 씀

    private class UpdateAsyncTask extends AsyncTask<String,Void,Void>{
        private String user;
        private String updateUser;
        private int position;

        public UpdateAsyncTask(String user,String updateUser,int position) {
            this.user = user;
            this.updateUser = updateUser;
            this.position = position;
        }

        @Override
        protected Void doInBackground(String... string) {
            Call<UserDTO> updateCall = selectAPI.put_update(user,updateUser);
            updateCall.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    if(response.isSuccessful()){
                        users.set(position,response.body());
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


    private class DeleteAsyncTask extends AsyncTask<String,Void,Void>{
        private String user;

        public DeleteAsyncTask(String user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(String... strings) {
            Call<UserDTO> deleteCall = selectAPI.delete_Delete(user);
            deleteCall.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    if(response.isSuccessful()){
                        Iterator<UserDTO> iterator= users.iterator();
                        while (iterator.hasNext()) {
                            UserDTO hasnickname = iterator.next();
                            if (hasnickname.getNickname().equals(user)) {
                               iterator.remove();
                            }
                        }
                        userDTO.setValue(users);
                    }
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {

                }
            });
            getUserDTO();
            return null;
        }
    }
}
