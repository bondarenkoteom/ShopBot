<h2>1. First of all we need to execute 2 commands 
in postgres database to set up indexes for full text search</h2>
<br>
<h3>
ALTER TABLE t_product ADD COLUMN product_vector tsvector
GENERATED ALWAYS AS (to_tsvector('english', coalesce(product_name, '') || ' ' || coalesce(description, ''))) STORED;

CREATE INDEX product_vector_idx ON t_product USING GIN (product_vector);
</h3>

SELECT product_name, description, ts_rank_cd(product_vector, query) as rank FROM t_product, to_tsquery('cc:*|1') query 
WHERE query @@ product_vector ORDER BY rank DESC;