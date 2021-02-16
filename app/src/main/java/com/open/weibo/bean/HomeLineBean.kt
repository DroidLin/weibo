package com.open.weibo.bean

import java.io.Serializable

data class HomeLineResult(
    val statuses: List<Statuses>? = null,
    val ad: List<Any>? = null,
    val advertises: List<Any>? = null,
    val has_unread: Long = 0,
    val hasvisible: Boolean = false,
    val interval: Long = 0,
    val max_id: Long = 0,
    val max_id_str: String? = null,
    val next_cursor: Long = 0,
    val next_cursor_str: String? = null,
    val previous_cursor: Long = 0,
    val previous_cursor_str: String? = null,
    val since_id: Long = 0,
    val since_id_str: String? = null,
    val total_number: Long = 0,
    val uve_blank: Long = 0
): Serializable

data class Statuses(
    val alchemy_params: AlchemyParams?,
    val annotations: List<Annotation>?,
    val attitudes_count: Long = 0,
    val biz_feature: Long = 0,
    val bmiddle_pic: String? = null,
    val can_edit: Boolean = false,
    val cardid: String? = null,
    val comment_manage_info: CommentManageInfo?,
    val comments_count: Long = 0,
    val content_auth: Long = 0,
    val created_at: String? = null,
    val darwin_tags: List<Any>?,
    val edit_at: String? = null,
    val edit_count: Long = 0,
    val enable_comment_guide: Boolean = false,
    val favorited: Boolean = false,
    val geo: Any?,
    val gif_ids: String? = null,
    val hasActionTypeCard: Long = 0,
    val hide_flag: Long = 0,
    val hot_weibo_tags: List<Any>?,
    val id: Long = 0,
    val idstr: String? = null,
    val in_reply_to_screen_name: String? = null,
    val in_reply_to_status_id: String? = null,
    val in_reply_to_user_id: String? = null,
    val isLongText: Boolean = false,
    val is_paid: Boolean = false,
    val is_show_bulletin: Long = 0,
    val mblog_vip_type: Long = 0,
    val mblogtype: Long = 0,
    val mid: String? = null,
    val mlevel: Long = 0,
    val more_info_type: Long = 0,
    val number_display_strategy: NumberDisplayStrategy?,
    val original_pic: String? = null,
    val pending_approval_count: Long = 0,
    val picStatus: String? = null,
    val pic_num: Long = 0,
    val pic_urls: ArrayList<PicUrl>?,
    val positive_recom_flag: Long = 0,
    val reposts_count: Long = 0,
    val retweeted_status: Statuses? = null,
    val reward_exhibition_type: Long = 0,
    val reward_scheme: String? = null,
    val rid: String? = null,
    val show_additional_indication: Long = 0,
    val source: String? = null,
    val source_allowclick: Long = 0,
    val source_type: Long = 0,
    val text: String? = null,
    val textLength: Long = 0,
    val text_tag_tips: List<Any>?,
    val thumbnail_pic: String? = null,
    val truncated: Boolean = false,
    val user: User?,
    val userType: Long = 0,
    val version: Long = 0,
    val visible: Visible
): Serializable

data class AlchemyParams(
    val comment_guide_type: Long = 0,
    val ug_red_envelope: Boolean = false
): Serializable

data class Annotation(
    val client_mblogid: String? = null,
    val mapi_request: Boolean = false
): Serializable

data class CommentManageInfo(
    val approval_comment_type: Long = 0,
    val comment_permission_type: Long = 0
): Serializable

data class NumberDisplayStrategy(
    val apply_scenario_flag: Long = 0,
    val display_text: String? = null,
    val display_text_min_number: Long = 0
): Serializable

data class PicUrl(
    val thumbnail_pic: String? = null
) : Serializable {
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
    val bi_followers_count: Long = 0,
    val block_app: Long = 0,
    val block_word: Long = 0,
    val cardid: String? = null,
    val city: String? = null,
    val `class`: Long = 0,
    val cover_image_phone: String? = null,
    val created_at: String? = null,
    val credit_score: Long = 0,
    val description: String? = null,
    val domain: String? = null,
    val favourites_count: Long = 0,
    val follow_me: Boolean = false,
    val followers_count: Long = 0,
    val following: Boolean = false,
    val friends_count: Long = 0,
    val gender: String? = null,
    val geo_enabled: Boolean = false,
    val has_service_tel: Boolean = false,
    val id: Long = 0,
    val idstr: String? = null,
    val insecurity: Insecurity?,
    val is_guardian: Long = 0,
    val is_teenager: Long = 0,
    val is_teenager_list: Long = 0,
    val lang: String? = null,
    val like: Boolean = false,
    val like_me: Boolean = false,
    val live_status: Long = 0,
    val location: String? = null,
    val mbrank: Long = 0,
    val mbtype: Long = 0,
    val name: String? = null,
    val online_status: Long = 0,
    val pagefriends_count: Long = 0,
    val pc_new: Long = 0,
    val planet_video: Long = 0,
    val profile_image_url: String? = null,
    val profile_url: String? = null,
    val province: String? = null,
    val ptype: Long = 0,
    val remark: String? = null,
    val screen_name: String? = null,
    val special_follow: Boolean = false,
    val star: Long = 0,
    val statuses_count: Long = 0,
    val story_read_state: Long = 0,
    val tab_manage: String? = null,
    val urank: Long = 0,
    val url: String? = null,
    val user_ability: Long = 0,
    val vclub_member: Long = 0,
    val verified: Boolean = false,
    val verified_contact_email: String? = null,
    val verified_contact_mobile: String? = null,
    val verified_contact_name: String? = null,
    val verified_detail: VerifiedDetail?,
    val verified_level: Long = 0,
    val verified_reason: String? = null,
    val verified_reason_modified: String? = null,
    val verified_reason_url: String? = null,
    val verified_source: String? = null,
    val verified_source_url: String? = null,
    val verified_state: Long = 0,
    val verified_trade: String? = null,
    val verified_type: Long = 0,
    val verified_type_ext: Long = 0,
    val video_mark: Long = 0,
    val video_play_count: Long = 0,
    val video_status_count: Long = 0,
    val weihao: String? = null
) : Serializable{

    companion object {
        const val spKey = "profile"
    }
}

data class Visible(
    val list_id: Long = 0,
    val type: Long = 0
): Serializable

data class Insecurity(
    val sexual_content: Boolean = false
): Serializable

data class VerifiedDetail(
    val custom: Long = 0,
    val `data`: List<Data>
): Serializable

data class Data(
    val desc: String? = null,
    val key: Long = 0,
    val weight: Long = 0
): Serializable