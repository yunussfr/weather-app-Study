package com.kampplus.hava.feature.plans.presentation

import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.model.PlanActivityStatus
import com.kampplus.hava.feature.plans.domain.model.PlanAdvisory
import com.kampplus.hava.feature.plans.domain.model.PlanFilter
import com.kampplus.hava.feature.plans.domain.model.PlanPeriod
import javax.inject.Inject

class PlanUiMapper @Inject constructor() {
    fun map(
        plans: List<Plan>,
        totalPlanCount: Int,
        selectedFilter: PlanFilter
    ): PlansUiState {
        val mappedPlans = plans.associateWith(::mapPlan)
        return PlansUiState(
            isLoading = false,
            selectedFilter = selectedFilter,
            totalPlanCount = totalPlanCount,
            activePlan = mappedPlans.entries.firstOrNull { it.key.period == PlanPeriod.TODAY }?.value,
            upcomingPlans = mappedPlans.filterKeys { it.period == PlanPeriod.UPCOMING }.values.toList(),
            pastPlans = mappedPlans.filterKeys { it.period == PlanPeriod.PAST }.values.toList()
        )
    }

    private fun mapPlan(plan: Plan): PlanUiModel {
        val completedCount = plan.activities.count { it.status == PlanActivityStatus.COMPLETED }
        val progress = if (plan.activities.isEmpty()) 0f else completedCount.toFloat() / plan.activities.size
        return PlanUiModel(
            id = plan.id,
            title = plan.title,
            dateLabel = plan.dateLabel,
            location = plan.location,
            temperatureText = "${plan.weather.temperatureC}°C",
            condition = plan.weather.condition,
            weatherEmoji = plan.weather.emoji,
            scheduleLabel = plan.scheduleLabel,
            activities = plan.activities.map { activity ->
                PlanActivityUiModel(
                    id = activity.id,
                    time = activity.time,
                    title = activity.title,
                    weatherLabel = activity.weatherLabel,
                    status = activity.status
                )
            },
            completedActivityCount = completedCount,
            progress = progress,
            advisory = plan.advisory?.toUiModel()
        )
    }

    private fun PlanAdvisory.toUiModel() = PlanAdvisoryUiModel(
        title = title,
        message = message,
        severity = severity
    )
}
