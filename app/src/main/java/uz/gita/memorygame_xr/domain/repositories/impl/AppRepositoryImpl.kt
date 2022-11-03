package uz.gita.memorygame_xr.domain.repositories.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.memorygame_xr.R
import uz.gita.memorygame_xr.data.MySharedPref
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level
import uz.gita.memorygame_xr.domain.repositories.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(val sharedPref: MySharedPref) : AppRepository {

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

    override fun saveScoresByLevel(level: Level, time: String, score: Int) {

        Log.d("TTT", "REPOSITORY $time")

        when (level.x) {

            3 -> {

                val one = sharedPref.easy1.split('#').first().toInt()
                val two = sharedPref.easy2.split('#').first().toInt()
                val three = sharedPref.easy3.split('#').first().toInt()


                if (score>one){
                    sharedPref.easy1 = "$score#$time"
                }
                else if (score>two){
                    sharedPref.easy2 = "$score#$time"
                }
                else if (score>three){
                    sharedPref.easy3 = "$score#$time"
                }
            }
            4 -> {
                val one = sharedPref.medium1.split('#').first().toInt()
                val two = sharedPref.medium2.split('#').first().toInt()
                val three = sharedPref.medium3.split('#').first().toInt()


                if (score>one){
                    sharedPref.medium1 = "$score#$time"
                }
                else if (score>two){
                    sharedPref.medium2 = "$score#$time"
                }
                else if (score>three){
                    sharedPref.medium3 = "$score#$time"
                }
            }

            5 -> {
                val one = sharedPref.hard1.split('#').first().toInt()
                val two = sharedPref.hard2.split('#').first().toInt()
                val three = sharedPref.hard3.split('#').first().toInt()


                if (score>one){
                    sharedPref.hard1 = "$score#$time"
                }
                else if (score>two){
                    sharedPref.hard2 = "$score#$time"
                }
                else if (score>three){
                    sharedPref.hard3 = "$score#$time"
                }
            }
        }
    }

}