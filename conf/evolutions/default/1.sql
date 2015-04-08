# Hubs schema

# --- !Ups

CREATE SEQUENCE hub_id_seq;
CREATE TABLE hub (
    id integer NOT NULL DEFAULT nextval('hub_id_seq'),
    name varchar(255),
    description varchar(1000),
    ip varchar(15)
);

# --- !Downs

DROP TABLE hubs;
DROP SEQUENCE hub_id_seq;