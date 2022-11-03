package uz.gita.memorygame_xr.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level
import uz.gita.memorygame_xr.domain.usecases.GameUseCase
import uz.gita.memorygame_xr.presentation.viewmodel.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(private val useCase: GameUseCase) : GameViewModel, ViewModel() {

    override val gameModeLiveData: MutableLiveData<List<GameModel>> = MutableLiveData()

    override val counter = MutableLiveData<Int>(0)
    override val score = MutableLiveData<Int>(0)
    override val time = MutableLiveData<Long>()
    override val level = MutableLiveData<Level>()

    override fun getDataByLevel(level: Level) {
        useCase.getDataByLevel(level)
            .onEach {
                gameModeLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    override fun btnClicked(state: Boolean, position: Int) {

    }

    override fun incr() {
        counter.value = counter.value!! +1
    }

    override fun decr() {
        counter.value = 0
        score.value = 0
    }

    override fun saveScoreAndTime(time: String, score: Int) {
        useCase.saveScores(level = level.value!!, time,score)
    }

    override fun setScore(earnedScore: Int) {
        this.score.value = this.score.value!! + earnedScore
    }

    override fun setLevel(level: Level){
        this.level.value = level
    }

}