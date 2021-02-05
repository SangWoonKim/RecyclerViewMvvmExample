package com.study.recyclermvvmexample.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        adapter.setOnItemClickListener(new UserAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String item = adapter.getItem(position);
                Intent selectOneIntent = new Intent(getApplicationContext(),UpdateDeleteActivity.class);
                selectOneIntent.putExtra("idValue",item);
                startActivity(selectOneIntent);
            }
        });

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
