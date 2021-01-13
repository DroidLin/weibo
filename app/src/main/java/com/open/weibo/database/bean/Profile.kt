package com.open.weibo.database.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sina.weibo.sdk.auth.Oauth2AccessToken

@Entity(tableName = "profile")
class Profile(
    @PrimaryKey
    @ColumnInfo val uid: String = "",
    @ColumnInfo val token: String? = null,
    @ColumnInfo val refreshToken: String? = null,
    @ColumnInfo val expiresTime: Long = 0,
    @ColumnInfo val phoneNum: String? = null
) {
    fun needRefresh(): Boolean = System.currentTimeMillis() >= expiresTime

    companion object {
        fun parseProfile(token: Oauth2AccessToken): Profile {
            return Profile(
                token.uid,
                token.token,
                token.refreshToken,
                token.expiresTime,
                token.phoneNum
            )
        }

        fun newClone(profile: Profile): Profile {
            return Profile(
                profile.uid,
                profile.token,
                profile.refreshToken,
                profile.expiresTime,
                profile.phoneNum
            )
        }
    }
}