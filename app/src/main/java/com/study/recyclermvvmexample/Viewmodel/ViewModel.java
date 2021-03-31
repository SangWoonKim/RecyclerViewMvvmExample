package com.study.recyclermvvmexample.Viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.study.recyclermvvmexample.model.vo.UserDTO;
import com.study.recyclermvvmexample.model.repository.UserRepository;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private final UserRepository repository;

    private MutableLiveData<ArrayList<UserDTO>> users = new MutableLiveData<>();

    public ViewModel(){
        repository = UserRepository.getInstance();
    }

    public MutableLiveData<ArrayList<UserDTO>> getUsers(){
        return users = repository.getUserDTO();
    }

    public void insert(String nickname, String pw){
        repository.insert(nickname,pw);
    }

    public void update(String user, String updateUser, int position){
        repository.update(user,updateUser,position);
    }
    public void delete(String user){
        repository.delete(user);
    }


}
