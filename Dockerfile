FROM postgres:17.2-alpine@sha256:e7897baa70dae1968d23d785adb4aeb699175e0bcaae44f98a7083ecb9668b93

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
