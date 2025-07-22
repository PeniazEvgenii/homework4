\c shop

CREATE SCHEMA app
    AUTHORIZATION postgres;

CREATE TABLE app.users
(
    id uuid,
    email character varying(128) NOT NULL,
    firstname character varying(64) NOT NULL,
    lastname character varying(64) NOT NULL,
    birth_date date NOT NULL,
    gender character varying(16) NOT NULL,
    password character varying(64) NOT NULL,
    discount numeric(3, 1),
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE (email)
);

ALTER TABLE IF EXISTS app.users
    OWNER to postgres;