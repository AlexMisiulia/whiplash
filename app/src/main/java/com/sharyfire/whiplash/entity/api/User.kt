package com.sharyfire.whiplash.entity.api

data class User(
    val accepted_tos: Boolean = false,
    val bio: String = "",
    val first_name: String = "",
    val id: String = "",
    val instagram_username: String = "",
    val last_name: String = "",
    val links: LinksX = LinksX(),
    val location: String = "",
    val name: String = "",
    val portfolio_url: String = "",
    val profile_image: ProfileImage = ProfileImage(),
    val total_collections: Int = 0,
    val total_likes: Int = 0,
    val total_photos: Int = 0,
    val twitter_username: String = "",
    val updated_at: String = "",
    val username: String = ""
)