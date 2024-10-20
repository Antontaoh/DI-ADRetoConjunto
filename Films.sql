Create database films;
use films;


CREATE TABLE user (
id int auto_increment primary key,
userName varchar(255),
password varchar(255)
);

CREATE TABLE film(
id int auto_increment primary key,
title varchar(255),
genre varchar(255),
year int,
description varchar(255),
director varchar(255)
);

CREATE TABLE copy(
id int auto_increment primary key,
`condition` enum ('New', 'Damaged'),
support enum ('DVD', 'Blu-ray')
);

ALTER TABLE copy ADD COLUMN film_id int,
ADD CONSTRAINT `fk_id_pelicula` 
FOREIGN KEY (film_id)
REFERENCES film (id);

ALTER TABLE copy ADD COLUMN user_id int,
ADD constraint `fp_id_usuario`
foreign key (user_id)
REFERENCES user (id);


INSERT INTO user (userName, password) VALUES
('johnsmith', 'securePass123'),
('emilywatson', 'passForEmily');

INSERT INTO film (title, genre, year, description, director) VALUES
('The Imitation Game', 'Biography', 2014, 'During World War II, British mathematician Alan Turing tries to crack the German Enigma code with help from fellow mathematicians.', 'Morten Tyldum'),
('Gravity', 'Sci-Fi', 2013, 'Two astronauts struggle to survive after their space shuttle is destroyed in space.', 'Alfonso Cuarón'),
('Blade Runner 2049', 'Sci-Fi', 2017, 'A young blade runner discovers a long-buried secret that leads him to track down former blade runner Rick Deckard, who has been missing for thirty years.', 'Denis Villeneuve'),
('The Grand Budapest Hotel', 'Comedy', 2014, 'A concierge at a famous European hotel becomes involved in a theft and recovery of a priceless Renaissance painting.', 'Wes Anderson');

INSERT INTO film (title, genre, year, description, director) VALUES
('12 Years a Slave', 'Biography', 2013, 'In the antebellum United States, Solomon Northup, a free black man from upstate New York, is abducted and sold into slavery.', 'Steve McQueen'),
('Mad Max: Fury Road', 'Action', 2015, 'In a post-apocalyptic wasteland, Max teams up with a mysterious woman to try and survive and fight off enemies.', 'George Miller'),
('Her', 'Romance', 2013, 'In a near future, a lonely writer develops an unlikely relationship with an artificial intelligence designed to meet his every need.', 'Spike Jonze'),
('Joker', 'Drama', 2019, 'In Gotham City, mentally troubled comedian Arthur Fleck is disregarded by society, leading him into a downward spiral of revolution and bloody crime.', 'Todd Phillips'),
('The Revenant', 'Adventure', 2015, 'A frontiersman on a fur trading expedition in the 1820s fights for survival after being mauled by a bear and left for dead by his companions.', 'Alejandro G. Iñárritu'),
('Whiplash', 'Drama', 2014, 'A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student\'s potential.', 'Damien Chazelle'),
('Spotlight', 'Drama', 2015, 'The true story of how the Boston Globe uncovered the massive scandal of child molestation and cover-up within the local Catholic Archdiocese.', 'Tom McCarthy'),
('Arrival', 'Sci-Fi', 2016, 'A linguist works with the military to communicate with alien lifeforms after twelve mysterious spacecrafts appear around the world.', 'Denis Villeneuve'),
('La La Land', 'Musical', 2016, 'A jazz musician and an aspiring actress fall in love but struggle to maintain their relationship as they chase their dreams in Los Angeles.', 'Damien Chazelle'),
('The Shape of Water', 'Fantasy', 2017, 'At a high-security government laboratory, a lonely janitor forms a unique relationship with an amphibious creature that is being held in captivity.', 'Guillermo del Toro'),
('Moonlight', 'Drama', 2016, 'A young African-American man grapples with his identity and sexuality while experiencing the everyday struggles of childhood, adolescence, and burgeoning adulthood.', 'Barry Jenkins'),
('Knives Out', 'Mystery', 2019, 'A detective investigates the death of a wealthy patriarch after a family gathering goes horribly awry.', 'Rian Johnson'),
('Jojo Rabbit', 'Comedy', 2019, 'A young boy in Nazi Germany whose world view is turned upside down when he discovers that his mother is hiding a Jewish girl in their home.', 'Taika Waititi');


INSERT INTO copy (film_id, user_id, `condition`, support)VALUES
(1,1,'New','Blu-ray'),
(1,1,'Damaged','DVD'),
(2,2,'New','DVD'),
(3,1,'Damaged','Blu-ray'),
(4,2,'New','Blu-ray'),
(4,1,'Damaged','DVD');


