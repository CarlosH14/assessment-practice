-- Sample PostgreSQL Tables and Stored Procedures
-- For Assessment Practice

-- Create sample products table
CREATE TABLE IF NOT EXISTS products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create sample orders table
CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'pending'
);

-- Create sample order_items table
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(order_id),
    product_id INTEGER REFERENCES products(product_id),
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Stored Procedure: Decrease Stock with Validation
-- This procedure demonstrates proper stock management with locking
CREATE OR REPLACE FUNCTION decrease_stock(
    p_product_id INT,
    p_quantity INT
) RETURNS BOOLEAN AS $$
DECLARE
    current_stock INT;
BEGIN
    -- Lock the row for update to prevent race conditions
    SELECT stock INTO current_stock
    FROM products
    WHERE product_id = p_product_id
    FOR UPDATE;

    -- Check if product exists
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Product with ID % not found', p_product_id;
    END IF;

    -- Check if enough stock is available
    IF current_stock < p_quantity THEN
        RAISE NOTICE 'Insufficient stock. Available: %, Requested: %', current_stock, p_quantity;
        RETURN FALSE;
    END IF;

    -- Decrease the stock
    UPDATE products
    SET stock = stock - p_quantity,
        updated_at = CURRENT_TIMESTAMP
    WHERE product_id = p_product_id;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

-- Stored Procedure: Increase Stock
CREATE OR REPLACE FUNCTION increase_stock(
    p_product_id INT,
    p_quantity INT
) RETURNS BOOLEAN AS $$
BEGIN
    UPDATE products
    SET stock = stock + p_quantity,
        updated_at = CURRENT_TIMESTAMP
    WHERE product_id = p_product_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Product with ID % not found', p_product_id;
    END IF;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

-- Stored Procedure: Create Order with Transaction
-- Demonstrates transactional integrity
CREATE OR REPLACE FUNCTION create_order(
    p_customer_name VARCHAR,
    p_product_id INT,
    p_quantity INT
) RETURNS INT AS $$
DECLARE
    v_order_id INT;
    v_price DECIMAL(10, 2);
    v_total DECIMAL(10, 2);
    stock_available BOOLEAN;
BEGIN
    -- Get product price
    SELECT price INTO v_price
    FROM products
    WHERE product_id = p_product_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Product with ID % not found', p_product_id;
    END IF;

    -- Calculate total
    v_total := v_price * p_quantity;

    -- Try to decrease stock
    stock_available := decrease_stock(p_product_id, p_quantity);

    IF NOT stock_available THEN
        RAISE EXCEPTION 'Insufficient stock for product %', p_product_id;
    END IF;

    -- Create order
    INSERT INTO orders (customer_name, total_amount, status)
    VALUES (p_customer_name, v_total, 'confirmed')
    RETURNING order_id INTO v_order_id;

    -- Create order item
    INSERT INTO order_items (order_id, product_id, quantity, price)
    VALUES (v_order_id, p_product_id, p_quantity, v_price);

    RETURN v_order_id;
END;
$$ LANGUAGE plpgsql;

-- Index for performance optimization
CREATE INDEX IF NOT EXISTS idx_products_stock ON products(stock);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_date ON orders(order_date);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_order_items_product_id ON order_items(product_id);

-- Database Optimization Notes:
-- 1. Indexes: Create on foreign keys, frequently queried columns, and WHERE clause columns
-- 2. FOR UPDATE: Use row-level locking to prevent race conditions in concurrent transactions
-- 3. Transactions: Wrap related operations in transactions to ensure ACID properties
-- 4. Connection Pooling: Use HikariCP (default in Spring Boot) for efficient connection management
-- 5. Prepared Statements: Prevent SQL injection and improve performance through query caching
-- 6. Batch Operations: Use batch inserts/updates to reduce database round trips
-- 7. Query Analysis: Use EXPLAIN ANALYZE to understand and optimize query execution plans
-- 8. Normalization: Normalize data to reduce redundancy, denormalize for read-heavy workloads when needed
-- 9. Partitioning: Consider table partitioning for very large tables
-- 10. Monitoring: Monitor slow query logs and use tools like pg_stat_statements

-- Sample Data for Testing
INSERT INTO products (product_name, description, price, stock) VALUES
    ('Laptop', 'High-performance laptop', 999.99, 50),
    ('Mouse', 'Wireless mouse', 29.99, 200),
    ('Keyboard', 'Mechanical keyboard', 79.99, 100),
    ('Monitor', '27-inch 4K monitor', 399.99, 75),
    ('Headphones', 'Noise-cancelling headphones', 199.99, 150)
ON CONFLICT DO NOTHING;
