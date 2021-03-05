package com.open.weibo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.open.weibo.database.bean.Emoji

@Dao
interface IEmojiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmoji(vararg emoji: Emoji)

    @Query("select * from Emoji")
    suspend fun getEmoji(): List<Emoji>

    @Query("delete from Emoji")
    suspend fun clear()
}