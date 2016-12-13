package com.example.administrator.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.Cpflxq_Adapter;
import com.example.administrator.recipes.adapter.Home_Adapter;
import com.example.administrator.recipes.utils.Contance;
import com.example.administrator.recipes.utils.User;
import com.example.administrator.recipes.utils.User_Bz;
import com.example.administrator.recipes.utils.User_Cpflxq;

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
 * Created by Administrator on 2016/12/8.
 */

public class Cpflxq_Activity extends AppCompatActivity {
    Context context;
    StringBuffer stringBuffer;
    ImageView cpflxq_fh_iv;
    TextView cpflxq_title_tv;
    RecyclerView cpflxq_rv;
    Cpflxq_Adapter adapter;
    LinearLayoutManager linearLayoutManager;
    public static final int SUCCESS = 0;
    static ArrayList<User> arrayList;
    ArrayList<User_Bz> arrayList_bz;
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
                        Log.e("集合AAAA", "arrayList:" + arrayList.size());

//                        adapter = new Home_Adapter(context);
//                        adapter.setArrayList(arrayList);
//                        home_rv.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    try {
//                        JSONObject jsonObject = new JSONObject(str);
//                        jsonObject = jsonObject.getJSONObject("result");
//                        JSONArray jsonArray = jsonObject.optJSONArray("data");
//                        for(int i = 0;i<jsonArray.length();i++){
//                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                            String name = jsonObject1.getString("title");
//                            String title = jsonObject1.getString("imtro");
//                            JSONArray jsonArray1 = jsonObject1.optJSONArray("albums");
//                            String img = jsonArray1.get(0).toString();
//                            arrayList.add(new User_Cpflxq(img,name,title));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    adapter = new Cpflxq_Adapter(context);
                    adapter.setArrayList(arrayList);
                    cpflxq_rv.setAdapter(adapter);
                    adapter.setMyOnClike(new Cpflxq_Adapter.MyOnClike() {
                        @Override
                        public void OnClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(Cpflxq_Activity.this,Cpxq1_Activity.class);
                            intent.putExtra("position",position);
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
        setContentView(R.layout.activity_cpflxq);
        if(context == null){
            context = this;
        }
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        Log.e("Cpflxq_Activity", "position:" + id);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String str = HttpRequest("http://apis.juhe.cn/cook/index?key=f5490283db415ad12c1ca19bf209ba88&cid="+id);
                    Message msg = new Message();
                    msg.obj = str;
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
        cpflxq_fh_iv = (ImageView) findViewById(R.id.cpflxq_fh_iv);
        cpflxq_title_tv = (TextView) findViewById(R.id.cpflxq_title_tv);
        linearLayoutManager = new LinearLayoutManager(context);
        cpflxq_rv = (RecyclerView) findViewById(R.id.cpflxq_rv);
        cpflxq_rv.setLayoutManager(linearLayoutManager);
        cpflxq_fh_iv.setOnClickListener(new View.OnClickListener() {
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
