package uz.gita.memorygame_xr.domain.repositories.impl

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

    override fun saveScoresByLevel(level: Level, time: Long, score: Int) {
        when (level.x) {

            3 -> {
                val list = sharedPref.scoreEasy.split('#')
                val parseList = ArrayList<Int>()
                list.forEach {
                    if (it.isNotEmpty()) {
                        parseList.add(it.toInt())
                    }
                }
                parseList.add(score)
                parseList.sort()

                sharedPref.scoreEasy = parseList.subList(0,2).joinToString ("#")

                val listTime = sharedPref.timeEasy.split("#")
                val parseListTime = ArrayList<Long>()
                listTime.forEach {
                    if (it.isNotEmpty()) {
                        parseListTime.add(it.toLong())
                    }
                }
                parseListTime.add(time)
                parseListTime.sort()

                sharedPref.timeEasy = parseList.subList(0,2).joinToString ("#")

            }
            4 -> {
                val list = sharedPref.scoreMedium.split('#')
                val parseList = ArrayList<Int>()
                list.forEach {
                    if (it.isNotEmpty()) {
                        parseList.add(it.toInt())
                    }
                }
                parseList.add(score)
                parseList.sort()

                sharedPref.scoreMedium = parseList.subList(0,2).joinToString ("#")

                val listTime = sharedPref.timeMedium.split("#")
                val parseListTime = ArrayList<Long>()
                listTime.forEach {
                    if (it.isNotEmpty()) {
                        parseListTime.add(it.toLong())
                    }
                }
                parseListTime.add(time)
                parseListTime.sort()

                sharedPref.timeMedium = parseList.subList(0,2).joinToString ("#")
            }
            5 -> {
                val list = sharedPref.scoreHard.split('#')
                val parseList = ArrayList<Int>()
                list.forEach {
                    if (it.isNotEmpty()) {
                        parseList.add(it.toInt())
                    }
                }
                parseList.add(score)
                parseList.sort()

                sharedPref.scoreHard = parseList.subList(0,2).joinToString ("#")

                val listTime = sharedPref.timeHard.split("#")
                val parseListTime = ArrayList<Long>()
                listTime.forEach {
                    if (it.isNotEmpty()) {
                        parseListTime.add(it.toLong())
                    }
                }
                parseListTime.add(time)
                parseListTime.sort()

                sharedPref.timeHard = parseList.subList(0,2).joinToString ("#")
            }
        }
    }

    override fun getEasyTime(): String {
        return sharedPref.timeEasy
    }

    override fun getEasyScore(): String {
        return sharedPref.scoreEasy
    }

    override fun getMediumTime(): String {
       return sharedPref.timeMedium
    }

    override fun getMediumScore(): String {
        return sharedPref.scoreMedium
    }

    override fun getHardTime(): String {
        return sharedPref.timeHard
    }

    override fun getHardScore(): String {
        return sharedPref.scoreHard
    }


}