package uz.gita.memorygame_xr.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.memorygame_xr.data.models.Level

interface LevelViewModel {
    val openGameLiveData: LiveData<Level>
    val openAboutScreen: LiveData<Unit>
    val openScoresScreen: LiveData<Unit>

    fun openGame(level: Level)

    fun openAbout()

    fun openScores()
}