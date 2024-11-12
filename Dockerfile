FROM postgres:17.0-alpine@sha256:72ff2e0890e35d33be50ffbdd81e87b6cc613ed8ea983623dee95acd29b120e1

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
