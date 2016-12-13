package com.example.administrator.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.utils.User;
import com.example.administrator.recipes.utils.User_Cpflxq;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/8.
 */

public class Cpflxq_Adapter extends RecyclerView.Adapter<Cpflxq_Adapter.MyViewHolder> {
    Context context;
    ArrayList<User> arrayList;

    public Cpflxq_Adapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<User> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<User> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.with(context).load(arrayList.get(position).getImg()).into(holder.home_item_img_iv);
        holder.home_item_title_tv.setText(arrayList.get(position).getTitle());
        holder.home_item_sumary_tv.setText(arrayList.get(position).getSumary());
        if(myOnClike !=null ){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myOnClike.OnClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView home_item_img_iv;
        TextView home_item_title_tv,home_item_sumary_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            home_item_img_iv = (ImageView) itemView.findViewById(R.id.home_item_img_iv);
            home_item_title_tv = (TextView) itemView.findViewById(R.id.home_item_title_tv);
            home_item_sumary_tv = (TextView) itemView.findViewById(R.id.home_item_sumary_tv);
        }
    }
    MyOnClike myOnClike;

    public MyOnClike getMyOnClike() {
        return myOnClike;
    }

    public void setMyOnClike(MyOnClike myOnClike) {
        this.myOnClike = myOnClike;
    }

    public interface MyOnClike{
        void OnClick(int position);
    }
}
