package uz.gita.memorygame_xr.presentation.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygame_xr.R
import uz.gita.memorygame_xr.databinding.FragmentRecordsScreenBinding
import uz.gita.memorygame_xr.presentation.viewmodel.ScoreViewModel
import uz.gita.memorygame_xr.presentation.viewmodel.impl.ScoreViewModelImpl

@AndroidEntryPoint
class RecordsScreen : Fragment(R.layout.fragment_records_screen) {

    private val viewBinding by viewBinding(FragmentRecordsScreenBinding::bind)
    private val viewModel:ScoreViewModel by viewModels<ScoreViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }

        fetchScores()

        fetchTimes()

    }

    private fun fetchScores(){

        viewBinding.bonusEasyText1.text = viewModel.sharedPref.easy1.split("#").first()
        viewBinding.bonusEasyText2.text = viewModel.sharedPref.easy2.split("#").first()
        viewBinding.bonusEasyText3.text = viewModel.sharedPref.easy3.split("#").first()


        viewBinding.bonusMediumText1.text = viewModel.sharedPref.medium1.split("#").first()
        viewBinding.bonusMediumText2.text = viewModel.sharedPref.medium2.split("#").first()
        viewBinding.bonusMediumText3.text = viewModel.sharedPref.medium3.split("#").first()



        viewBinding.bonusHardText1.text = viewModel.sharedPref.hard1.split("#").first()
        viewBinding.bonusHardText2.text = viewModel.sharedPref.hard2.split("#").first()
        viewBinding.bonusHardText3.text = viewModel.sharedPref.hard3.split("#").first()
    }

    private fun fetchTimes(){

        viewBinding.timeEastText1.text = viewModel.sharedPref.easy1.split("#")[1]
        viewBinding.timeEasyText2.text = viewModel.sharedPref.easy2.split("#")[1]
        viewBinding.timeEastText3.text = viewModel.sharedPref.easy3.split("#")[1]


        viewBinding.timeMediumText1.text = viewModel.sharedPref.medium1.split("#")[1]
        viewBinding.timeMediumText2.text = viewModel.sharedPref.medium2.split("#")[1]
        viewBinding.timeMediumText3.text = viewModel.sharedPref.medium3.split("#")[1]



        viewBinding.timeHardText1.text = viewModel.sharedPref.hard1.split("#")[1]
        viewBinding.timeHardText2.text = viewModel.sharedPref.hard2.split("#")[1]
        viewBinding.timeHardText3.text = viewModel.sharedPref.hard3.split("#")[1]
    }
}