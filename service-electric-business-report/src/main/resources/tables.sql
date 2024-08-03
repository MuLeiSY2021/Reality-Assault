CREATE TABLE goods_flow (
                            flow_id VARCHAR(64) PRIMARY KEY,
                            order_id VARCHAR(64),
                            product_id VARCHAR(64),
                            quantity INT,
                            flow_time DATETIME
);
CREATE TABLE money_flow (
                            flow_id VARCHAR(64) PRIMARY KEY,
                            order_id VARCHAR(64),
                            payment_method VARCHAR(64),
                            amount DECIMAL(10, 2),
                            flow_time DATETIME
);
CREATE TABLE coupon_flow (
                             flow_id VARCHAR(64) PRIMARY KEY,
                             coupon_id VARCHAR(64),
                             order_id VARCHAR(64),
                             discount DECIMAL(10, 2),
                             flow_time DATETIME
);
CREATE TABLE gold_flow (
                           flow_id VARCHAR(64) PRIMARY KEY,
                           order_id VARCHAR(64),
                           amount DECIMAL(10, 2),
                           flow_time DATETIME
);
CREATE TABLE diamond_flow (
                              flow_id VARCHAR(64) PRIMARY KEY,
                              order_id VARCHAR(64),
                              amount DECIMAL(10, 2),
                              flow_time DATETIME
);
