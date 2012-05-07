-- Student: Randy Hanak
-- G00526134
-- Project #1 Phase 2
-- Instructor - Dr. Jessica Lin
-- Email: rhanak@masonlive.gmu.edu

 drop table interested_in;
 drop table valid_at;
 drop table reside_at;
 drop table uses_credit;
 drop table subscribes_to;
 drop table credit_cards;
 drop table vouchers;
 drop table purchase_history;
 drop table reviews;
 drop table merchant_locations;
 drop table locations; 
 drop table customers;
 drop table deals;
 drop table merchants;
 drop table types;

/* 
 * Purchase History
 * Purchase_history (id, buy_date, quantity)
 * Primary_Key (id)
 */
 CREATE TABLE purchase_history
 (
 id VARCHAR2(25),
 buy_date TIMESTAMP,
 quantity INTEGER,
 Primary Key (id)
 );
 
/* 
 * Merchants
 * Merchants (id, name, address, city, state, country)
 * Primary_Key (id)
 */
CREATE TABLE merchants
(
 id VARCHAR2(25),
 name VARCHAR2(50),
 address VARCHAR2(50),
 city VARCHAR2(25),
 state VARCHAR2(25),
 country VARCHAR2(25),
 Primary Key (id)
);
 
/* 
 * Merchant Locations
 * Merchant_locations (address, city, state,country)
 * Primary_Key (address, city, state,country)
 */
 CREATE TABLE merchant_locations
 (
  address VARCHAR2(50),
  city VARCHAR2(25),
  state VARCHAR2(25),
  country VARCHAR2(25),
  CONSTRAINT pk_Merchant PRIMARY KEY (address,city,state,country)
 );
 
/* 
 * Types
 * Types (id, category)
 * Primary_Key (id)
 */
CREATE TABLE types
(
 id VARCHAR2(25),
 category VARCHAR2(45),
 Primary Key (id)
);

/* 
 * Deals
 * Deals (id, description, original_price, quantity, sale_start, sale_end, deal_price, expiration, type_id, merchant_id)
 * Primary_Key (id)
 * Foreign_Key (type_id) references types(id)
 * Foreign_Key (merchant_id) references merchants(id)
 */
 CREATE TABLE deals
 (
  id VARCHAR2(25),
  description VARCHAR2(200),
  original_price INTEGER,
  quantity INTEGER,
  sale_start TIMESTAMP,
  sale_end TIMESTAMP,
  deal_price INTEGER,
  expiration TIMESTAMP,
  type_id VARCHAR2(25),
  merchant_id VARCHAR2(25),
  Primary Key (id),
  Foreign Key (type_id) REFERENCES types(id),
  Foreign Key (merchant_id) REFERENCES merchants(id)
 );
 
 /* 
  * Vouchers
  * Vouchers (id, status, who_for, purchase_history, deal)
  * Primary_Key (id)
  * Foreign_Key (purchase_history) references purchase_history(id)
  * Foreign_Key (deal) references deals(id)
  */
  CREATE TABLE vouchers
  (
   id VARCHAR2(25),
   status VARCHAR2(25),
   who_for VARCHAR2(25),
   purchase_history VARCHAR2(25) NOT NULL,
   deal VARCHAR2(25),
   Primary Key (id),
   Foreign Key (purchase_history) REFERENCES purchase_history(id),
   Foreign Key (deal) REFERENCES deals(id)
  );
 
 /* 
  * Reside At
  * Reside_at (voucher_id, address, city, state, country)
  * Primary_Key (voucher_id, address, city, state, country)
  * Foreign_Key (voucher_id) references vouchers(id)
  * Foreign_Key (address, city, state, country) references merchant_locations(address,city,state,country)
  */
 CREATE TABLE reside_at
  (
   voucher_id VARCHAR2(25),
   address VARCHAR2(50),
   city VARCHAR2(25),
   state VARCHAR2(25),
   country VARCHAR2(25),
   CONSTRAINT pk_Reside PRIMARY KEY (voucher_id,address,city,state,country),
   Foreign Key (voucher_id) REFERENCES vouchers(id),
   Foreign Key (address,city,state,country) REFERENCES merchant_locations(address,city,state,country)
  );

/* 
 * Valid At
 * Valid_at (deal_id, address, city, state, country)
 * Primary_Key (deal_id, address, city, state, country)
 * Foreign_Key (deal_id) references deals(id)
 * Foreign_Key (address, city, state, country) references merchant_locations(address, city, state, country)
 */
CREATE TABLE valid_at
 (
  deal_id VARCHAR2(25),
  address VARCHAR2(50),
  city VARCHAR2(25),
  state VARCHAR2(25),
  country VARCHAR2(25),
  CONSTRAINT pk_Valid PRIMARY KEY (deal_id,address,city,state,country),
  Foreign Key (deal_id) REFERENCES deals(id),
  Foreign Key (address,city,state,country) REFERENCES merchant_locations(address,city,state,country)
 );

/* 
 * Customers
 * Customers (email, first_name, last_name, age, gender, address, city, state)
 * Primary_Key (email)
 */
 CREATE TABLE customers
 (
  email VARCHAR2(50),
  first_name VARCHAR2(50),
  last_name VARCHAR2(50),
  age INTEGER,
  gender VARCHAR2(6),
  address VARCHAR2(50),
  city VARCHAR2(25),
  state VARCHAR2(25),
  Primary Key (email)
 );

/* 
 * Credit Cards
 * Credit_cards (email, name, card_number, expiration, code)
 * Primary_Key (email, card_number)
 */
 CREATE TABLE credit_cards
 (
  email VARCHAR2(50),
  name VARCHAR2(50),
  card_number VARCHAR2(16),
  expiration DATE,
  code VARCHAR2(6),
  CONSTRAINT pk_Credit PRIMARY KEY (email, card_number)
 );
 
 /* 
  * Locations
  * Locations (city, state, country)
  * Primary_Key (city, state, country)
  */
  CREATE TABLE locations
  (
   city VARCHAR2(25),
   state VARCHAR2(25),
   country VARCHAR2(25),
   CONSTRAINT pk_Location PRIMARY KEY (city, state, country)
  );

 /* 
  * Reviews
  * Reviews (id, rating, review, customer, deal)
  * Primary_Key (id)
  * Foreign_Key (customer) references customers(email)
  * Foreign_Key (deal) references deals(id)
  */
  CREATE TABLE reviews
  (
   id VARCHAR2(25),
   rating INTEGER,
   review VARCHAR2(200),
   customer VARCHAR2(50),
   deal VARCHAR2(25),
   Primary Key (id),
   Foreign Key (customer) REFERENCES customers(email),
   Foreign Key (deal) REFERENCES deals(id)
  );
 
 /* 
  * Uses Credit
  * Uses_credit (email_customer, email_credit, card_number, default_flag)
  * Primary_Key (email_credit, card_number)
  * Foreign_Key (email_customer) references customers(email)
  * Foreign_Key (email_credit, card_number) references credit_cards(email, card_number)
  */
  CREATE TABLE uses_credit
  (
   email_customer VARCHAR2(50),
   email_credit VARCHAR2(50),
   card_number VARCHAR2(16),
   default_flag CHAR(1),
   CONSTRAINT pk_Uses_Credit PRIMARY KEY (email_credit, card_number),
   Foreign Key (email_customer) REFERENCES customers(email),
   Foreign Key (email_credit, card_number) REFERENCES credit_cards(email, card_number)
  );

/* 
 * Interested In
 * Interested_in (email, type_id)
 * Primary_Key (email, type_id)
 * Foreign_Key (email) references customers(email)
 * Foreign_Key (type_id) references types(id)
 */
 CREATE TABLE interested_in
 (
  email VARCHAR2(50),
  type_id VARCHAR2(25),
  CONSTRAINT pk_Interest PRIMARY KEY (email, type_id),
  Foreign Key (email) REFERENCES customers(email),
  Foreign Key (type_id) REFERENCES types(id)
 );

/* 
 * Subscribes To
 * Subscribes_to (email, city, state, country)
 * Primary_Key (email, city, state, country)
 * Foreign_Key (city, state, country) references locations(city, state, country)
 */
 CREATE TABLE subscribes_to
 (
  email VARCHAR2(50),
  city VARCHAR2(25),
  state VARCHAR2(25),
  country VARCHAR2(25),
  CONSTRAINT pk_Subscribe PRIMARY KEY (email,city, state, country),
  Foreign Key (city,state,country) REFERENCES locations(city,state,country)
 );
 
INSERT INTO merchant_locations
VALUES ('123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO merchant_locations
VALUES ('10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO merchant_locations
VALUES ('12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO merchant_locations
VALUES ('100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO merchant_locations
VALUES ('123 fairview ln', 'Dunn Loring', 'VA', 'US');

INSERT INTO locations VALUES ('Fairfax', 'VA', 'US');
INSERT INTO locations VALUES ('Dunn Loring', 'VA', 'US');
INSERT INTO locations VALUES ('Durham', 'NC', 'US');

INSERT INTO merchants VALUES ('1', 'Sport rock', '123 hq ln', 'Las Vegas', 'NV', 'US');
INSERT INTO merchants VALUES ('2', 'Hair Cuttery', '13 jackson ln', 'Minneapolus', 'MI', 'US');
INSERT INTO merchants VALUES ('3', 'Giant Food', '1 johnson st', 'Phoenix', 'AZ', 'US');
INSERT INTO merchants VALUES ('4', 'Wegmans', '2 jane st', 'Los Angeles', 'CA', 'US');
INSERT INTO merchants VALUES ('5', 'Whole Foods', '3 judge st', 'Colorado Springs', 'CO', 'US');
INSERT INTO merchants VALUES ('6', 'Safeway', '4 red st', 'New York', 'NY', 'US');
INSERT INTO merchants VALUES ('7', 'Shoppers', '5 blue st', 'Charleston', 'SC', 'US');
INSERT INTO merchants VALUES ('8', 'Nail Salon', '6 oak st', 'Atlanta', 'GA', 'US');
INSERT INTO merchants VALUES ('9', 'Pizza Hut', '7 joe st', 'Miami', 'FL', 'US');
INSERT INTO merchants VALUES ('10', 'Dominos', '8 sally st', 'Gumbo', 'LO', 'US');

INSERT INTO customers VALUES ('jill@robinson.com', 'Jill', 'Robinson', 20, 'Female', '1 robinson st', 'New York', 'NY');
INSERT INTO customers VALUES ('jack@robinson.com', 'Jack', 'Robinson', 21, 'Male', '1 robinson st', 'New York', 'NY');
INSERT INTO customers VALUES ('bob@baker.com', 'Bob', 'Baker', 25, 'Male', '5 caramel ave', 'Cincinnati', 'OH');
INSERT INTO customers VALUES ('jenny@baker.com', 'Jenny', 'Baker', 18, 'Female', '30 reeses st', 'Fairfax', 'VA');
INSERT INTO customers VALUES ('tim@robinson.com', 'Tim', 'Robinson', 16, 'Male', '1000 youngster ln', 'Fairfax', 'VA');
INSERT INTO customers VALUES ('jason@clark.com', 'Jason', 'Clark', 30, 'Male', '2 clark st', 'Manasses', 'VA');
INSERT INTO customers VALUES ('jordan@campbell.com', 'Jordan', 'Campbell', 20, 'Male', '1 robinson st', 'New York', 'NY');
INSERT INTO customers VALUES ('elayne@nickels.com', 'Elayne', 'Nickels', 20, 'Male', '10 nickels st', 'South Beach', 'Miami');
INSERT INTO customers VALUES ('jillian@clark.com', 'Jillian', 'Clark', 20, 'Male', '2 clark st', 'Manasses', 'VA');
INSERT INTO customers VALUES ('jaclyn@nickels.com', 'Jaclyn', 'Nickels', 20, 'Male', '10 nickels st', 'South Beach', 'Miami');

INSERT INTO types VALUES ('1', 'Grocery Stores');
INSERT INTO types VALUES ('2', 'Restaurants');
INSERT INTO types VALUES ('3', 'Sport Activites');
INSERT INTO types VALUES ('4', 'Beauty');

INSERT INTO deals VALUES ('1', 'Pay $10 for $20 in your favorite pizza from Pizza Hut', 20, 55, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 10, timestamp'2013-01-01 09:00:00.123456789', '2', '9');
	
INSERT INTO deals VALUES ('2', 'Pay $20 for 2 Large Pizzas and Chicken Wings', 32, 43, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 20, timestamp'2013-01-01 09:00:00.123456789', '2', '10');
	
INSERT INTO deals VALUES ('3', 'Pay $20 for $30 in Nail Care', 30, 72, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 20, timestamp'2013-01-01 09:00:00.123456789', '4', '8');
	
INSERT INTO deals VALUES ('4', 'Pay $50 for $72 in groceries', 72, 13, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 50, timestamp'2013-01-01 09:00:00.123456789', '1', '7');
	
INSERT INTO deals VALUES ('5', 'Pay $10 for $18 in baked goods', 18, 103, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 10, timestamp'2013-01-01 09:00:00.123456789', '1', '6');
	
INSERT INTO deals VALUES ('6', 'Pay $35 for a wine tasting event.', 35, 62, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 35, timestamp'2013-01-01 09:00:00.123456789', '1', '5');
	
INSERT INTO deals VALUES ('7', 'Pay $15 for $30 in beer', 30, 25, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 15, timestamp'2013-01-01 09:00:00.123456789', '1', '4');
	
INSERT INTO deals VALUES ('8', 'Pay $20 for $30 on your favorite frozen foods', 30, 15, timestamp'2012-01-08 09:00:00.123456789',
	timestamp'2012-03-01 09:00:00.123456789', 20, timestamp'2013-01-01 09:00:00.123456789', '1', '3');
	
INSERT INTO deals VALUES ('9', 'Pay $10 for a haircut', 18, 23, timestamp'2012-03-08 09:00:00.123456789',
	timestamp'2012-05-01 09:00:00.123456789', 10, timestamp'2013-01-01 09:00:00.123456789', '4', '2');
	
INSERT INTO deals VALUES ('10', 'Pay $50 for $80 in coupons to rock climb.', 80, 30, timestamp'2012-04-03 08:00:00.123456789',
	timestamp'2012-06-01 03:00:00.123456789', 50, timestamp'2012-10-01 09:00:00.123456789', '3', '1');

INSERT INTO purchase_history VALUES ('1', timestamp'2011-01-01 09:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('2', timestamp'2011-01-04 05:00:00.123456789', 2);
INSERT INTO purchase_history VALUES ('3', timestamp'2011-02-02 04:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('4', timestamp'2011-01-03 02:00:00.123456789', 3);
INSERT INTO purchase_history VALUES ('5', timestamp'2011-01-04 01:00:00.123456789', 2);
INSERT INTO purchase_history VALUES ('6', timestamp'2011-01-05 12:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('7', timestamp'2011-01-06 11:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('8', timestamp'2011-01-07 10:00:00.123456789', 2);
INSERT INTO purchase_history VALUES ('9', timestamp'2011-01-08 09:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('10', timestamp'2011-01-09 08:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('11', timestamp'2011-01-10 07:00:00.123456789', 3);
INSERT INTO purchase_history VALUES ('12', timestamp'2011-03-01 06:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('13', timestamp'2011-03-02 05:00:00.123456789', 4);
INSERT INTO purchase_history VALUES ('14', timestamp'2011-01-04 04:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('15', timestamp'2011-04-06 03:00:00.123456789', 2);
INSERT INTO purchase_history VALUES ('16', timestamp'2011-06-02 02:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('17', timestamp'2012-03-01 01:00:00.123456789', 1);
INSERT INTO purchase_history VALUES ('18', timestamp'2011-04-03 12:00:00.123456789', 1);

INSERT INTO vouchers VALUES ('1', 'current', 'bob', '1', '7');
INSERT INTO vouchers VALUES ('2', 'current', 'ben', '2', '6');
INSERT INTO vouchers VALUES ('3', 'current', 'jane', '2', '9');
INSERT INTO vouchers VALUES ('4', 'current', 'jamie', '3', '2');

INSERT INTO vouchers VALUES ('5', 'current', 'jack', '4', '1');
INSERT INTO vouchers VALUES ('6', 'used', 'jim', '4', '3');
INSERT INTO vouchers VALUES ('7', 'refunded', 'john', '4', '8');
INSERT INTO vouchers VALUES ('8', 'current', 'bob', '5', '2');
INSERT INTO vouchers VALUES ('9', 'expired', 'jill', '5', '7');
INSERT INTO vouchers VALUES ('10', 'current', 'renae', '6', '5');
INSERT INTO vouchers VALUES ('11', 'current', 'allison', '7', '1');
INSERT INTO vouchers VALUES ('12', 'current', 'jackie', '8', '2');
INSERT INTO vouchers VALUES ('13', 'current', 'jonathon', '8', '3');
INSERT INTO vouchers VALUES ('14', 'used', 'zed', '9', '4');
INSERT INTO vouchers VALUES ('15', 'used', 'abe', '10', '6');
INSERT INTO vouchers VALUES ('16', 'expired', 'lincoln', '11', '2');
INSERT INTO vouchers VALUES ('17', 'current', 'johnson', '11', '4');
INSERT INTO vouchers VALUES ('18', 'refunded', 'ted', '11', '2');

INSERT INTO vouchers VALUES ('19', 'current', 'ted', '12', '3');
INSERT INTO vouchers VALUES ('20', 'current', 'tina', '13', '4');
INSERT INTO vouchers VALUES ('21', 'current', 'tiger', '13', '5');
INSERT INTO vouchers VALUES ('22', 'refunded', 'tiny', '13', '6');
INSERT INTO vouchers VALUES ('23', 'current', 'tim', '13', '7');
INSERT INTO vouchers VALUES ('24', 'used', 'janis', '14', '8');
INSERT INTO vouchers VALUES ('25', 'refunded', 'john', '15', '9');
INSERT INTO vouchers VALUES ('26', 'current', 'megan', '15', '10');
INSERT INTO vouchers VALUES ('27', 'used', 'barlow', '16', '2');
INSERT INTO vouchers VALUES ('28', 'used', 'tom', '17', '3');
INSERT INTO vouchers VALUES ('29', 'expired', 'jackson', '18', '3');

INSERT INTO credit_cards VALUES ('jill@robinson.com', 'Jill Robinson', '5000123434561356', TO_DATE('12.03.2016', 'DD.MM.YYYY'), '134');
INSERT INTO credit_cards VALUES ('jack@robinson.com', 'Jack Robinson', '4100123434561356', TO_DATE('12.03.2014', 'DD.MM.YYYY'), '134');
INSERT INTO credit_cards VALUES ('jaclyn@nickels.com', 'Jacklyn Nickels', '3060123434561356', TO_DATE('12.03.2013', 'DD.MM.YYYY'), '134');
INSERT INTO credit_cards VALUES ('tim@robinson.com', 'Tim Robinson', '2000123434211356', TO_DATE('12.03.2015', 'DD.MM.YYYY'), '134');
INSERT INTO credit_cards VALUES ('jordan@campbell.com', 'Jordan Campbell', '3070123434561356', TO_DATE('12.03.2012', 'DD.MM.YYYY'), '134');

INSERT INTO uses_credit VALUES ('jill@robinson.com', 'jill@robinson.com', '5000123434561356', 'D');
INSERT INTO uses_credit VALUES ('jack@robinson.com', 'jack@robinson.com', '4100123434561356', 'D');
INSERT INTO uses_credit VALUES ('tim@robinson.com', 'tim@robinson.com', '2000123434211356', '');
INSERT INTO uses_credit VALUES ('jordan@campbell.com', 'jordan@campbell.com', '3070123434561356', 'D');
INSERT INTO uses_credit VALUES ('jaclyn@nickels.com', 'jaclyn@nickels.com', '3060123434561356', '');

INSERT INTO subscribes_to VALUES ('jill@robinson.com', 'Fairfax', 'VA', 'US');
INSERT INTO subscribes_to VALUES ('jack@robinson.com', 'Fairfax', 'VA', 'US');
INSERT INTO subscribes_to VALUES ('jason@clark.com', 'Fairfax', 'VA', 'US');
INSERT INTO subscribes_to VALUES ('tim@robinson.com','Dunn Loring', 'VA', 'US');
INSERT INTO subscribes_to VALUES ('jaclyn@nickels.com', 'Durham', 'NC', 'US');
INSERT INTO subscribes_to VALUES ('elayne@nickels.com', 'Durham', 'NC', 'US');
INSERT INTO subscribes_to VALUES ('jordan@campbell.com', 'Durham', 'NC', 'US');

INSERT INTO valid_at VALUES ('1', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO valid_at VALUES ('2', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO valid_at VALUES ('3','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO valid_at VALUES ('4', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO valid_at VALUES ('5', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO valid_at VALUES ('6', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO valid_at VALUES ('7', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO valid_at VALUES ('8','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO valid_at VALUES ('9', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO valid_at VALUES ('10', '123 fairview ln', 'Dunn Loring', 'VA', 'US');

INSERT INTO reside_at VALUES ('1', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('2', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO reside_at VALUES ('3','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO reside_at VALUES ('4', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('5', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO reside_at VALUES ('6', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('7', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO reside_at VALUES ('8','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO reside_at VALUES ('9', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('10', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO reside_at VALUES ('11', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('12', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO reside_at VALUES ('13','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO reside_at VALUES ('14', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('15', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO reside_at VALUES ('16','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO reside_at VALUES ('17', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('18', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO reside_at VALUES ('19', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('20', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO reside_at VALUES ('21','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO reside_at VALUES ('22', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('23', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO reside_at VALUES ('24', '123 memory ln', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('25', '10 orchard st', 'Durham', 'NC', 'US');
INSERT INTO reside_at VALUES ('26','12 bmw ln', 'Munich', 'Some Province', 'Germany');
INSERT INTO reside_at VALUES ('27', '100 university dr', 'Fairfax', 'VA', 'US');
INSERT INTO reside_at VALUES ('28', '123 fairview ln', 'Dunn Loring', 'VA', 'US');
INSERT INTO reside_at VALUES ('29', '123 memory ln', 'Fairfax', 'VA', 'US');

INSERT INTO reviews VALUES ('1', 4, 'It was great.', 'jill@robinson.com', '7');
INSERT INTO reviews VALUES ('2', 5, 'It was ok.', 'jack@robinson.com', '8');
INSERT INTO reviews VALUES ('3', 6, 'It was fine.', 'jillian@clark.com', '6');
INSERT INTO reviews VALUES ('4', 7, 'It was bad.', 'jordan@campbell.com', '5');
INSERT INTO reviews VALUES ('5', 8, 'It was great.', 'jenny@baker.com', '4');
INSERT INTO reviews VALUES ('6', 9, 'It was awesome.', 'jill@robinson.com', '3');
INSERT INTO reviews VALUES ('7', 10, 'It was amazing.', 'jason@clark.com', '10');
INSERT INTO reviews VALUES ('8', 4, 'It was terrible.', 'jill@robinson.com', '9');
INSERT INTO reviews VALUES ('9', 5, 'It was the best product ever!!!!!.', 'jason@clark.com', '8');
INSERT INTO reviews VALUES ('10',7, 'It was alright.', 'jill@robinson.com', '7');
INSERT INTO reviews VALUES ('11', 8, 'It was ok.', 'tim@robinson.com', '6');
INSERT INTO reviews VALUES ('12', 9, 'It was sweet hot sauce..', 'jill@robinson.com', '4');
INSERT INTO reviews VALUES ('13', 4, 'It was outlandishly sweetness.', 'elayne@nickels.com', '5');
INSERT INTO reviews VALUES ('14', 3, 'It was great.', 'jordan@campbell.com', '2');
INSERT INTO reviews VALUES ('15', 5, 'It was the greatest.', 'jaclyn@nickels.com', '1');

