CREATE TABLE ACCOUNT (
  id BIGINT DEFAULT nextval('account_sequence') PRIMARY KEY,
  balance DECIMAL(10,2) NOT NULL,
  customer_id BIGINT NOT NULL,
  create_time timestamp with time zone not null,
  update_time timestamp with time zone not null
);