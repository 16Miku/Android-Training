package com.example.day7_animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Property_Animation_Activity extends AppCompatActivity {


    private ImageView animatorSetImageView;
    private Button startAnimatorSetButton;


    private Button stopAnimatorSetButton;

    private Button toTweenButton;

    private AnimatorSet currentAnimatorSet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.property_animation_activity);



        animatorSetImageView = findViewById(R.id.property_image_view);

        startAnimatorSetButton = findViewById(R.id.start_animation_button);

        stopAnimatorSetButton = findViewById(R.id.stop_animation_button);

        toTweenButton = findViewById(R.id.to_tween_animation);

        startAnimatorSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimatorSet();
            }
        });


        // 设置停止按钮点击监听器
        stopAnimatorSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimatorSet();
            }
        });


        toTweenButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new  Intent( Property_Animation_Activity.this, Tween_Animation_MainActivity.class );

                        startActivity(intent);
                    }
                }
        );



    }





    /**
     * 启动属性动画集合
     */
    public void startAnimatorSet() {

        // 如果有正在运行的动画，先取消它，确保每次点击都从新动画开始
        if (currentAnimatorSet != null && currentAnimatorSet.isRunning()) {
            currentAnimatorSet.cancel();
        }


        // **重要：在开始新动画前，将 ImageView 的属性重置到初始状态**
        // 这样可以确保动画每次都从 View 的原始位置和状态开始，而不是从上次动画结束的位置开始
        animatorSetImageView.setRotationX(0f);
        animatorSetImageView.setTranslationX(0f);
        animatorSetImageView.setAlpha(1.0f); // 确保透明度回到完全不透明


        // 动画1：围绕 X 轴旋转 360 度
        // ObjectAnimator.ofFloat(目标视图, 属性名, 起始值, 结束值)
        ObjectAnimator rotationX  = ObjectAnimator.ofFloat(
                animatorSetImageView,
                "rotationX",
                0f,360f
        );

        rotationX.setDuration(1000);

        // 应用自定义插值器1
        rotationX.setInterpolator(new CustomInterpolator1());


        // 动画2：向右平移 120px
        ObjectAnimator translationX = ObjectAnimator.ofFloat(
                animatorSetImageView,
                "translationX",
                0f,120f
        );


        translationX.setDuration(1000);

        // 应用自定义估值器
        translationX.setEvaluator(new CustomFloatEvaluator());



        // 动画3：透明度从不透明变为 0.5
        ObjectAnimator alpha = ObjectAnimator.ofFloat(
                animatorSetImageView, "alpha", 1.0f, 0.5f );

        alpha.setDuration(500);

        // 应用自定义插值器2
        alpha.setInterpolator(new CustomInterpolator2());

        currentAnimatorSet = new AnimatorSet();


        // 编排动画：
        // 1. rotationX 和 translationX 同时执行
        currentAnimatorSet.play(translationX).with(rotationX);


        // 2. alpha 在 rotationX 和 translationX 完成后执行
        // 因为 rotationX 和 translationX 是同时开始和结束的，所以 after(rotationX) 意味着在两者都结束后
        currentAnimatorSet.play(alpha).after(rotationX);


        // 启动动画集合
        currentAnimatorSet.start();




    }




    /**
     * 停止属性动画集合并重置视图状态
     */
    public void stopAnimatorSet(){



        if( currentAnimatorSet != null && currentAnimatorSet.isRunning() ) {

            currentAnimatorSet.cancel();
        }

        animatorSetImageView.setTranslationX(0f);

        animatorSetImageView.setRotationX(0f);

        animatorSetImageView.setAlpha(1.0f);





    }



    /**
     * 自定义插值器1：一个简单的自定义缓入缓出效果
     * 动画开始和结束时慢，中间快
     */
    private static class CustomInterpolator1 implements TimeInterpolator {


        @Override
        public float getInterpolation(float input) {


            if( input < 0.5f ) {
                // 前半段 (0.0 - 0.5)：加速

                return (float) ( 0.5 * Math.pow( input * 2, 2 ) );
            }
            else {
                // 后半段 (0.5 - 1.0)：减速

                return ( float ) ( 0.5 + 0.5 * ( 1- Math.pow( (1-input)*2 , 2 ) ) );
            }

        }
    }


    /**
     * 自定义插值器2：一个简单的自定义延迟插值器
     * 动画开始时有短暂延迟，然后线性进行
     */
    private static class CustomInterpolator2 implements TimeInterpolator {


        @Override
        public float getInterpolation(float input) {

            float delayFraction = 0.2f;

            if( input < delayFraction ) {

                return 0f;
            }
            else {

                // 延迟结束后，将剩余的动画进度 (1 - delayFraction) 映射到 0 到 1
                return (input - delayFraction) / (1.0f - delayFraction);
            }



        }
    }



    private static class CustomFloatEvaluator implements TypeEvaluator<Float> {


        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {

// 首先计算线性插值的结果
            float value = startValue + fraction * (endValue - startValue);

            // 添加一个基于正弦波的“摆动”效果
            // Math.sin(fraction * Math.PI * 4) 会在 fraction 从 0 到 1 时完成两个完整的正弦周期
            // 0.05 是摆动幅度，4 是频率（控制摆动次数）
            float wobbleAmount = (float) (0.05 * Math.sin(fraction * Math.PI * 4));
            // 将摆动幅度乘以动画的总范围，使其与动画值相关
            return value + wobbleAmount * (endValue - startValue);
        }
    }



}