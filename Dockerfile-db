FROM postgres:17.2-alpine@sha256:d37d2c160d34430877c802e5adc22824a2ad453499db9bab1a2ceb2be6c1a46f

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
