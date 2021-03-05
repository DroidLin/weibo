package com.open.weibo.utils

import android.graphics.drawable.Drawable
import android.util.Log
import com.open.core_base.database.instance.DBInstance
import com.open.core_base.interfaces.IContext
import com.open.core_base.service.ServiceFacade
import com.open.core_image.impl.DefaultIconListener
import com.open.core_image_interface.interfaces.IImageLoader
import com.open.weibo.database.DatabaseInstance
import com.open.weibo.database.bean.Emoji
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentHashMap

class EmojiUtils {

    companion object {
        private var instance: EmojiUtils? = null

        @JvmStatic
        fun getInstance(): EmojiUtils {
            if (instance == null) {
                synchronized(EmojiUtils::class.java) {
                    if (instance == null) {
                        instance = EmojiUtils()
                    }
                }
            }
            return instance!!
        }
    }

    private val emojiMap: ConcurrentHashMap<String, Drawable?> = ConcurrentHashMap()

    fun init() {
        runBlocking {
            val profile = ProfileUtils.getInstance().profile
            if (profile != null) {
                val service = DBInstance.getInstance(
                    DatabaseInstance::class.java,
                    DatabaseInstance.dbName
                ).emojiDao
                val localCache = service.getEmoji()
                val realList = if (localCache.isNullOrEmpty()) {
                    val remoteEmojiList =
                        HNetworkAgent.getOfficialEmoji(false, "access_token", profile.token)
                    if (remoteEmojiList != null) {
                        service.clear()
                        service.saveEmoji(*remoteEmojiList.toTypedArray())
                    }
                    remoteEmojiList
                } else {
                    localCache
                }
                if (realList != null) {
                    val iContext = ServiceFacade.getInstance().get(IContext::class.java)
                    val imageLoader = ServiceFacade.getInstance().get(IImageLoader::class.java)
                    for (emoji in realList) {
                        imageLoader.loadAsync(
                            emoji.icon,
                            iContext.applicationContext,
                            object : DefaultIconListener() {
                                override fun onLoadSuccess(drawable: Drawable) {
                                    emojiMap[emoji.phrase] = drawable
                                }
                            })
                    }
                }
            }
        }
    }

    fun match(phrase: String?): Drawable? {
        if (phrase == null) {
            return null
        }
        return emojiMap[phrase]
    }


}

fun List<Emoji>.containsEmoji(emoji: Emoji): Emoji? {
    for (emotion in this) {
        if (emotion.phrase == emoji.phrase) {
            return emoji
        }
    }
    return null
}