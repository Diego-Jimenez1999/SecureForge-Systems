# SecureForge Systems

Monorepo con dos proyectos:

- `secureauth-desktop`: aplicación Java Swing con autenticación segura.
- `secureforge-web`: landing/catálogo web con arquitectura MVC en JavaScript.

## Estructura

```text
SecureForge_Systems/
  secureauth-desktop/
    pom.xml
    src/
  secureforge-web/
    index.html
    css/
    js/
      app.js
      controllers/
      models/
      views/
      services/
```

## Ejecución rápida

1. Backend desktop:
```bash
cd secureauth-desktop
mvn test
mvn exec:java
```

2. Frontend web:
```bash
cd secureforge-web
# abrir index.html con un servidor estático local
```

## Configuración backend

- Perfil por defecto: `dev`
- Perfil producción: `prod` (`application-prod.properties`)
- Variables de entorno soportadas:
  - `DB_URL`
  - `DB_USERNAME`
  - `DB_PASSWORD`
  - `APP_PROFILE`
