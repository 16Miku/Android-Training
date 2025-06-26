package com.example.day11_kotlinrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load // 导入 Coil 的 load 扩展函数

/**
 * RecyclerView 的适配器，用于显示不同类型的列表项 (文本和图片)。
 * 使用 `ListAdapter` 简化列表更新，自动处理 `DiffUtil`。
 *
 * @param onItemClicked 一个 Lambda 表达式，当列表项被点击时调用，传入被点击的 `ListItem`。
 * @param onLikeClicked 一个 Lambda 表达式，当点赞按钮被点击时调用，传入被点击的 `ListItem`。
 */
class MyListAdapter(
    private val onItemClicked: (ListItem) -> Unit,
    private val onLikeClicked: (ListItem) -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(ListItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_TEXT = 1
        private const val VIEW_TYPE_IMAGE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.TextItem -> VIEW_TYPE_TEXT
            is ListItem.ImageItem -> VIEW_TYPE_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val view = inflater.inflate(R.layout.item_text, parent, false)
                TextViewHolder(view, onLikeClicked)
            }
            VIEW_TYPE_IMAGE -> {
                val view = inflater.inflate(R.layout.item_image, parent, false)
                ImageViewHolder(view, onLikeClicked)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        // 设置整个列表项的点击监听器 (跳转详情页)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }

        // 根据 ViewHolder 的具体类型绑定数据
        when (holder) {
            is TextViewHolder -> holder.bind(item as ListItem.TextItem)
            is ImageViewHolder -> holder.bind(item as ListItem.ImageItem)
        }
    }

    /**
     * 文本列表项的 ViewHolder。
     * @param onLikeClicked 点赞按钮点击回调。
     */
    class TextViewHolder(itemView: View, private val onLikeClicked: (ListItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textViewContent: TextView = itemView.findViewById(R.id.textViewContent)
        private val imageViewLikeButton: ImageView = itemView.findViewById(R.id.imageViewLikeButton)

        fun bind(item: ListItem.TextItem) {
            textViewContent.text = item.textContent

            // 根据 isLiked 状态更新点赞按钮图标
            updateLikeButtonIcon(item.isLiked)

            // 设置点赞按钮的点击监听器
            imageViewLikeButton.setOnClickListener {
                onLikeClicked(item) // 触发点赞回调
            }
        }

        // 更新点赞按钮图标的方法
        private fun updateLikeButtonIcon(isLiked: Boolean) {
            imageViewLikeButton.setImageResource(
                if (isLiked) R.drawable.ic_like_filled
                else R.drawable.ic_like_border
            )
        }
    }

    /**
     * 图片列表项的 ViewHolder。
     * @param onLikeClicked 点赞按钮点击回调。
     */
    class ImageViewHolder(itemView: View, private val onLikeClicked: (ListItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageViewContent: ImageView = itemView.findViewById(R.id.imageViewContent)
        private val imageViewLikeButton: ImageView = itemView.findViewById(R.id.imageViewLikeButton)

        fun bind(item: ListItem.ImageItem) {
            imageViewContent.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(android.R.drawable.ic_menu_gallery)
            }

            // 根据 isLiked 状态更新点赞按钮图标
            updateLikeButtonIcon(item.isLiked)

            // 设置点赞按钮的点击监听器
            imageViewLikeButton.setOnClickListener {
                onLikeClicked(item) // 触发点赞回调
            }
        }

        // 更新点赞按钮图标的方法
        private fun updateLikeButtonIcon(isLiked: Boolean) {
            imageViewLikeButton.setImageResource(
                if (isLiked) R.drawable.ic_like_filled
                else R.drawable.ic_like_border
            )
        }
    }

    /**
     * DiffUtil.ItemCallback 的实现，用于高效地更新列表。
     * 当数据发生变化时，它会计算出最小的更新操作，而不是刷新整个列表。
     */
    class ListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
        /**
         * 检查两个列表项是否代表同一个 Item (通常通过唯一 ID 判断)。
         * 如果 ID 相同，即使内容不同，也认为是同一个 Item。
         */
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * 检查两个列表项的内容是否相同 (在 areItemsTheSame 返回 true 的前提下)。
         * 如果内容相同，RecyclerView 不会重新绑定 ViewHolder。
         */
        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            // isLiked 现在是 data class 主构造函数的一部分，
            // oldItem == newItem 会自动比较 isLiked 属性。
            return oldItem == newItem
        }
    }
}
