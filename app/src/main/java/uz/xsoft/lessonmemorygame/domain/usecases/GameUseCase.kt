package uz.xsoft.lessonmemorygame.domain.usecases

import kotlinx.coroutines.flow.Flow
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level

interface GameUseCase {

    fun getDataByLevel(level: Level): Flow<List<GameModel>>
}