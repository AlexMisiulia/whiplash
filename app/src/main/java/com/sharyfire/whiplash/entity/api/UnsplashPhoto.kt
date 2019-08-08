package com.sharyfire.whiplash.entity.api

data class UnsplashPhoto(
    val alt_description: String = "",
    val categories: List<Any> = listOf(),
    val color: String = "",
    val created_at: String = "",
    val current_user_collections: List<Any> = listOf(),
    val description: Any? = Any(),
    val downloads: Int = 0,
    val exif: Exif = Exif(),
    val height: Int = 0,
    val id: String = "",
    val liked_by_user: Boolean = false,
    val likes: Int = 0,
    val links: Links = Links(),
    val sponsorship: Sponsorship = Sponsorship(),
    val updated_at: String = "",
    val urls: Urls = Urls(),
    val user: User = User(),
    val views: Int = 0,
    val width: Int = 0
)