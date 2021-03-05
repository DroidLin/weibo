package com.open.weibo.database.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class EmojiMeta(
    val emojis: List<Emoji>? = null
)

@Entity(tableName = "Emoji")
data class Emoji(
    @ColumnInfo val category: String? = null,
    @ColumnInfo val common: Boolean? = null,
    @ColumnInfo val hot: Boolean? = null,
    @ColumnInfo val icon: String? = null,
    @PrimaryKey
    @ColumnInfo val phrase: String = "",
    @ColumnInfo val picid: String? = null,
    @ColumnInfo val type: String? = null,
    @ColumnInfo val url: String? = null,
    @ColumnInfo val value: String? = null
)