# 🌤️ Hava Muhalefeti

**Hava değişir, planlar değişmez.**

Hava Muhalefeti, Kotlin ve Jetpack Compose kullanılarak geliştirilmiş bir Android hava durumu uygulamasıdır.

Uygulama, yalnızca hava tahminlerini göstermekle kalmaz; hava koşullarını değerlendirerek günlük planlar ve açık hava etkinlikleri için öneriler sunar.

## ✨ Özellikler

### 🌍 Şehir Arama ve Favoriler
- Dünyanın farklı şehirlerini arama
- Seçilen şehrin hava durumu bilgilerine ulaşma
- Şehirleri favorilere ekleme ve favorilerden çıkarma
- Favori şehirleri uygulama yeniden açıldığında koruma

### ☀️ Hava Durumu Tahminleri
- Anlık sıcaklık ve hissedilen sıcaklık
- Nem oranı ve rüzgâr hızı
- Saatlik hava durumu tahminleri
- 7 günlük hava durumu tahmini
- Hava koşullarına uygun simgeler ve arka plan renkleri

### 📊 Hava Muhalefeti Endeksi
Hava koşullarını değerlendirerek 0–100 arasında bir uygunluk puanı hesaplar.

Hesaplamada sıcaklık, rüzgâr hızı, yağış ihtimali ve hava durumu koşulları dikkate alınır.

### 🧺 Etkinlik Uygunluk Analizi
Kullanıcı, aşağıdaki etkinliklerden birini seçerek hava koşullarının planına ne kadar uygun olduğunu öğrenebilir:

- Piknik
- Yürüyüş
- Koşu
- Bisiklet
- Fotoğraf çekimi

Her etkinlik için ayrı bir uygunluk puanı ve değerlendirme mesajı sunulur.

### ☂️ Bugün Ne Yapmalı?
Güncel hava durumuna göre günlük öneriler sunar.

Örneğin yağışlı havalarda şemsiye, soğuk havalarda kalın kıyafet, rüzgârlı havalarda uygun giyim tercihleri önerilir.

### 🎨 Kullanıcı Arayüzü
- Jetpack Compose ve Material 3 bileşenleri
- Özel uygulama ikonu
- Hoş geldin ekranı
- Hava durumuna göre değişen renk geçişleri
- Şehir arama ve favori şehir kartları

## 🛠️ Kullanılan Teknolojiler

| Teknoloji | Kullanım Amacı |
|---|---|
| Kotlin | Android uygulama geliştirme |
| Jetpack Compose | Kullanıcı arayüzü |
| Material 3 | Arayüz bileşenleri |
| MVVM | Uygulama mimarisi |
| ViewModel & StateFlow | Durum yönetimi |
| Kotlin Coroutines | Asenkron işlemler |
| Retrofit | API istekleri |
| Gson | JSON verilerinin işlenmesi |
| DataStore | Favori şehirlerin saklanması |
| Open-Meteo API | Hava durumu ve şehir verileri |

## 🏗️ Mimari

Uygulamada kullanıcı arayüzü, durum yönetimi ve veri erişimi ayrı sorumluluklarla düzenlenmiştir.

`Jetpack Compose UI → ViewModel → Repository → Retrofit → Open-Meteo API`

Hava Muhalefeti Endeksi ve etkinlik puanları ise hava durumu verileri üzerinden uygulama içinde hesaplanır.

## 🚀 Kurulum ve Çalıştırma

1. Repository'yi bilgisayarınıza indirin veya klonlayın.
2. Projeyi Android Studio ile açın.
3. Gradle senkronizasyonunun tamamlanmasını bekleyin.
4. Android emülatörü veya USB hata ayıklama etkinleştirilmiş bir Android cihaz seçin.
5. **Run** butonuyla uygulamayı çalıştırın.

Hava durumu verilerinin alınabilmesi için internet bağlantısı gereklidir.

## 🌐 Veri Kaynağı

Hava durumu ve şehir arama verileri [Open-Meteo](https://open-meteo.com/) servisleri üzerinden alınmaktadır.

## 👩‍💻 Geliştirici

**Menekşe Şevval Çorum**

İstanbul Üniversitesi — Bilgisayar Mühendisliği

Bilişim Kampı Android uygulama geliştirme çalışması.
