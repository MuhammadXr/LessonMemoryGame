package uz.gita.memorygame_xr.data

import android.content.Context
import android.content.SharedPreferences
import uz.gita.memorygame_xr.utils.SharedPreference
import javax.inject.Inject


class MySharedPref @Inject constructor(
    private val ctx: Context,
    private val sharedPreferences: SharedPreferences
) : SharedPreference(ctx, sharedPreferences) {

    var easy1:String by Strings()
    var easy2:String by Strings()
    var easy3:String by Strings()
    var medium1:String by Strings()
    var medium2:String by Strings()
    var medium3:String by Strings()
    var hard1:String by Strings()
    var hard2:String by Strings()
    var hard3:String by Strings()

    var isFirst: Boolean by Booleans()

    init {
        if (!isFirst){
            easy1 = "0#0"
            easy2 = "0#0"
            easy3 = "0#0"

            medium1 = "0#0"
            medium2 = "0#0"
            medium3 = "0#0"

            hard1 = "0#0"
            hard2 = "0#0"
            hard3 = "0#0"

            isFirst = true
        }
    }
}