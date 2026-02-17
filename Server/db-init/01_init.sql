CREATE TABLE Users(
    userID SERIAL PRIMARY KEY,
    username TEXT UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE Channels(
    channelID SERIAL PRIMARY KEY,
    channelName TEXT NOT NULL
);

CREATE TABLE UsersInChannel(
    userID INT REFERENCES Users(userID) ON DELETE CASCADE,
    channelID INT REFERENCES Channels(channelID) ON DELETE CASCADE,
    PRIMARY KEY (userID, channelID)
);

CREATE TABLE Messages(
    messageID SERIAL PRIMARY KEY,
    messageTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    userID INT REFERENCES Users(userID) ON DELETE CASCADE,
    channelID INT REFERENCES Channels(channelID) ON DELETE CASCADE,
    UNIQUE (messageTime, userID, channelID),

    content TEXT NOT NULL,
    image_url TEXT

);