package uz.xsoft.lessonmemorygame.domain.repositories.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.xsoft.lessonmemorygame.R
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level
import uz.xsoft.lessonmemorygame.domain.repositories.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor() : AppRepository {
    private val data = ArrayList<GameModel>()

    init {
        load()
    }

    private fun load() {
        data.add(GameModel(1, R.drawable.acorn_96x96))
        data.add(GameModel(2, R.drawable.apple_96x96))
        data.add(GameModel(3, R.drawable.apple_green_96x96))
        data.add(GameModel(4, R.drawable.banana_96x96))
        data.add(GameModel(5, R.drawable.cherry_96x96))
        data.add(GameModel(6, R.drawable.egg_96x96))
        data.add(GameModel(7, R.drawable.eggs_96x96))
        data.add(GameModel(8, R.drawable.green_grape_96x96))
        data.add(GameModel(9, R.drawable.hearth_96x96))
        data.add(GameModel(10, R.drawable.hearth_pink_96x96))
        data.add(GameModel(11, R.drawable.leaf_green_96x96))
        data.add(GameModel(12, R.drawable.leaf_yellow_96x96))
        data.add(GameModel(13, R.drawable.lemon_96x96))
        data.add(GameModel(14, R.drawable.orange_96x96))
        data.add(GameModel(15, R.drawable.peanut_96x96))
        data.add(GameModel(16, R.drawable.peer_96x96))
        data.add(GameModel(17, R.drawable.pineapple_96x96))
        data.add(GameModel(18, R.drawable.plum_96x96))
        data.add(GameModel(19, R.drawable.potato_96x96))
        data.add(GameModel(20, R.drawable.red_grape_96x96))
        data.add(GameModel(21, R.drawable.strawberry_96x96))
        data.add(GameModel(22, R.drawable.walnut_96x96))
        data.add(GameModel(23, R.drawable.watermelon_96x96))
    }


    override fun loadDataByLevel(level: Level): Flow<List<GameModel>> = flow {
        val count = level.x * level.y
        data.shuffle()
        emit(data.subList(0, count / 2))
    }
}