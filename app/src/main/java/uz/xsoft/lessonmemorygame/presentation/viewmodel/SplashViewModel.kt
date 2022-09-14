package uz.xsoft.lessonmemorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.navigation.NavController

interface SplashViewModel {


    val openLevelScreenLiveData: LiveData<Unit>

    val loadingLiveData: LiveData<Boolean>


}