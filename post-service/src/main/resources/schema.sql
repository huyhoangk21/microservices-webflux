CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS posts (
                                     post_id UUID DEFAULT uuid_generate_v4(),
                                     image_url VARCHAR(255) NOT NULL,
                                     description VARCHAR(255) NOT NULL,
                                     user_id UUID NOT NULL,
                                     created_at TIMESTAMP NOT NULL,
                                     updated_at TIMESTAMP NOT NULL,
                                     PRIMARY KEY (post_id)
);