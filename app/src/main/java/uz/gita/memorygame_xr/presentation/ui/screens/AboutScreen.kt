package uz.gita.memorygame_xr.presentation.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygame_xr.R
import uz.gita.memorygame_xr.databinding.FragmentAboutScreenBinding


class AboutScreen : Fragment(R.layout.fragment_about_screen) {

    private val viewBinding by viewBinding(FragmentAboutScreenBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }

    }
}