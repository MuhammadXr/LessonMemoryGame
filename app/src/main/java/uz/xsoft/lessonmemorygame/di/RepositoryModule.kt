package uz.xsoft.lessonmemorygame.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.xsoft.lessonmemorygame.domain.repositories.AppRepository
import uz.xsoft.lessonmemorygame.domain.repositories.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @[Binds Singleton]
    fun bindAppRepository(impl:AppRepositoryImpl) : AppRepository

}