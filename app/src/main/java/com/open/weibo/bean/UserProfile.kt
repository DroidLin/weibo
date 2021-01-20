package com.open.weibo.bean

class UserProfile(val id: Long = 0) {
    var screen_name: String? = null
    var name: String? = null
    var province: String? = null
    var city: String? = null
    var location: String? = null
    var description: String? = null
    var url: String? = null
    var profile_image_url: String? = null
    var domain: String? = null
    var gender: String? = null
    var followers_count: Long = 0
    var friends_count: Long = 0
    var statuses_count: Long = 0
    var favourites_count: Long = 0
    var created_at: String? = null
    var following: Boolean = false
    var allow_all_act_msg: Boolean = false
    var geo_enabled: Boolean = false
    var verified: Boolean = false
    var status: Status? = null
    var allow_all_comment: Boolean = false
    var avatar_large: String? = null
    var verified_reason: String? = null
    var follow_me: Boolean = false
    var online_status: Int = 0
    var bi_followers_count: Long =0
}

class Status(val id: Long = 0) {
    var created_at: String? = null
    var text: String? = null
    var source: String? = null
    var favorited: Boolean = false
    var truncated: Boolean = false
    var in_reply_to_status_id: String? = null
    var in_reply_to_user_id: String? = null
    var in_reply_to_screen_name: String? = null
    var geo: Any? = null
    var mid: String? = null
    var annotations: Array<Any>? = null
    var reposts_count: Long = 0
    var comments_count: Long = 0
}