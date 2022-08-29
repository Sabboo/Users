package com.saber.users.di

import com.saber.users.api.RemoteDataSource
import com.saber.users.db.UsersDb
import com.saber.users.db.LocalDataSource
import com.saber.users.repository.UsersRepository
import com.saber.users.repository.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        coroutineDispatcher: CoroutineDispatcher
    ): UsersRepository =
        UsersRepositoryImpl(localDataSource, remoteDataSource, coroutineDispatcher)


    @ViewModelScoped
    @Provides
    fun provideRemoteDataSource(retrofit: Retrofit): RemoteDataSource {
        return RemoteDataSource(retrofit)
    }

    @ViewModelScoped
    @Provides
    fun provideLocalDataSource(db: UsersDb): LocalDataSource {
        return LocalDataSource(db.usersDao())
    }
}