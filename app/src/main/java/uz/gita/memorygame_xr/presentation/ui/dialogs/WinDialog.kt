package uz.gita.memorygame_xr.presentation.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import uz.gita.memorygame_xr.R

class WinDialog(context: Context): AlertDialog(context) {

    private lateinit var exitButton: TextView
    private lateinit var playAgainButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_win)

        exitButton = findViewById(R.id.exit_btn)
        playAgainButton = findViewById(R.id.play_again_btn)


        exitButton.setOnClickListener{
            exitButtonListener?.invoke()
        }

        playAgainButton.setOnClickListener {
            playAgainButtonListener?.invoke()
        }



    }

    public fun setExitListener(block: ()->Unit){
        exitButtonListener = block
    }

    public fun setReplayListener(block: () -> Unit){
        playAgainButtonListener = block
    }

    private var exitButtonListener: (()->Unit)? = null

    private var playAgainButtonListener: (()->Unit)? = null
}