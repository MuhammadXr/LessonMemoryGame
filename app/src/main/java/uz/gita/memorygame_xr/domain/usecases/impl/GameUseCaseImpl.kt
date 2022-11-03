package uz.gita.memorygame_xr.domain.usecases.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level
import uz.gita.memorygame_xr.domain.repositories.AppRepository
import uz.gita.memorygame_xr.domain.usecases.GameUseCase
import javax.inject.Inject

class GameUseCaseImpl @Inject constructor(private val appRepository: AppRepository) : GameUseCase {

    override fun getDataByLevel(level: Level): Flow<List<GameModel>> = flow {

        val result = ArrayList<GameModel>()
        val list = appRepository.loadDataByLevel(level)

        list.collect {
            result.addAll(it)
            result.addAll(it)
        }
        result.shuffle()
        emit(result)
    }
        .flowOn(Dispatchers.Default)

    override fun saveScores(level: Level, time: String, score: Int) {
        appRepository.saveScoresByLevel(level, time, score)
    }
}