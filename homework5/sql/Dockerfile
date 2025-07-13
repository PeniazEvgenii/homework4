FROM postgres:16.4-alpine

ADD ddl_00.sql /docker-entrypoint-initdb.d/
COPY ddl_01.sql /docker-entrypoint-initdb.d/
COPY ddl_02.sql /docker-entrypoint-initdb.d/
COPY ddl_03.sql /docker-entrypoint-initdb.d/