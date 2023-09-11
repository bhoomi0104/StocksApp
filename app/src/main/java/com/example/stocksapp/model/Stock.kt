package com.example.stocksapp.model

data class Stock(
    val name: String,
    val fullName: String,
    val value: Float,
    val change: Float
)