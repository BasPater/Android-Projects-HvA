package com.example.level3task2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel;
import com.example.level3task2.Portal

class PortalViewModel: ViewModel() {
    val portals: MutableList<Portal> = mutableStateListOf(createSamplePortal())

    fun createSamplePortal(): Portal {
        return (Portal("Apple", "https://apple.com"))
    }

}