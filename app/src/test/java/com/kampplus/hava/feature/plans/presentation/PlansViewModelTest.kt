package com.kampplus.hava.feature.plans.presentation

import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.model.PlanFilter
import com.kampplus.hava.feature.plans.domain.model.PlanPeriod
import com.kampplus.hava.feature.plans.domain.model.PlanWeather
import com.kampplus.hava.feature.plans.domain.repository.PlanRepository
import com.kampplus.hava.feature.plans.domain.usecase.FilterPlansUseCase
import com.kampplus.hava.feature.plans.domain.usecase.ObservePlansUseCase
import com.kampplus.hava.testing.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class PlansViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakePlanRepository()

    private fun createViewModel() = PlansViewModel(
        observePlans = ObservePlansUseCase(repository),
        filterPlans = FilterPlansUseCase(),
        uiMapper = PlanUiMapper()
    )

    @Test
    fun `maps active and upcoming plans into separate sections`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch { viewModel.uiState.collect {} }
        runCurrent()

        assertEquals(3, viewModel.uiState.value.totalPlanCount)
        assertEquals(1L, viewModel.uiState.value.activePlan?.id)
        assertEquals(listOf(2L), viewModel.uiState.value.upcomingPlans.map { it.id })
    }

    @Test
    fun `past filter exposes only past plans`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch { viewModel.uiState.collect {} }
        runCurrent()

        viewModel.onFilterSelected(PlanFilter.PAST)
        runCurrent()

        assertNull(viewModel.uiState.value.activePlan)
        assertEquals(listOf(3L), viewModel.uiState.value.pastPlans.map { it.id })
    }
}

private class FakePlanRepository : PlanRepository {
    private val plans = MutableStateFlow(
        listOf(
            plan(1, PlanPeriod.TODAY),
            plan(2, PlanPeriod.UPCOMING),
            plan(3, PlanPeriod.PAST)
        )
    )

    override fun observePlans(): Flow<List<Plan>> = plans
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
