package com.kampplus.hava.feature.settings.presentation

import com.kampplus.hava.feature.settings.domain.model.TemperatureUnit
import com.kampplus.hava.feature.settings.domain.model.ThemePreference

/** Ayarlar ekranının çizilebilmesi için gereken tüm seçimlerin UI state'i. */
data class SettingsUiState(
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val weatherNotificationsEnabled: Boolean = false,
    val isLoading: Boolean = true
)
