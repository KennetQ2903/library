# 📚 Bibliosoft Library API

**Bibliosoft** es un microservicio REST desarrollado con Spring Boot que permite gestionar una biblioteca con usuarios, libros y autores.  
Incluye persistencia con H2, documentación con Swagger y empaquetado para Docker.

---

## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database (en memoria)
- Lombok
- Swagger (OpenAPI 3)
- Maven
- Docker

---

## ▶️ Cómo ejecutar el proyecto

### 🔧 Requisitos previos

- Java JDK 17+
- Maven o usar el wrapper (`./mvnw`)
- Docker (opcional)
- ENV file

Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

```bash
JWT_SECRET_KEY=YOUR_SECRET_KEY
```

---

### 🏃 Ejecutar en modo desarrollo

```bash
./mvnw spring-boot:run
````

La aplicación se ejecutará en:
📍 [http://localhost:8080](http://localhost:8080)

---

### 📦 Empaquetar el proyecto

```bash
./mvnw clean package
```

Esto generará un archivo `.jar` en la carpeta `target`.

---

## 🧪 Base de datos H2

El sistema usa una base de datos en memoria.
Puedes acceder a la consola de H2 en:

📍 `http://localhost:8080/h2-console`

* **JDBC URL:** `jdbc:h2:mem:testdb`

---

## 📑 Documentación Swagger

Puedes acceder a la documentación interactiva de la API:

📍 `http://localhost:8080/swagger-ui.html`
📍 `http://localhost:8080/swagger-ui/index.html` (alternativa)

---

## 🐳 Docker

### 🔨 Crear imagen Docker

```bash
docker build -t springservice .
```

---

### 🚀 Ejecutar el contenedor

```bash
docker run -it --rm -p 8080:8080 --name bibliosoftService springservice
```

Una vez corriendo, accede a:

📍 [http://localhost:8080](http://localhost:8080)

---

## ✅ Endpoints principales

| Recurso | Endpoint base  |
| ------- | -------------- |
| Users   | `/api/users`   |
| Books   | `/api/books`   |
| Authors | `/api/authors` |
| Auth    | `/api/auth`    |

---

## 👨‍💻 Autores

Proyecto desarrollado por:
- **Kennet Quiróz**
- **Lisbeth Hernandez**
- **Diego Lopez**

