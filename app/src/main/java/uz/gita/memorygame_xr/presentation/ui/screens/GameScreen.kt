package uz.gita.memorygame_xr.presentation.ui.screens


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memorygame_xr.R
import uz.gita.memorygame_xr.data.models.GameModel
import uz.gita.memorygame_xr.data.models.Level
import uz.gita.memorygame_xr.databinding.ScreenGameBinding
import uz.gita.memorygame_xr.explode_view.ExplosionField
import uz.gita.memorygame_xr.presentation.ui.dialogs.PauseDialog
import uz.gita.memorygame_xr.presentation.ui.dialogs.WinDialog
import uz.gita.memorygame_xr.presentation.viewmodel.GameViewModel
import uz.gita.memorygame_xr.presentation.viewmodel.impl.GameViewModelImpl
import java.util.*


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

    //Dialogs
    private var pauseDialog:PauseDialog? = null
    private var winDialog: WinDialog? = null

    private var isAnimationOver = false


    private var seconds = 0
    private var startSeconds = 0f
    var bonusAmount = 0

    // Is the stopwatch running?
    private var running = false

    private var wasRunning = false





    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pauseDialog = PauseDialog(requireContext())
        winDialog = WinDialog(requireContext())
        pauseDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        winDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState
                .getInt("seconds")
            running = savedInstanceState
                .getBoolean("running")
            wasRunning = savedInstanceState
                .getBoolean("wasRunning")
        }
        runTimer()
        onClickStart()



        viewBinding.containerImage.bringToFront()

        viewModel.setLevel(args.level)
        args.level.apply {
            level = this
            count = x * y
        }

        viewBinding.pauseBtn.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    viewBinding.pauseBtn.setImageResource(R.drawable.pause_th_clicked)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    viewBinding.pauseBtn.setImageResource(R.drawable.pause_th)

                    pauseDialog?.setExitListener {
                        findNavController().navigateUp()
                        pauseDialog?.dismiss()
                    }
                    pauseDialog?.setResumeListener {
                        pauseDialog?.dismiss()
                    }
                    pauseDialog?.show()
                    true
                }
                else -> {
                    false
                }
            }

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
        viewModel.score.observe(viewLifecycleOwner, scoreObserver)


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
                imageViewBg.setPadding(2)
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

                        if (imageList.isEmpty())
                            startSeconds = seconds.toFloat()
                        imageView.isClickable = false
                        openView(imageView)
                        imageList.add(imageView)



                        if (imageList.first().tag == imageView.tag && imageList.size == 2) {


                            bonusAmount = (55f *(1 / (if((seconds-startSeconds) <= 0.05f) 1f else seconds - startSeconds)) ).toInt()
                            Log.d("TTT","$startSeconds  $seconds  ")
                            viewModel.setScore(bonusAmount*2)
                            viewModel.incr()
                            viewModel.incr()
                            lifecycleScope.launch(Dispatchers.Main) {




//                            imageView.setColorFilter(Color.GREEN)
//                            imageList.first().setColorFilter(Color.GREEN)

                                delay(1000)
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

            for (i in it.indices) {
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

        if (it >= count) {

            viewModel.saveScoreAndTime(viewBinding.timeText.text.toString(),viewModel.score.value!!)

            winDialog?.setExitListener {
                findNavController().navigateUp()
                winDialog?.dismiss()
            }
            winDialog?.setReplayListener {
                images.clear()
                viewBinding.containerImage.removeAllViews()
                loadImages()
                viewModel.getDataByLevel(level)
                viewModel.decr()
                winDialog?.dismiss()
                seconds = 0

            }
            winDialog?.setCancelable(false)

            winDialog?.show()
        }
    }

    private val scoreObserver = Observer<Int>{
        viewBinding.bonusText.text = it.toString()
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
                    .withEndAction {
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

        val lp = imageView.layoutParams as RelativeLayout.LayoutParams
        val imageViewOk = ImageView(requireContext())
        imageViewOk.x = imageView.x
        imageViewOk.y = imageView.y
        val bonusText = TextView(requireContext())
        bonusText.x = imageView.x
        bonusText.y = imageView.y

        exp.explode(imageView)



        viewBinding.containerImage.addView(imageViewOk)
        viewBinding.containerImage.addView(bonusText)


        lp.apply {
            width = _width
            height = _height
        }


        lp.setMargins(1, 1, 1, 1)
        imageViewOk.setPadding(6)
        imageViewOk.scaleType = ImageView.ScaleType.FIT_XY
        imageViewOk.layoutParams = lp
        imageViewOk.setImageResource(R.drawable.ok)


        bonusText.layoutParams = lp
        bonusText.text = bonusAmount.toString()
        bonusText.setTextColor(Color.YELLOW)
        bonusText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        bonusText.gravity = Gravity.CENTER

        showBonus(bonusText)
        appearView(imageViewOk)

    }

    private fun appearView(imageView: ImageView) {
        imageView.animate()
            .setDuration(50)
            .scaleXBy(-.5f)
            .scaleYBy(-.5f)
            //.rotationY(90f)
            .withEndAction {
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

    private fun showBonus(view: View){
        view.animate()
            .setDuration(1000)
            .scaleXBy(5f)
            .scaleYBy(5f)
            .alpha(0f)
            //.rotationY(90f)
            .withEndAction {
//                view.animate()
//                    .setDuration(200)
//                    .scaleXBy(.5f)
//                    .scaleYBy(.5f)
//                    .alphaBy(.1f)
//                    .setInterpolator(DecelerateInterpolator())
//                    //.rotationY(0f)
//                    .withEndAction {
//                    }
//                    .start()
            }
            .start()
    }



    // Save the state of the stopwatch
    // if it's about to be destroyed.
    override fun onSaveInstanceState(
        savedInstanceState: Bundle
    ) {
        savedInstanceState
            .putInt("seconds", seconds)
        savedInstanceState
            .putBoolean("running", running)
        savedInstanceState
            .putBoolean("wasRunning", wasRunning)
    }

    // If the activity is paused,
    // stop the stopwatch.
    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    fun onClickStart() {
        running = true
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    fun onClickStop() {
        running = false
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    fun onClickReset() {
        running = false
        seconds = 0
    }


    private fun runTimer() {

        // Get the text view.
        val timeView = viewBinding.timeText

        // Creates a new Handler
        val handler = Handler(Looper.getMainLooper())

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(object : Runnable {
            override fun run() {
                val minutes: Int = seconds % 3600 / 60
                val secs: Int = seconds % 60

//                // Format the seconds into hours, minutes,
//                // and seconds.
                val time: String = java.lang.String
                    .format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        minutes, secs
                    )
//
//                // Set the text view text.
                timeView.text = time

//                // If running is true, increment the
//                // seconds variable.
                if (running) {
                    seconds++
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000)
            }
        })
    }


}