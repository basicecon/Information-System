CREATE TABLE ACTOR (
actor_name	varchar(255) 	NOT NULL,
gender		varchar(255),
date_of_birth	date, 
PRIMARY KEY (actor_name) 
);
CREATE TABLE MOVIE (
movie_title	varchar(255)	NOT NULL,
release_year	integer 	NOT NULL,
genre		varchar(255),
movie_length	integer, 
PRIMARY KEY (movie_title, release_year)
);
CREATE TABLE CAST_MEMBER (
actor_name 	varchar(255)	NOT NULL,
movie_title 	varchar(255)	NOT NULL,
release_year	integer		NOT NULL,
actor_role	varchar(255), 
PRIMARY KEY (actor_name, movie_title, release_year),
FOREIGN KEY (actor_name) REFERENCES ACTOR(actor_name),
FOREIGN KEY (movie_title, release_year) REFERENCES MOVIE(movie_title, release_year)
);
CREATE TABLE AWARDS_EVENT (
event_name	varchar(255)	NOT NULL,
event_year	integer		NOT NULL,
venue		varchar(255), 
PRIMARY KEY (event_name, event_year)
);
CREATE TABLE NOMINATION (
event_name 	varchar(255)	NOT NULL,
event_year	integer		NOT NULL,
movie_title	varchar(255)	NOT NULL,
release_year	integer		NOT NULL,
category	varchar(255)	NOT NULL,
won		varchar(255), 
PRIMARY KEY (event_name, event_year, movie_title, release_year, category),
FOREIGN KEY (event_name, event_year) REFERENCES AWARDS_EVENT(event_name, event_year),
FOREIGN KEY (movie_title, release_year) REFERENCES MOVIE(movie_title, release_year)
);

CREATE TABLE DB_USER (
userid		varchar(255) 	NOT NULL,
PRIMARY KEY (userid)
);

CREATE TABLE MOVIE_RATING (
userid 		varchar(255)	NOT NULL,
movie_title	varchar(255) 	NOT NULL,
release_year	integer 	NOT NULL,
rating 		real,
PRIMARY KEY (userid, movie_title, release_year),
FOREIGN KEY (userid) REFERENCES DB_USER(userid),
FOREIGN KEY (movie_title, release_year) REFERENCES MOVIE(movie_title, release_year)
);
