package com.example.day11_kotlinrecyclerview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myListAdapter: MyListAdapter
    private val dataList = mutableListOf<ListItem>()

    private lateinit var detailActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        detailActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // 处理从 DetailActivity 返回的结果
            if (result.resultCode == Activity.RESULT_OK) {
                // 使用 getParcelableExtra<ListItem>() 获取返回的 ListItem
                // 由于 isLiked 现在在 data class 的主构造函数中，Parcelize 会正确处理它
                val updatedItem = result.data?.getParcelableExtra<ListItem>(DetailActivity.EXTRA_LIST_ITEM)
                updatedItem?.let { item ->
                    // 找到被更新的项在 dataList 中的索引
                    val index = dataList.indexOfFirst { it.id == item.id }
                    if (index != -1) {
                        // 直接用返回的更新后的项替换 dataList 中对应的旧项
                        // 此时 item 的 isLiked 状态已经是 DetailActivity 中修改后的最新状态
                        dataList[index] = item
                        // 提交新的列表到 ListAdapter，ListAdapter 会自动计算差异并更新 UI
                        myListAdapter.submitList(dataList.toMutableList()) // 提交一个副本
                        Toast.makeText(this, "列表项 ${item.id} 状态已更新并刷新列表", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 初始化适配器，传入两个回调：列表项点击（跳转详情）和点赞按钮点击
        myListAdapter = MyListAdapter(
            onItemClicked = { clickedItem ->
                // 跳转详情页的逻辑保持不变
                val intent = Intent(this, DetailActivity::class.java).apply {
                    // 传递被点击的 ListItem。由于 isLiked 现在是主构造函数的一部分，它会被正确序列化。
                    putExtra(DetailActivity.EXTRA_LIST_ITEM, clickedItem)
                }
                detailActivityLauncher.launch(intent)
            },
            onLikeClicked = { likedItem -> // 处理列表页的点赞按钮点击回调
                // 找到被点赞的项在 dataList 中的索引
                val index = dataList.indexOfFirst { it.id == likedItem.id }
                if (index != -1) {
                    // 创建一个新的 ListItem 实例，并复制旧实例的内容，只改变 isLiked 状态
                    // 由于 isLiked 已经在 data class 的主构造函数中，直接使用 copy(isLiked = ...) 即可
                    val updatedItem = when (likedItem) {
                        is ListItem.TextItem -> likedItem.copy(isLiked = !likedItem.isLiked)
                        is ListItem.ImageItem -> likedItem.copy(isLiked = !likedItem.isLiked)
                    }
                    // 更新 dataList 中对应位置的项
                    dataList[index] = updatedItem
                    // 提交新的列表到 ListAdapter，ListAdapter 会自动计算差异并更新 UI
                    myListAdapter.submitList(dataList.toMutableList()) // 提交一个副本
                    Toast.makeText(this, "列表项 ${likedItem.id} 点赞状态: ${if (updatedItem.isLiked) "已点赞" else "待点赞"}", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myListAdapter

        prepareData()
        myListAdapter.submitList(dataList)
    }

    /**
     * 准备初始数据列表，包含文本项和图片项。
     */
    private fun prepareData() {
        // 添加文本项
        for (i in 0 until 10) {
            val longText = "这是第 ${i + 1} 个文本内容。这是一个更长的文本示例，用于在详情页展示更多的信息。我们可以添加多段文字，来模拟一篇短文章，以便更好地测试滚动视图和布局效果。例如，一段关于Android开发的介绍，或者一些随机的Lorem Ipsum文本。这个文本会很长，长到足以在屏幕上滚动，从而验证我们的UI布局是否合理。".repeat(2 + i % 3) // 重复几次，让文本更长
            dataList.add(ListItem.TextItem(id = "text_$i", textContent = longText))
        }
        // 添加图片项
        val imageUrls = listOf(
            "https://picsum.photos/id/10/300/200",
            "https://picsum.photos/id/20/300/200",
            "https://picsum.photos/id/30/300/200",
            "https://picsum.photos/id/40/300/200",
            "https://picsum.photos/id/50/300/200"
        )
        for (i in 0 until 5) {
            dataList.add(ListItem.ImageItem(id = "image_$i", imageUrl = imageUrls[i % imageUrls.size]))
        }
        // 随机打乱顺序，使文本和图片项交错显示
        dataList.shuffle()
    }
}
