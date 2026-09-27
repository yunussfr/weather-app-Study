package com.kampplus.hava.feature.plans.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampplus.hava.R
import com.kampplus.hava.feature.plans.domain.model.PlanFilter

@Composable
internal fun PlanFilterRow(
    selectedFilter: PlanFilter,
    totalPlanCount: Int,
    onFilterSelected: (PlanFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(PlanFilter.entries) { filter ->
            val selected = filter == selectedFilter
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (selected) PlanPalette.Primary else PlanPalette.SurfaceHigh)
                    .clickable { onFilterSelected(filter) }
                    .padding(horizontal = 15.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                if (filter == PlanFilter.TODAY) {
                    Box(
                        Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(PlanPalette.Secondary)
                    )
                }
                Text(
                    text = filter.label(totalPlanCount),
                    color = if (selected) PlanPalette.Card else PlanPalette.TextMuted,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun PlanFilter.label(totalPlanCount: Int) = when (this) {
    PlanFilter.ALL -> stringResource(R.string.plans_filter_all, totalPlanCount)
    PlanFilter.TODAY -> stringResource(R.string.plans_filter_today)
    PlanFilter.WEEKLY -> stringResource(R.string.plans_filter_weekly)
    PlanFilter.PAST -> stringResource(R.string.plans_filter_past)
}
