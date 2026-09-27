package com.kampplus.hava.feature.plans.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampplus.hava.R
import com.kampplus.hava.feature.plans.domain.model.PlanActivityStatus
import com.kampplus.hava.feature.plans.presentation.PlanActivityUiModel
import com.kampplus.hava.feature.plans.presentation.PlanUiModel
import kotlin.math.roundToInt

@Composable
internal fun ActivePlanSection(plan: PlanUiModel, onPlanClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Text("☀", fontSize = 19.sp)
                Text(
                    stringResource(R.string.plans_active_title),
                    color = PlanPalette.Text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
            Text(
                stringResource(R.string.plans_live_tracking),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFFFDDB8))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                color = Color(0xFF653E00),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
        ActivePlanCard(plan = plan, onPlanClick = onPlanClick)
    }
}

@Composable
private fun ActivePlanCard(plan: PlanUiModel, onPlanClick: (Long) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PlanPalette.Card),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PlanCardHeading(plan)
            PlanProgress(plan)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                plan.activities.forEach { activity -> ActivityRow(activity) }
            }
            Button(
                onClick = { onPlanClick(plan.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlanPalette.SurfaceHigh,
                    contentColor = PlanPalette.Primary
                )
            ) {
                Text(stringResource(R.string.plans_view_detail), fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(5.dp))
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun PlanCardHeading(plan: PlanUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(plan.dateLabel, color = PlanPalette.TextMuted, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
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
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(plan.weatherEmoji, fontSize = 17.sp)
            Column(horizontalAlignment = Alignment.End) {
                Text(plan.temperatureText, color = PlanPalette.Text, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text(plan.location, color = PlanPalette.TextMuted, fontSize = 9.sp)
            }
        }
    }
}

@Composable
private fun PlanProgress(plan: PlanUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(13.dp))
            .background(PlanPalette.SurfaceLow)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "✓  ${stringResource(R.string.plans_progress, plan.completedActivityCount, plan.activities.size)}",
                color = PlanPalette.Primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            Text(
                stringResource(R.string.plans_progress_percent, (plan.progress * 100).roundToInt()),
                color = PlanPalette.Primary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        LinearProgressIndicator(
            progress = { plan.progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(CircleShape),
            color = PlanPalette.Primary,
            trackColor = PlanPalette.SurfaceHigh
        )
    }
}

@Composable
private fun ActivityRow(activity: PlanActivityUiModel) {
    val isCompleted = activity.status == PlanActivityStatus.COMPLETED
    val isCurrent = activity.status == PlanActivityStatus.CURRENT
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (activity.status == PlanActivityStatus.UPCOMING) 0.62f else 1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> PlanPalette.Primary
                        isCurrent -> PlanPalette.Secondary
                        else -> PlanPalette.SurfaceHigh
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when {
                    isCompleted -> Icons.Filled.Check
                    isCurrent -> Icons.Filled.ArrowForward
                    else -> Icons.Filled.DateRange
                },
                contentDescription = null,
                tint = if (isCompleted || isCurrent) Color.White else PlanPalette.Outline,
                modifier = Modifier.size(15.dp)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(if (isCurrent) PlanPalette.SurfaceHigh else PlanPalette.SurfaceLow)
                .padding(horizontal = 11.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                activity.time,
                color = if (isCurrent) PlanPalette.Secondary else PlanPalette.Outline,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp
            )
            Spacer(Modifier.width(8.dp))
            Text(
                activity.title,
                modifier = Modifier.weight(1f),
                color = if (isCurrent) PlanPalette.Text else PlanPalette.TextMuted,
                fontWeight = if (isCurrent) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if (isCompleted) TextDecoration.LineThrough else null
            )
            Spacer(Modifier.width(6.dp))
            Text(
                activity.weatherLabel,
                color = if (isCurrent) PlanPalette.Secondary else PlanPalette.Primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 9.sp
            )
        }
    }
}
