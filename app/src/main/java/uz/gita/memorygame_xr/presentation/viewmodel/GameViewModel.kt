package uz.gita.memorygame_xr.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level

interface GameViewModel {
    val gameModeLiveData: LiveData<List<GameModel>>

    val counter: LiveData<Int>

    val score: LiveData<Int>

    val time: LiveData<Long>

    val level : LiveData<Level>

    fun getDataByLevel(level: Level)

    fun btnClicked(state: Boolean, position: Int)

    fun incr()

    fun decr()

    fun saveScoreAndTime(time: String, score:Int)

    fun setScore(score: Int)
    fun setLevel(level: Level)
}