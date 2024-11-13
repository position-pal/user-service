FROM postgres:17.0-alpine@sha256:d388be15cfb665c723da47cccdc7ea5c003ed71f700c5419bbd075033227ce1f

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
