package uz.gita.memorygame_xr.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygame_xr.data.MySharedPref
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @[Provides Singleton]
    fun provideSharedPref(@ApplicationContext ctx: Context): SharedPreferences =
        ctx.getSharedPreferences("app_date", Context.MODE_PRIVATE)

    @[Provides Singleton]
    fun provideMySharedPref(
        @ApplicationContext ctx: Context,
        sharedPreferences: SharedPreferences
    ): MySharedPref = MySharedPref(ctx, sharedPreferences)
}