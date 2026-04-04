# Redstone Books
<p align="center">
<img src="docs/logo.png" width="300">
</p>

Pensado para:
- Cinemáticas
- Misiones
- RPG / aventuras
- Datapacks
- Mapas personalizados
- Servidores
- Mods con historia
  
![Banner](docs/banner.png)

Sistema de libros cinematográficos configurable por JSON para Minecraft Forge 1.20.1  
Este mod permite mostrar libros animados en pantalla con texto, voz, animaciones, efectos de transición y configuración completa desde archivos JSON.

---

## Características

- Animación de apertura de libro por frames
- Animación inversa al cerrar
- Texto configurable por página
- Voz por página
- Efecto máquina de escribir
- Fade in / fade out configurable
- Layout configurable por libro
- Posición del texto configurable
- Colores configurables
- Escala configurable
- Indicador de página opcional
- Configuración por JSON
- Soporte para múltiples libros
- HUD oculto automático
- Bloqueo de input opcional

---

## Instalación

1. Instalar Forge 1.20.1  
2. Copiar el archivo `.jar` en:
    .minecraft/mods/
    o en el servidor:
    server/mods/


3. Iniciar el juego

---

## Uso básico

Comando:
    `/redstonebook open <id>`


> [!NOTE]  
> Ejemplo:
> ```
> /redstonebook open guardian
> ```

El libro se carga desde:
    `config/redstonebooks/books/<id>.json`

---

## 📁 Estructura del sistema (v1.1.0)

Cada libro es completamente independiente.

### Configuración
    `config/redstonebooks/books/<id>.json`

### Resourcepack
assets/redstonebooks/ \
├── `textures/books/<id>/` \
└── `sounds/books/<id>/`

---

## Carpeta de configuración

Se crea automáticamente:
    config/redstonebooks/books/

Ejemplos:\
    config/redstonebooks/books/guardian.json \
    config/redstonebooks/books/intro.json \
    config/redstonebooks/books/note1.json

---

## Carpeta de texturas


`assets/redstonebooks/textures/books/<id>/`


Ejemplo:


assets/redstonebooks/textures/books/guardian/book_cover.png \
assets/redstonebooks/textures/books/guardian/book_reading.png \
assets/redstonebooks/textures/books/guardian/anim/frame_001.png


---

## Carpeta de sonidos


`assets/redstonebooks/sounds/books/<id>/`


Ejemplo:


assets/redstonebooks/sounds/books/guardian/guardian_p1.ogg \
assets/redstonebooks/sounds/books/guardian/guardian_p2.ogg \
assets/redstonebooks/sounds/books/guardian/guardian_p3.ogg


---

## ⚠️ IMPORTANTE

- No usar `textures/gui/...`
- Usar siempre:


`textures/books/<id>/`


---

## Ejemplo completo de JSON

```json
{
  "id": "guardian",

  "meta": {
    "title": "CRÓNICAS DEL GUARDIÁN"
  },

  "theme": {
    "folder": "guardian"
  },

  "animation": {
    "enabled": true,
    "folder": "anim",
    "frameCount": 143,
    "frameRate": 1
  },

  "assets": {
    "cover": "book_cover.png",
    "reading": "book_reading.png"
  },

  "options": {
    "hideHud": true,
    "lockInput": true,
    "autoAdvance": true,
    "pageTurnTicks": 8,
    "showPageIndicator": true
  },

  "style": {
    "textScale": 1.0,
    "align": "LEFT",
    "lineSpacing": 1,
    "textColor": "#1E1A16",
    "textShadow": false
  },

  "typewriter": {
    "enabled": true,
    "charsPerTick": 2,
    "startDelay": 10
  },

  "layout": {
    "mode": "book",
    "openBookWidth": 0.74,
    "openBookHeight": 0.74,
    "textStartX": 0.55,
    "textStartY": 0.14,
    "textWidth": 0.30,
    "textHeight": 0.60
  },

  "transition": {
    "useFadeBetweenAnimationAndReading": true,
    "fadeOutTicks": 18,
    "blackHoldTicks": 8,
    "fadeInTicks": 20
  },

  "pages": [
    {
      "durationTicks": 200,
      "text": "Texto de ejemplo",
      "voiceSound": "redstonebooks:books.guardian.guardian_p1",
      "voiceVolume": 1.0,
      "voicePitch": 1.0
    }
  ]
}
```

## 🎵 Sonido

Ejemplo de sounds.json:
```json
{
  "books.guardian.guardian_p1": {
    "sounds": [
      {
        "name": "books/guardian/guardian_p1",
        "stream": true
      }
    ]
  }
}
```

## 🧪 Testing
/playsound redstonebooks:books.guardian.guardian_p1 master @p \
Showcase

### LICENCIA

All Rights Reserved

## CHANGELOG

### v1.1.0

### 🔧 Estabilidad
- Recuperada la base estable del proyecto desde GitHub
- Corregidas las rutas de texturas de animación del libro
- Solucionado el problema de animaciones (cuadros rosa/negro)

### 🎬 Sistema de libros
- Actualizado el archivo de ejemplo guardian.json
- Soporte completo para animaciones por libro
- Soporte para audio por página funcional

### 📁 Nueva estructura de contenidos
- Ahora cada libro puede tener su propio directorio
- Separación de configuración, texturas y sonidos por libro
- Sistema preparado para múltiples libros personalizados

Estructura recomendada:

```text
config/redstonebooks/books/<id>.json
assets/redstonebooks/textures/books/<id>/
assets/redstonebooks/sounds/books/<id>/
```
🎵 Audio
- Sistema de reproducción validado con resourcepacks externos
- Compatible con sounds.json del resourcepack

⚠️ Cambios importantes:
- La estructura de rutas ha cambiado respecto a versiones anteriores
- Es necesario adaptar los resourcepacks existentes

### v1.0.0

🚀 Lanzamiento inicial
- Sistema base de libros cinematográficos
- Carga de libros mediante JSON
- Soporte para múltiples páginas
- Sistema de autoavance de páginas
  
🎬 Renderizado
- Renderizado de libro en pantalla completa
- Fondo personalizable por libro
- Sistema de layout configurable
  
✍️ Texto
- Renderizado de texto multilínea
- Alineación configurable (LEFT, CENTER, RIGHT)
- Escala de texto configurable
- Colores personalizados
  
⌨️ Efectos
- Efecto máquina de escribir
- Control de velocidad de escritura
- Delay inicial configurable
  
🎵 Audio
- Reproducción de sonido por página
- Control de volumen y tono
  
⚙️ Opciones
- Ocultar HUD automáticamente
- Bloqueo de input del jugador
- Indicador de página
  
📁 Configuración
- Sistema basado en archivos JSON
- Soporte para múltiples libros independientes

La estructura de rutas ha cambiado respecto a versiones anteriores. \
Es necesario adaptar los resourcepacks existentes.
