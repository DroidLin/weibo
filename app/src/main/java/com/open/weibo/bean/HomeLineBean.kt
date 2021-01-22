package com.open.weibo.bean

data class HomeLineResult(
    val statuses: List<Statuses>? = null,
    val ad: List<Any>? = null,
    val advertises: List<Any>? = null,
    val has_unread: Int = 0,
    val hasvisible: Boolean,
    val interval: Int = 0,
    val max_id: Long = 0,
    val max_id_str: String? = null,
    val next_cursor: Long = 0,
    val next_cursor_str: String? = null,
    val previous_cursor: Int = 0,
    val previous_cursor_str: String? = null,
    val since_id: Long = 0,
    val since_id_str: String? = null,
    val total_number: Int = 0,
    val uve_blank: Int = 0
)

data class Statuses(
    val alchemy_params: AlchemyParams?,
    val annotations: List<Annotation>?,
    val attitudes_count: Int = 0,
    val biz_feature: Long = 0,
    val bmiddle_pic: String? = null,
    val can_edit: Boolean = false,
    val cardid: String? = null,
    val comment_manage_info: CommentManageInfo?,
    val comments_count: Int = 0,
    val content_auth: Int = 0,
    val created_at: String? = null,
    val darwin_tags: List<Any>?,
    val edit_at: String? = null,
    val edit_count: Int = 0,
    val enable_comment_guide: Boolean = false,
    val favorited: Boolean = false,
    val geo: Any?,
    val gif_ids: String? = null,
    val hasActionTypeCard: Int = 0,
    val hide_flag: Int = 0,
    val hot_weibo_tags: List<Any>?,
    val id: Long = 0,
    val idstr: String? = null,
    val in_reply_to_screen_name: String? = null,
    val in_reply_to_status_id: String? = null,
    val in_reply_to_user_id: String? = null,
    val isLongText: Boolean = false,
    val is_paid: Boolean = false,
    val is_show_bulletin: Int = 0,
    val mblog_vip_type: Int = 0,
    val mblogtype: Int = 0,
    val mid: String? = null,
    val mlevel: Int = 0,
    val more_info_type: Int = 0,
    val number_display_strategy: NumberDisplayStrategy?,
    val original_pic: String? = null,
    val pending_approval_count: Int = 0,
    val picStatus: String? = null,
    val pic_num: Int = 0,
    val pic_urls: List<PicUrl>?,
    val positive_recom_flag: Int = 0,
    val reposts_count: Int = 0,
    val reward_exhibition_type: Int = 0,
    val reward_scheme: String? = null,
    val rid: String? = null,
    val show_additional_indication: Int = 0,
    val source: String? = null,
    val source_allowclick: Int = 0,
    val source_type: Int = 0,
    val text: String? = null,
    val textLength: Int = 0,
    val text_tag_tips: List<Any>?,
    val thumbnail_pic: String? = null,
    val truncated: Boolean = false,
    val user: User?,
    val userType: Int = 0,
    val version: Int = 0,
    val visible: Visible
)

data class AlchemyParams(
    val comment_guide_type: Int = 0,
    val ug_red_envelope: Boolean = false
)

data class Annotation(
    val client_mblogid: String? = null,
    val mapi_request: Boolean = false
)

data class CommentManageInfo(
    val approval_comment_type: Int = 0,
    val comment_permission_type: Int = 0
)

data class NumberDisplayStrategy(
    val apply_scenario_flag: Int = 0,
    val display_text: String? = null,
    val display_text_min_number: Int = 0
)

data class PicUrl(
    val thumbnail_pic: String? = null
) {
    companion object {
        const val thumbnail_tag = "thumbnail"
        const val bmiddle_tag = "bmiddle"
        const val large_tag = "large"
    }

    fun thumbToMidPic(): String? {
        val pic = thumbnail_pic ?: return null
        return pic.replace(thumbnail_tag, bmiddle_tag)
    }

    fun thumbToLarge(): String? {
        val pic = thumbnail_pic ?: return null
        return pic.replace(thumbnail_tag, large_tag)
    }
}

data class User(
    val allow_all_act_msg: Boolean = false,
    val allow_all_comment: Boolean = false,
    val avatar_hd: String? = null,
    val avatar_large: String? = null,
    val bi_followers_count: Int = 0,
    val block_app: Int = 0,
    val block_word: Int = 0,
    val cardid: String? = null,
    val city: String? = null,
    val `class`: Int = 0,
    val cover_image_phone: String? = null,
    val created_at: String? = null,
    val credit_score: Int = 0,
    val description: String? = null,
    val domain: String? = null,
    val favourites_count: Int = 0,
    val follow_me: Boolean = false,
    val followers_count: Int = 0,
    val following: Boolean = false,
    val friends_count: Int = 0,
    val gender: String? = null,
    val geo_enabled: Boolean = false,
    val has_service_tel: Boolean = false,
    val id: Long = 0,
    val idstr: String? = null,
    val insecurity: Insecurity?,
    val is_guardian: Int = 0,
    val is_teenager: Int = 0,
    val is_teenager_list: Int = 0,
    val lang: String? = null,
    val like: Boolean = false,
    val like_me: Boolean = false,
    val live_status: Int = 0,
    val location: String? = null,
    val mbrank: Int = 0,
    val mbtype: Int = 0,
    val name: String? = null,
    val online_status: Int = 0,
    val pagefriends_count: Int = 0,
    val pc_new: Int = 0,
    val planet_video: Int = 0,
    val profile_image_url: String? = null,
    val profile_url: String? = null,
    val province: String? = null,
    val ptype: Int = 0,
    val remark: String? = null,
    val screen_name: String? = null,
    val special_follow: Boolean = false,
    val star: Int = 0,
    val statuses_count: Int = 0,
    val story_read_state: Int = 0,
    val tab_manage: String? = null,
    val urank: Int = 0,
    val url: String? = null,
    val user_ability: Int = 0,
    val vclub_member: Int = 0,
    val verified: Boolean = false,
    val verified_contact_email: String? = null,
    val verified_contact_mobile: String? = null,
    val verified_contact_name: String? = null,
    val verified_detail: VerifiedDetail?,
    val verified_level: Int = 0,
    val verified_reason: String? = null,
    val verified_reason_modified: String? = null,
    val verified_reason_url: String? = null,
    val verified_source: String? = null,
    val verified_source_url: String? = null,
    val verified_state: Int = 0,
    val verified_trade: String? = null,
    val verified_type: Int = 0,
    val verified_type_ext: Int = 0,
    val video_mark: Int = 0,
    val video_play_count: Int = 0,
    val video_status_count: Int = 0,
    val weihao: String? = null
)

data class Visible(
    val list_id: Int = 0,
    val type: Int = 0
)

data class Insecurity(
    val sexual_content: Boolean = false
)

data class VerifiedDetail(
    val custom: Int = 0,
    val `data`: List<Data>
)

data class Data(
    val desc: String? = null,
    val key: Int = 0,
    val weight: Int = 0
)