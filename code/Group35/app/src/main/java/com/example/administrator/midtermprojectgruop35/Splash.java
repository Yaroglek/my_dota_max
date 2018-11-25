package com.example.administrator.midtermprojectgruop35;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Splash extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       imageView = (ImageView) findViewById(R.id.loadImage);
        // 设置加载动画透明度渐变从（0.1不显示-1.0完全显示）
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        // 设置动画时间5s
        animation.setDuration(500);
        // 将组件与动画关联
        imageView.setAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {
            // 动画开始时执行
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                // 初始化

            }

            // 动画重复时执行
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            // 动画结束时执行
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}