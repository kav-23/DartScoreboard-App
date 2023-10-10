package com.example.dartscoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<User> usersList;

    private ClickHandler clickHandler;

    public recyclerAdapter(ArrayList<User> usersList, ClickHandler clickHandler){
        this.usersList = usersList;
        this.clickHandler = clickHandler;
    }

    public interface ClickHandler {
        void onMyButtonClicked (View view, final int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ClickHandler clickHandler;
        private TextView nameTxt;
        private Button removeUserButton;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.name_text1);
            //removeUserButton = view.findViewById(R.id.remove_user);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickHandler.onMyButtonClicked(v, getAdapterPosition());
        }
    }



    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = usersList.get(position).getUsername();
        holder.nameTxt.setText(name);
        holder.clickHandler = this.clickHandler;

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
