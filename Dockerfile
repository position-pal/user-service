FROM postgres:17.0-alpine@sha256:14195b0729fce792f47ae3c3704d6fd04305826d57af3b01d5b4d004667df174

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
