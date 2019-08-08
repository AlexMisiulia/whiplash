package com.sharyfire.whiplash.feature.photolist

import com.sharyfire.whiplash.feature.shared.AdapterItem

data class DisplayablePhoto(
    val url: String?,
    val id: String
) : AdapterItem