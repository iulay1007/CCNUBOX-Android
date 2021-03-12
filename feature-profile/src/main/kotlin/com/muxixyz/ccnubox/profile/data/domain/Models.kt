package com.muxixyz.ccnubox.profile.data.domain

data class User(
    var id: String,
    var nickname: String,
    var sid: String,
    var loggedIn: Boolean, // 是否登录，暂时允许匿名使用
    var currentCourseTableId: String // 当前使用的课程表实例id
)