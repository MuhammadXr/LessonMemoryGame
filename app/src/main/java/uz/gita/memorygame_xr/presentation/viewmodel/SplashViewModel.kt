package uz.gita.memorygame_xr.presentation.viewmodel

import androidx.lifecycle.LiveData

interface SplashViewModel {


    val openLevelScreenLiveData: LiveData<Unit>

    val loadingLiveData: LiveData<Boolean>


}