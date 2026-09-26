package com.kampplus.hava.feature.settings.data.di

import com.kampplus.hava.feature.settings.data.local.SettingsLocalDataSource
import com.kampplus.hava.feature.settings.data.local.SharedPreferencesSettingsDataSource
import com.kampplus.hava.feature.settings.data.repository.SettingsRepositoryImpl
import com.kampplus.hava.feature.settings.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Hilt bağımlılıklarını uygulama ömrü boyunca tek örnek olacak biçimde bağlar. */
@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsDataModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    abstract fun bindSettingsLocalDataSource(impl: SharedPreferencesSettingsDataSource): SettingsLocalDataSource
}
