package com.example.day7_animation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Tween_Animation_MainActivity extends AppCompatActivity {

    private static final String TAG = "TweenAnimation";

    private ImageView animatedImageView;

    private AnimationSet myAnimationSet;

    private int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.tween_animation_activity_main);



        animatedImageView = findViewById(R.id.tween_image_view);

        Button startButton = findViewById(R.id.start_animation_button);

        Button stopButton = findViewById(R.id.stop_animation_button);

        Button toPropertyButton = findViewById(R.id.to_property_animation);


        // 1. 初始化类成员变量 myAnimationSet
        // 创建一个动画集合，参数 true 表示集合中的子动画共享同一个插值器（如果子动画没有单独指定）
        myAnimationSet = new AnimationSet(true);



        ScaleAnimation scaleAnimation = new ScaleAnimation( 1.0f,1.5f,1.0f,1.5f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );

        scaleAnimation.setDuration(2000);

        scaleAnimation.setRepeatCount(3);


        RotateAnimation rotateAnimation = new RotateAnimation(0f, -720f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 旋转中心为自身中心

        rotateAnimation.setDuration(2000);

        rotateAnimation.setRepeatCount(3);


        AlphaAnimation alphaAnimation = new AlphaAnimation( 1.0f,0.8f );

        alphaAnimation.setDuration(2000);

        alphaAnimation.setRepeatCount(3);


        // 将所有子动画添加到动画集合中
        myAnimationSet.addAnimation(scaleAnimation);

        myAnimationSet.addAnimation(rotateAnimation);

        myAnimationSet.addAnimation(alphaAnimation);





        // myAnimationSet.setRepeatCount(3);
        // 此命令实践发现无效，无法让动画重复播放。注：实践发现对myAnimationSet设置重复和监听器均不能实现重复和监听重复。

        myAnimationSet.setRepeatMode(Animation.RESTART);





        // 为scaleAnimation设置动画监听器。注：实践发现对myAnimationSet设置重复和监听器均不能实现重复和监听重复。
        scaleAnimation.setAnimationListener(

                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        count=0;

                        Log.d( TAG, "animation start");

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Log.d( TAG, "animation end");

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                        count++;

                        Log.d( TAG, "animation repeat " + count + " times" );

                    }
                }

        );


        //  设置按钮点击监听器
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimation();
            }
        });


        toPropertyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new  Intent( Tween_Animation_MainActivity.this, Property_Animation_Activity.class );

                        startActivity(intent);


                    }
                }
        );





    }



    private void startAnimation( ) {

        // 每次启动前，先清除 ImageView 上可能存在的旧动画，确保动画能重新开始
        if (animatedImageView != null) {
            animatedImageView.clearAnimation();
        }


        if( animatedImageView != null && myAnimationSet != null ) {

            // **关键修改：在启动动画前，重置动画集合的内部状态**
            myAnimationSet.reset(); // 确保动画从头开始并正确处理重复



            animatedImageView.startAnimation(myAnimationSet);
            // 启动动画，将预先配置好的动画集合应用到 ImageView 上


        }


    }



    private void stopAnimation( ) {


        if( animatedImageView != null ) {

            animatedImageView.clearAnimation();
            // 清除动画：停止当前正在播放的动画，并移除其对视图的视觉影响

        }



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时，确保清除动画，防止内存泄漏
        if (animatedImageView != null) {

            animatedImageView.clearAnimation();

            Log.d(TAG, "Animation cleared in onDestroy.");
        }
    }


}