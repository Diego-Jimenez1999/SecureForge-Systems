# secureforge-api

Backend Spring Boot para `secureforge-web`.

## Endpoints

- `POST /api/briefings`
- `POST /api/payments` (multipart con `paymentData` y `receipt` opcional)
- `GET /api/resources`
- `POST /api/contact`

## Variables de entorno (correo)

- `MAIL_FROM_ADDRESS`
- `MAIL_FROM_PASSWORD`
- `MAIL_ADMIN_ADDRESS`

## Ejecutar

```bash
cd secureforge-api
mvn spring-boot:run
```

## Frontend

`secureforge-web/js/config/appConfig.js` apunta por defecto a `http://localhost:8080`.
