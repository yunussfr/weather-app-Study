# Mimari

Tek `:app` modülü, **feature-first** paketleme; her feature kendi `data / domain / presentation` katmanına sahiptir.
Bağımlılık yönü: `presentation → domain ← data`. Domain saf Kotlin'dir; `LayerDependencyTest` domain'de Android/Retrofit/Room importunu, presentation'da data importunu yasaklar.

## Paket haritası

```
com.kampplus.hava
├── HavaApplication.kt / MainActivity.kt (splash) / HavaApp.kt (Scaffold + BottomBar)
├── core/
│   ├── common/        AppResult (+ runCatchingApp), AppError, ErrorMapper (+ Default), dispatcher qualifier'ları, CommonModule
│   ├── network/       NetworkModule (OkHttp + HTTP cache, Json, @ForecastRetrofit / @GeocodingRetrofit), NetworkErrorMapper
│   ├── database/      HavaDatabase, DatabaseModule
│   ├── ui/            theme (+ TemperaturePalette), component (Loading/Error/Empty/Shimmer/FavoriteToggle/TemperatureBadge),
│   │                  UiState, UiText, AppErrorText
│   └── navigation/    Destinations (List, Favorites, Plans, Profile, Settings, Forecast(cityId, name, region, country, lat, lon)),
│                      TopLevelDestination, BottomBar, HavaNavHost
└── feature/
    ├── weather/
    │   ├── domain/    model (City, Coordinates, WeatherCode, CurrentWeather, CityWeather, Forecast, Hourly/DailyForecast)
    │   │              repository (WeatherRepository, CityRepository)
    │   │              usecase (GetCityWeathers, GetForecast, SearchCityWeathers — iki repository'yi birleştirir)
    │   │              policy (WeatherConditionClassifier + WmoWeatherConditionClassifier)
    │   ├── data/      local/CityCatalog (+ TurkishCityCatalog, 20 şehir, geocoding kimlikleriyle)
    │   │              remote/ WeatherRemoteDataSource (Fake → OpenMeteo), CityRemoteDataSource (OpenMeteo), api, dto
    │   │              mapper (ForecastDtoMapper — sütun → satır, GeocodingDtoMapper), repository, di/WeatherDataModule
    │   └── presentation/ list (CityList Route/Screen/ViewModel/UiState, CitySearchField, CityWeatherCard)
    │                  detail (ForecastDetail Route/Screen/ViewModel, HourlyForecastRow, DailyForecastItem, ShareButton)
    │                  model (UI modelleri, WeatherUiMapper, WeatherConditionUiRegistry, FavoriteMapping, TemperatureColors)
    │                  di/WeatherConditionUiModule (@IntoMap + özel @MapKey)
    ├── favorites/
    │   ├── domain/    FavoriteCity, FavoriteCityRepository, Observe/ObserveIds/Toggle use case'leri
    │   ├── data/      FavoriteCityLocalDataSource (InMemory → Room), dao, entity, repository, di
    │   └── presentation/ Favorites Route/Screen/ViewModel, FavoritesEvent (undo), FavoriteCityCard
    ├── plans/
    │   ├── domain/    Plan, PlanActivity, PlanFilter; repository sözleşmesi; Observe/Filter use case'leri
    │   ├── data/      InMemoryPlanLocalDataSource, repository implementasyonu ve Hilt bağları
    │   └── presentation/ Plans Route/Screen/ViewModel/UiState, UI mapper ve küçük Compose bileşenleri
    ├── profile/
    │   ├── domain/    UserProfile, repository sözleşmesi, Observe/Update use case'leri ve doğrulama kuralları
    │   ├── data/      SharedPreferences veri kaynağı, repository implementasyonu, Hilt modülü
    │   └── presentation/ Profile Route/Screen/ViewModel/UiState (görüntüleme + düzenleme)
    └── settings/
        ├── domain/    AppSettings, ThemePreference, TemperatureUnit, Observe/Update use case'leri
        ├── data/      SharedPreferences veri kaynağı, repository implementasyonu, Hilt modülü
        └── presentation/ Settings Route/Screen/ViewModel/UiState

test/          ViewModel'ler (Turbine, debounce için virtual time), use case, MockWebServer veri kaynağı testleri,
               NetworkErrorMapper, WMO sınıflandırıcı, favori senkron testi, LayerDependencyTest
androidTest/   Room DAO testi
```

## Veri akışı

`Retrofit/Room/SharedPreferences → DataSource → RepositoryImpl → UseCase → ViewModel (StateFlow<UiState>) → Route (collectAsStateWithLifecycle) → Screen (stateless)`

## Open/Closed genişleme noktaları

| Senaryo | Eklenir | Değişir | Dokunulmaz |
|---|---|---|---|
| Sabit veri → gerçek API (CP3 → CP4) | `OpenMeteoWeatherRemoteDataSource`, DTO'lar, mapper | `WeatherDataModule` (1 `@Binds`) | Domain, ViewModel, ekranlar |
| Favoriler bellek → disk (CP3 → CP4) | `RoomFavoriteCityDataSource`, entity, DAO | `FavoritesDataModule` (1 `@Binds`) | Use case'ler, ViewModel'ler |
| Hata eşleme genel → ağ (CP4) | `NetworkErrorMapper` | `CommonModule` (1 `@Binds`) | Repository'ler |
| Yeni hava durumu görünümü (ör. dolu) | `WeatherConditionUiModule`'e `@IntoMap` girdisi | — | Mapper, ekranlar |
| Farklı şehir listesi (ör. Avrupa başkentleri) | Yeni `CityCatalog` implementasyonu | `WeatherDataModule` | Tüm üst katmanlar |
| Yeni hava değişkeni (ör. UV indeksi) | DTO alanı + domain alanı (varsayılanlı) | Mapper, istek parametre listesi | ViewModel sözleşmeleri |
