package uz.gita.memorygame_xr.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygame_xr.domain.repositories.AppRepository
import uz.gita.memorygame_xr.presentation.viewmodel.ScoreViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModelImpl @Inject constructor(private val rep: AppRepository): ScoreViewModel, ViewModel() {
    override var easyTime = String()
    override var easyScore = String()
    override var mediumTime = String()
    override var mediumScore = String()
    override var hardTime = String()
    override var hardScore = String()

    init {
        fetch()
    }

    fun fetch(){
        easyTime = rep.getEasyTime()
        easyScore = rep.getEasyScore()

        mediumScore = rep.getMediumScore()
        mediumTime = rep.getMediumTime()

        hardScore = rep.getHardScore()
        hardTime = rep.getHardTime()
    }

}