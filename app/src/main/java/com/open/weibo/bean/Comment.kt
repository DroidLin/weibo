package com.open.weibo.bean

data class CommentResult(
    val comments: List<Comment>? = null,
    val hasvisible: Boolean? = null,
    val marks: List<Any>? = null,
    val max_id: Long? = null,
    val max_id_str: String? = null,
    val next_cursor: Long? = null,
    val next_cursor_str: String? = null,
    val previous_cursor: Long? = null,
    val previous_cursor_str: String? = null,
    val since_id: Long? = null,
    val since_id_str: String? = null,
    val status: Status? = null,
    val total_number: Long? = null
)

data class Comment(
    val comment_badge: List<CommentBadge>? = null,
    val created_at: String? = null,
    val disable_reply: Long? = null,
    val floor_number: Long? = null,
    val id: Long? = null,
    val idstr: String? = null,
    val mid: String? = null,
    val readtimetype: String? = null,
    val reply_comment: ReplyComment? = null,
    val reply_original_text: String? = null,
    val rootid: Long? = null,
    val rootidstr: String? = null,
    val status: Status? = null,
    val text: String? = null,
    val user: User? = null
)

data class CommentBadge(
    val actionlog: Actionlog? = null,
    val length: Double? = null,
    val name: String? = null,
    val pic_url: String? = null,
    val scheme: String? = null
)

data class ReplyComment(
    val comment_badge: List<CommentBadge>? = null,
    val created_at: String? = null,
    val disable_reply: Long? = null,
    val floor_number: Long? = null,
    val id: Long? = null,
    val idstr: String? = null,
    val mid: String? = null,
    val rootid: Long? = null,
    val rootidstr: String? = null,
    val text: String? = null,
    val user: User? = null
)

data class Actionlog(
    val act_code: String? = null,
    val ext: String? = null
)
