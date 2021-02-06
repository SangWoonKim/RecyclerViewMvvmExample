package com.study.recyclermvvmexample.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.recyclermvvmexample.R;
import com.study.recyclermvvmexample.Service.Vo.UserDTO;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private onItemClickListener recyclerInterfaceListener = null;

    public void setOnItemClickListener(onItemClickListener listener){
        this.recyclerInterfaceListener = listener;
    }

    Context context;
    ArrayList<UserDTO> users;


    public void updataData(ArrayList<UserDTO> users){
        this.users = users;
    }

    public UserAdapter(Context context, ArrayList<UserDTO> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);
        ViewHolder vH = new ViewHolder(view);
        return vH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nickname = users.get(position).getNickname();
        Integer id = users.get(position).getId();

        holder.nickname.setText(nickname);
        holder.id.setText(String.valueOf(id));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public String getItem(int position){

        String item_id = Integer.toString(users.get(position).getId());
        return item_id;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,nickname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            nickname = itemView.findViewById(R.id.nickname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION){
                        if (recyclerInterfaceListener != null){
                            recyclerInterfaceListener.onItemClick(v,currentPosition);

                            Log.d("AdapterPosition",""+getAdapterPosition());
                        }
                    }
                }
            });
        }
    }


    public interface onItemClickListener{
        void onItemClick(View v, int position);
    }
}
