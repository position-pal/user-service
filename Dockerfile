FROM postgres:17.2-alpine@sha256:7691da1fee373a0d2c7f661521e17d51947e2a1b6e2d7e25ea3be18108348397

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
