package uz.xsoft.lessonmemorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level

interface GameViewModel {
    val gameModeLiveData: LiveData<List<GameModel>>

    val counter: LiveData<Int>

    fun getDataByLevel(level: Level)

    fun btnClicked(state: Boolean, position: Int)

    fun incr()

    fun decr()
}