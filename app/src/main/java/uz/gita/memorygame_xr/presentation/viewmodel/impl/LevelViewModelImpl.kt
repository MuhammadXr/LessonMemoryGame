package uz.gita.memorygame_xr.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygame_xr.data.models.Level
import uz.gita.memorygame_xr.presentation.viewmodel.LevelViewModel
import javax.inject.Inject

@HiltViewModel
class LevelViewModelImpl @Inject constructor() : LevelViewModel, ViewModel() {

    override val openGameLiveData: MutableLiveData<Level> = MutableLiveData()
    override val openAboutScreen = MutableLiveData<Unit>()
    override val openScoresScreen = MutableLiveData<Unit>()

    override fun openGame(level: Level) {
        openGameLiveData.value = level
    }

    override fun openAbout() {
        openAboutScreen.value = Unit
    }

    override fun openScores() {
        openScoresScreen.value = Unit
    }
}