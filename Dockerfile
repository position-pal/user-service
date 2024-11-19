FROM postgres:17.1-alpine@sha256:0d9624535618a135c5453258fd629f4963390338b11aaffb92292c12df3a6c17

COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 5432
