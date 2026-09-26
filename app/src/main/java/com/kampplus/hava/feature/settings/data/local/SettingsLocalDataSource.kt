package com.kampplus.hava.feature.settings.data.local

import com.kampplus.hava.feature.settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/** Cihaz üzerindeki ayar kayıtlarının data-layer operasyonları. */
interface SettingsLocalDataSource {
    fun observe(): Flow<AppSettings>

    suspend fun save(settings: AppSettings)
}
