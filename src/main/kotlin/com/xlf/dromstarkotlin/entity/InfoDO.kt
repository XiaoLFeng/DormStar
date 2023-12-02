package com.xlf.dromstarkotlin.entity

data class InfoDO (
    val id: Int,
    val value: String,
    val data: String? = null,
    val commit: String,
)