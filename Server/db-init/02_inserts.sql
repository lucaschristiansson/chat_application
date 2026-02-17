INSERT INTO Users (username, password) VALUES
    ('user1', 'password1'),
    ('user2', 'password2'),
    ('user3', 'password3');

INSERT INTO Channels (id, name) VALUES
    (1, 'Channel1'),
    (2, 'Channel2');

INSERT INTO UsersInChannel (username, channel) VALUES
    (1, 1),
    (2, 1),
    (3, 2);

INSERT INTO Messages (time, sender_name, channel_id, content, image_url) VALUES
    (CURRENT_TIMESTAMP, 1, 1, 'Hello everyone!', NULL),

    (CURRENT_TIMESTAMP + INTERVAL '5 minutes', 2, 1, 'Hi user1, how are you?', NULL),

    (CURRENT_TIMESTAMP + INTERVAL '10 minutes', 3, 2, 'Check out this cat!', '/uploads/cat.jpg'),

    (CURRENT_TIMESTAMP + INTERVAL '15 minutes', 1, 1, 'I am doing great, thanks!', NULL);