FROM postgres:15

ENV POSTGRES_PASSWORD=3ea14367A4
ENV POSTGRES_DB=easybank

RUN mkdir -p /docker-entrypoint-initdb.d

COPY ./src/main/java/org/youcode/easybank/db/migration/*.sql /docker-entrypoint-initdb.d/

EXPOSE 5433