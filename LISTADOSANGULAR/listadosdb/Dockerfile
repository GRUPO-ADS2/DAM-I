# Etapa 1: Construcción de la aplicación Angular
# Usar una imagen de Node.js como entorno de construcción
FROM node:20.17.0 AS builder

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar los archivos package.json y package-lock.json al contenedor
COPY package*.json ./

# Instalar dependencias
RUN npm install

# Copiar todos los archivos del proyecto al contenedor
COPY . .

# Compilar la aplicación Angular
RUN npm run build

# Etapa 2: Configuración del servidor para SSR
# Usar una nueva imagen de Node.js para la producción
FROM node:20.17.0 AS production

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar solo los archivos necesarios desde la etapa de construcción
COPY --from=builder /app/dist ./dist
COPY package*.json ./

# Instalar solo dependencias de producción
RUN npm install --only=production

# Exponer el puerto en el que correrá la aplicación
EXPOSE 4000

# Definir la variable de entorno NODE_ENV como producción
ENV NODE_ENV=production

# Comando para ejecutar la aplicación usando el script SSR
CMD ["npm", "run", "serve:ssr:listadosdb"]
