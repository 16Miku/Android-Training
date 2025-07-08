package com.example.listviewtest;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.listviewtest.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * ListView绝对可以称得上是Android中最常用的控件之一，几乎所有的应
 * 用程序都会用到它。由于手机屏幕空间都比较有限，能够一次性在屏幕上
 * 显示的内容并不多，当我们的程序中有大量的数据需要展示的时候，就可
 * 以借助ListView来实现。ListView允许用户通过手指上下滑动的方式将屏
 * 幕外的数据滚动到屏幕内，同时屏幕上原有的数据则会滚动出屏幕。相信
 * 你其实每天都在使用这个控件，比如查看QQ聊天记录，翻阅微博最新消
 * 息，等等。
 */
public class MainActivity extends AppCompatActivity {

    private String[] data = {
            "0","1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30"
    };


    /**
     * 不过，数组中的数据是无法直接传递给ListView的，我们还需要借助适配
     * 器来完成。Android中提供了很多适配器的实现类，其中我认为最好用的
     * 就是ArrayAdapter。它可以通过泛型来指定要适配的数据类型，然后在构
     * 造函数中把要适配的数据传入。ArrayAdapter有多个构造函数的重载，你
     * 应该根据实际情况选择最合适的一种。这里由于我们提供的数据都是字符
     * 串，因此将ArrayAdapter的泛型指定为String ，然后在ArrayAdapter的
     * 构造函数中依次传入当前上下文、ListView子项布局的id，以及要适配的
     * 数据。注意，我们使用了android.R.layout.simple_list_item_1
     * 作为ListView子项布局的id，这是一个Android内置的布局文件，里面只
     * 有一个TextView，可用于简单地显示一段文本。这样适配器对象就构建好
     * 了。
     * 最后，还需要调用ListView的setAdapter() 方法，将构建好的适配器
     * 对象传递进去，这样ListView和数据之间的关联就建立完成了。
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1 , data
        );


        ListView listView = (ListView) findViewById(R.id.listview1);

        listView.setAdapter(adapter);


    }

}