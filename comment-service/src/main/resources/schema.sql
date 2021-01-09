CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS comments (
    comment_id UUID DEFAULT uuid_generate_v4(),
    content VARCHAR(255),
    user_id UUID,
    post_id UUID,
    created_at TIMESTAMP ,
    updated_at TIMESTAMP,
    PRIMARY KEY (comment_id)
);