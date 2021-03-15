package com.open.weibo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.open.weibo.database.bean.Profile

@Dao
interface IProfileDao {
    @Insert
    suspend fun saveProfile(vararg profiles: Profile)

    @Query("select * from profile")
    suspend fun getProfile(): List<Profile>

    @Query("delete from profile")
    suspend fun delete()
}