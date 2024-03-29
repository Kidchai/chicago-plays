-- Populate the H2 database for unit tests

INSERT INTO events(event_id, title, first_date, last_date, theatre, description, event_url, image_url, min_price,
                   max_price, next_show)
VALUES (1, 'Event 1', '2023-07-21', '2023-07-22', 'Theatre 1', 'Description 1', 'http://event1.com',
        'http://event1.com/image.jpg', 20, 50, '2023-07-21 10:00:00'),
       (2, 'Event 2', '2023-07-23', '2023-07-26', 'Theatre 2', 'Description 2', 'http://event2.com',
        'http://event2.com/image.jpg', null, 75, '2023-07-23 10:00:00'),
       (3, 'Event 3', '2023-07-25', '2023-07-28', 'Theatre 3', 'Description 3', 'http://event3.com',
        'http://event3.com/image.jpg', 30, null, '2023-07-25 10:00:00'),
       (4, 'Event 4', '2023-06-10', '2023-08-30', 'Theatre 4', 'Description 4', 'http://event4.com',
        'http://event4.com/image.jpg', null, null, '2023-07-27 10:00:00');

INSERT INTO genres(genre_id, genre)
VALUES (1, 'Comedy'),
       (2, 'Musical'),
       (3, 'Drama');

INSERT INTO events_genres(event_id, genre_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3);