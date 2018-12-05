CREATE DATABASE example_ecsite
  TEMPLATE template0 ENCODING 'UTF-8'
  LC_COLLATE 'ja_JP.UTF-8' LC_CTYPE 'ja_JP.UTF-8'
;

CREATE ROLE example_ecsite_user WITH LOGIN PASSWORD 'example_ecsite_user';


\connect example_ecsite example_ecsite_user


--DROP TABLE kind;
CREATE TABLE kind(
    kind_id	    VARCHAR(10),
    kind_name   VARCHAR(30),
    PRIMARY KEY(kind_id)
)
;

--DROP TABLE item;
CREATE TABLE item(
    item_id     VARCHAR(20),
    item_name   VARCHAR(30),
    item_price  NUMERIC(13, 0),
    item_stock  INTEGER,
    PRIMARY KEY(item_id)
)
;

--DROP TABLE kind_item;
CREATE TABLE kind_item(
    kind_id	    VARCHAR(10),
    item_id	    VARCHAR(10),
    PRIMARY KEY(kind_id, item_id)
)
;

--DROP TABLE order_history;
CREATE TABLE order_history(
    order_history_id           BIGSERIAL,
    order_history_timestamptz  TIMESTAMPTZ,
    order_history_send_name    VARCHAR(300),
    order_history_send_addr    VARCHAR(300),
    order_history_send_email   VARCHAR(100),
    PRIMARY KEY(order_history_id)
)
;

--ALTER TABLE order_history ALTER order_history_id TYPE bigint;

--DROP TABLE order_history_item;
CREATE TABLE order_history_item(
    order_history_id       BIGINT,
    item_id                VARCHAR(20),
    item_price             NUMERIC(13, 0),
    PRIMARY KEY(order_history_id, item_id)
)
;

