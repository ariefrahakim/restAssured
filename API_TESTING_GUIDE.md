# Panduan Pembuatan API Test Automation (RestAssured + TestNG + Gradle)

Dokumen ini menjelaskan struktur proyek otomasi pengujian API yang ada saat ini dan memberikan panduan langkah-demi-langkah tentang cara membuat, menjalankan, dan memelihara test case baru menggunakan arsitektur yang sudah terbentuk di dalam repository ini.

---

## 📂 Struktur Proyek & Peran Komponen

Proyek ini menggunakan bahasa pemrograman **Java** dengan framework **RestAssured** untuk HTTP request, **TestNG** sebagai test runner, **Gradle** sebagai build tool, dan **org.json** untuk manipulasi JSON payload.

Berikut adalah struktur folder utama beserta penjelasannya:

```text
restAssured/
├── build.gradle                          # Konfigurasi dependensi (RestAssured, TestNG, org.json, dll)
├── gradlew                               # Wrapper Gradle untuk menjalankan command build/test
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── body/                     # Representasi Request Body (Payload JSON)
│   │   │   │   ├── auth/                 # Payload khusus Auth (e.g., LoginBody)
│   │   │   │   ├── sportActivity/        # Payload khusus Sport Activity (e.g., CreateSportActivityBody)
│   │   │   │   └── sportCategory/        # Payload khusus Sport Category
│   │   │   └── utils/                    # Utility helper classes
│   │   │       ├── ConfigReader.java     # Helper untuk membaca config.properties
│   │   │       └── Utils.java            # Helper acak (random title, manipulasi tanggal)
│   │   └── resources/
│   │       ├── config.properties         # Konfigurasi lingkungan (baseUrl, kredensial)
│   │       └── json/                     # Penyimpanan state sementara (token, activity_id, dll)
│   └── test/
│       └── java/
│           ├── base/
│           │   └── BaseTest.java         # Base Setup Class (Inisialisasi Base URI)
│           ├── runner/
│           │   ├── testng.xml            # Konfigurasi Suite TestNG utama
│           │   └── basicLogin.xml        # Konfigurasi Suite TestNG alternatif
│           └── tests/                    # Implementasi Test Case
│               ├── auth/                 # Unit test untuk Auth (e.g., LoginTest)
│               ├── sportActivity/        # Unit test CRUD Sport Activity (e.g., CreateSportActivityTest)
│               └── sportCategory/        # Unit test CRUD Sport Category
```

### 1. Representasi Request Body (`src/main/java/body/`)
Kita tidak menulis string JSON secara mentah (hardcoded) di dalam file test. Kita menggunakan kelas bantuan di dalam package `body` yang memanfaatkan `org.json.JSONObject` untuk menyusun payload secara dinamis dan rapi.
Contoh: `CreateSportActivityWithParamBody` dapat menerima parameter seperti `title` agar datanya dinamis di setiap test case.

### 2. Utilitas (`src/main/java/utils/`)
*   **`ConfigReader.java`**: Digunakan untuk membaca nilai properti dari `src/resources/config.properties`.
*   **`Utils.java`**: Menyediakan generator data dinamis seperti judul unik menggunakan UUID (`generateRandomTitle()`) atau menghitung tanggal di masa mendatang (`getDateAfterSevenDays()`, `getDateAfterFourDays()`).

### 3. State-sharing File JSON (`src/resources/json/`)
Untuk menangani ketergantungan antar-API (seperti membutuhkan JWT Token hasil Login untuk memanggil API CRUD, atau membutuhkan `activity_id` hasil Create untuk memanggil API Get/Update/Delete), kita menyimpan nilai-nilai tersebut secara fisik ke dalam file JSON di folder `src/resources/json/`. Cara ini membuat test case dapat berjalan secara modular dan terurut.

### 4. Base Test (`src/test/java/base/BaseTest.java`)
Merupakan superclass untuk semua kelas test. Mengatur konfigurasi global seperti `RestAssured.baseURI` secara otomatis sebelum kelas test dijalankan menggunakan anotasi `@BeforeClass`.

---

## 🛠️ Langkah Demi Langkah Membuat API Test Baru

Berikut adalah panduan standar untuk menambahkan test automation API baru (misalnya membuat modul baru bernama **Sport Venue**):

### Langkah 1: Tambahkan Konfigurasi Baru (Opsional)
Jika Anda membutuhkan endpoint baru atau kredensial khusus, tambahkan di dalam file `src/resources/config.properties`:
```properties
baseUrl=https://sport-reservation-2-api-bootcamp.do.dibimbing.id/api/v1
email=syukran@gmail.com
password=syukran123
# Tambahkan properti baru di sini jika ada
```

### Langkah 2: Buat Class Request Body
Buatlah class representasi body di folder `src/main/java/body/{nama_modul}/`.
Gunakan `org.json.JSONObject` untuk menyusun struktur JSON-nya.

**Contoh: `CreateVenueBody.java`**
```java
package body.venue;

import org.json.JSONObject;
import utils.Utils;

public class CreateVenueBody {

    // Body statis/bawaan
    public JSONObject getBody() {
        JSONObject body = new JSONObject();
        body.put("name", Utils.generateRandomTitle());
        body.put("location", "Jakarta Barat");
        body.put("capacity", 50);
        return body;
    }

    // Body dinamis dengan parameter
    public JSONObject getBodyWithParam(String venueName) {
        JSONObject body = getBody();
        body.put("name", venueName); // Override nama venue
        return body;
    }
}
```

### Langkah 3: Buat Class Test Baru
Buat class test di folder `src/test/java/tests/{nama_modul}/` yang mewarisi/extends `BaseTest`.

**Contoh: `CreateVenueTest.java`**
```java
package tests.venue;

import base.BaseTest;
import body.venue.CreateVenueBody;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateVenueTest extends BaseTest {

    private String token;

    @BeforeClass
    public void setupTest() throws Exception {
        // 1. Inisialisasi Base URI (otomatis terpanggil dari BaseTest)
        super.setup();

        // 2. Baca token autentikasi dari token.json hasil LoginTest
        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();
    }

    @Test
    public void testCreateVenueSuccess() throws IOException {
        // Ambil request body dinamis
        CreateVenueBody bodyObj = new CreateVenueBody();
        String uniqueName = "Venue-" + Utils.generateRandomTitle();
        JSONObject requestBody = bodyObj.getBodyWithParam(uniqueName);

        // Kirim request POST menggunakan RestAssured
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/venues/create") // Sesuaikan endpoint
                .then()
                .extract().response();

        // Log response ke console untuk debugging
        System.out.println("Response: " + response.asString());

        // --- VALIDASI / ASSERTIONS ---
        // 1. Assert Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code harus 200");

        // 2. Assert Error status di body response
        Assert.assertFalse(response.jsonPath().getBoolean("error"), "Status error harus false");

        // 3. Assert Response Message
        Assert.assertEquals(response.jsonPath().getString("message"), "venue saved successfully");

        // --- SIMPAN STATE UNTUK TEST BERIKUTNYA ---
        // Ambil ID venue baru dari response dan simpan ke file JSON
        String venueId = response.jsonPath().getString("result.id");
        System.out.println("Venue ID saved: " + venueId);

        JSONObject stateJson = new JSONObject();
        stateJson.put("venue_id", venueId);

        try (FileWriter file = new FileWriter("src/resources/json/venue_id.json")) {
            file.write(stateJson.toString(4));
            file.flush();
        }
    }
}
```

### Langkah 4: Daftarkan Test Case di TestNG Suite
Agar test baru dapat berjalan dalam urutan yang benar (misalnya Login -> Create Venue -> Get/Update/Delete Venue), daftarkan kelas tersebut ke dalam XML suite TestNG (`src/test/java/runner/testng.xml`).

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
<suite name="API Test Suite" verbose="1" parallel="false">
    <test name="Sport Venue Tests">
        <classes>
            <!-- Login harus dijalankan pertama untuk men-generate token.json -->
            <class name="tests.auth.LoginTest"/>
            <class name="tests.venue.CreateVenueTest"/>
            <!-- Tambahkan test case lainnya secara terurut di sini -->
        </classes>
    </test>
</suite>
```

---

## 🚀 Cara Menjalankan Pengujian

Anda dapat menjalankan pengujian menggunakan Gradle CLI lewat Terminal:

### 1. Menjalankan Suite Default (`testng.xml`)
Perintah ini akan menjalankan seluruh rangkaian pengujian yang aktif di file `testng.xml`:
```bash
./gradlew test
```

### 2. Menjalankan Suite Tertentu secara Dinamis
Proyek ini mendukung pergantian suite XML via command-line parameter `-Dsuite={nama_file_tanpa_ekstensi}`:
```bash
./gradlew test -Dsuite=basicLogin
```

### 3. Menjalankan Spesifik Class atau Package
Untuk menjalankan pengujian tertentu tanpa memedulikan XML suite:
```bash
./gradlew test --tests "tests.sportActivity.CreateSportActivityTest"
```

---

## 📌 Best Practices & Konvensi Pengkodean

1.  **Dilarang Meng-hardcode Data Sensitif & Base URL**: Gunakan selalu `ConfigReader.getProperty()` untuk mengambil nilai dari `config.properties`.
2.  **Gunakan Generator Data Unik**: Untuk menghindari bentrokan data (misal duplikasi nama aktivitas/kategori), selalu gunakan kelas `Utils` untuk menghasilkan judul unik (`Utils.generateRandomTitle()`) atau penentuan tanggal otomatis.
3.  **Ketergantungan Data Menggunakan JSON**: Saling bertukar ID/token antar-API test harus melalui file penyimpanan sementara di `src/resources/json/` agar test class dapat dieksekusi secara independen atau terstruktur dengan rapi.
4.  **Logging yang Informatif**: Selalu cetak response string (`response.asString()`) dan ID penting ke console menggunakan `System.out.println()` untuk mempercepat investigasi jika test gagal.
5.  **Assertive Assertions**: Selalu tambahkan pesan deskriptif di parameter terakhir `Assert` (misalnya: `Assert.assertEquals(actual, expected, "Pesan error jika asersi gagal")`) agar mempermudah analisis saat automation diintegrasikan dengan CI/CD pipeline.
