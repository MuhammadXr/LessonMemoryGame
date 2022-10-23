package uz.xsoft.lessonmemorygame.presentation.ui.screens

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import uz.xsoft.lessonmemorygame.R
import uz.xsoft.lessonmemorygame.data.models.GameModel
import uz.xsoft.lessonmemorygame.data.models.Level
import uz.xsoft.lessonmemorygame.databinding.ScreenGameBinding
import uz.xsoft.lessonmemorygame.explode_view.ExplosionField
import uz.xsoft.lessonmemorygame.presentation.viewmodel.GameViewModel
import uz.xsoft.lessonmemorygame.presentation.viewmodel.impl.GameViewModelImpl

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val viewBinding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private var level = Level.EASY
    private var _width = 0
    private var _height = 0
    private var count = 0
    private var imageList = ArrayList<ImageView>()

    private lateinit var images: ArrayList<ImageView>
    private val args: GameScreenArgs by navArgs()

    private var isAnimationOver = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        args.level.apply {
            level = this
            count = x * y
        }

        images = ArrayList(count)
        Log.d("TTT", " Container ${viewBinding.containerMain.width}")
        viewBinding.containerMain.post {
            _width = viewBinding.containerMain.width / (level.x + 1)
            _height = viewBinding.containerMain.height / (level.y + 1) - 130

            Log.d("TTT", "result ${viewBinding.containerMain.width} $_width $_height")
            viewBinding.containerImage.layoutParams.apply {
                width = _width * level.x
                height = _height * level.y
            }
            loadImages()
        }

        viewModel.getDataByLevel(level)
        viewModel.gameModeLiveData.observe(viewLifecycleOwner, gameDataObserver)
        viewModel.counter.observe(viewLifecycleOwner, finishObserver)

    }

    fun loadImages() {


        for (x in 0 until level.x) {
            for (y in 0 until level.y) {
                val imageView = ImageView(requireContext())
                val imageViewBg = ImageView(requireContext())

                viewBinding.containerImage.addView(imageView)
                viewBinding.containerImage.addView(imageViewBg)
                val lp = imageView.layoutParams as RelativeLayout.LayoutParams
                val lpBg = imageViewBg.layoutParams as RelativeLayout.LayoutParams

                lp.apply {
                    width = _width
                    height = _height
                }

                lpBg.apply {
                    width = _width
                    height = _height
                }

                imageView.x = x * lp.width * 1f
                imageView.y = y * lp.height * 1f
                lp.setMargins(1, 1, 1, 1)
                lp.setMargins(1, 1, 1, 1)
                imageView.setPadding(12)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.layoutParams = lp
                imageView.setImageResource(R.drawable.question_mark)
                images.add(imageView)

                imageViewBg.x = x * lp.width * 1f
                imageViewBg.y = y * lp.height * 1f
                lpBg.setMargins(1, 1, 1, 1)
                imageViewBg.setPadding(6)
                imageViewBg.scaleType = ImageView.ScaleType.FIT_XY
                imageViewBg.layoutParams = lp
                imageViewBg.setImageResource(R.drawable.a_panel_96x96)

            }
        }
    }

    private val gameDataObserver = Observer<List<GameModel>> {
        lifecycleScope.launch(Dispatchers.Main) {
            val koef: Long = 500L / count
            val koefWait: Long = 90L * count
            for (i in it.indices) {
                val imageView = images[i]
                imageView.tag = it[i]
                imageView.visibility = ImageView.VISIBLE



                imageView.isClickable = false
                delay(koef)
                openView(imageView)



                imageView.setOnClickListener {


                    if (imageList.size < 2 && isAnimationOver) {

                        Log.d("TTT", "Ishlab ketdi")

                        imageView.isClickable = false
                        openView(imageView)
                        imageList.add(imageView)

                        if (imageList.first().tag == imageView.tag && imageList.size == 2) {

                            viewModel.incr()
                            viewModel.incr()
                            lifecycleScope.launch(Dispatchers.Main) {

                                delay(1000)

//                            imageView.setColorFilter(Color.GREEN)
//                            imageList.first().setColorFilter(Color.GREEN)
                                explodeView(imageList.first())
                                explodeView(imageView)

                                imageList.first().visibility = ImageView.INVISIBLE
                                imageView.visibility = ImageView.INVISIBLE

                                imageList.clear()

                            }
                        } else if (imageList.size > 1) {
                            lifecycleScope.launch(Dispatchers.Main) {

                                delay(1000)

//                            imageView.setColorFilter(Color.RED)
//                            imageList.first().setColorFilter(Color.RED)

                                shakeView(imageList.first())
                                shakeView(imageView)

                                delay(1500)

                                imageView.isClickable = true
                                imageList.first().isClickable = true
                                closeView(imageList.first())
                                closeView(imageView)

                                imageList.clear()
                            }
                        }


                    }

                }
            }

            delay(koefWait)

            for (i in it.indices){
                val imageView = images[i]
                imageView.tag = it[i]
                imageView.visibility = ImageView.VISIBLE
                delay(koef)
                closeView(imageView)
                imageView.isClickable = true

            }
            delay(50)
            isAnimationOver = true
        }

    }


    private val finishObserver = Observer<Int> {
        Log.d("TTT", " $it  == $count")
        if (it >= count) {
            val dialog =
                AlertDialog.Builder(requireContext()).setMessage("You win! restart or Quit?")
                    .setNegativeButton(
                        "QUIT"
                    ) { p0, p1 ->
                        findNavController().navigateUp()
                    }.setPositiveButton("RESTART") { p0, p1 ->

                        images.clear()
                        viewBinding.containerImage.removeAllViews()
                        loadImages()
                        viewModel.getDataByLevel(level)
                        viewModel.decr()

                    }.setCancelable(false).create()
            dialog.show()
        }
    }


    private fun closeView(imageView: ImageView) {
        imageView.animate()
            .setDuration(50)
            .scaleXBy(-.5f)
            .scaleYBy(-.5f)
            //.rotationY(90f)
            .withEndAction {
                imageView.setImageResource(R.drawable.question_mark)
                imageView.animate()
                    .setDuration(100)
                    .scaleXBy(.5f)
                    .scaleYBy(.5f)
                    .setInterpolator(DecelerateInterpolator())
                    //.rotationY(0f)
                    .withEndAction {
                    }
                    .start()
            }
           .start()

    }

    private fun openView(imageView: ImageView) {
        imageView.bringToFront()
        imageView.animate()
            .setDuration(50)
            .scaleXBy(-.5f)
            .scaleYBy(-.5f)
            .withEndAction {
                imageView.animate()
//                    .setDuration(100)
//                    .rotationY(90f)
//                    .withEndAction {
                        imageView.setImageResource((imageView.tag as GameModel).image)
//                        imageView.animate()
//                            .setDuration(100)
//                            .rotationY(180f)
//
//                            .withEndAction {
                                imageView.animate()
                                    .setDuration(100)
                                    .scaleXBy(.5f)
                                    .scaleYBy(.5f)
                                    .setInterpolator(DecelerateInterpolator())
                                    .withEndAction{
                                    }
                                    .start()
 //                           }
//                            .start()
//                    }
//                    .start()
            }

            .start()
    }

    private fun shakeView(imageView: ImageView) {
        imageView.animate()
            .setDuration(50)
            .xBy(12f)
            .scaleXBy(.1f)
            .scaleYBy(.1f)
            .withEndAction {
                imageView.animate()
                    .setDuration(50)
                    .xBy(-24f)
                    //.setInterpolator(DecelerateInterpolator())
                    .withEndAction {
                        imageView.animate()
                            .setDuration(50)
                            .xBy(24f)
                            .withEndAction {
                                imageView.animate()
                                    .setDuration(50)
                                    .xBy(-12f)
                                    .scaleXBy(-.1f)
                                    .scaleYBy(-.1f)
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }
            .start()

    }

    private fun explodeView(imageView: ImageView) {

        val exp = ExplosionField.attach2Window(activity)

        exp.explode(imageView)

    }


}