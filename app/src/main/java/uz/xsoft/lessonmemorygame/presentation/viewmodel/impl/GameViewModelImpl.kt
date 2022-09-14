package uz.xsoft.lessonmemorygame.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level
import uz.xsoft.lessonmemorygame.domain.usecases.GameUseCase
import uz.xsoft.lessonmemorygame.presentation.viewmodel.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(private val useCase: GameUseCase) : GameViewModel, ViewModel() {

    override val gameModeLiveData: MutableLiveData<List<GameModel>> = MutableLiveData()

    override val counter = MutableLiveData<Int>(0)

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
    }


}