# Profil ve Ayarlar Ekranları

Bu belge, iki yeni ekranın hangi verileri kullandığını, sorumluluk sınırlarını ve kodun nereye yerleştirildiğini açıklar.

## 1. Kullanıcı Profili

**Sorumluluğu:** Kullanıcı bilgilerini göstermek, düzenleme formunu açmak, ad/e-posta doğrulamasını çalıştırmak ve geçerli profili cihazda saklamak.

**Verileri:**

- `id`: Kullanıcının değişmeyen kimliği.
- `fullName`: Profilde gösterilen ve zorunlu olan ad soyad.
- `email`: Biçimi doğrulanan e-posta adresi.
- `city`: Kullanıcının yaşadığı şehir.
- `biography`: Kısa profil açıklaması.
- `memberSinceYear`: Değiştirilmeyen üyelik başlangıç yılı.

**Kullanıcı olayları:** Düzenle, iptal, kaydet ve dört form alanının değişimi.

Kod `feature/profile` altındadır. `ProfileScreen` yalnızca arayüzü çizer; `ProfileViewModel` state ve olayları yönetir; `UpdateUserProfileUseCase` doğrulama yapar; `SharedPreferencesUserProfileDataSource` kalıcı kaydı gerçekleştirir.

## 2. Ayarlar

**Sorumluluğu:** Uygulama tercihlerini göstermek, kullanıcının seçimini kaydetmek ve ayar değişikliklerini uygulamanın diğer bölümlerine yayınlamak.

**Verileri:**

- `themePreference`: Sistem, açık veya koyu tema.
- `temperatureUnit`: Santigrat veya Fahrenhayt tercihi.
- `weatherNotificationsEnabled`: Hava bildirimlerinin istenip istenmediği.

Tema seçimi `MainActivity` tarafından izlenir ve tüm uygulamaya anında uygulanır. Sıcaklık birimi kalıcı bir tercih olarak hazırdır; hava verisi mapper'ında dönüşüm yapılacağı zaman bu değer kullanılmalıdır. Bildirim anahtarı kullanıcının tercihini saklar; gerçek bildirim göndermek için ayrıca Android bildirim izni ve WorkManager işi gerekir.

Kod `feature/settings` altındadır. `SettingsScreen` kontrolleri çizer; `SettingsViewModel` seçimleri yönetir; use case'ler repository sözleşmesini çağırır; SharedPreferences data source veriyi cihazda saklar.

## Modüler veri akışı

```text
Kullanıcı etkileşimi
        ↓
Screen (stateless Compose)
        ↓ callback
Route → ViewModel → UseCase → Repository arayüzü
                                 ↑
                RepositoryImpl → LocalDataSource → SharedPreferences
        ↑
StateFlow ile güncellenen UiState
```

### Katmanların sınırı

- `presentation`: Ekran, Route, ViewModel ve UI state. `data` katmanını doğrudan import etmez.
- `domain`: Modeller, repository arayüzleri ve iş kuralları. Android/Compose bilmez.
- `data`: Repository implementasyonu ve gerçek saklama teknolojisi. Domain sözleşmelerini uygular.
- `data/di`: Hilt'e arayüzün hangi implementasyona bağlanacağını söyler.
- `core/navigation`: Feature'ları birbirine bağlar; ekranların iç işleyişini bilmez.

Bu yapı sayesinde SharedPreferences ileride API veya Room ile değiştirildiğinde ekran kodları değişmeden kalır.

## Navigasyon

`ProfileDestination` ve `SettingsDestination`, `core/navigation/Destinations.kt` içinde tanımlıdır. `HavaNavHost.kt` hedefleri ekran Route'larına bağlar. `TopLevelDestination.kt`, iki ekranı alt navigasyon çubuğunda görünür yapar.
