package com.example.administrator.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.administrator.recipes.R;
import com.example.administrator.recipes.adapter.Home_Adapter;
import com.example.administrator.recipes.utils.Contance;
import com.example.administrator.recipes.utils.User;
import com.example.administrator.recipes.utils.User_Bz;

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

public class Home_Activity extends AppCompatActivity {
    RecyclerView home_rv;
    ImageView home_msg_iv,home_dl_iv,home_lxcp_iv,home_cfbd_iv,home_cpfl_iv;
    TextView home_lxcp_tv,home_cfbd_tv,home_cpfl_tv;
    SearchView home_srk_sv;
    Context context;
    StringBuffer stringBuffer;
    public static final int SUCCESS = 0;
    static ArrayList<User> arrayList;
    ArrayList<User_Bz> arrayList_bz;
    Home_Adapter adapter;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    String str = (String) msg.obj;
                    arrayList = new ArrayList<>();

//                    Log.e("内容",str);
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

                        adapter = new Home_Adapter(context);
                        adapter.setArrayList(arrayList);
                        home_rv.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    adapter = new Home_Adapter(context);
                    adapter.setMyOnClick(new Home_Adapter.MyOnClick() {
                        @Override
                        public void OnClick(int position) {
                            Log.e("AAA","AAA");
                            Intent intent = new Intent();
                            intent.setClass(Home_Activity.this,Cpxq_Activity.class);
                            intent.putExtra("position",position);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        if(context == null){
            context = this;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String str1 = HttpRequest(Contance.SHARESDK_API_SEARCH+"?"+"key="+Contance.SHARESDK_API_KEY+"&menu=鸡");
                    Message msg = new Message();
                    msg.obj = str1;
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void initView(){

        linearLayoutManager = new LinearLayoutManager(context);
        home_rv = (RecyclerView) findViewById(R.id.home_rv);
        home_rv.setLayoutManager(linearLayoutManager);

        home_cfbd_iv = (ImageView) findViewById(R.id.home_cfbd_iv);
        home_lxcp_iv = (ImageView) findViewById(R.id.home_lxcp_iv);
        home_cpfl_iv = (ImageView) findViewById(R.id.home_cpfl_iv);

        home_cfbd_tv = (TextView) findViewById(R.id.home_cfbd_tv);
        home_lxcp_tv = (TextView) findViewById(R.id.home_lxcp_tv);
        home_cpfl_tv = (TextView) findViewById(R.id.home_cpfl_tv);

        home_dl_iv = (ImageView) findViewById(R.id.home_dl_iv);
        home_msg_iv = (ImageView) findViewById(R.id.home_msg_iv);
        home_srk_sv = (SearchView) findViewById(R.id.home_srk_sv);
        // 设置该SearchView默认是否自动缩小为图标
        home_srk_sv.setIconifiedByDefault(false);
        // 设置该SearchView显示搜索按钮
        home_srk_sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        home_srk_sv.setQueryHint("菜谱");
        //设置searchView的监听
        home_srk_sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Log.e("Home_Activity", s);
                Intent intent = new Intent();
                intent.setClass(Home_Activity.this,Ss_Activity.class);
                intent.putExtra("msg",s);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        //菜品分类按钮的监听
        home_cpfl_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Home_Activity.this,Cpfl_Activity.class);
                startActivity(intent);
            }
        });
        //流行菜谱按钮监听
        home_lxcp_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Home_Activity.this,Lxcp_Activity.class);
                startActivity(intent);
            }
        });
        //厨房宝典按钮监听
        home_cfbd_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Home_Activity.this,Sc_Activity.class);
                startActivity(intent);
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
