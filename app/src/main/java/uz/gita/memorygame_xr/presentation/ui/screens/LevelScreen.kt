package uz.gita.memorygame_xr.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygame_xr.R
import uz.gita.memorygame_xr.data.models.Level
import uz.gita.memorygame_xr.databinding.ScreenLevelBinding
import uz.gita.memorygame_xr.presentation.viewmodel.LevelViewModel
import uz.gita.memorygame_xr.presentation.viewmodel.impl.LevelViewModelImpl

@AndroidEntryPoint
class LevelScreen : Fragment(R.layout.screen_level) {
    private val viewBinding by viewBinding(ScreenLevelBinding::bind)
    private val viewModel: LevelViewModel by viewModels<LevelViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openGameLiveData.observe(this@LevelScreen, openGameObserver)
        viewModel.openScoresScreen.observe(this@LevelScreen, openScoresObserver)
        viewModel.openAboutScreen.observe(this@LevelScreen, openAboutObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnEasy.setOnClickListener { viewModel.openGame(Level.EASY) }
        viewBinding.btnMedium.setOnClickListener { viewModel.openGame(Level.MEDIUM) }
        viewBinding.btnHard.setOnClickListener { viewModel.openGame(Level.HARD) }
        viewBinding.aboutButton.setOnClickListener { viewModel.openAbout() }
        viewBinding.scoresButton.setOnClickListener { viewModel.openScores() }
    }


    private val openGameObserver = Observer<Level> {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(it))
    }

    private val openAboutObserver = Observer<Unit>{
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToAboutScreen())
    }

    private val openScoresObserver = Observer<Unit>{
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToRecordsScreen())
    }
}