# FastAPI Clean Architecture Project

Este es un proyecto FastAPI con estructura de arquitectura limpia (Clean Architecture).

## Estructura del Proyecto

```
fastapi-project/
├── app/
│   ├── __init__.py
│   ├── controllers/          # Controladores - Endpoints de la API
│   │   ├── __init__.py
│   │   └── user_controller.py
│   ├── services/             # Servicios - Lógica de negocio
│   │   ├── __init__.py
│   │   └── user_service.py
│   ├── repositories/         # Repositorios - Acceso a datos
│   │   ├── __init__.py
│   │   └── user_repository.py
│   └── utils/                # Utilidades - Funciones auxiliares
│       ├── __init__.py
│       └── validators.py
├── main.py                   # Punto de entrada de la aplicación
├── requirements.txt          # Dependencias del proyecto
├── .gitignore
└── README.md
```

## Arquitectura Limpia

El proyecto sigue los principios de Clean Architecture:

- **Controllers (Controladores)**: Manejan las peticiones HTTP y respuestas. Define los endpoints de la API.
- **Services (Servicios)**: Contienen la lógica de negocio. Orquestan las operaciones entre repositorios.
- **Repositories (Repositorios)**: Gestionan el acceso a los datos. En este ejemplo usa datos en memoria, pero puede conectarse a bases de datos.
- **Utils (Utilidades)**: Funciones auxiliares y herramientas reutilizables.

## Instalación

### Requisitos
- Python 3.8 o superior

### Pasos

1. Crear un entorno virtual:
```bash
python -m venv venv
```

2. Activar el entorno virtual:
```bash
# En Linux/Mac
source venv/bin/activate

# En Windows
venv\Scripts\activate
```

3. Instalar dependencias:
```bash
pip install -r requirements.txt
```

## Ejecución

### Iniciar el servidor de desarrollo:
```bash
python main.py
```

O usando uvicorn directamente:
```bash
uvicorn main:app --reload
```

El servidor estará disponible en: `http://localhost:8000`

## Documentación de la API

FastAPI genera automáticamente documentación interactiva:

- **Swagger UI**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc

## Endpoints Disponibles

### General
- `GET /` - Página de inicio
- `GET /health` - Estado del servicio

### Usuarios
- `GET /api/users/` - Obtener todos los usuarios
- `GET /api/users/{user_id}` - Obtener un usuario por ID
- `POST /api/users/` - Crear un nuevo usuario
- `PUT /api/users/{user_id}` - Actualizar un usuario
- `DELETE /api/users/{user_id}` - Eliminar un usuario

## Ejemplo de Uso

### Crear un usuario
```bash
curl -X POST "http://localhost:8000/api/users/" \
  -H "Content-Type: application/json" \
  -d '{"name": "Carlos Hernández", "email": "carlos@example.com"}'
```

### Obtener todos los usuarios
```bash
curl "http://localhost:8000/api/users/"
```

### Obtener un usuario específico
```bash
curl "http://localhost:8000/api/users/1"
```

## Tecnologías Utilizadas

- **FastAPI**: Framework web moderno y rápido
- **Uvicorn**: Servidor ASGI
- **Pydantic**: Validación de datos
- **Python-dotenv**: Gestión de variables de entorno

## Desarrollo

### Agregar nuevas funcionalidades

1. **Crear un nuevo repositorio** en `app/repositories/`
2. **Crear un nuevo servicio** en `app/services/`
3. **Crear un nuevo controlador** en `app/controllers/`
4. **Registrar el controlador** en `main.py` usando `app.include_router()`

### Ejemplo de extensión

Para agregar funcionalidad de productos:

1. Crear `app/repositories/product_repository.py`
2. Crear `app/services/product_service.py`
3. Crear `app/controllers/product_controller.py`
4. Agregar en `main.py`: `app.include_router(product_controller.router)`

## Mejoras Futuras

- Agregar base de datos (PostgreSQL, MySQL, MongoDB)
- Implementar autenticación y autorización (JWT)
- Agregar tests unitarios y de integración
- Implementar logging
- Agregar caché (Redis)
- Dockerizar la aplicación
- Implementar CI/CD

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.
