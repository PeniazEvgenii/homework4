\c shop
CREATE TABLE app.files
(
    id uuid,
    status character varying(10) NOT NULL,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    CONSTRAINT files_pk PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS app.files
    OWNER to postgres;