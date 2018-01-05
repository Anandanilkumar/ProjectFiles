package com.firebase.login.dbtest1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alan Lal on 10-Nov-17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    List<UserData> currentData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView phonenumber;
        TextView status;
        public MyViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernametitle);
            phonenumber = itemView.findViewById(R.id.numbertitle);
            status = itemView.findViewById(R.id.statustitle);
        }
    }

    UserListAdapter(List<UserData> currentData){
        this.currentData = currentData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserData currentInstance = currentData.get(position);
        holder.username.setText(currentInstance.getName());
        holder.phonenumber.setText(currentInstance.getPhoneNumber());
        holder.status.setText(currentInstance.getStatus());
    }

    @Override
    public int getItemCount() {
        return currentData.size();
    }


}
