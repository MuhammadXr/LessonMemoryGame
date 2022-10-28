package uz.gita.memorygame_xr.presentation.ui.screens

import android.os.Bundle
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


        val scoreEasyList = ArrayList<Int>()
        scoreEasyList.apply {
            if (viewModel.easyScore.isNotEmpty())
            viewModel.easyScore.split('#').forEach{
                this.add(it.toInt())
            }
            else
                scoreEasyList.addAll(listOf(0,0,0))
        }

        scoreEasyList.sortDescending()

        viewBinding.bonusEasyText1.text = scoreEasyList.first().toString()
        viewBinding.bonusEasyText2.text = scoreEasyList[1].toString()
        viewBinding.bonusEasyText3.text = scoreEasyList.last().toString()

        val scoreMediumList = ArrayList<Int>()
        scoreMediumList.apply {
            if (viewModel.mediumScore.isNotEmpty())
            viewModel.mediumScore.split('#').forEach{
                this.add(it.toInt())
            }
            else
                scoreMediumList.addAll(listOf(0,0,0))
        }

        scoreMediumList.sortDescending()

        viewBinding.bonusMediumText1.text = scoreMediumList.first().toString()
        viewBinding.bonusMediumText2.text = scoreMediumList[1].toString()
        viewBinding.bonusMediumText3.text = scoreMediumList.last().toString()


        val scoreHardList = ArrayList<Int>()
        scoreHardList.apply {
            if (viewModel.hardScore.isNotEmpty())
            viewModel.hardScore.split('#').forEach{
                this.add(it.toInt())
            }
            else
                scoreHardList.addAll(listOf(0,0,0))
        }

        scoreHardList.sortDescending()

        viewBinding.bonusHardText1.text = scoreHardList.first().toString()
        viewBinding.bonusHardText2.text = scoreHardList[1].toString()
        viewBinding.bonusHardText3.text = scoreHardList.last().toString()
    }
}