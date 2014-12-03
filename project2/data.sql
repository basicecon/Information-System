insert into ACTOR values('Jennifer Lawrence', 'Female', to_date('08/15/1990', 'mm/dd/yyyy'));
insert into ACTOR values('Brad Pitt', 'Male', to_date('12/18/1963', 'mm/dd/yyyy'));
insert into ACTOR values('Jennifer Aniston', 'Female', to_date('02/11/1969', 'mm/dd/yyyy'));
insert into ACTOR values('Leonardo DiCaprio', 'Male', to_date('11/11/1974', 'mm/dd/yyyy'));
insert into ACTOR values('Amy Adams', 'Female', to_date('08/20/1974', 'mm/dd/yyyy'));
insert into ACTOR values('Christian Bale', 'Male', to_date('01/30/1974', 'mm/dd/yyyy'));

insert into MOVIE values('American Hustle', 2013, 'Comedy', 138);
insert into MOVIE values('Inglourious Basterds', 2009, 'Adventure', 153);
insert into MOVIE values('Seven Years in Tibet', 1997, 'Adventure', 136);
insert into MOVIE values('World War Z', 2013, 'Adventure', 116);
insert into MOVIE values('Silver Linings Playbook', 2012, 'Comedy', 122);
insert into MOVIE values('12 Years a Slave', 2013, 'Drama', 134);
insert into MOVIE values('Django Unchained', 2012, 'Western', 165);
insert into MOVIE values('The Wolf of Wall Street', 2013, 'Comedy', 180);

insert into CAST_MEMBER values('Brad Pitt', '12 Years a Slave', 2013, 'Bass');
insert into CAST_MEMBER values('Brad Pitt', 'World War Z', 2013, 'lead actor');
insert into CAST_MEMBER values('Brad Pitt', 'Inglourious Basterds', 2009, 'lead actor');
insert into CAST_MEMBER values('Brad Pitt', 'Seven Years in Tibet', 1997, 'lead actor');
insert into CAST_MEMBER values('Jennifer Lawrence', 'American Hustle', 2013, 'supporting actress');
insert into CAST_MEMBER values('Jennifer Lawrence', 'Silver Linings Playbook', 2012, 'lead actress');
insert into CAST_MEMBER values('Leonardo DiCaprio', 'Django Unchained', 2012, 'supporting actor');
insert into CAST_MEMBER values('Leonardo DiCaprio', 'The Wolf of Wall Street', 2013, 'lead actor');
insert into CAST_MEMBER values('Amy Adams', 'American Hustle', 2013, 'lead actress');
insert into CAST_MEMBER values('Christian Bale', 'American Hustle', 2013, 'lead actor');

insert into AWARDS_EVENT values('Academy Awards', 2014, 'Los Angeles, CA');
insert into AWARDS_EVENT values('Academy Awards', 2013, 'Los Angeles, CA');
insert into AWARDS_EVENT values('Golden Globes', 2014, 'Santa Monica, CA');
insert into AWARDS_EVENT values('Golden Globes', 2013, 'Santa Monica, CA');

insert into NOMINATION values('Academy Awards', 2014, '12 Years a Slave', 2013, 'best picture', 'Yes');
insert into NOMINATION values('Academy Awards', 2014, 'American Hustle', 2013, 'best lead actress', 'No');
insert into NOMINATION values('Academy Awards', 2014, 'American Hustle', 2013, 'best picture', 'No');
insert into NOMINATION values('Academy Awards', 2014, 'American Hustle', 2013, 'best supporting actress', 'No');
insert into NOMINATION values('Academy Awards', 2014, 'American Hustle', 2013, 'best lead actor', 'No');
insert into NOMINATION values('Academy Awards', 2014, 'American Hustle', 2013, 'best supporting actor', 'No');
insert into NOMINATION values('Academy Awards', 2014, 'American Hustle', 2013, 'best costume design', 'No');
insert into NOMINATION values('Academy Awards', 2013, 'Silver Linings Playbook', 2012, 'best lead actress', 'Yes');
insert into NOMINATION values('Golden Globes', 2013, 'Silver Linings Playbook', 2012, 'best lead actress', 'Yes');
insert into NOMINATION values('Golden Globes', 2013, 'Silver Linings Playbook', 2012, 'best picture', 'Yes');
insert into NOMINATION values('Golden Globes', 2014, 'American Hustle', 2013, 'best picture', 'Yes');
insert into NOMINATION values('Golden Globes', 2014, 'American Hustle', 2013, 'best supporting actress', 'Yes');
insert into NOMINATION values('Golden Globes', 2014, 'American Hustle', 2013, 'best lead actress', 'Yes');
insert into NOMINATION values('Academy Awards', 2014, 'The Wolf of Wall Street', 2013, 'best lead actor', 'No');
insert into NOMINATION values('Golden Globes', 2014, 'The Wolf of Wall Street', 2013, 'best lead actor', 'Yes');
insert into NOMINATION values('Golden Globes', 2013, 'Django Unchained', 2012, 'best supporting actor', 'No');

insert into DB_USER values('user1');
insert into DB_USER values('user2');
insert into DB_USER values('user3');
insert into DB_USER values('user4');
insert into DB_USER values('user5');

insert into MOVIE_RATING values('user2', 'American Hustle', 2013, 7);
insert into MOVIE_RATING values('user1', 'Silver Linings Playbook', 2012, 3);
insert into MOVIE_RATING values('user1', 'Django Unchained', 2012, 9);
insert into MOVIE_RATING values('user3', 'Django Unchained', 2012, 8);
insert into MOVIE_RATING values('user4', 'Django Unchained', 2012, 5);
insert into MOVIE_RATING values('user3', 'American Hustle', 2013, 6);
insert into MOVIE_RATING values('user5', 'Silver Linings Playbook', 2012, 5);

