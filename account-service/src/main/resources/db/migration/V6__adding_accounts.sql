INSERT INTO account (id, balance, customer_id, create_time, update_time) VALUES
  (NEXTVAL('account_sequence'), 200.00, 1, CURRENT_TIME, CURRENT_TIME),
  (NEXTVAL('account_sequence'), 240.00, 2, CURRENT_TIME, CURRENT_TIME),
  (NEXTVAL('account_sequence'), 56.00, 3, CURRENT_TIME, CURRENT_TIME);