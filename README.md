# 📖 Redstone Books

<p align="center">
  <img src="docs/logo.png" width="260">
</p>

## 🎬 Preview
![Preview](docs/preview.gif)

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-1.20.1-brightgreen">
  <img src="https://img.shields.io/badge/Loader-Forge-blue">
  <img src="https://img.shields.io/badge/Version-1.1.0-orange">
  <img src="https://img.shields.io/badge/Status-Stable-success">
</p>

<p align="center">
<b>🇬🇧 Cinematic book system for Minecraft</b><br>
<b>🇪🇸 Sistema de libros cinematográficos para Minecraft</b>
</p>

---

![Banner](docs/banner.png)

## ✨ Overview

Redstone Books permite crear **libros cinematográficos totalmente personalizados** dentro de Minecraft mediante configuración JSON.

Diseñado para:

* 🎬 Cinemáticas
* 🧭 RPG / aventuras
* 🧱 Datapacks
* 🌍 Servidores
* 🗺️ Mapas personalizados
* 📜 Mods narrativos

---

## 🚀 Características principales

* 📖 Animaciones de libro por frames
* 🔊 Audio por página (voz/narración)
* ✍️ Efecto máquina de escribir
* 🎨 Layout configurable
* 🌫️ Transiciones (fade in/out)
* 📚 Soporte para múltiples libros
* 🧠 Configuración completa mediante JSON
* 🎮 Control del HUD e input

---

## 📦 Instalación

1. Instalar Forge 1.20.1
2. Copiar el `.jar` en:

```text
.minecraft/mods/
```

Servidor:

```text
server/mods/
```

3. Iniciar el juego

---

## 🎮 Uso básico

```text
/redstonebook open <id>
```

Ejemplo:

```text
/redstonebook open guardian
```

Archivo de configuración:

```text
config/redstonebooks/books/<id>.json
```

---

## 📁 Estructura del sistema (v1.1.0)

Cada libro es independiente:

```text
config/redstonebooks/books/<id>.json
```

```text
assets/redstonebooks/
├── textures/books/<id>/
└── sounds/books/<id>/
```

---

## 🧪 Ejemplo de JSON

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

---

## 🎵 Sonido

Ejemplo de `sounds.json`:

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

---

## 🧪 Testing

```text
/playsound redstonebooks:books.guardian.guardian_p1 master @p
```

---

## 📸 Showcase

![Showcase](docs/showcase.png)

---

## 📄 Licencia

All Rights Reserved

---

## 📜 Changelog

### 🆕 v1.1.0

* Correcciones de animación (texturas rosa/negro)
* Nueva estructura modular por libro
* Sistema de audio completamente funcional

### 🚀 v1.0.0

* Sistema base de libros cinematográficos
* Configuración mediante JSON
* Soporte para múltiples páginas
* Renderizado de texto y efectos
* Sistema de audio inicial
