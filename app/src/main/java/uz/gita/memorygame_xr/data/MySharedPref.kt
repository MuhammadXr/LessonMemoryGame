package uz.gita.memorygame_xr.data

import android.content.Context
import android.content.SharedPreferences
import uz.gita.memorygame_xr.utils.SharedPreference
import javax.inject.Inject


class MySharedPref @Inject constructor(
    private val ctx: Context,
    private val sharedPreferences: SharedPreferences
) : SharedPreference(ctx, sharedPreferences) {

    var scoreEasy:String by Strings()
    var scoreMedium:String by Strings()
    var scoreHard:String by Strings()

    var timeEasy: String by Strings()
    var timeMedium: String by Strings()
    var timeHard: String by Strings()

}