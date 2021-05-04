package com.krm0219.mooda.util

class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {

        return if (hasBeenHandled) {
            null
        } else {

            hasBeenHandled = true
            content
        }
    }
}