CREATE TABLE app.products
(
    id bigserial,
    name character varying(128) NOT NULL,
    price numeric(10,2) NOT NULL,
    quantity int,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    CONSTRAINT products_pk PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS app.products
    OWNER to postgres;