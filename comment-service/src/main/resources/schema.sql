CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS comments (
                                     comment_id UUID DEFAULT uuid_generate_v4(),
                                     post_id UUID NOT NULL,
                                     user_id UUID NOT NULL,
                                     content VARCHAR(255) NOT NULL,
                                     created_at TIMESTAMP NOT NULL,
                                     updated_at TIMESTAMP NOT NULL,
                                     PRIMARY KEY (post_id)
);