package com.sharyfire.whiplash.testutils

import org.mockito.Mockito.`when`

fun <T> whenever(methodCall: T) = `when`(methodCall)