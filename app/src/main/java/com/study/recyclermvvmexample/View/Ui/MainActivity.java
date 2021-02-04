package com.study.recyclermvvmexample.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.study.recyclermvvmexample.R;
import com.study.recyclermvvmexample.Service.Vo.UserDTO;
import com.study.recyclermvvmexample.View.Adapter.UserAdapter;
import com.study.recyclermvvmexample.Viewmodel.ViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<UserDTO> users = new ArrayList<>();
    UserAdapter adapter;
    RecyclerView recyclerView;
    ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getUsers()
                .observe(this, new Observer<ArrayList<UserDTO>>() {
            @Override
            public void onChanged(ArrayList<UserDTO> userResponse) {
//                ArrayList<UserDTO> items = userResponse.get().getId();
//                int arrayValue = userResponse.indexOf(userResponse.size());
               // userResponse.add();
                users = userResponse;
                adapter.updataData(users);
                adapter.notifyDataSetChanged();
            }
        });
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        if (adapter == null){
            adapter = new UserAdapter(MainActivity.this,users);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
}
