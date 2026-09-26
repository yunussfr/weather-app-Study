package com.kampplus.hava.feature.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kampplus.hava.R
import com.kampplus.hava.core.ui.component.LoadingView
import com.kampplus.hava.core.ui.theme.HavaTheme
import com.kampplus.hava.feature.settings.domain.model.TemperatureUnit
import com.kampplus.hava.feature.settings.domain.model.ThemePreference

/**
 * Ayarlar ekranının sorumluluğu uygulama tercihlerini göstermek ve seçim eventlerini
 * dışarı göndermektir. İçerdiği veriler: tema, sıcaklık birimi ve hava bildirimi izni.
 * Kalıcı kayıt veya tema değiştirme işi bu Composable'ın görevi değildir.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onThemePreferenceChange: (ThemePreference) -> Unit,
    onTemperatureUnitChange: (TemperatureUnit) -> Unit,
    onWeatherNotificationsChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.settings_title)) }) }
    ) { innerPadding ->
        if (uiState.isLoading) {
            LoadingView(modifier = Modifier.padding(innerPadding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SettingsSectionTitle(stringResource(R.string.settings_appearance))
                ChoiceCard {
                    ThemePreference.entries.forEachIndexed { index, preference ->
                        if (index > 0) HorizontalDivider()
                        RadioSettingRow(
                            title = preference.label(),
                            selected = uiState.themePreference == preference,
                            onClick = { onThemePreferenceChange(preference) }
                        )
                    }
                }

                SettingsSectionTitle(stringResource(R.string.settings_temperature_unit))
                ChoiceCard {
                    TemperatureUnit.entries.forEachIndexed { index, unit ->
                        if (index > 0) HorizontalDivider()
                        RadioSettingRow(
                            title = unit.label(),
                            selected = uiState.temperatureUnit == unit,
                            onClick = { onTemperatureUnitChange(unit) }
                        )
                    }
                }

                SettingsSectionTitle(stringResource(R.string.settings_notifications))
                ChoiceCard {
                    SwitchSettingRow(
                        title = stringResource(R.string.settings_weather_notifications),
                        description = stringResource(R.string.settings_weather_notifications_description),
                        checked = uiState.weatherNotificationsEnabled,
                        onCheckedChange = onWeatherNotificationsChange
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
}

@Composable
private fun ChoiceCard(content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), content = content)
}

@Composable
private fun RadioSettingRow(title: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun SwitchSettingRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun ThemePreference.label(): String = when (this) {
    ThemePreference.SYSTEM -> stringResource(R.string.settings_theme_system)
    ThemePreference.LIGHT -> stringResource(R.string.settings_theme_light)
    ThemePreference.DARK -> stringResource(R.string.settings_theme_dark)
}

@Composable
private fun TemperatureUnit.label(): String = when (this) {
    TemperatureUnit.CELSIUS -> stringResource(R.string.settings_unit_celsius)
    TemperatureUnit.FAHRENHEIT -> stringResource(R.string.settings_unit_fahrenheit)
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    HavaTheme {
        SettingsScreen(
            uiState = SettingsUiState(isLoading = false),
            onThemePreferenceChange = {},
            onTemperatureUnitChange = {},
            onWeatherNotificationsChange = {}
        )
    }
}
