package com.kampplus.hava.feature.plans.domain.usecase

import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.model.PlanFilter
import com.kampplus.hava.feature.plans.domain.model.PlanPeriod
import javax.inject.Inject

/** Plan filtreleme kurallarını presentation katmanından ayırır. */
class FilterPlansUseCase @Inject constructor() {
    operator fun invoke(plans: List<Plan>, filter: PlanFilter): List<Plan> = when (filter) {
        PlanFilter.ALL -> plans
        PlanFilter.TODAY -> plans.filter { it.period == PlanPeriod.TODAY }
        PlanFilter.WEEKLY -> plans.filter { it.period != PlanPeriod.PAST }
        PlanFilter.PAST -> plans.filter { it.period == PlanPeriod.PAST }
    }
}
