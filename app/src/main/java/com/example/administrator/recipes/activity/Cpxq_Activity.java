package com.example.administrator.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.Cpxq_Adapter;
import com.example.administrator.recipes.db.MySqliteHelper;
import com.example.administrator.recipes.utils.User;
import com.example.administrator.recipes.utils.User_Bz;
import com.example.administrator.recipes.utils.User_Cpflxq;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7.
 */

public class Cpxq_Activity extends AppCompatActivity {
    Context context;
    ImageView cpxq_fh_iv,cpxq_ylt_iv,sc_iv;
    TextView cpxq_name_tv,cpxq_js_tv,cpxq_sc_tv;
    RecyclerView cpxq_rv;
    ArrayList<User> arrayList;
    ArrayList<User_Bz> arrayList_bz;
    ArrayList<User_Cpflxq> arrayList_sc;
    LinearLayoutManager linearLayoutManager;
    MySqliteHelper mySqliteHelper;
    Cpxq_Adapter adapter;
    CoordinatorLayout container;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpxq);
        if(context == null){
            context = this;
        }
        initView();
        cpxq_rv.setLayoutManager(linearLayoutManager);
        adapter = new Cpxq_Adapter(context);

        adapter.setArrayList(arrayList_bz);
        cpxq_rv.setAdapter(adapter);

    }
    private void initView(){
        arrayList = Home_Activity.arrayList;
        arrayList_sc = new ArrayList<>();
        container = (CoordinatorLayout) findViewById(R.id.container);
        mySqliteHelper = new MySqliteHelper(this);
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",-1);

        Log.e("Cpxq_Activity", "position:" + position);

        cpxq_fh_iv = (ImageView) findViewById(R.id.cpxq_fh_iv);
        cpxq_ylt_iv = (ImageView) findViewById(R.id.cpxq_ylt_iv);
        cpxq_name_tv = (TextView) findViewById(R.id.cpxq_name_tv);
        cpxq_js_tv = (TextView) findViewById(R.id.cpxq_js_tv);
        cpxq_sc_tv = (TextView) findViewById(R.id.cpxq_sc_tv);
        sc_iv = (ImageView) findViewById(R.id.sc_iv);

        linearLayoutManager = new LinearLayoutManager(context);
        cpxq_rv = (RecyclerView) findViewById(R.id.cpxq_rv);
        arrayList_bz = arrayList.get(position).getArrayList();

        Picasso.with(context).load(arrayList.get(position).getImg()).into(cpxq_ylt_iv);
        cpxq_name_tv.setText(arrayList.get(position).getTitle());
        cpxq_js_tv.setText(arrayList.get(position).getSumary());
        cpxq_sc_tv.setText(arrayList.get(position).getBurden()+","+arrayList.get(position).getIngredients());

        //返回按钮的监听
        cpxq_fh_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sc_iv.setOnClickListener(new View.OnClickListener() {
            boolean b = true;
            @Override
            public void onClick(View view) {

                arrayList_sc = mySqliteHelper.findUser();

                for (int i =0;i<arrayList_sc.size();i++){
                    if(arrayList_sc.get(i).getImg().equals(arrayList.get(position).getTitle())){
                        b =false;
                        Log.e("i", arrayList.get(i).getTitle());
                        Log.e("p", arrayList.get(position).getTitle());
//                        Toast.makeText(context, "AAA", Toast.LENGTH_SHORT).show();
                        Snackbar.make(container,"菜品已收藏",Snackbar.LENGTH_SHORT).show();
                    }
                }
                if(b){
                    String title = arrayList.get(position).getTitle();
                    String content = arrayList.get(position).getSumary();
                    String imgurl = arrayList.get(position).getImg();
                    mySqliteHelper.addUser(title,content,imgurl);
//                    Toast.makeText(context, "BBB", Toast.LENGTH_SHORT).show();
                    Snackbar.make(container,"收藏成功",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }
}
