package com.kampplus.hava.feature.plans.data.repository

import com.kampplus.hava.feature.plans.data.local.PlanLocalDataSource
import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.repository.PlanRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PlanRepositoryImpl @Inject constructor(
    private val localDataSource: PlanLocalDataSource
) : PlanRepository {
    override fun observePlans(): Flow<List<Plan>> = localDataSource.observePlans()
}
