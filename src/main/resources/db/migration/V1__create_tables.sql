
CREATE TABLE customer (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL
);

CREATE TABLE address (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  address_type VARCHAR(8) NOT NULL,
  name VARCHAR(150) NOT NULL,
  address_number VARCHAR(8) NOT NULL,
  unit VARCHAR(20),
  city VARCHAR(50) NOT NULL,
  address_state VARCHAR(50) NOT NULL,
  zip_code VARCHAR(10) NOT NULL,
  country VARCHAR(50) NOT NULL,
  customer_id BIGINT NOT NULL,
  foreign key (customer_id) references customer(id) 
);