package uz.xsoft.lessonmemorygame.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.xsoft.lessonmemorygame.R
import uz.xsoft.lessonmemorygame.data.models.Level
import uz.xsoft.lessonmemorygame.databinding.ScreenLevelBinding
import uz.xsoft.lessonmemorygame.presentation.viewmodel.LevelViewModel
import uz.xsoft.lessonmemorygame.presentation.viewmodel.impl.LevelViewModelImpl

@AndroidEntryPoint
class LevelScreen : Fragment(R.layout.screen_level) {
    private val viewBinding by viewBinding(ScreenLevelBinding::bind)
    private val viewModel: LevelViewModel by viewModels<LevelViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openGameLiveData.observe(this@LevelScreen, openGameObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnEasy.setOnClickListener { viewModel.openGame(Level.EASY) }
        viewBinding.btnMedium.setOnClickListener { viewModel.openGame(Level.MEDIUM) }
        viewBinding.btnHard.setOnClickListener { viewModel.openGame(Level.HARD) }
    }


    private val openGameObserver = Observer<Level> {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(it))
    }
}