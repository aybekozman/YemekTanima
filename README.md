# 🍽️ Yemek Tarifi ve Besin Değerleri Uygulaması

Bu proje, kullanıcıların:
- Yemek fotoğrafı yükleyerek
- Ya da listeden yemek seçerek  
  yemek tarifleri ve besin değerlerini görüntüleyebileceği bir Android uygulamasıdır.

##  Özellikler
- Firebase Authentication ile kullanıcı girişi
- Oturum açık kalma (auto login)
- Yapay zeka modeli ile yemek tanıma
- Firebase Firestore’dan tarif ve besin değeri çekme
- Arama özellikli yemek listesi
- Modern ve sade arayüz

## Kullanılan Teknolojiler
- **Kotlin**
- **Android Studio**
- **Firebase Auth**
- **Firebase Firestore**
- **RecyclerView**
- **Material Design**

##  Uygulama Akışı
1. Kullanıcı giriş yapar
2. Fotoğraf yükler **veya** tarif listesinden seçim yapar
3. Yemek tarifi ve besin değerleri görüntülenir

## 📂 Proje Yapısı

```text
YemekTanima/
├── .gitignore
├── gradle/
├── gradle.properties
├── settings.gradle.kts
├── build.gradle.kts
├── gradlew
├── gradlew.bat
└── app/
    ├── build/
    ├── libs/
    ├── src/
    │   ├── androidTest/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/yemektarifi/
    │   │   │       ├── LoginActivity.kt
    │   │   │       ├── RegisterActivity.kt
    │   │   │       ├── MainActivity.kt
    │   │   │       ├── RecipesActivity.kt
    │   │   │       ├── FoodDetailsActivity.kt
    │   │   │       └── FoodAdapter.kt
    │   │   ├── res/
    │   │   │   ├── layout/
    │   │   │   │   ├── activity_login.xml
    │   │   │   │   ├── activity_register.xml
    │   │   │   │   ├── activity_main.xml
    │   │   │   │   ├── activity_recipes.xml
    │   │   │   │   ├── activity_food_details.xml
    │   │   │   │   └── item_food.xml
    │   │   │   ├── drawable/
    │   │   │   ├── font/
    │   │   │   └── values/
    │   │   └── AndroidManifest.xml
    │   └── test/
    └── build.gradle.kts
```


## Uygulama Akışı

- Kullanıcı giriş yapar / kayıt olur

- Oturum açık kalır

Ana ekranda:

- 📸 Fotoğraf yükleyerek yemek tanıma

- 📋 Tarifler butonu ile yemek seçimi

Seçilen yemeğin:

- Malzemeleri

- Yapılış tarifi

- Besin değerleri gösterilir


## 📌 Kurulum
1. Projeyi klonlayın

git clone https://github.com/kullaniciAdi/yemekTarifi.git

2. Android Studio’da açın
3. Firebase bağlantılarını kendi projenize göre ayarlayın
4. Çalıştırın

## 👤 Geliştirici
- **Aybek Özman**
