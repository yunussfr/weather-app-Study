package com.kampplus.hava.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kampplus.hava.feature.settings.domain.model.AppSettings
import com.kampplus.hava.feature.settings.domain.model.TemperatureUnit
import com.kampplus.hava.feature.settings.domain.model.ThemePreference
import com.kampplus.hava.feature.settings.domain.usecase.ObserveSettingsUseCase
import com.kampplus.hava.feature.settings.domain.usecase.UpdateSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Ayarlar ekranındaki kullanıcı seçimlerini domain modeline dönüştürür ve kaydeder.
 * UI yalnızca event gönderir; kalıcı kayıt işleminin nasıl yapıldığını bilmez.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeSettings: ObserveSettingsUseCase,
    private val updateSettings: UpdateSettingsUseCase
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = observeSettings()
        .map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
            initialValue = SettingsUiState()
        )

    fun onThemePreferenceChange(value: ThemePreference) = update { it.copy(themePreference = value) }

    fun onTemperatureUnitChange(value: TemperatureUnit) = update { it.copy(temperatureUnit = value) }

    fun onWeatherNotificationsChange(enabled: Boolean) = update { it.copy(weatherNotificationsEnabled = enabled) }

    private fun update(transform: (AppSettings) -> AppSettings) {
        val current = uiState.value.toDomain()
        viewModelScope.launch { updateSettings(transform(current)) }
    }

    private fun AppSettings.toUiState() = SettingsUiState(
        themePreference = themePreference,
        temperatureUnit = temperatureUnit,
        weatherNotificationsEnabled = weatherNotificationsEnabled,
        isLoading = false
    )

    private fun SettingsUiState.toDomain() = AppSettings(
        themePreference = themePreference,
        temperatureUnit = temperatureUnit,
        weatherNotificationsEnabled = weatherNotificationsEnabled
    )

    private companion object {
        const val STOP_TIMEOUT_MS = 5_000L
    }
}
