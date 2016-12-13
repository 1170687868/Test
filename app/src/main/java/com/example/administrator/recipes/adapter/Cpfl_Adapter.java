package com.example.administrator.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.utils.User_Fl;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6.
 */

public class Cpfl_Adapter extends RecyclerView.Adapter<Cpfl_Adapter.MyViewHolder> {
    Context context;
    ArrayList<User_Fl> arrayList;

    public Cpfl_Adapter(Context context) {
        this.context = context;
         arrayList = new ArrayList<>();
    }

    public ArrayList<User_Fl> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<User_Fl> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cpfl_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.cpfl_item_name_tv.setText(arrayList.get(position).getName());
        if(myOnClick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Home_Adapter", "BBB");
                    myOnClick.OnClick(position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cpfl_item_img_iv;
        TextView cpfl_item_name_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            cpfl_item_img_iv = (ImageView) itemView.findViewById(R.id.cpfl_item_img_iv);
            cpfl_item_name_tv = (TextView) itemView.findViewById(R.id.cpfl_item_name_tv);
        }
    }
    MyOnClick myOnClick;//声明接口

    public MyOnClick getMyOnClick() {
        return myOnClick;
    }

    public void setMyOnClick(MyOnClick myOnClick) {
        this.myOnClick = myOnClick;
    }

    public  interface MyOnClick{
        void OnClick(int position);//短按
    }
}
