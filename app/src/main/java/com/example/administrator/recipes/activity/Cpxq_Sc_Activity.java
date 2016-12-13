package com.example.administrator.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.Cpflxq_Adapter;
import com.example.administrator.recipes.adapter.Cpxq_Adapter;
import com.example.administrator.recipes.db.MySqliteHelper;
import com.example.administrator.recipes.utils.Contance;
import com.example.administrator.recipes.utils.User;
import com.example.administrator.recipes.utils.User_Bz;
import com.example.administrator.recipes.utils.User_Cpflxq;
import com.squareup.picasso.Picasso;

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
 * Created by Administrator on 2016/12/12.
 */

public class Cpxq_Sc_Activity extends AppCompatActivity {
    StringBuffer stringBuffer;
    public static final int SUCCESS = 0;
    Context context;
    ImageView cpxq_fh_iv,cpxq_ylt_iv,sc_iv;
    MySqliteHelper mySqliteHelper;
    CoordinatorLayout container;
    ArrayList<User_Cpflxq> arrayList_sc;
    TextView cpxq_name_tv,cpxq_js_tv,cpxq_sc_tv;
    RecyclerView cpxq_rv;
    ArrayList<User> arrayList;
    ArrayList<User_Bz> arrayList_bz;

    LinearLayoutManager linearLayoutManager;
    Cpxq_Adapter adapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    String str = (String) msg.obj;

                    Log.e("STR", str);

                    arrayList = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        jsonObject = jsonObject.getJSONObject("result");
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            JSONArray jsonArray1 =jsonObject1.optJSONArray("albums");
                            //成品图
                            String albums = jsonArray1.get(0).toString();
                            //标题
                            String title = jsonObject1.getString("title");
                            //简介
                            String imtro = jsonObject1.getString("imtro");
                            //材料
                            String ingredients = jsonObject1.getString("ingredients");
                            //配料
                            String burden = jsonObject1.getString("burden");


                            JSONArray jsonArray2 = jsonObject1.optJSONArray("steps");
                            arrayList_bz = new ArrayList<>();
                            for(int x = 0;x<jsonArray2.length();x++){
                                JSONObject jsonObject2 = (JSONObject) jsonArray2.get(x);
                                String img = jsonObject2.getString("img");
                                String step = jsonObject2.getString("step");
                                Log.e("img", img);
                                Log.e("step", step);
                                arrayList_bz.add(new User_Bz(img,step));
                            }
                            arrayList.add(new User(albums,title,imtro,ingredients,burden,arrayList_bz));
                        }
//                        Log.e("集合AAAA", "arrayList:" + arrayList.size());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cpxq_rv.setLayoutManager(linearLayoutManager);
                    adapter = new Cpxq_Adapter(context);

                    adapter.setArrayList(arrayList_bz);
                    cpxq_rv.setAdapter(adapter);

                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpxq);
        if(context == null){
            context = this;
        }
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String str = HttpRequest("http://apis.juhe.cn/cook/query?key=f5490283db415ad12c1ca19bf209ba88&menu="+msg);
                    Message msg = new Message();
                    msg.obj = str;
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    private void initView(){
        arrayList = Ss_Activity.arrayList;
        arrayList_sc = new ArrayList<>();
        container = (CoordinatorLayout) findViewById(R.id.container);
        mySqliteHelper = new MySqliteHelper(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        cpxq_fh_iv = (ImageView) findViewById(R.id.cpxq_fh_iv);
        cpxq_ylt_iv = (ImageView) findViewById(R.id.cpxq_ylt_iv);
        cpxq_name_tv = (TextView) findViewById(R.id.cpxq_name_tv);
        cpxq_js_tv = (TextView) findViewById(R.id.cpxq_js_tv);
        cpxq_sc_tv = (TextView) findViewById(R.id.cpxq_sc_tv);
        sc_iv = (ImageView) findViewById(R.id.sc_iv);

        linearLayoutManager = new LinearLayoutManager(context);
        cpxq_rv = (RecyclerView) findViewById(R.id.cpxq_rv);

//        Log.d("AAAAAA", "arrayList.size():" + arrayList.size());

        arrayList_bz = arrayList.get(position).getArrayList();
        Log.e("DDDDDD", "arrayList_bz.size():" + arrayList_bz.size());

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
