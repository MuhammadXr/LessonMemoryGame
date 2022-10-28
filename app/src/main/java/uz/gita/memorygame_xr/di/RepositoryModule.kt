package uz.gita.memorygame_xr.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygame_xr.domain.repositories.AppRepository
import uz.gita.memorygame_xr.domain.repositories.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @[Binds Singleton]
    fun bindAppRepository(impl:AppRepositoryImpl) : AppRepository

}