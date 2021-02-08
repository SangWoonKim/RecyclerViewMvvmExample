package com.study.recyclermvvmexample.View.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.study.recyclermvvmexample.R;
import com.study.recyclermvvmexample.Service.Connection.RetrofitClient;
import com.study.recyclermvvmexample.Service.Vo.UserDTO;
import com.study.recyclermvvmexample.Viewmodel.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView id_tv,nickname_tv;
    private EditText nickname_et,nicknameParam_et;
    private Button update_btn,delete_btn;
    String selectOne,nickname,id;
    int position;
    ViewModel viewModel;

    RetrofitClient.SelectAPI selectAPI;

    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        selectAPI=RetrofitClient.getApiService();

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        id_tv=(TextView)findViewById(R.id.id_tv);
        nickname_tv=(TextView)findViewById(R.id.nickname_tv);

        nickname_et = (EditText)findViewById(R.id.nickname_et);
        nicknameParam_et = (EditText)findViewById(R.id.nicknameparam_et);

        update_btn=(Button)findViewById(R.id.update_btn);
        delete_btn=(Button)findViewById(R.id.delete_btn);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);

        Intent selectOneIntent = getIntent();
        selectOne = selectOneIntent.getStringExtra("idValue");
        position = selectOneIntent.getExtras().getInt("itemPosition");
        Call<UserDTO> selectOneCall = selectAPI.get_Select(selectOne);
        selectOneCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.isSuccessful()){
                    UserDTO selectOne = response.body();
                    nickname = selectOne.getNickname();
                    id = Integer.toString(selectOne.getId());
                    nickname_tv.setText(nickname);
                    nickname_et.setText(nickname);
                    id_tv.setText(id);
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_btn:
                String nicknameParam = nicknameParam_et.getText().toString();
                viewModel.update(nickname,nicknameParam,position); //UserRepository의 update메소드 parameter String으로 변경해야함 + position값도 삽입하고 parameter변경
                break;

            case R.id.delete_btn:
                String deleteNicknameParam = nickname_et.getText().toString();
                viewModel.delete(deleteNicknameParam); //UserRepository의 delete메소드 parameter String으로 변경해야함
                break;
        }
    }
}
