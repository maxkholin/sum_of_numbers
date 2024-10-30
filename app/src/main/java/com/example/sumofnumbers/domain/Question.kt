package com.example.sumofnumbers.domain

data class Question (
    val sum: Int,
    val firstVisibleNumber: Int,
    val options: List<Int>
)