package uz.xsoft.lessonmemorygame.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level

interface AppRepository {
    fun loadDataByLevel(level: Level): Flow<List<GameModel>>
}