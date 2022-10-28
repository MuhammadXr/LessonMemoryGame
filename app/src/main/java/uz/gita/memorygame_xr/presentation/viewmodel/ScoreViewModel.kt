package uz.gita.memorygame_xr.presentation.viewmodel

import androidx.lifecycle.LiveData

interface ScoreViewModel {

    val easyTime: String
    val easyScore: String

    val mediumTime: String
    val mediumScore: String

    val hardTime: String
    val hardScore: String

}