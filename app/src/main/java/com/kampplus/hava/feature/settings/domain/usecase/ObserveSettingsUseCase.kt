package com.kampplus.hava.feature.settings.domain.usecase

import com.kampplus.hava.feature.settings.domain.model.AppSettings
import com.kampplus.hava.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/** Ayarlardaki değişiklikleri sürekli izler; tek seferlik okuma yapmaz. */
class ObserveSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<AppSettings> = repository.observeSettings()
}
