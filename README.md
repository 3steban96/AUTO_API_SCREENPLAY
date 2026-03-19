# Automatización CRUD de API con Screenplay y Serenity Rest - Reservas Sofka

Este proyecto valida el ciclo completo de CRUD (POST, GET, PUT, DELETE) para la entidad de Ciudades (`Cities`) en el servicio `locations-service` de la aplicación **[Reservas Sofka](https://github.com/Sherman97/reservasSofka)**.

## 📋 Prerrequisitos

Para ejecutar esta automatización con éxito, asegúrese de que los siguientes servicios del backend estén activos:
1. **Auth Service** (Puerto 3001) - Para la generación del token JWT.
2. **Locations Service** (Puerto 3004) - La API objetivo.
3. **MariaDB** (Puerto 3306/3307) - Base de datos para la persistencia.
4. **RabbitMQ** (Puerto 5672) - Broker de eventos (requerido por el backend).

## ⚙️ Configuración

El proyecto utiliza `serenity.properties` para la configuración:
- `restapi.baseurl`: `http://localhost:3004` (Servicio de Ubicaciones).
- `restapi.authurl`: `http://localhost:3001` (Servicio de Autenticación).

## 🚀 Ejecución

Ejecute el siguiente comando desde la raíz de la carpeta `CRUD`:

```powershell
gradle.bat clean test aggregate
```

## 📊 Reportes

Después de la ejecución, el reporte detallado estará disponible en:
`target/site/serenity/index.html`

## 🧩 Estructura del Proyecto
- **Features**: `src/test/resources/features/cities_crud.feature`
- **Tasks**: `com.reservassofka.crud.tasks` (Create, Get, Update, Delete, Auth)
- **Models**: `com.reservassofka.crud.models.CityInfo`
- **Questions**: `com.reservassofka.crud.questions` (ServerResponse, CityDetails)
