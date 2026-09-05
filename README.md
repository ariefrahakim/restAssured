# API Test Automation (RestAssured + TestNG + Gradle)

Proyek ini adalah repositori otomatisasi pengujian API (API Test Automation) yang dikembangkan menggunakan **Java**, **RestAssured**, **TestNG**, dan **Gradle**.

## 📖 Dokumentasi Utama

Untuk panduan lengkap mengenai struktur proyek, cara menulis test case baru mengikuti arsitektur yang ada, serta praktik terbaik pengodean, silakan baca:

👉 **[Panduan Pembuatan API Test Automation (API_TESTING_GUIDE.md)](API_TESTING_GUIDE.md)**

## 🚀 Cara Cepat Menjalankan Pengujian

Pastikan Anda telah menginstal Java JDK (minimum versi 11 atau lebih baru).

### Menjalankan Semua Pengujian (Default Suite)
Gunakan Gradle Wrapper yang sudah disediakan di dalam repositori:
```bash
./gradlew test
```

### Menjalankan Test Suite Tertentu
Jika ingin menjalankan file XML suite TestNG tertentu (misal `basicLogin.xml`):
```bash
./gradlew test -Dsuite=basicLogin
```

### Menjalankan Spesifik Class Test
```bash
./gradlew test --tests "tests.sportActivity.CreateSportActivityTest"
```

## 🛠️ Tech Stack & Dependensi Utama

- **Java JDK 11+**
- **RestAssured**: HTTP Client untuk melakukan testing REST API.
- **TestNG**: Test runner & assertion framework.
- **Gradle**: Build tool & dependency management.
- **org.json**: Manipulasi & pembentukan data payload JSON secara dinamis.
