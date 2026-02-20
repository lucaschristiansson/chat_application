CREATE TABLE Users(
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL
);

CREATE TABLE Channels(
    channelID SERIAL PRIMARY KEY,
    channelName TEXT NOT NULL
);

CREATE TABLE UsersInChannel(
    username INT REFERENCES Users(username) ON DELETE CASCADE,
    channelID INT REFERENCES Channels(channelID) ON DELETE CASCADE,
    PRIMARY KEY (username, channelID)
);

CREATE TABLE Messages(
    messageID SERIAL PRIMARY KEY,
    messageTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username INT REFERENCES Users(username) ON DELETE CASCADE,
    channelID INT REFERENCES Channels(channelID) ON DELETE CASCADE,
    UNIQUE (messageTime, username, channelID),

    content TEXT NOT NULL,
    imagePath TEXT

);