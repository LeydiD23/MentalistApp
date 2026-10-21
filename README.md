# El Mentalista — App Android

Aplicación Android estilo Netflix inspirada en la serie **El Mentalista (The Mentalist)**. Incluye portada con slider, personajes, momentos con video, un minijuego de observación y una sección de contacto.

## Vista previa
- **Portada**: slider con imágenes destacadas y botones reproducir / más info.
- **Personajes**: listado con foto y detalle de cada personaje.
- **Momentos**: escenas de la serie con reproducción de video.
- **Juega conmigo**: minijuego "Sigue la carta" — memoriza, sigue la mezcla y toca dónde terminó.
- **Contrátame**: datos de contacto y currículum (PDF).

---

## Tecnologías utilizadas

| Área | Tecnología |
|------|------------|
| Lenguaje | **Kotlin** |
| Interfaz | **XML layouts** + **ViewBinding** (sin Compose) |
| Entorno | **Android Studio**, **Gradle Kotlin DSL** |
| Build | AGP 9.4.1, Gradle 9.6.0 |
| SDK | `minSdk 26`, `targetSdk / compileSdk 37` |
| Navegación | **Navigation Component** + **BottomNavigationView** |
| Listas | **RecyclerView** |
| Slider | **ViewPager2** |
| Video | **Media3 / ExoPlayer** |
| Imágenes | **Glide** |
| UI | **Material Components**, **ConstraintLayout** |
| Estilo | Tema oscuro tipo Netflix (negro `#000000` + rojo `#E50914`) |

---

## Cómo descargar e instalar la app

### Opción 1 — Instalar el APK en tu teléfono (más fácil)

1. Descarga el archivo **`app-debug.apk`** desde la sección
   [**Releases**](../../releases) del repositorio (o desde la carpeta de salida
   del build, ver Opción 2).
2. En tu teléfono, permite la instalación de **orígenes desconocidos**
   (Ajustes → Seguridad → Instalar apps desconocidas).
3. Abre el archivo `.apk` descargado y toca **Instalar**.
4. Abre la app **El Mentalista** desde tu pantalla de inicio.

> Requiere Android 8.0 (API 26) o superior.

### Opción 2 — Compilar desde el código fuente

**Requisitos**
- [Android Studio](https://developer.android.com/studio) (versión reciente)
- JDK 11 o superior
- SDK de Android (API 37)

**Pasos**
```bash
# 1. Clona el repositorio
git clone https://github.com/LeydiD23/MentalistApp.git
cd MentalistApp

# 2. Compila el APK de depuración (Windows)
gradlew.bat assembleDebug

# En macOS / Linux
./gradlew assembleDebug
```

El APK se genera en:
```
app/build/outputs/apk/debug/app-debug.apk
```

**Instalar por ADB en un dispositivo/emulador conectado**
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

O simplemente abre el proyecto en **Android Studio** y pulsa **Run ▶**.

---

## Estructura del proyecto

```
app/src/main/
├── java/com/example/mentalistapp/
│   ├── SplashActivity.kt          # Pantalla de inicio (logo + nombre)
│   ├── MainActivity.kt            # Contenedor con navegación inferior
│   ├── adapter/                   # Adaptadores (Slider, Personajes, Momentos)
│   ├── fragment/                  # Vistas: Portada, Personajes, Momentos, Juego, Contrátame
│   └── model/                     # Datos y repositorios
├── res/
│   ├── layout/                    # Diseños XML
│   ├── drawable/                  # Imágenes e íconos vectoriales
│   ├── navigation/nav_graph.xml   # Grafo de navegación
│   ├── raw/                       # Videos de los momentos
│   └── values/                    # Colores, strings, tema
└── assets/cv_leydi.pdf            # Currículum en PDF
```

---

## Autora

**Leydi Massiel Diaz Rodriguez**
- Correo: leidymasieldiaz@gmail.com
- Teléfono: 8099172769

---

## Licencia

Proyecto con fines educativos. *El Mentalista (The Mentalist)* y sus personajes
pertenecen a sus respectivos propietarios (CBS / Warner Bros. Television).
