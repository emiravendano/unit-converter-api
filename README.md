# Unit Converter API

API REST desarrollada en Java 17 y Spring Boot para la conversión de unidades de longitud, peso, temperatura y moneda.

## Descripción

El servicio permite convertir valores entre distintas unidades según el tipo especificado.  
Para la conversión de monedas se integra una API externa de tasas de cambio.

Se aplican principios REST, manejo adecuado de errores HTTP y patrones de diseño para mantener una arquitectura limpia y extensible.

---

## Arquitectura

Estructura organizada por capas:

```
src/main/java/com/reto/unitconverter/
├── controller/     
├── service/        
├── strategy/       
├── factory/        
├── client/         
├── dto/            
├── exception/      
└── config/         
```

### Patrones de diseño aplicados

- Strategy
- Factory
- Gateway/Client

---

## Requisitos

- Java 17 o superior
- Maven 3.8 o superior
- Docker (opcional)

---

## Ejecución

### Ejecutar localmente

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

### Ejecutar con Docker

```bash
docker build -t unit-converter .
docker run -p 8080:8080 unit-converter
```

---

## Endpoint principal

```
GET /api/convert
```

### Parámetros

- type: LENGTH, WEIGHT, TEMPERATURE, CURRENCY
- from: unidad de origen
- to: unidad de destino
- value: valor numérico positivo

Ejemplo:

```
GET /api/convert?type=LENGTH&from=METERS&to=FEET&value=10
```

---

## Unidades soportadas

- LENGTH: METERS, INCHES, FEET
- WEIGHT: KILOGRAMS, POUNDS, OUNCES
- TEMPERATURE: CELSIUS, FAHRENHEIT
- CURRENCY: USD, PEN, EUR, GBP, JPY, BRL, MXN

---

## Manejo de errores

La API devuelve códigos HTTP adecuados:

- 400: parámetros inválidos o unidades no soportadas
- 503: error en la comunicación con la API externa

---

## Postman

La colección con los casos probados y respuestas guardadas se encuentra en:

```
postman/unit-converter-api.postman_collection.json
```

---

## API externa de monedas

Se utiliza ExchangeRate-API para obtener tasas en tiempo real.

Configurar la API key en `application.properties`:

```properties
exchange.api.key=TU_API_KEY
```

---

## Ejecutar tests

```bash
mvn test
```