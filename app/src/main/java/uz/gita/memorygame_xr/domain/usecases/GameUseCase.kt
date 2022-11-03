package uz.gita.memorygame_xr.domain.usecases

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level

interface GameUseCase {

    fun getDataByLevel(level: Level): Flow<List<GameModel>>

    fun saveScores(level: Level, time: String, score:Int)
}