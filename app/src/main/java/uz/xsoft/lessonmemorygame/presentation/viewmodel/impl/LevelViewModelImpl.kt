package uz.xsoft.lessonmemorygame.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.xsoft.lessonmemorygame.data.models.Level
import uz.xsoft.lessonmemorygame.presentation.viewmodel.LevelViewModel
import javax.inject.Inject

@HiltViewModel
class LevelViewModelImpl @Inject constructor() : LevelViewModel, ViewModel() {

    override val openGameLiveData: MutableLiveData<Level> = MutableLiveData()

    override fun openGame(level: Level) {
        openGameLiveData.value = level
    }
}