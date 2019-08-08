package com.sharyfire.whiplash.entity.api

data class Sponsorship(
    val impressions_id: String = "",
    val sponsor: Sponsor = Sponsor(),
    val tagline: String = ""
)