package com.example.administrator.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.utils.User_Bz;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7.
 */

public class Cpxq_Adapter extends RecyclerView.Adapter<Cpxq_Adapter.MyViewHolder> {
    Context context;
    ArrayList<User_Bz> arrayList;

    public Cpxq_Adapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }


    public ArrayList<User_Bz> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<User_Bz> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cpxq_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cpxq_item_tv.setText(arrayList.get(position).getMsg());
        Picasso.with(context).load(arrayList.get(position).getImg()).into(holder.cpxq_item_iv);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cpxq_item_iv;
        TextView cpxq_item_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            cpxq_item_iv = (ImageView) itemView.findViewById(R.id.cpxq_item_iv);
            cpxq_item_tv = (TextView) itemView.findViewById(R.id.cpxq_item_tv);
        }
    }
}
