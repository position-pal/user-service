FROM postgres:14.13-alpine@sha256:2d28cf41ec156f9a6dc9489fd3e5b59888f89229d68fc622efbfbe576e114b13

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
