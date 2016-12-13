package com.example.administrator.recipes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.ViewPager_Adapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ViewPager_Activity extends AppCompatActivity {
    ArrayList<View> arrayList_view;
    View view1,view2,view3,view4,view5;
    ViewPager viewPager;
    ViewPager_Adapter adapter;
    ImageView imageView;
    SharedPreferences sharedPreferences;
    private boolean isFirst = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        sharedPreferences = getSharedPreferences("isFirst",MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("isFirst",true);
        //判断是否是第首次登录
        if(isFirst){
            initData();
            initView();
            isFirst = false;
        }else {
            Intent intent = new Intent();
            intent.setClass(ViewPager_Activity.this,Welcome_Activity.class);
            startActivity(intent);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirst",isFirst);
        editor.commit();
    }
    private void initData(){
        arrayList_view = new ArrayList<>();
        //找布局
        view1 = LayoutInflater.from(this).inflate(R.layout.activity_view1,null);
        view2 = LayoutInflater.from(this).inflate(R.layout.activity_view2,null);
        view3 = LayoutInflater.from(this).inflate(R.layout.activity_view3,null);
        view4 = LayoutInflater.from(this).inflate(R.layout.activity_view4,null);
        view5 = LayoutInflater.from(this).inflate(R.layout.activity_view5,null);
        arrayList_view.add(view1);
        arrayList_view.add(view2);
        arrayList_view.add(view3);
        arrayList_view.add(view4);
        arrayList_view.add(view5);
    }
    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPager_Adapter(arrayList_view);
        viewPager.setAdapter(adapter);
        imageView = (ImageView) view5.findViewById(R.id.view5_iv_start);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ViewPager_Activity.this,Welcome_Activity.class);
                startActivity(intent);
            }
        });

    }
}
