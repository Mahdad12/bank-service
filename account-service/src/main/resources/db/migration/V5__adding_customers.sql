INSERT INTO customer (id, firstname, lastname, create_time, update_time) VALUES
   (NEXTVAL('customer_sequence'), 'John', '1 John', CURRENT_TIME, CURRENT_TIME),
   (NEXTVAL('customer_sequence'), 'John', '2 John', CURRENT_TIME, CURRENT_TIME),
   (NEXTVAL('customer_sequence'), 'John', '3 John', CURRENT_TIME, CURRENT_TIME),
   (NEXTVAL('customer_sequence'), 'John', '4 John', CURRENT_TIME, CURRENT_TIME);