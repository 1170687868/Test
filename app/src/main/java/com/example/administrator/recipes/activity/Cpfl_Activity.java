package com.example.administrator.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.Cpfl_Adapter;
import com.example.administrator.recipes.adapter.Home_Adapter;
import com.example.administrator.recipes.utils.Contance;
import com.example.administrator.recipes.utils.RecycleViewGridDivier;
import com.example.administrator.recipes.utils.User;
import com.example.administrator.recipes.utils.User_Fl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6.
 */

public class Cpfl_Activity extends AppCompatActivity {
    ImageView cpfl_fh_iv;
    TextView cpfl_title_tv,cpfl_name_tv,cpfl_name_tv1,cpfl_name_tv2,cpfl_name_tv3,cpfl_name_tv4;
    RecyclerView cpfl_rv,cpfl_rv1,cpfl_rv2,cpfl_rv3,cpfl_rv4;
    Context context;
    StringBuffer stringBuffer;
    RecycleViewGridDivier recycleViewGridDivier;
    Cpfl_Adapter adapter;
    public static final int SUCCESS = 0;
    ArrayList<String> arrayList;
    ArrayList<User_Fl> arrayList1,arrayList2,arrayList3,arrayList4,arrayList5,arrayList6;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1,linearLayoutManager2,linearLayoutManager3,linearLayoutManager4 ;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    String str = (String) msg.obj;
                    arrayList = new ArrayList<>();
                    arrayList1 = new ArrayList<>();
                    arrayList2 = new ArrayList<>();
                    arrayList3 = new ArrayList<>();
                    arrayList4 = new ArrayList<>();
                    arrayList5 = new ArrayList<>();
                    arrayList6 = new ArrayList<>();
                    Log.e("内容",str);
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray jsonArray = jsonObject.optJSONArray("result");
                        for(int i = 0;i<5;i++){
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            String name = jsonObject1.getString("name");
                            Log.e("name", name);
                            arrayList.add(name);
                        }
                        for(int i = 0;i<5;i++){
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            JSONArray jsonArray1 = jsonObject1.optJSONArray("list");
                            for(int x = 0;x<jsonArray1.length();x++){
                                JSONObject jsonObject2 = (JSONObject) jsonArray1.get(x);
                                String name1 = jsonObject2.getString("name");
                                String id = jsonObject2.getString("id");
                                Log.d("name1", name1);
                                arrayList1.add(new User_Fl(name1,id));
//                                Log.e("Cpfl_Activity", "arrayList1.size():" + arrayList1.size());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cpfl_name_tv.setText(arrayList.get(0));
                    cpfl_name_tv1.setText(arrayList.get(1));
                    cpfl_name_tv2.setText(arrayList.get(2));
                    cpfl_name_tv3.setText(arrayList.get(3));
                    cpfl_name_tv4.setText(arrayList.get(4));
                    for(int i = 0;i<arrayList1.size();i++){
                        if(i<8){
                            arrayList2.add(arrayList1.get(i));
                        }
                        if(i>=8&&i<40){
                            arrayList3.add(arrayList1.get(i));
                        }
                        if(i>=40&&i<49){
                            arrayList4.add(arrayList1.get(i));
                        }
                        if(i>=49&&i<78){
                            arrayList5.add(arrayList1.get(i));
                        }
                        if(i>=78&&i<87){
                            arrayList6.add(arrayList1.get(i));
                        }
                    }

                    adapter = new Cpfl_Adapter(context);
                    adapter.setArrayList(arrayList2);
                    cpfl_rv.setAdapter(adapter);
                    adapter.setMyOnClick(new Cpfl_Adapter.MyOnClick() {
                        @Override
                        public void OnClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(Cpfl_Activity.this,Cpflxq_Activity.class);
                            intent.putExtra("id",arrayList2.get(position).getId());
                            startActivity(intent);
                        }
                    });

                    adapter = new Cpfl_Adapter(context);
                    adapter.setArrayList(arrayList3);
                    cpfl_rv1.setAdapter(adapter);
                    adapter.setMyOnClick(new Cpfl_Adapter.MyOnClick() {
                        @Override
                        public void OnClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(Cpfl_Activity.this,Cpflxq_Activity.class);
                            intent.putExtra("id",arrayList3.get(position).getId());
                            startActivity(intent);
                        }
                    });

                    adapter = new Cpfl_Adapter(context);
                    adapter.setArrayList(arrayList4);
                    cpfl_rv2.setAdapter(adapter);
                    adapter.setMyOnClick(new Cpfl_Adapter.MyOnClick() {
                        @Override
                        public void OnClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(Cpfl_Activity.this,Cpflxq_Activity.class);
                            intent.putExtra("id",arrayList4.get(position).getId());
                            startActivity(intent);
                        }
                    });

                    adapter = new Cpfl_Adapter(context);
                    adapter .setArrayList(arrayList5);
                    cpfl_rv3.setAdapter(adapter);
                    adapter.setMyOnClick(new Cpfl_Adapter.MyOnClick() {
                        @Override
                        public void OnClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(Cpfl_Activity.this,Cpflxq_Activity.class);
                            intent.putExtra("id",arrayList5.get(position).getId());
                            startActivity(intent);
                        }
                    });

                    adapter = new Cpfl_Adapter(context);
                    adapter.setArrayList(arrayList6);
                    cpfl_rv4.setAdapter(adapter);
                    adapter.setMyOnClick(new Cpfl_Adapter.MyOnClick() {
                        @Override
                        public void OnClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(Cpfl_Activity.this,Cpflxq_Activity.class);
                            intent.putExtra("id",arrayList6.get(position).getId());
                            startActivity(intent);
                        }
                    });


                    break;
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpfl);
        if(context == null){
            context = this;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String str1 = HttpRequest("http://apis.juhe.cn/cook/category?key=f5490283db415ad12c1ca19bf209ba88");
                    Message msg = new Message();
                    msg.obj = str1;
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        initView();
    }
    private void initView(){
//        linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager1 = new LinearLayoutManager(context);
//        linearLayoutManager2 = new LinearLayoutManager(context);
//        linearLayoutManager3 = new LinearLayoutManager(context);
//        linearLayoutManager4 = new LinearLayoutManager(context);

        cpfl_fh_iv = (ImageView) findViewById(R.id.cpfl_fh_iv);
        cpfl_title_tv = (TextView) findViewById(R.id.cpfl_title_tv);

        cpfl_name_tv = (TextView) findViewById(R.id.cpfl_name_tv);
        cpfl_name_tv1 = (TextView) findViewById(R.id.cpfl_name_tv1);
        cpfl_name_tv2 = (TextView) findViewById(R.id.cpfl_name_tv2);
        cpfl_name_tv3 = (TextView) findViewById(R.id.cpfl_name_tv3);
        cpfl_name_tv4 = (TextView) findViewById(R.id.cpfl_name_tv4);

        cpfl_title_tv.setText("全部菜谱");


        cpfl_rv = (RecyclerView) findViewById(R.id.cpfl_rv);
        cpfl_rv1 = (RecyclerView) findViewById(R.id.cpfl_rv1);
        cpfl_rv2 = (RecyclerView) findViewById(R.id.cpfl_rv2);
        cpfl_rv3 = (RecyclerView) findViewById(R.id.cpfl_rv3);
        cpfl_rv4 = (RecyclerView) findViewById(R.id.cpfl_rv4);

        RecyclerView.LayoutManager  grid=new GridLayoutManager(this,4);
        RecyclerView.LayoutManager  grid1=new GridLayoutManager(this,4);
        RecyclerView.LayoutManager  grid2=new GridLayoutManager(this,4);
        RecyclerView.LayoutManager  grid3=new GridLayoutManager(this,4);
        RecyclerView.LayoutManager  grid4=new GridLayoutManager(this,4);
        cpfl_rv.setLayoutManager(grid);
        cpfl_rv1.setLayoutManager(grid1);
        cpfl_rv2.setLayoutManager(grid2);
        cpfl_rv3.setLayoutManager(grid3);
        cpfl_rv4.setLayoutManager(grid4);
//        cpfl_rv.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
//        cpfl_rv.addItemDecoration(recycleViewGridDivier);//添加分割线

        cpfl_fh_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public String HttpRequest(String strUrl) throws IOException {
        stringBuffer = new StringBuffer();
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("apikey", Contance.SHARESDK_API_KEY);
        connection.setRequestMethod("GET");
        InputStream in = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        int temp = 0;
        while ((temp = bufferedReader.read())!=-1){
            stringBuffer.append((char) temp);
        }
        return stringBuffer.toString();
    }
}
