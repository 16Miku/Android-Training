package com.example.fruitlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;


/**
 * 创建一个自定义的适配器，这个适配器继承自ArrayAdapter，
 * 并将泛型指定为Fruit 类。新建类FruitAdapter
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {


    private int resourceId;

    /**
     * FruitAdapter 重写了父类的一组构造函数，用于将上下文、ListView
     * 子项布局的id和数据都传递进来。
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public FruitAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Fruit> objects) {

        super(context, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }


    /**
     * 另外又重写了getView() 方法，这个
     * 方法在每个子项被滚动到屏幕内的时候会被调用。在getView() 方法
     * 中，首先通过getItem() 方法得到当前项的Fruit实例，然后使用
     * LayoutInflater 来为这个子项加载我们传入的布局。
     * 这里LayoutInflater 的inflate() 方法接收3个参数，前两个参数我
     * 们已经知道是什么意思了，第三个参数指定成false ，表示只让我们在
     * 父布局中声明的layout 属性生效，但不会为这个View添加父布局，因为
     * 一旦View有了父布局之后，它就不能再添加到ListView中了。如果你现在
     * 还不能理解这段话的含义也没关系，只需要知道这是ListView中的标准写
     * 法就可以了，当你以后对View理解得更加深刻的时候，再来读这段话就
     * 没有问题了。
     * 我们继续往下看，接下来调用View的findViewById() 方法分别获取到
     * ImageView和TextView的实例，并分别调用它们的
     * setImageResource() 和setText() 方法来设置显示的图片和文字，
     * 最后将布局返回，这样我们自定义的适配器就完成了。
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        Fruit fruit = getItem( position );

        View view = LayoutInflater.from( getContext() ).inflate( resourceId, parent, false );

        ImageView fruitImage = (ImageView) view.findViewById(R.id.imageview);

        TextView fruitName = (TextView) view.findViewById(R.id.textview);

        fruitImage.setImageResource( fruit.getImageId() );

        fruitName.setText( fruit.getName() );

        return view;


    }



}
