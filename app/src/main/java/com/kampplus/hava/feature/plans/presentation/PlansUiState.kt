package com.kampplus.hava.feature.plans.presentation

import com.kampplus.hava.feature.plans.domain.model.PlanActivityStatus
import com.kampplus.hava.feature.plans.domain.model.PlanAdvisorySeverity
import com.kampplus.hava.feature.plans.domain.model.PlanFilter

data class PlansUiState(
    val isLoading: Boolean = true,
    val selectedFilter: PlanFilter = PlanFilter.ALL,
    val totalPlanCount: Int = 0,
    val activePlan: PlanUiModel? = null,
    val upcomingPlans: List<PlanUiModel> = emptyList(),
    val pastPlans: List<PlanUiModel> = emptyList()
) {
    val isEmpty: Boolean
        get() = !isLoading && activePlan == null && upcomingPlans.isEmpty() && pastPlans.isEmpty()
}

data class PlanUiModel(
    val id: Long,
    val title: String,
    val dateLabel: String,
    val location: String,
    val temperatureText: String,
    val condition: String,
    val weatherEmoji: String,
    val scheduleLabel: String,
    val activities: List<PlanActivityUiModel>,
    val completedActivityCount: Int,
    val progress: Float,
    val advisory: PlanAdvisoryUiModel?
)

data class PlanActivityUiModel(
    val id: Long,
    val time: String,
    val title: String,
    val weatherLabel: String,
    val status: PlanActivityStatus
)

data class PlanAdvisoryUiModel(
    val title: String,
    val message: String,
    val severity: PlanAdvisorySeverity
)
