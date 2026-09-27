package com.kampplus.hava.feature.plans.data.local

import com.kampplus.hava.feature.plans.domain.model.Plan
import com.kampplus.hava.feature.plans.domain.model.PlanActivity
import com.kampplus.hava.feature.plans.domain.model.PlanActivityStatus
import com.kampplus.hava.feature.plans.domain.model.PlanAdvisory
import com.kampplus.hava.feature.plans.domain.model.PlanAdvisorySeverity
import com.kampplus.hava.feature.plans.domain.model.PlanPeriod
import com.kampplus.hava.feature.plans.domain.model.PlanWeather
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/** Stitch ekranındaki örnek içeriği sağlar; Room/API adaptörüyle değiştirilebilir. */
@Singleton
class InMemoryPlanLocalDataSource @Inject constructor() : PlanLocalDataSource {
    private val plans = MutableStateFlow(seedPlans())

    override fun observePlans(): Flow<List<Plan>> = plans
}

private fun seedPlans() = listOf(
    Plan(
        id = 1,
        title = "Sahil Yürüyüşü & Açık Hava Çalışması",
        dateLabel = "Bugün, 24 Mayıs Cumartesi",
        location = "Kadıköy",
        weather = PlanWeather(temperatureC = 24, condition = "Güneşli", emoji = "☀️"),
        scheduleLabel = "09:00 - 21:00",
        period = PlanPeriod.TODAY,
        activities = listOf(
            PlanActivity(11, "09:00", "Sabah Koşusu (Moda Sahili)", "19°C", PlanActivityStatus.COMPLETED),
            PlanActivity(12, "13:30", "Moda Çay Bahçesi Çalışması", "23°C", PlanActivityStatus.COMPLETED),
            PlanActivity(13, "17:00", "Gün Batımı Buluşması", "22°C • Açık", PlanActivityStatus.CURRENT),
            PlanActivity(14, "20:30", "Akşam Bisiklet Turu", "20°C", PlanActivityStatus.UPCOMING)
        )
    ),
    Plan(
        id = 2,
        title = "Belgrad Ormanı Doğa Yürüyüşü",
        dateLabel = "Yarın • Pazar",
        location = "Sarıyer",
        weather = PlanWeather(temperatureC = 19, condition = "Hafif Yağmurlu", emoji = "🌧️"),
        scheduleLabel = "10:00 - 15:30 (3 Durak)",
        period = PlanPeriod.UPCOMING,
        advisory = PlanAdvisory(
            title = "Hava: 19°C Hafif Yağmurlu",
            message = "Yağmurluk ve su geçirmez ayakkabı almayı unutmayın!",
            severity = PlanAdvisorySeverity.WARNING
        )
    ),
    Plan(
        id = 3,
        title = "Şehir İçi Ofis & Kahve Molaları",
        dateLabel = "26 Mayıs • Pazartesi",
        location = "Levent / Beşiktaş",
        weather = PlanWeather(temperatureC = 21, condition = "Bulutlu", emoji = "☁️"),
        scheduleLabel = "09:00 - 18:00 (4 Aktivite)",
        period = PlanPeriod.UPCOMING,
        advisory = PlanAdvisory(
            title = "İç mekan çalışması için ideal hava",
            message = "%10 yağış riski",
            severity = PlanAdvisorySeverity.INFO
        )
    ),
    Plan(
        id = 4,
        title = "Sahil Şeridi Bisiklet Turu",
        dateLabel = "28 Mayıs • Çarşamba",
        location = "Bostancı - Maltepe",
        weather = PlanWeather(temperatureC = 25, condition = "Açık", emoji = "☀️"),
        scheduleLabel = "18:00 - 20:30 (Mükemmel Rüzgâr Hızı)",
        period = PlanPeriod.UPCOMING
    ),
    Plan(
        id = 5,
        title = "Emirgan Korusu Fotoğraf Gezisi",
        dateLabel = "18 Mayıs • Pazar",
        location = "Emirgan",
        weather = PlanWeather(temperatureC = 22, condition = "Az Bulutlu", emoji = "🌤️"),
        scheduleLabel = "08:30 - 12:00 (Tamamlandı)",
        period = PlanPeriod.PAST
    )
)
