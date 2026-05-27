# Paso 1: Compilación con Node.js
FROM node:20-alpine AS build
WORKDIR /app

# Instalar el CLI de Angular global en el contenedor
RUN npm install -g @angular/cli@21.1.5 --legacy-peer-deps

# Copiar archivos de dependencias
COPY package*.json ./
RUN npm install --legacy-peer-deps

# Forzar la instalación de angular/animations que necesita PrimeNG/Platform-browser
RUN npm install @angular/animations@21.2.0 --legacy-peer-deps

# Copiar el resto del código
COPY . .

# Ahora ejecutamos el build de producción usando el comando nativo de npm
RUN npm run build -- --configuration=production

# Paso 2: Servidor Nginx ligero para servir el Frontend
FROM nginx:alpine

# CAMBIO AQUÍ: Usamos "Malla_Turnos" tal como está en el angular.json
COPY --from=build /app/dist/Malla_Turnos/browser /usr/share/nginx/html

EXPOSE 4200

# Reemplazar el puerto por defecto de Nginx (80) al 4200
RUN sed -i 's/listen[:[:space:]]*80;/listen 4200;/g' /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]
