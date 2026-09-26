package com.kampplus.hava.feature.settings.domain.repository

import com.kampplus.hava.feature.settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/** Ayarların okunması ve güncellenmesi için teknoloji bağımsız domain sözleşmesi. */
interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>

    suspend fun updateSettings(settings: AppSettings)
}
