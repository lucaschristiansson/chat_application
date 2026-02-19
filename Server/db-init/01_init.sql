CREATE TABLE Users(
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL
);

CREATE TABLE Channels(
    channelID INT PRIMARY KEY,
    channelName TEXT NOT NULL
);

CREATE TABLE UsersInChannel(
    username TEXT REFERENCES Users(username) ON DELETE CASCADE,
    channelID INT REFERENCES Channels(channelID) ON DELETE CASCADE,
    PRIMARY KEY (username, channelID)
);

CREATE TABLE Messages(
    timestamp TIMESTAMP,
    sender TEXT REFERENCES Users(username) ON DELETE CASCADE,
    channelID INT REFERENCES Channels(channelID) ON DELETE CASCADE,
    PRIMARY KEY (timestamp, sender, channelID),

    content TEXT NOT NULL,
    imagePath TEXT

);