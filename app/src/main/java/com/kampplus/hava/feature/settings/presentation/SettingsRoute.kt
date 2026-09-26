package com.kampplus.hava.feature.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/** ViewModel state ve eventlerini stateless SettingsScreen'e bağlayan navigasyon sınırı. */
@Composable
fun SettingsRoute(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onThemePreferenceChange = viewModel::onThemePreferenceChange,
        onTemperatureUnitChange = viewModel::onTemperatureUnitChange,
        onWeatherNotificationsChange = viewModel::onWeatherNotificationsChange,
        modifier = modifier
    )
}
