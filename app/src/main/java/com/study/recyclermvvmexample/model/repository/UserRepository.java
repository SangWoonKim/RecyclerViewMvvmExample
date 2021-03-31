package com.study.recyclermvvmexample.model.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.study.recyclermvvmexample.model.Connection.RetrofitClient;
import com.study.recyclermvvmexample.model.vo.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final String TAG = getClass().getSimpleName();
    private static ArrayList<UserDTO> users;
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

    public void insert(String nickname,String pw){
        HashMap<String, String> insertData = new HashMap<>();
        insertData.put("nickname",nickname);
        insertData.put("password",pw);
        new InsertAsyncTask(insertData).execute(insertData);
    }

    public void update(String user, String updateUser,int position){
        new UpdateAsyncTask(user,updateUser,position).execute(user);
    }

    public void delete(String user){
        new DeleteAsyncTask(user).execute(user);
    }




    //굳이 deprecated인 AsyncTask를 쓸 필요도 없을 뿐더러 적은 데이터를 보내는 행동이라
    //비동기적 방식을 사용하지 않아도 되나 그냥 씀
    private class InsertAsyncTask extends AsyncTask<HashMap<String, String>,Void,Void>{
        private HashMap<String,String> data_send;

        public InsertAsyncTask(HashMap<String, String> data_send) {
            this.data_send = data_send;
        }

        @Override
        protected Void doInBackground(HashMap<String, String>... hashMaps) {
            Call<UserDTO> insert = selectAPI.post_Insert(data_send);
            insert.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    if (response.isSuccessful()){
                        UserDTO userResponseData = response.body();
                        int responseId = userResponseData.getId();
                        String responseNick = userResponseData.getNickname();
                        users.add(new UserDTO(responseId,responseNick));
                        userDTO.setValue(users);
                    }
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {

                }
            });

            return null;
        }
    }


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

            Call<UserDTO> updateCall = selectAPI.patch_update(user,updateUser);
            updateCall.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    UserDTO responseUser = response.body();
                    users.set(position,responseUser);
                    userDTO.setValue(users);
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
                            UserDTO hasNickname = iterator.next();
                            if (hasNickname.getNickname().equals(user)) {
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
            return null;
        }
    }
}
