package com.kampplus.hava.feature.plans.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kampplus.hava.feature.plans.domain.model.PlanFilter
import com.kampplus.hava.feature.plans.domain.usecase.FilterPlansUseCase
import com.kampplus.hava.feature.plans.domain.usecase.ObservePlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PlansViewModel @Inject constructor(
    observePlans: ObservePlansUseCase,
    private val filterPlans: FilterPlansUseCase,
    private val uiMapper: PlanUiMapper
) : ViewModel() {
    private val selectedFilter = MutableStateFlow(PlanFilter.ALL)

    val uiState: StateFlow<PlansUiState> = combine(observePlans(), selectedFilter) { plans, filter ->
        uiMapper.map(
            plans = filterPlans(plans, filter),
            totalPlanCount = plans.size,
            selectedFilter = filter
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
        initialValue = PlansUiState()
    )

    fun onFilterSelected(filter: PlanFilter) {
        selectedFilter.value = filter
    }

    private companion object {
        const val STOP_TIMEOUT_MS = 5_000L
    }
}
