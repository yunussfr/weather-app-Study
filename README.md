# Hava — Hava Durumu Keşif Uygulaması

Mobil Kamp "Keşif Uygulaması" gereksinimlerinin hava durumu temalı **referans çözümü**. Şehirlerin anlık havasını listeler, seçilen şehrin saatlik/günlük tahminini gösterir, favori şehirleri cihazda saklar ve hava koşullarına bağlı kişisel planları sunar; yükleniyor / veri / boş / hata durumlarının tamamını ele alır.

**Teknoloji:** Kotlin · Jetpack Compose (Material 3) · Clean Architecture · Coroutines & StateFlow · Hilt · Retrofit · Room · Navigation Compose

## API — Open-Meteo (API key gerekmez)

| Amaç | Çağrı |
|---|---|
| Anlık hava (çoklu şehir tek istekte) | `https://api.open-meteo.com/v1/forecast?latitude=41.01,39.92&longitude=28.98,32.85&current=temperature_2m,weather_code&timezone=auto` → JSON **dizi** |
| Tahmin (tek şehir) | `.../v1/forecast?latitude=..&longitude=..&current=..&hourly=temperature_2m,weather_code,precipitation_probability&daily=weather_code,temperature_2m_max,temperature_2m_min&forecast_days=7&timezone=auto` → JSON **nesne** |
| Şehir arama | `https://geocoding-api.open-meteo.com/v1/search?name=Ankara&count=20&language=tr` → sonuç yoksa `results` alanı hiç gelmez (= Boş durum) |

- `weather_code` WMO standardındadır (0 açık, 61 yağmur, 95 fırtına…); `WeatherConditionClassifier` ile alan kavramına çevrilir.
- Ücretsiz kullanım ticari olmayan projeler içindir (~10.000 istek/gün). Kurumsal yayında ücretli plan gerekir.

## Çalıştırma

```bash
./gradlew assembleDebug              # debug APK
./gradlew testDebugUnitTest          # unit testler (mimari testi dahil)
./gradlew spotlessCheck              # ktlint formatı (düzeltmek için spotlessApply)
./gradlew connectedDebugAndroidTest  # cihaz/emülatörde Room DAO testleri
./gradlew assembleRelease            # release APK (R8 açık)
```

## Checkpoint'ler

| Branch | İçerik |
|---|---|
| `cp1-baslangic` | Boş proje iskeleti |
| `cp1-bitis` / `cp2-baslangic` | Sabit veriyle (20 şehir) liste ekranı |
| `cp2-bitis` / `cp3-baslangic` | Navigasyon ve tahmin detay ekranı |
| `cp3-bitis` / `cp4-baslangic` | Favori şehirler, paylaşılan state |
| `cp4-bitis` | Open-Meteo API, dört durum, Room, arama — tam referans çözüm |

Bir bloğu yetiştiremediyseniz kodunuzu `katilimci/<ad>/cpN` branch'ine kaydedip sonraki bloğun başlangıç branch'ine geçin.

## Git

Git Flow (`main` ← `release/*` ← `develop` ← `feature/*`), feature → develop `--no-ff` merge, Conventional Commits (`feat(list): add city search`). Geçmiş: `git log --graph --oneline --all`

Ayrıntılar: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) · [docs/GIT-HISTORY.md](docs/GIT-HISTORY.md)

## Kurumsal ağ uyarısı (TLS denetimi)

Kurumsal güvenlik duvarı `open-meteo.com` trafiğini kendi iç sertifikasıyla yeniden imzalıyorsa (TLS inspection) cihaz/emülatör bu sertifikaya güvenmez ve uygulama **"İnternet bağlantısı yok"** hatası gösterir (logcat: `SSLHandshakeException: Trust anchor for certification path not found`). Kontrol: `echo | openssl s_client -connect api.open-meteo.com:443 2>/dev/null | openssl x509 -noout -issuer`. Kamp öncesi cihazların kurumsal olmayan bir ağda (misafir Wi-Fi / hotspot) test edilmesi önerilir.

**Release imzası:** `keystore.properties.example` dosyasını `keystore.properties` olarak kopyalayıp doldurun (repoya girmez). Dosya yoksa release APK debug anahtarıyla imzalanır.
