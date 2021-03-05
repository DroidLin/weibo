package com.open.weibo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.open.weibo.database.bean.Emoji
import com.open.weibo.database.bean.Profile
import com.open.weibo.database.dao.IEmojiDao
import com.open.weibo.database.dao.IProfileDao

@Database(entities = [Profile::class, Emoji::class], version = 1, exportSchema = false)
abstract class DatabaseInstance : RoomDatabase() {
    companion object {
        const val dbName: String = "Database"
    }

    abstract val profileDao: IProfileDao
    abstract val emojiDao: IEmojiDao
}