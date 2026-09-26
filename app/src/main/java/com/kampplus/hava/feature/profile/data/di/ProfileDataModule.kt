package com.kampplus.hava.feature.profile.data.di

import com.kampplus.hava.feature.profile.data.local.SharedPreferencesUserProfileDataSource
import com.kampplus.hava.feature.profile.data.local.UserProfileLocalDataSource
import com.kampplus.hava.feature.profile.data.repository.UserProfileRepositoryImpl
import com.kampplus.hava.feature.profile.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Hilt'e profil sözleşmelerinde hangi gerçek sınıfları kullanacağını bildirir. */
@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileDataModule {
    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(impl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    abstract fun bindUserProfileLocalDataSource(impl: SharedPreferencesUserProfileDataSource): UserProfileLocalDataSource
}
