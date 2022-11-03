package uz.gita.memorygame_xr.presentation.ui.dialogs

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import uz.gita.memorygame_xr.R


class PauseDialog(context: Context): AlertDialog(context) {

    private lateinit var exitButton: TextView
    private lateinit var resumeButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(uz.gita.memorygame_xr.R.layout.dialog_pause)

        exitButton = findViewById(R.id.exit_btn)
        resumeButton = findViewById(R.id.resume_btn)


        exitButton.setOnClickListener{
            exitButtonListener?.invoke()
        }

        resumeButton.setOnClickListener {
            resumeButtonListener?.invoke()
        }



    }


    public fun setExitListener(block: ()->Unit){
        exitButtonListener = block
    }

    public fun setResumeListener(block: () -> Unit){
        resumeButtonListener = block
    }

    private var exitButtonListener: (()->Unit)? = null

    private var resumeButtonListener: (()->Unit)? = null

}