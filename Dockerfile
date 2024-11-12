FROM postgres:17.0-alpine@sha256:3d5a60633216631bf980dc412f20b864563c60733a966dd2eb33bf30f0106b03

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
