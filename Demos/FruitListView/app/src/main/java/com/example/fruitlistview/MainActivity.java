package com.example.fruitlistview;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fruitlistview.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 这里添加了一个initFruits() 方法，用于初始化所有的水
 * 果数据。在Fruit 类的构造函数中将水果的名字和对应的图片id传入，然
 * 后把创建好的对象添加到水果列表中。另外我们使用了一个for 循环将所
 * 有的水果数据添加了两遍，这是因为如果只添加一遍的话，数据量还不足
 * 以充满整个屏幕。接着在onCreate() 方法中创建了FruitAdapter 对
 * 象，并将FruitAdapter 作为适配器传递给ListView，这样定制ListView
 * 界面的任务就完成了。
 */
public class MainActivity extends AppCompatActivity {


    public List<Fruit> fruitList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initFruits();
        // 初始化水果数据

        FruitAdapter adapter = new FruitAdapter( this, R.layout.fruit_item , fruitList );


        ListView listView = findViewById( R.id.list_view );

        listView.setAdapter(adapter);



    }


    public void initFruits() {
        // 初始化水果数据

        for( int i=0; i < 2; i++) {

            Fruit apple = new Fruit( "Apple", R.drawable.apple_pic );

            fruitList.add( apple );


            Fruit banana = new Fruit("Banana", R.drawable.banana_pic);

            fruitList.add(banana);

            Fruit orange = new Fruit("Orange", R.drawable.orange_pic);

            fruitList.add(orange);

            Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);

            fruitList.add(watermelon);

            Fruit pear = new Fruit("Pear", R.drawable.pear_pic);

            fruitList.add(pear);

            Fruit grape = new Fruit("Grape", R.drawable.grape_pic);

            fruitList.add(grape);

            Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);

            fruitList.add(pineapple);

            Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);

            fruitList.add(strawberry);

            Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);

            fruitList.add(cherry);

            Fruit mango = new Fruit("Mango", R.drawable.mango_pic);

            fruitList.add(mango);

        }






        return;
    }

}