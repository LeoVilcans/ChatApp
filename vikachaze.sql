CREATE DATABASE IF NOT EXISTS vikachaze;
USE vikachaze;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
	status VARCHAR(100),
	pfp LONGBLOB
);

CREATE TABLE rooms (
	id INT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	icon LONGBLOB
);

CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
	room_id INT NOT NULL,
	text VARCHAR(250) NOT NULL,
	sent_time TIMESTAMP NOT NULL,
    attachment LONGBLOB,
    user_id INT NOT NULL,

    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
 
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
	sent_time TIMESTAMP NOT NULL,
    attachment LONGBLOB,
    user_id INT NOT NULL,
	title VARCHAR(100) NOT NULL,
    text VARCHAR(1000) NOT NULL,
    
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
