package com.kampplus.hava.feature.plans.domain.usecase

import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.repository.PlanRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObservePlansUseCase @Inject constructor(
    private val repository: PlanRepository
) {
    operator fun invoke(): Flow<List<Plan>> = repository.observePlans()
}
