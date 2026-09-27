package com.kampplus.hava.feature.plans.domain.usecase

import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.model.PlanFilter
import com.kampplus.hava.feature.plans.domain.model.PlanPeriod
import com.kampplus.hava.feature.plans.domain.model.PlanWeather
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterPlansUseCaseTest {
    private val useCase = FilterPlansUseCase()
    private val plans = listOf(
        plan(1, PlanPeriod.TODAY),
        plan(2, PlanPeriod.UPCOMING),
        plan(3, PlanPeriod.PAST)
    )

    @Test
    fun `all returns every plan in repository order`() {
        assertEquals(listOf(1L, 2L, 3L), useCase(plans, PlanFilter.ALL).map { it.id })
    }

    @Test
    fun `weekly excludes past plans`() {
        assertEquals(listOf(1L, 2L), useCase(plans, PlanFilter.WEEKLY).map { it.id })
    }

    @Test
    fun `past returns only completed plans`() {
        assertEquals(listOf(3L), useCase(plans, PlanFilter.PAST).map { it.id })
    }

    private fun plan(id: Long, period: PlanPeriod) = Plan(
        id = id,
        title = "Plan $id",
        dateLabel = "Tarih",
        location = "İstanbul",
        weather = PlanWeather(20, "Açık", "☀️"),
        scheduleLabel = "09:00",
        period = period
    )
}
