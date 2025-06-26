package com.example.day11_kotlinrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 密封类 `ListItem` 用于表示 RecyclerView 中的两种不同类型的列表项。
 *
 * 每个列表项都包含一个唯一的 `id`。
 * `isLiked` 用于表示该项是否被点赞过，现在作为每个具体数据类的属性。
 *
 * 所有子类都实现 `Parcelable` 接口，以便在 Activity 之间传递。
 */
sealed class ListItem : Parcelable {

    abstract val id: String
    // 声明一个抽象的 isLiked 属性。所有 ListItem 的子类都必须实现它。
    abstract var isLiked: Boolean

    /**
     * 文本类型的列表项。
     * @param id 唯一标识符。
     * @param textContent 文本内容。
     * @param isLiked 该项的点赞状态，默认为 false。
     */
    @Parcelize
    data class TextItem(
        override val id: String,
        val textContent: String,
        override var isLiked: Boolean = false // <-- 实现抽象属性
    ) : ListItem()

    /**
     * 图片类型的列表项。
     * @param id 唯一标识符。
     * @param imageUrl 图片的 URL。
     * @param isLiked 该项的点赞状态，默认为 false。
     */
    @Parcelize
    data class ImageItem(
        override val id: String,
        val imageUrl: String,
        override var isLiked: Boolean = false // <-- 实现抽象属性
    ) : ListItem()
}
