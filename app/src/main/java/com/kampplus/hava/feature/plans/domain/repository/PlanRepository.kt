package com.kampplus.hava.feature.plans.domain.repository

import com.kampplus.hava.feature.plans.domain.model.Plan
import kotlinx.coroutines.flow.Flow

/** Plan kaynağının teknoloji bağımsız sözleşmesi. */
interface PlanRepository {
    fun observePlans(): Flow<List<Plan>>
}
