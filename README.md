# Redstone Books
<p align="center">
<img src="docs/logo.png" width="300">
</p>

Sistema de libros cinematográficos configurable por JSON para Minecraft Forge 1.20.1

Este mod permite mostrar libros animados en pantalla con texto, voz, animaciones, efectos de transición y configuración completa desde archivos JSON.

Pensado para:

- Cinemáticas
- Misiones
- RPG / aventuras
- Datapacks
- Mapas personalizados
- Servidores
- Mods con historia

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

1. Instalar Forge 1.20.1 (si no lo tienes instalado ya previamente, claro)
2. Copiar el archivo '.jar' en:
        .minecraft/mods/
    o en servidor:
        server/mods/
3. Iniciar el juego

---

## Uso básico

Comando:
    /redstonebook open \<id>
        
> [!NOTE]
> Por ejemplo:
> /redstonebook open guardian


El libro se carga desde:
    config/redstonebooks/books/guardian.json

---

## Carpeta de configuración

Se crea automáticamente:
    config/redstonebooks/books/

Cada archivo JSON define un libro con sus propias características.

> [!NOTE]
> Por ejemplo: \
> config/redstonebooks/books/guardian.json \
> config/redstonebooks/books/intro.json \
> config/redstonebooks/books/note1.json
    
## Carpeta de texturas
Las imágenes del libro se cargan desde el resourcepack: \
assets/redstonebooks/textures/gui/books/[tema]/ \
donde [tema] será el nombre del libro.
    
> [!NOTE]
> Por ejemplo: \
> assets/redstonebooks/textures/gui/books/guardian/book_cover.png \
> assets/redstonebooks/textures/gui/books/guardian/book_reading.png \
> assets/redstonebooks/textures/gui/books/guardian/anim/frame_001.png

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
    "folder": "guardian/anim",
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
    "mode": "single_page",
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
      "voiceSound": "redstonebooks:test",
      "voiceVolume": 1.0,
      "voicePitch": 1.0
    }
  ]
}

```

---

## Parámetros disponibles

 `meta` 
| Parámetro | Descripción |
|-----:|-----------|
| `title` | Título del libro |

 `theme`
| Parámetro | Descripción |
|-----:|-----------|
| `folder` | carpeta de texturas |

 `animation` 
| Parámetro | Descripción |
|-----:|-----------|
| `enabled` | usar animación |
| `folder` | carpeta de frames |
| `frameCount` | número de frames |
| `frameRate` | velocidad |

 `assets` 
| Parámetro | Descripción |
|-----:|-----------|
| `cover` | imagen libro cerrado |
| `reading` | imagen libro abierto |

`options`
| Parámetro | Descripción |
|-----:|-----------|
| `hideHud` | oculta HUD |
| `lockInput` | bloquea controles |
| `autoAdvance` | pasa páginas automáticamente |
| `pageTurnTicks` | tiempo cambio |
| `showPageIndicator` | mostrar número |

`style`
| Parámetro | Descripción |
|-----:|-----------|
| `textScale` | escala texto |
| `align` | LEFT / CENTER / RIGHT |
| `lineSpacing` | espacio líneas |
| `textColor` | color |
| `textShadow` | sombra |

`typewriter`
| Parámetro | Descripción |
|-----:|-----------|
| `enabled` | activar |
| `charsPerTick` | velocidad |
| `startDelay` | retraso |

`layout`
| Parámetro | Descripción |
|-----:|-----------|
| `openBookWidth` | tamaño del libro en ancho |
| `openBookHeight` | tamaño del libro en alto |
| `textStartX` | posición X |
| `textStartY` | posición Y |
| `textWidth` | ancho del texto |
| `textHeight` | alto del texto |

`transition`
| Parámetro | Descripción |
|-----:|-----------|
| `fadeOutTicks` | fade salida |
| `blackHoldTicks` | negro |
| `fadeInTicks` | fade entrada |

`pages`
| Parámetro | Descripción |
|-----:|-----------|
| `durationTicks` | duración |
| `text` | texto |
| `voiceSound` | sonido |
| `voiceVolume` | volumen |
| `voicePitch` | tono |

---

LICENCIA
All Rights Reserved
