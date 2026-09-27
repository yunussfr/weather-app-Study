package com.kampplus.hava.feature.plans.data.di

import com.kampplus.hava.feature.plans.data.local.InMemoryPlanLocalDataSource
import com.kampplus.hava.feature.plans.data.local.PlanLocalDataSource
import com.kampplus.hava.feature.plans.data.repository.PlanRepositoryImpl
import com.kampplus.hava.feature.plans.domain.repository.PlanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlansDataModule {
    @Binds
    @Singleton
    abstract fun bindPlanRepository(impl: PlanRepositoryImpl): PlanRepository

    @Binds
    abstract fun bindPlanLocalDataSource(impl: InMemoryPlanLocalDataSource): PlanLocalDataSource
}
