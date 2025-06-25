// app/src/main/java/com/example/appdemo/HomeAdapter.java
package com.example.appdemo; // 请替换为您的实际包名

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide; // 导入 Glide
import com.chad.library.adapter.base.BaseQuickAdapter; // 导入 BRVAH 的 BaseQuickAdapter
// 【重要修改】BaseViewHolder 的导入路径在 BRVAH 3.x 中发生了变化
import com.chad.library.adapter.base.viewholder.BaseViewHolder; // 导入 BRVAH 3.x 的 BaseViewHolder

import java.util.List; // 导入 List 接口

// HomeAdapter 继承自 BaseQuickAdapter，用于首页瀑布流的 RecyclerView
// 第一个泛型参数是数据类型 (HomeItem)，第二个是 ViewHolder 类型 (BaseViewHolder)
public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {

    // 构造函数
    // layoutResId: 列表项的布局文件ID (item_home.xml)
    // data: 列表数据集合 (List<HomeItem>)
    public HomeAdapter(int layoutResId, List<HomeItem> data) {
        super(layoutResId, data);
    }

    // 重写 convert 方法，用于绑定数据到视图
    // holder: ViewHolder 帮助类，提供了获取视图的方法 (在 BRVAH 3.x 中，参数名通常是 holder)
    // item: 当前要绑定的数据对象
    @Override
    protected void convert(BaseViewHolder holder, HomeItem item) { // 参数名从 helper 改为 holder 更符合习惯
        // 获取 ImageView 实例
        // 在 BRVAH 3.x 中，BaseViewHolder 提供了 getView 方法来获取子视图，与 2.x 类似
        ImageView itemImage = holder.getView(R.id.item_image);
        // 获取 TextView 实例
        TextView itemTitle = holder.getView(R.id.item_title);

        // 设置图片的高度，以实现瀑布流效果
        // 获取 ImageView 的布局参数
        ViewGroup.LayoutParams layoutParams = itemImage.getLayoutParams();
        // 设置高度为数据模型中定义的 itemHeight
        layoutParams.height = item.getItemHeight();
        // 应用新的布局参数
        itemImage.setLayoutParams(layoutParams);

        // 使用 Glide 加载图片
        // 【重要修改】在 BRVAH 3.x 中，不再直接暴露 mContext 字段。
        // 应通过 holder.itemView.getContext() 获取上下文
        Glide.with(holder.itemView.getContext()) // 使用 itemView.getContext() 获取 Context
                .load(item.getImageUrl())
                // .placeholder(R.drawable.your_placeholder) // 可以添加占位图
                // .error(R.drawable.your_error_image)     // 可以添加加载失败图
                .into(itemImage);

        // 设置标题文本
        itemTitle.setText(item.getTitle());

        // 如果需要为列表项或其子视图添加点击事件，可以在这里设置
        // 注意：BRVAH 3.x 中，子视图点击事件的设置方式有所不同，通常通过 adapter.addChildClickViewIds()
        // 并在 Activity/Fragment 中通过 adapter.setOnItemChildClickListener() 来监听。
        // 对于整个 item 的点击事件，通过 adapter.setOnItemClickListener() 设置。
        // helper.addOnClickListener(R.id.item_image); // 此方法在 3.x 中不直接在 BaseViewHolder 上调用
        // helper.addOnClickListener(R.id.item_title); // 同上
        // holder.itemView.setOnClickListener(...) // 为整个itemview设置点击事件，如果不需要BRVAH的click listener机制
    }
}
