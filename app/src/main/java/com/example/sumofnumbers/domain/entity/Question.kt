package com.example.sumofnumbers.domain.entity

data class Question (
    val sum: Int,
    val firstVisibleNumber: Int,
    val options: List<Int>
)