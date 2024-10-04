// CharacterViewModelFactory.kt
package com.omccolgan.requiemcharactersheet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CharacterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { // Removed '?' after ViewModel
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
