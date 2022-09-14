package uz.xsoft.lessonmemorygame.domain.usecases.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level
import uz.xsoft.lessonmemorygame.domain.repositories.AppRepository
import uz.xsoft.lessonmemorygame.domain.usecases.GameUseCase
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
}