package com.kampplus.hava.feature.plans.data.local

import com.kampplus.hava.feature.plans.domain.model.Plan
import kotlinx.coroutines.flow.Flow

interface PlanLocalDataSource {
    fun observePlans(): Flow<List<Plan>>
}
