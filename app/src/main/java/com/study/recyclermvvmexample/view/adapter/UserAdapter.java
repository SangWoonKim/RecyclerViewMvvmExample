package com.study.recyclermvvmexample.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.study.recyclermvvmexample.BR;
import com.study.recyclermvvmexample.R;
import com.study.recyclermvvmexample.model.vo.UserDTO;
import com.study.recyclermvvmexample.databinding.ItemBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    ArrayList<UserDTO> users;
    OnItemClickListener itemClickListener;
    public void updataData(ArrayList<UserDTO> users){
        this.users = users;
    }

    public UserAdapter(Context context, ArrayList<UserDTO> users, OnItemClickListener listener) {
        this.context = context;
        this.users = users;
        this.itemClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDTO userDTO = users.get(position);
        holder.bind(userDTO,itemClickListener);

    }

    public String getItem(int position){
        String item_id = Integer.toString(users.get(position).getId());
        return item_id;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBinding itemBinding;

        public ViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        void bind(UserDTO userDTO, OnItemClickListener listener) {
            itemBinding.setVariable(BR.userItem, userDTO);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION){
                        if (itemClickListener != null){
                            itemClickListener.onItemClick(currentPosition);

                            Log.d("AdapterPosition",""+getAdapterPosition());
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
