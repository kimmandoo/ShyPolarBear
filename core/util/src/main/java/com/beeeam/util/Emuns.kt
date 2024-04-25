package com.beeeam.util // ktlint-disable filename

enum class Page(val page: Int) {
    TERMS(0),
    NAME(1),
    PHONE(2),
    MAIL(3),
}

enum class FeedDetailLikeBtnType(val itemType: Int) {
    POST_LIKE_BTN(0),
    COMMENT_LIKE_BTN(1),
    REPLY_LIKE_BTN(2),
}

enum class FeedCommentViewType(val commentType: Int) {
    LOADING(0),
    NORMAL(1),
    DELETE(2),
}

enum class CommentType(val type: Int) {
    COMMENT(0),
    REPLY(1),
}

enum class CommentLoadType(val type: Int) {
    INIT(0),
    COMMENT_LOAD(1),
}

enum class FeedViewType(val viewType: Int) {
    LOADING(0),
    ITEM_HAS_IMAGES(1),
    ITEM_HAS_NO_IMAGES(2),
}

enum class WriteChangeDivider {
    WRITE,
    CHANGE,
}

enum class LikeBtnType(val itemType: Int) {
    POST_LIKE_BTN(0),
    BEST_COMMENT_LIKE_BTN(1),
}

enum class ActiveState(val state: String) {
    ACTIVE("active"),
    NONACTIVE("nonActive"),
}

enum class availableState(val state: String) {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable"),
}

enum class FeedContentType {
    POST,
    COMMENT,
}

enum class PostProperty(val state: Int) {
    MODIFY(0),
    DELETE(1),
}

enum class LoginState(val state: String) {
    LOGIN("Login"),
    LOGOUT("Logout"),
}

enum class InputState(val state: Int) {
    ACCEPT(0),
    ERROR(1),
    ON(2),
    OFF(3),
}

enum class DialogType {
    REVIEW,
    DEFAULT,
}

enum class QuizType(val type: String) {
    MULTI("MULTIPLE_CHOICE"),
    OX("OX"),
}

enum class QuizNavType {
    MULTI,
    OX,
    MAIN,
}

enum class ItemType(val state: Int) {
    ITEM(1),
    LOADING(0),
}

enum class ImageType(val type: String) {
    PROFILE("profile"),
    FEED("feed"),
}

enum class RankingItemType(val type: Int) {
    HEADER(0),
    MY_RANKING(1),
    TOTAL_ITEM(2),
    LOADING(3)
}