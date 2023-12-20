
# Nagihan Arabaci Final Projesi (E-Commerce Application)

Turkcell Geleceği Yazanlar İleri Seviye Kotlin Bootomcap programı için geliştirilmiş bir projedir.
Proje kapsamında verilen hazır Restfull Api yapısı kullanılarak, bir e-ticaret uygulaması geliştirilmesi amaçlanmıştır.




## Kullanılan Yapı ve Paketler

- MVVM
- Retrofit
- Dagger Hilt
- ViewModel
- LiveData
- Firebase

  
## API Kullanımı

#### Api Url

```http
  https://dummyjson.com/
```

#### Giriş Yap

```http
  POST /auth/login
```

#### Tüm Ürünleri Getir

```http
  GET /products
```

#### Kategoriye Göre Ürün Listesi

```http
  GET /products/category/{category}
```

#### Kategoriler Listesi

```http
  GET /products/categories
```

#### Aktif Kullanıyı Getir

```http
  GET /users/{id}
```

#### Kullanıcının Sepetteki Ürünleri

```http
  GET /carts/user/{id}
```

#### Kullanıcının Sepetteki Ürünleri

```http
  GET /carts/user/{id}
```

#### Aktif Kullanıcı Bilgilerini Güncelleme

```http
  PUT /users/{id}
```

#### Arama

```http
  GET /products/search
```

  
## Yazarlar ve Teşekkür

- Uygulama tasarımında [figma tasarımı](https://www.figma.com/file/Dm1Qvx7ob4QPDvyqL5izTC/Online-Groceries-App-UI-(Community)?type=design&node-id=1%3A682&mode=design&t=bWlyuIdI43D18K4o-1) referans alınmıştır.

  