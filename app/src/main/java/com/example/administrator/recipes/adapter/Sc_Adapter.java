package com.example.administrator.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;

import com.example.administrator.recipes.db.MySqliteHelper;
import com.example.administrator.recipes.utils.User_Cpflxq;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Sc_Adapter extends RecyclerView.Adapter<Sc_Adapter.MyViewHolder>{
    Context context;
    MySqliteHelper mySqliteHelper;
    ArrayList<User_Cpflxq> arrayList;
    public Sc_Adapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<User_Cpflxq> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<User_Cpflxq> arrayList) {
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
        Picasso.with(context).load(arrayList.get(position).getTitle()).into(holder.home_item_img_iv);
        holder.home_item_title_tv.setText(arrayList.get(position).getImg());
        holder.home_item_sumary_tv.setText(arrayList.get(position).getName());
        if(myOnCilck != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myOnCilck.opens(position);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    myOnCilck.deletes(position);
                    return true;
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
    MyOnCilck myOnCilck;

    public MyOnCilck getMyOnCilck() {
        return myOnCilck;
    }

    public void setMyOnCilck(MyOnCilck myOnCilck) {
        this.myOnCilck = myOnCilck;
    }

    public interface MyOnCilck{
        void opens(int position);//打开网页
        void deletes(int position);//删除收藏
    }
    public void removeData(int position) {
        mySqliteHelper.deleteUser(arrayList.get(position).getTitle());
        arrayList.remove(position);
        notifyItemRemoved(position);

    }
}
