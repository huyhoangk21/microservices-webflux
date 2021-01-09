CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS post_likes (
                                        like_id UUID DEFAULT uuid_generate_v4(),
                                        post_id UUID NOT NULL,
                                        user_id UUID NOT NULL,
                                        PRIMARY KEY (like_id)
);

CREATE TABLE IF NOT EXISTS comment_likes (
                                          like_id UUID DEFAULT uuid_generate_v4(),
                                          comment_id UUID NOT NULL,
                                          user_id UUID NOT NULL,
                                          PRIMARY KEY (like_id)
);