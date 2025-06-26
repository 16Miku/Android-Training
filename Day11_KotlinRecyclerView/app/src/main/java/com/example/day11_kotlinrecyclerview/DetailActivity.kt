package com.example.day11_kotlinrecyclerview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import android.widget.ScrollView // 导入 ScrollView

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIST_ITEM = "extra_list_item"
    }

    private lateinit var detailTextView: TextView
    private lateinit var detailImageView: ImageView
    private lateinit var detailLikeStatusTextView: TextView
    private lateinit var detailLikeButton: ImageView
    private lateinit var detailTextScrollView: ScrollView // <-- 绑定 ScrollView

    private var currentItem: ListItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 绑定 UI 组件
        detailTextScrollView = findViewById(R.id.detailTextScrollView) // <-- 绑定
        detailTextView = findViewById(R.id.detailTextView)
        detailImageView = findViewById(R.id.detailImageView)
        detailLikeStatusTextView = findViewById(R.id.detailLikeStatusTextView)
        detailLikeButton = findViewById(R.id.detailLikeButton)

        currentItem = intent.getParcelableExtra(EXTRA_LIST_ITEM)

        currentItem?.let { item ->
            when (item) {
                is ListItem.TextItem -> {
                    detailTextView.text = item.textContent
                    detailTextScrollView.visibility = View.VISIBLE // 显示 ScrollView
                    detailImageView.visibility = View.GONE
                }
                is ListItem.ImageItem -> {
                    detailImageView.load(item.imageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_background)
                        error(android.R.drawable.ic_menu_gallery)
                    }
                    detailImageView.visibility = View.VISIBLE
                    detailTextScrollView.visibility = View.GONE // 隐藏 ScrollView
                }
            }
            updateLikeStatusAndButton(item.isLiked)
        } ?: run {
            detailTextView.text = "错误：未找到列表项数据"
            detailTextScrollView.visibility = View.VISIBLE // 显示 ScrollView 以显示错误
            detailImageView.visibility = View.GONE
            detailLikeButton.isEnabled = false
        }

        detailLikeButton.setOnClickListener {
            currentItem?.let { item ->
                val updatedItem = when (item) {
                    is ListItem.TextItem -> item.copy(isLiked = !item.isLiked)
                    is ListItem.ImageItem -> item.copy(isLiked = !item.isLiked)
                }
                currentItem = updatedItem
                updateLikeStatusAndButton(currentItem?.isLiked ?: false)
            }
        }
    }

    private fun updateLikeStatusAndButton(isLiked: Boolean) {
        detailLikeStatusTextView.text = "点赞状态: ${if (isLiked) "已点赞" else "待点赞"}"
        detailLikeStatusTextView.setTextColor(
            resources.getColor(
                if (isLiked) android.R.color.holo_green_dark
                else android.R.color.darker_gray
            )
        )
        detailLikeButton.setImageResource(
            if (isLiked) R.drawable.ic_like_filled
            else R.drawable.ic_like_border
        )
    }

    override fun finish() {
        currentItem?.let { item ->
            val resultIntent = Intent().apply {
                putExtra(EXTRA_LIST_ITEM, item)
            }
            setResult(Activity.RESULT_OK, resultIntent)
        }
        super.finish()
    }
}
