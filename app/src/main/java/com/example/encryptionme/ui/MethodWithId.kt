package com.example.encryptionme.ui

data class MethodWithId(
    val id: Int,
    val method: String,
    var isSelected: Boolean) {

    override fun toString(): String {
        return method
    }
}