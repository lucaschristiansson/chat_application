INSERT INTO Users (username, password) VALUES
    ('user1', 'password1'),
    ('user2', 'password2'),
    ('user3', 'password3');

INSERT INTO Channels (id, name) VALUES
    (1,'Channel1'),
    (2,'Channel2');

INSERT INTO Messages (time, sender_name, channel_id, content, image_url) VALUES
    (NOW(), 'user1', 1, 'Hello everyone!', NULL),

    (NOW() + INTERVAL '5 minutes', 'user2', 1, 'Hi user1, how are you?', NULL),

    (NOW() + INTERVAL '10 minutes', 'user3', 2, 'Check out this cat!', '/uploads/cat.jpg'),

    (NOW() + INTERVAL '15 minutes', 'user1', 1, 'I am doing great, thanks!', NULL);