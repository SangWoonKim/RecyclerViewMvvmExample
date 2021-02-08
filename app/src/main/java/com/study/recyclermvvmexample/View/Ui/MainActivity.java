package com.study.recyclermvvmexample.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    Button insert_Btn;
    EditText add_Nickname, add_Pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_Nickname = findViewById(R.id.add_nickname);
        add_Pw = findViewById(R.id.add_pw);
        recyclerView = findViewById(R.id.recyclerview);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getUsers()
                .observe(this, new Observer<ArrayList<UserDTO>>() {
            @Override
            public void onChanged(ArrayList<UserDTO> userResponse) {
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
                selectOneIntent.putExtra("itemPosition",position);
                startActivity(selectOneIntent);
            }
        });

        insert_Btn = (Button)findViewById(R.id.Insert_btn);
        insert_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick,pw;
                nick = add_Nickname.getText().toString();
                pw = add_Pw.getText().toString();
                viewModel.insert(nick,pw);
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
