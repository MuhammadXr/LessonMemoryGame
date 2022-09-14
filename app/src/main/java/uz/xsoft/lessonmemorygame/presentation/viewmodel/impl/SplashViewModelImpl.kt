package uz.xsoft.lessonmemorygame.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import uz.xsoft.lessonmemorygame.presentation.viewmodel.SplashViewModel

class SplashViewModelImpl : SplashViewModel, ViewModel() {

    override val openLevelScreenLiveData = MutableLiveData<Unit>()

    override val loadingLiveData = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {


            loadingLiveData.value = true
            delay(2500)
            openLevelScreenLiveData.value = Unit

            }

    }
}