# Examples

Example configuration files and resourcepack structure for Redstone Books.

---

## 📘 Configuración de libros

Copia los archivos JSON en:

```text
config/redstonebooks/books/
```
Ejemplo:
```text
config/redstonebooks/books/guardian.json
```
---

## 🎨 Resourcepack

Los recursos (texturas y sonidos) deben estar en un resourcepack activo.

Estructura correcta:

assets/redstonebooks/ \
├── textures/ \
│   └── books/ \
│       └── <id>/\
│           ├── book_cover.png \
│           ├── book_reading.png \
│           └── anim/ \
│               ├── frame_001.png \
│               ├── frame_002.png \
│               └── ... \
│ \
├── sounds/ \
│   └── books/ \
│       └── <id>/ \
│           ├── sound1.ogg \
│           ├── sound2.ogg \
│           └── ... \
│ \
└── sounds.json

---

## 🧱 Ejemplo real (guardian)

textures:
```text assets/redstonebooks/textures/books/guardian/```

sounds:
```text assets/redstonebooks/sounds/books/guardian/```

---

## 🔊 sounds.json

Ejemplo mínimo:

```json
{
  "books.guardian.guardian_p1": {
    "sounds": [
      {
        "name": "redstonebooks:books/guardian/guardian_p1",
        "stream": true
      }
    ]
  }
}
