CREATE TABLE question
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    description TEXT,
    gmt_create BIGINT,
    creator INT,
    comment_count INT,
    view_count INT,
    like_count INT,
    tag VARCHAR(256)
);