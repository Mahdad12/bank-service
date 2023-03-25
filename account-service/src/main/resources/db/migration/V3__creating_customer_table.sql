CREATE TABLE customer (
  id BIGINT DEFAULT nextval('customer_sequence') PRIMARY KEY,
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  create_time timestamp with time zone not null,
  update_time timestamp with time zone not null
);