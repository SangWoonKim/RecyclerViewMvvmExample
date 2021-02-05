package com.study.recyclermvvmexample.Viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.study.recyclermvvmexample.Service.Vo.UserDTO;
import com.study.recyclermvvmexample.Service.repository.UserRepository;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private final UserRepository repository;

    private MutableLiveData<ArrayList<UserDTO>> users = new MutableLiveData<>();

    public ViewModel(){
        repository = new UserRepository();
    }

    public MutableLiveData<ArrayList<UserDTO>> getUsers(){
        return users = repository.getUserDTO();
    }

    public void insert(UserDTO user){
        repository.insert(user);
    }

    public void update(UserDTO user, String updateUser){
        repository.update(user,updateUser);
    }
    public void delete(UserDTO user){
        repository.delete(user);
    }


}
