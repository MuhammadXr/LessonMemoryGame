package uz.gita.memorygame_xr.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygame_xr.data.MySharedPref
import uz.gita.memorygame_xr.domain.repositories.AppRepository
import uz.gita.memorygame_xr.presentation.viewmodel.ScoreViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModelImpl @Inject constructor(
    override val sharedPref: MySharedPref
): ScoreViewModel, ViewModel() {


}