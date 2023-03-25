INSERT INTO TRANSACTION_DATA (id, account_id, amount, create_time, update_time) VALUES
  (NEXTVAL('transaction_sequence'), 1, 120, CURRENT_TIME, CURRENT_TIME),
  (NEXTVAL('transaction_sequence'), 1, 110, CURRENT_TIME, CURRENT_TIME),
  (NEXTVAL('transaction_sequence'), 1, -30, CURRENT_TIME, CURRENT_TIME),
  (NEXTVAL('transaction_sequence'), 2, 240, CURRENT_TIME, CURRENT_TIME),
  (NEXTVAL('transaction_sequence'), 3, 56, CURRENT_TIME, CURRENT_TIME);