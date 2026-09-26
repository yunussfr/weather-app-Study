package com.kampplus.hava.feature.settings.domain.usecase

import com.kampplus.hava.feature.settings.domain.model.AppSettings
import com.kampplus.hava.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject

/** Ayar güncelleme işlemi için uygulamanın ortak giriş noktası. */
class UpdateSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(settings: AppSettings) = repository.updateSettings(settings)
}
