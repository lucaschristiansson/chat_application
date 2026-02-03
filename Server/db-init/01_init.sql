CREATE TABLE Users(
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL
);

CREATE TABLE Channels(
    id INT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE UsersInChannel(
    username TEXT REFERENCES Users(username) ON DELETE CASCADE,
    channel INT REFERENCES Channels(id) ON DELETE CASCADE,
    PRIMARY KEY (username, channel)
);

CREATE TABLE Messages(
    time TIMESTAMP, /* save time with timzone in mind, convert to UTC */
    sender_name TEXT REFERENCES Users(username) ON DELETE CASCADE,
    channel_id INT REFERENCES Channels(id) ON DELETE CASCADE,
    PRIMARY KEY (time, sender_name, channel_id),

    content TEXT NOT NULL,
    image_url TEXT

);