package com.atiwari.stateinfocenter.models

data class StateData(
    val counties: Int,
    val detail: String,
    val population: Int,
    val state: String,
    var selector: Boolean
)