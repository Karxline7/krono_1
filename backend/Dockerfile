# Paso 1: Compilación con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el POM y descargar las dependencias primero (aprovecha la caché)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar el archivo JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Paso 2: Imagen ligera para ejecutar la aplicación
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR generado desde el paso anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Comando para arrancar el backend con optimizaciones
ENTRYPOINT ["java", "-jar", "app.jar"]