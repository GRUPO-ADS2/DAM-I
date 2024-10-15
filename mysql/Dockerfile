FROM mysql:8.0.33

# Copiar el script SQL al contenedor
COPY init.sql /docker-entrypoint-initdb.d/

# Configurar el entorno de MySQL
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_PASSWORD=mysql
ENV MYSQL_DATABASE=bdinnovaschool
