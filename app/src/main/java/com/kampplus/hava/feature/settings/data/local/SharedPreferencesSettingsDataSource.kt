package com.kampplus.hava.feature.settings.data.local

import android.content.Context
import androidx.core.content.edit
import com.kampplus.hava.feature.settings.domain.model.AppSettings
import com.kampplus.hava.feature.settings.domain.model.TemperatureUnit
import com.kampplus.hava.feature.settings.domain.model.ThemePreference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Tema, sıcaklık birimi ve bildirim seçimini uygulama kapanınca kaybolmayacak şekilde saklar.
 * Enum değerleri bozulur veya eski bir değer gelirse güvenli varsayılanlar kullanılır.
 */
@Singleton
class SharedPreferencesSettingsDataSource @Inject constructor(
    @ApplicationContext context: Context
) : SettingsLocalDataSource {
    private val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val settings = MutableStateFlow(readSettings())

    override fun observe(): Flow<AppSettings> = settings.asStateFlow()

    override suspend fun save(settings: AppSettings) {
        preferences.edit {
            putString(KEY_THEME, settings.themePreference.name)
            putString(KEY_TEMPERATURE_UNIT, settings.temperatureUnit.name)
            putBoolean(KEY_NOTIFICATIONS, settings.weatherNotificationsEnabled)
        }
        this.settings.value = settings
    }

    private fun readSettings() = AppSettings(
        themePreference = preferences.getString(KEY_THEME, null).toEnumOrDefault(ThemePreference.SYSTEM),
        temperatureUnit = preferences.getString(KEY_TEMPERATURE_UNIT, null).toEnumOrDefault(TemperatureUnit.CELSIUS),
        weatherNotificationsEnabled = preferences.getBoolean(KEY_NOTIFICATIONS, false)
    )

    private inline fun <reified T : Enum<T>> String?.toEnumOrDefault(default: T): T =
        enumValues<T>().firstOrNull { it.name == this } ?: default

    private companion object {
        const val FILE_NAME = "app_settings"
        const val KEY_THEME = "theme"
        const val KEY_TEMPERATURE_UNIT = "temperature_unit"
        const val KEY_NOTIFICATIONS = "weather_notifications"
    }
}
