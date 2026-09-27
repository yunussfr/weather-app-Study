package com.kampplus.hava.feature.plans.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/** ViewModel bağlantısını yapar; [PlansScreen] yalnızca state ve event sözleşmelerini bilir. */
@Composable
fun PlansRoute(
    modifier: Modifier = Modifier,
    onNewPlanClick: () -> Unit = {},
    onPlanClick: (Long) -> Unit = {},
    onEditPlanClick: (Long) -> Unit = {},
    onAdviceClick: () -> Unit = {},
    viewModel: PlansViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PlansScreen(
        uiState = uiState,
        onFilterSelected = viewModel::onFilterSelected,
        onNewPlanClick = onNewPlanClick,
        onPlanClick = onPlanClick,
        onEditPlanClick = onEditPlanClick,
        onAdviceClick = onAdviceClick,
        modifier = modifier
    )
}
