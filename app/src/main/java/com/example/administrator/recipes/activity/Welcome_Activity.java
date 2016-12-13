package com.example.administrator.recipes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.recipes.R;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Welcome_Activity extends AppCompatActivity {
    ImageView welcome_iv;
    Animation animation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        show();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Welcome_Activity.this,Home_Activity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void initView(){
        welcome_iv = (ImageView) findViewById(R.id.welcome_iv);
    }
    private void show(){
        animation = AnimationUtils.loadAnimation(this,R.anim.welcome);
        welcome_iv.startAnimation(animation);
    }
}
