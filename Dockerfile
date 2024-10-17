FROM postgres:14.1-alpine@sha256:578ca5c8452c08a4e0f5e65b55dce5e1812fe63c8fee40ea837641031598e51e

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
