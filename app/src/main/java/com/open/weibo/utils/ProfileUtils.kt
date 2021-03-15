package com.open.weibo.utils

import com.open.core_base.database.instance.DBInstance
import com.open.core_network.impl.GsonAdapter
import com.open.core_network.impl.MoshiConverterAdapter
import com.open.weibo.bean.User
import com.open.weibo.database.DatabaseInstance
import com.open.weibo.database.bean.Profile
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileUtils {

    companion object {
        private var mInstance: ProfileUtils? = null

        @JvmStatic
        fun getInstance(): ProfileUtils {
            if (mInstance == null) {
                synchronized(ProfileUtils::class.java) {
                    if (mInstance == null) {
                        mInstance = ProfileUtils()
                    }
                }
            }
            return mInstance!!
        }
    }

    var profile: Profile? = null
    var userProfile: User? = null

    fun init() {
        runBlocking {
            loadUserProfile()
        }
    }

    suspend fun saveUserProfile(p0: Oauth2AccessToken): Boolean {
        profile = Profile.parseProfile(p0)
        val profileDao = DBInstance.getInstance(
            DatabaseInstance::class.java,
            DatabaseInstance.dbName
        ).profileDao
        return try {
            profileDao.delete()
            profileDao.saveProfile(profile!!)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loadUserProfile() {
        if (profile == null) {
            val profileDao = DBInstance.getInstance(
                DatabaseInstance::class.java,
                DatabaseInstance.dbName
            ).profileDao
            profile = try {
                profileDao.getProfile()[0]
            } catch (e: Throwable) {
                null
            }
        }

        if (userProfile == null) {
            userProfile = HPreferenceUtils.getDetailProfile()
        }
    }

    fun saveUserDetail(profileString: String) {
        userProfile = MoshiConverterAdapter.getInstance().parseString(profileString, User::class.java)
        HPreferenceUtils.saveDetailProfileString(profileString)
    }
}