package com.kampplus.hava.feature.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Route, stateful sınırdır: ViewModel'i oluşturur ve state/event bağlantısını yapar.
 * ProfileScreen'in Hilt veya ViewModel bilmesine gerek kalmaz.
 */
@Composable
fun ProfileRoute(modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiState = uiState,
        onEditClick = viewModel::onEditClick,
        onCancelClick = viewModel::onCancelClick,
        onSaveClick = viewModel::onSaveClick,
        onFullNameChange = viewModel::onFullNameChange,
        onEmailChange = viewModel::onEmailChange,
        onCityChange = viewModel::onCityChange,
        onBiographyChange = viewModel::onBiographyChange,
        modifier = modifier
    )
}
