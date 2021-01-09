CREATE TABLE comments (comment_id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
content VARCHAR(255),
 user_id UUID,
  post_id UUID,
   created_at TIMESTAMP,
    updated_at TIMESTAMP);