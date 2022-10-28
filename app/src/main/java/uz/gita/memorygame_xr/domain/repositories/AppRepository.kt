package uz.gita.memorygame_xr.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level

interface AppRepository {
    fun loadDataByLevel(level: Level): Flow<List<GameModel>>

    fun saveScoresByLevel(level: Level, time:Long,score:Int)

    fun getEasyTime(): String
    fun getEasyScore(): String

    fun getMediumTime(): String
    fun getMediumScore(): String

    fun getHardTime(): String
    fun getHardScore(): String
}