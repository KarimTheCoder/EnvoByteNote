package com.example.envobytenote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envobytenote.util.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor( private val pref: ThemePreference) : ViewModel() {


    val isDarkTheme = pref.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleTheme() {
        viewModelScope.launch {
            pref.saveTheme(!isDarkTheme.value)
        }
    }


}



