CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
                                        user_id UUID DEFAULT uuid_generate_v4(),
                                        username VARCHAR(255) NOT NULL UNIQUE,
                                        email VARCHAR(255) NOT NULL UNIQUE,
                                        password VARCHAR(255) NOT NULL,
                                        created_at TIMESTAMP NOT NULL,
                                        updated_at TIMESTAMP NOT NULL,
                                        PRIMARY KEY (user_id)
);