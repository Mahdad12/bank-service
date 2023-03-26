INSERT INTO customer (id, firstname, lastname, create_time, update_time) VALUES
   (NEXTVAL('customer_sequence'), 'William', 'Bible', CURRENT_TIME, CURRENT_TIME),
   (NEXTVAL('customer_sequence'), 'Clayton', 'Cook', CURRENT_TIME, CURRENT_TIME),
   (NEXTVAL('customer_sequence'), 'Rhonda', 'Granados', CURRENT_TIME, CURRENT_TIME),
   (NEXTVAL('customer_sequence'), 'Barbara', 'Cortez', CURRENT_TIME, CURRENT_TIME);