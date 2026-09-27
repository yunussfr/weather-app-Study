package com.kampplus.hava.feature.plans.domain.model

data class Plan(
    val id: Long,
    val title: String,
    val dateLabel: String,
    val location: String,
    val weather: PlanWeather,
    val scheduleLabel: String,
    val period: PlanPeriod,
    val activities: List<PlanActivity> = emptyList(),
    val advisory: PlanAdvisory? = null
)

data class PlanWeather(
    val temperatureC: Int,
    val condition: String,
    val emoji: String
)

data class PlanActivity(
    val id: Long,
    val time: String,
    val title: String,
    val weatherLabel: String,
    val status: PlanActivityStatus
)

data class PlanAdvisory(
    val title: String,
    val message: String,
    val severity: PlanAdvisorySeverity
)

enum class PlanPeriod {
    TODAY,
    UPCOMING,
    PAST
}

enum class PlanActivityStatus {
    COMPLETED,
    CURRENT,
    UPCOMING
}

enum class PlanAdvisorySeverity {
    INFO,
    WARNING
}

enum class PlanFilter {
    ALL,
    TODAY,
    WEEKLY,
    PAST
}
