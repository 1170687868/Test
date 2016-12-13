package com.example.administrator.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.Sc_Adapter;
import com.example.administrator.recipes.db.MySqliteHelper;
import com.example.administrator.recipes.utils.User_Cpflxq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6.
 */
public class Sc_Activity extends AppCompatActivity{
    ImageView sc_fh_iv;
    TextView sc_title_iv;
    MySqliteHelper mySqliteHelper;
    LinearLayoutManager linearLayoutManager;
    RecyclerView sc_rv;
    Context context;
    Sc_Adapter adapter;
    ArrayList<User_Cpflxq> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);
        if(context == null){
            context = this;
        }
        initView();
    }
    private void initView(){
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sc_rv = (RecyclerView) findViewById(R.id.sc_rv);
        sc_rv.setLayoutManager(linearLayoutManager);

        sc_fh_iv = (ImageView) findViewById(R.id.sc_fh_iv);
        sc_title_iv = (TextView) findViewById(R.id.sc_title_tv);
        mySqliteHelper = new MySqliteHelper(context);
        arrayList = mySqliteHelper.findUser();
        adapter = new Sc_Adapter(context);
        adapter.setArrayList(arrayList);
        sc_rv.setAdapter(adapter);


        sc_fh_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter.setMyOnCilck(new Sc_Adapter.MyOnCilck() {
            @Override
            public void opens(int position) {
                Intent intent = new Intent();
                intent.setClass(Sc_Activity.this,Cpxq_Sc_Activity.class);
                intent.putExtra("name",arrayList.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void deletes(int position) {
                adapter.notifyItemRemoved(position);
                adapter.removeData(position);
            }
        });
    }
}
