package uz.xsoft.lessonmemorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.xsoft.lessonmemorygame.data.models.Level

interface LevelViewModel {
    val openGameLiveData: LiveData<Level>

    fun openGame(level: Level)
}