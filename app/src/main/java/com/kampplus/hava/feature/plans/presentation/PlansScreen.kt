package com.kampplus.hava.feature.plans.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampplus.hava.R
import com.kampplus.hava.core.ui.theme.HavaTheme
import com.kampplus.hava.feature.plans.domain.model.PlanFilter
import com.kampplus.hava.feature.plans.presentation.component.ActivePlanSection
import com.kampplus.hava.feature.plans.presentation.component.BrandHeader
import com.kampplus.hava.feature.plans.presentation.component.PlanAdviceCard
import com.kampplus.hava.feature.plans.presentation.component.PlanFilterRow
import com.kampplus.hava.feature.plans.presentation.component.PlanPalette
import com.kampplus.hava.feature.plans.presentation.component.PlanSummaryCard
import com.kampplus.hava.feature.plans.presentation.component.PlansTitle

@Composable
fun PlansScreen(
    uiState: PlansUiState,
    onFilterSelected: (PlanFilter) -> Unit,
    onNewPlanClick: () -> Unit,
    onPlanClick: (Long) -> Unit,
    onEditPlanClick: (Long) -> Unit,
    onAdviceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlanPalette.Canvas)
    ) {
        BrandHeader()
        when {
            uiState.isLoading -> LoadingContent()
            else -> PlansContent(
                uiState = uiState,
                onFilterSelected = onFilterSelected,
                onNewPlanClick = onNewPlanClick,
                onPlanClick = onPlanClick,
                onEditPlanClick = onEditPlanClick,
                onAdviceClick = onAdviceClick
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = PlanPalette.Primary)
    }
}

@Composable
private fun PlansContent(
    uiState: PlansUiState,
    onFilterSelected: (PlanFilter) -> Unit,
    onNewPlanClick: () -> Unit,
    onPlanClick: (Long) -> Unit,
    onEditPlanClick: (Long) -> Unit,
    onAdviceClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            PlansTitle(
                onNewPlanClick = onNewPlanClick,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
        item {
            PlanFilterRow(
                selectedFilter = uiState.selectedFilter,
                totalPlanCount = uiState.totalPlanCount,
                onFilterSelected = onFilterSelected
            )
        }
        uiState.activePlan?.let { plan ->
            item {
                ActivePlanSection(
                    plan = plan,
                    onPlanClick = onPlanClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        if (uiState.upcomingPlans.isNotEmpty()) {
            item {
                SectionTitle(
                    title = stringResource(R.string.plans_upcoming),
                    detail = stringResource(R.string.plans_next_seven_days)
                )
            }
            items(uiState.upcomingPlans, key = { it.id }) { plan ->
                PlanSummaryCard(
                    plan = plan,
                    onEditClick = onEditPlanClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        if (uiState.pastPlans.isNotEmpty()) {
            item {
                SectionTitle(
                    title = stringResource(R.string.plans_past),
                    detail = stringResource(R.string.plans_completed)
                )
            }
            items(uiState.pastPlans, key = { it.id }) { plan ->
                PlanSummaryCard(
                    plan = plan,
                    onEditClick = onEditPlanClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        if (uiState.isEmpty) {
            item {
                Text(
                    stringResource(R.string.plans_empty),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    color = PlanPalette.TextMuted
                )
            }
        }
        item {
            PlanAdviceCard(
                onClick = onAdviceClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item { Spacer(Modifier.height(8.dp)) }
    }
}

@Composable
private fun SectionTitle(title: String, detail: String) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = PlanPalette.Text, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Text(detail, color = PlanPalette.Outline, fontWeight = FontWeight.SemiBold, fontSize = 10.sp)
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun PlansScreenPreview() {
    HavaTheme {
        PlansScreen(
            uiState = PlansUiState(isLoading = false, totalPlanCount = 0),
            onFilterSelected = {},
            onNewPlanClick = {},
            onPlanClick = {},
            onEditPlanClick = {},
            onAdviceClick = {}
        )
    }
}
