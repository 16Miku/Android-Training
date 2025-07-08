package com.example.viewtest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.viewtest.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView text1 = findViewById(R.id.text1);

        SpannableString span = new SpannableString("强力型迪迦,菜鸟教程,空中型迪迦和黑暗迪迦，");

        //1.设置背景色,setSpan时需要指定的flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
        span.setSpan(new ForegroundColorSpan(Color.RED),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //2.用超链接标记文本
        span.setSpan( new URLSpan("https://www.runoob.com/w3cnote/android-tutorial-textview.html"),6,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        //3.用样式标记文本（斜体）
        span.setSpan( new StyleSpan(Typeface.BOLD_ITALIC),5,7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        text1.setText(span);
        
    }


}