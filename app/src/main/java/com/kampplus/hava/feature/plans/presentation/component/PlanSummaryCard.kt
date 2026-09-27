package com.kampplus.hava.feature.plans.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampplus.hava.R
import com.kampplus.hava.feature.plans.domain.model.PlanAdvisorySeverity
import com.kampplus.hava.feature.plans.presentation.PlanAdvisoryUiModel
import com.kampplus.hava.feature.plans.presentation.PlanUiModel

@Composable
internal fun PlanSummaryCard(
    plan: PlanUiModel,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PlanPalette.Card),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SummaryHeading(plan)
            plan.advisory?.let { AdvisoryBanner(it) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = null,
                        tint = PlanPalette.Outline,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        plan.scheduleLabel,
                        color = PlanPalette.TextMuted,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                TextButton(onClick = { onEditClick(plan.id) }) {
                    Text(
                        stringResource(R.string.plans_edit),
                        color = PlanPalette.Primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(Icons.Filled.Edit, contentDescription = null, tint = PlanPalette.Primary)
                }
            }
        }
    }
}

@Composable
private fun SummaryHeading(plan: PlanUiModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    plan.dateLabel.uppercase(),
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(PlanPalette.PrimarySoft)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    color = PlanPalette.Primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    maxLines = 1
                )
                Text("  •  ${plan.location}", color = PlanPalette.TextMuted, fontSize = 10.sp, maxLines = 1)
            }
            Text(
                plan.title,
                color = PlanPalette.Text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.width(10.dp))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(13.dp))
                .background(PlanPalette.SurfaceLow)
                .padding(horizontal = 9.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(plan.weatherEmoji, fontSize = 16.sp)
            Text(plan.temperatureText, color = PlanPalette.Text, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
    }
}

@Composable
private fun AdvisoryBanner(advisory: PlanAdvisoryUiModel) {
    val isWarning = advisory.severity == PlanAdvisorySeverity.WARNING
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isWarning) PlanPalette.Warning else PlanPalette.SurfaceLow)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text(if (isWarning) "☂" else "☕", fontSize = 17.sp)
        Column {
            Text(
                advisory.title,
                color = if (isWarning) PlanPalette.Secondary else PlanPalette.TextMuted,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp
            )
            Text(advisory.message, color = PlanPalette.TextMuted, fontSize = 10.sp, maxLines = 2)
        }
    }
}
