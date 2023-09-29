---------------------------------
```
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
sudo apt-get update
sudo apt-get -y install postgresql
```
---------------------------------
```
CREATE DATABASE centaur;
CREATE DATABASE test;
ALTER USER postgres WITH PASSWORD 'Kill';
```
---------------------------------
```
ALTER TABLE t_product ADD COLUMN product_vector tsvector
GENERATED ALWAYS AS (to_tsvector('english', coalesce(product_name, '') || ' ' || coalesce(description, ''))) STORED;

CREATE INDEX product_vector_idx ON t_product USING GIN (product_vector);

CREATE OR REPLACE FUNCTION pull_element(product_id bigint) RETURNS text
LANGUAGE plpgsql AS $$
DECLARE deleted_item text;
DECLARE items_count int;
BEGIN
  SELECT items[1] INTO deleted_item
  FROM t_product
  WHERE id = product_id FOR UPDATE;
  UPDATE t_product SET items = items[2:] WHERE id = product_id;
  SELECT array_length(items, 1) INTO items_count
  FROM t_product
  WHERE id = product_id FOR UPDATE;
  IF items_count IS NULL THEN
  UPDATE t_product SET status = 'NOT_ACTIVE' WHERE id = product_id;
  END IF;
  RETURN deleted_item;
END;
$$;

CREATE OR REPLACE PROCEDURE update_overdue_orders(overdue_date date)
LANGUAGE plpgsql AS $$
BEGIN
  UPDATE t_purchase p SET status = 'CONFIRMED' WHERE p.date < overdue_date AND p.status = 'IN_PROGRESS';
  UPDATE t_purchase p SET status = 'DECLINED' WHERE p.date < overdue_date AND p.status = 'DISPUTE';
  UPDATE t_product pr SET rating_good = rating_good + 1 WHERE pr = (SELECT pr FROM t_product pr JOIN t_purchase p ON pr.id = p.product_id WHERE p.date < overdue_date AND p.status = 'IN_PROGRESS');
  UPDATE t_product pr SET rating_bad = rating_bad + 1 WHERE pr = (SELECT pr FROM t_product pr JOIN t_purchase p ON pr.id = p.product_id WHERE p.date < overdue_date AND p.status = 'DISPUTE');
END;
$$;

CREATE OR REPLACE PROCEDURE confirm_order(order_id bigint)
LANGUAGE plpgsql AS $$
DECLARE product t_product;
BEGIN
  SELECT pr.* INTO product FROM t_product pr JOIN t_purchase p ON pr.id = p.product_id WHERE p.id = order_id;
  UPDATE t_purchase p SET status = 'CONFIRMED' WHERE p.id = order_id AND p.status IN('IN_PROGRESS', 'DISPUTE');
  UPDATE t_product pr SET rating_good = rating_good + 1 WHERE pr.id = product.id;
  UPDATE t_user u SET rating = rating + 1 WHERE u.id = product.owner_id;
END;
$$;

CREATE OR REPLACE PROCEDURE decline_order(order_id bigint)
LANGUAGE plpgsql AS $$
DECLARE product t_product;
BEGIN
  SELECT pr.* INTO product FROM t_product pr JOIN t_purchase p ON pr.id = p.product_id WHERE p.id = order_id;
  UPDATE t_purchase p SET status = 'DECLINED' WHERE p.id = order_id AND p.status = 'DISPUTE';
  UPDATE t_product pr SET rating_bad = rating_bad + 1 WHERE pr.id = product.id;
  UPDATE t_user u SET rating = rating - 1 WHERE u.id = product.owner_id;
END;
$$;
```
---------------------------------
```
SELECT product_name, description, ts_rank_cd(product_vector, query) as rank FROM t_product, to_tsquery('cc:*|1') query 
WHERE query @@ product_vector ORDER BY rank DESC;
```
---------------------------------
```
SELECT pull_element(18);
```
---------------------------------
```
INSERT INTO t_role(id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER'), (3, 'ROLE_VENDOR');
```
---------------------------------
```
LISTEN dispute_message_notify;
SELECT 1;

SELECT * FROM pg_listening_channels();
```
---------------------------------