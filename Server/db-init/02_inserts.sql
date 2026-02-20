INSERT INTO Users (username, password) VALUES
    ('user1', 'password1'),
    ('user2', 'password2'),
    ('user3', 'password3');

INSERT INTO Channels (channelName) VALUES
    ('Channel1'),
    ('Channel2');

INSERT INTO UsersInChannel (username, channelID) VALUES
    ('user1', 1),
    ('user2', 1),
    ('user3', 2);

INSERT INTO Messages (messageTime, username, channelID, content, imagePath) VALUES
    (CURRENT_TIMESTAMP, 'user1', 1, 'Hello everyone!', NULL),

    (CURRENT_TIMESTAMP + INTERVAL '5 minutes', 'user2', 1, 'Hi user1, how are you?', NULL),

    (CURRENT_TIMESTAMP + INTERVAL '10 minutes', 'user3', 2, 'Check out this cat!', '/uploads/cat.jpg'),

    (CURRENT_TIMESTAMP + INTERVAL '15 minutes', 'user1', 1, 'I am doing great, thanks!', NULL);