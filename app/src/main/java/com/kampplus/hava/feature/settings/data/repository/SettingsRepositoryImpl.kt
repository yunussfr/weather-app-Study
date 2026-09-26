package com.kampplus.hava.feature.settings.data.repository

import com.kampplus.hava.feature.settings.data.local.SettingsLocalDataSource
import com.kampplus.hava.feature.settings.domain.model.AppSettings
import com.kampplus.hava.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/** Domain repository sözleşmesini yerel ayar kaynağına bağlar. */
class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) : SettingsRepository {
    override fun observeSettings(): Flow<AppSettings> = localDataSource.observe()

    override suspend fun updateSettings(settings: AppSettings) = localDataSource.save(settings)
}
