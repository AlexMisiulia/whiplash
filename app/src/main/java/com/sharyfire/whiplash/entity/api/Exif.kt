package com.sharyfire.whiplash.entity.api

data class Exif(
    val aperture: String = "",
    val exposure_time: String = "",
    val focal_length: String = "",
    val iso: Int = 0,
    val make: String = "",
    val model: String = ""
)