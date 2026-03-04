DROP TABLE IF EXISTS Messages;
CREATE TABLE Users(
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL
);

CREATE TABLE Channels(
    channel_id SERIAL PRIMARY KEY,
    channel_name TEXT NOT NULL
);

CREATE TABLE UsersInChannel(
    username TEXT REFERENCES Users(username) ON DELETE CASCADE,
    channel_id INT REFERENCES Channels(channel_id) ON DELETE CASCADE,
    PRIMARY KEY (username, channel_id)
);

CREATE TABLE Messages(
    message_id SERIAL PRIMARY KEY,
    message_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username TEXT REFERENCES Users(username) ON DELETE CASCADE,
    channel_id INT REFERENCES Channels(channel_id) ON DELETE CASCADE,
    UNIQUE (message_time, username, channel_id),
    content TEXT NOT NULL
);


CREATE TABLE Images(
    image_id SERIAL PRIMARY KEY,
    image_path TEXT NOT NULL
);

CREATE TABLE ImagesInMessage(
    message_id INT REFERENCES Messages(message_id) ON DELETE CASCADE,
    image_id INT REFERENCES Images(image_id) ON DELETE CASCADE,
    PRIMARY KEY (message_id, image_id)
);
