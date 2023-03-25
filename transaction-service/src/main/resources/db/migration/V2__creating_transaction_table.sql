CREATE TABLE TRANSACTION_DATA (
  id BIGINT DEFAULT nextval('transaction_sequence') PRIMARY KEY,
  account_id BIGINT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  create_time timestamp with time zone not null,
  update_time timestamp with time zone not null
);