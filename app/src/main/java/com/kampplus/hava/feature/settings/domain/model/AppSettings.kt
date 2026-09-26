package com.kampplus.hava.feature.settings.domain.model

/**
 * Uygulama genelinde kullanılan kullanıcı tercihleri.
 *
 * Bu veriler yalnızca Ayarlar ekranına ait değildir; tema ve sıcaklık birimi gibi
 * seçimler uygulamanın diğer ekranlarının davranışını da etkileyebilir.
 */
data class AppSettings(
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val weatherNotificationsEnabled: Boolean = false
)

enum class ThemePreference {
    SYSTEM,
    LIGHT,
    DARK
}

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}
