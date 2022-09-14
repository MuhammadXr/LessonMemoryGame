package uz.xsoft.lessonmemorygame.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.xsoft.lessonmemorygame.presentation.viewmodel.impl.SplashViewModelImpl

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}