create database moviedb;
use moviedb;

-- 'User' is a reserved keyword
create table UserProfile (
	user_id integer not null auto_increment,
    username varchar(64) not null,
    constraint pk primary key (user_id)
);

create table Category (
	cat_id integer not null auto_increment,
    cat_name varchar(64) not null,
    constraint pk primary key (cat_id)
);

create table Company (
	comp_id integer not null auto_increment,
    comp_name varchar(64) not null,
    url varchar(256) not null,
    country varchar(64) not null,
    address varchar(256) not null,
    constraint pk primary key (comp_id)
);

create table Series (
	series_id integer not null auto_increment,
	comp_id integer not null,
	title varchar(64) default '',
	constraint pk primary key (series_id),
	constraint fk foreign key (comp_id)
		references Company(comp_id)
			on update cascade
            on delete no action
);

create table Tag (
	series_id integer not null,
    cat_id integer not null,
    constraint fk1 foreign key (series_id)
		references Series(series_id)
			on update cascade
            on delete cascade,
	constraint fk2 foreign key (cat_id)
		references Category(cat_id)
			on update cascade
            on delete no action
);

create table Person (
	person_id integer not null auto_increment,
    full_name varchar(64) not null,
    birthdate date,
    country varchar(64),
    constraint pk primary key (person_id)
);

create table Film (
	film_id integer not null auto_increment,
    series_id integer not null,
    title varchar(64) not null,
    runlength integer,
    pub_year integer,
    pub_date date,
    storyline text,
    season integer default 0,
    episode integer default 0,
    constraint pk primary key (film_id),
    constraint film_fk foreign key (series_id)
		references Series(series_id)
			on update cascade
            on delete cascade
);

create table Music (
	music_id integer not null auto_increment,
    title varchar(64) not null,
    composer varchar(64),
    performer varchar(64),
    constraint pk primary key (music_id)
);

create table Soundtrack (
	film_id integer not null,
    music_id integer not null,
    constraint st_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint st_fk2 foreign key (music_id)
		references Music(music_id)
			on update cascade
            on delete cascade
);

create table RoleBy (
	film_id integer not null,
    person_id integer not null,
    role_name varchar(64) not null,
    constraint role_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint role_fk2 foreign key (person_id)
		references Person(person_id)
			on update cascade
            on delete cascade
);

create table DirectedBy (
	film_id integer not null,
    person_id integer not null,
    constraint director_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint director_fk2 foreign key (person_id)
		references Person(person_id)
			on update cascade
            on delete cascade
);

create table WrittenBy (
	film_id integer not null,
    person_id integer not null,
    constraint writer_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint writer_fk2 foreign key (person_id)
		references Person(person_id)
			on update cascade
            on delete cascade
);

create table Review (
	film_id integer not null,
    user_id integer not null,
    rating integer not null,
    review_text text,
    constraint review_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint review_fk2 foreign key (user_id)
		references UserProfile(user_id)
			on update cascade
            on delete no action
);

-- Add some values
insert into Company (comp_name, country, address, url) values ("20th Century FOX", "USA", "10th Fox st", "fox.com");
insert into Company (comp_name, country, address, url) values ("Disney", "USA", "Disney Land", "danceonroses.disney");
insert into Company (comp_name, country, address, url) values ("Fantefilm", "Norge", "Henrik Ibsens gate 28", "fantafilm.no");
insert into Company (comp_name, country, address, url) values ("BBC", "England", "1st london av", "bbc.co.uk");
insert into Company (comp_name, country, address, url) values ("Universal", "USA", "2nd multiversial st", "universalpictures.com");
insert into Company (comp_name, country, address, url) values ("HBO", "USA", "New York", "hbo.com");

insert into Series (title, comp_id) values ("Game of Thrones", 6);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength, season, episode) values ("Winter is coming", 1, 2011, '2011-07-12', "En desertør fra Nattevakten blir funnet nær Vinterfall, og Lord Eddard 'Ned' Stark må fullbyrde straffen.", 56, 1, 1);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength, season, episode) values ("The Kingsroad", 1, 2011, '2011-07-19', "Bran overlever på mirakuløst vis fallet fra tårnet, og Cersei og Jaime vil ikke at han skal leve videre og avsløre deres svik.", 56, 1, 1);

insert into Series (title, comp_id) values ("Grusomme meg", 5);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength) values ("Grusomme meg", 2, 2010, '2010-10-15', "Møt Gru og han onde medhjelpere", 95);
insert into Series (title, comp_id) values ("Grusomme meg 2", 5);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength) values ("Grusomme meg 2", 3, 2013, '2013-07-05', "Gjensyn med Gru og han onde medhjelpere", 98);
insert into Series (title, comp_id) values ("Grusomme meg 3", 5);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength) values ("Grusomme meg 3", 4, 2017, '2017-06-30', "Den tidligere skurken Gru er nå en agent for Antiskurkeligaen (Anti-Villain League, AVL). Han og hans partner/hustru Lucy er sendt til å forpurre planene til Balthazar Bratt, en tidligere barnestjerne som er blitt ond.", 96);
insert into Series (title, comp_id) values ("Bølgen", 3);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength) values ("Bølgen", 5, 2015, '2015-08-28', "Kristoffer Joner er ikke glad i så mye vann", 105);
insert into Series (title, comp_id) values ("Skjelvet", 3);
insert into Film (title, series_id, pub_year, pub_date, storyline, runlength) values ("Skjelvet", 6, 2018, '2018-06-30', "Kristoffer Joner er tilbake for å spå jordens undergang", 108);

insert into Category (cat_name) values ("Komedie");
insert into Category (cat_name) values ("Katastrofe");
insert into Category (cat_name) values ("Drama");
insert into Category (cat_name) values ("Familie");

delete from Tag where series_id > 0;
insert into Tag (series_id, cat_id) values (1, 3);
insert into Tag (series_id, cat_id) values (2, 1);
insert into Tag (series_id, cat_id) values (2, 4);
insert into Tag (series_id, cat_id) values (3, 1);
insert into Tag (series_id, cat_id) values (3, 4);
insert into Tag (series_id, cat_id) values (4, 1);
insert into Tag (series_id, cat_id) values (4, 4);
insert into Tag (series_id, cat_id) values (5, 2);
insert into Tag (series_id, cat_id) values (6, 2);

insert into Person (full_name, birthdate, country) values ("Kristoffer Joner", '1972-09-19', "Norge");
insert into RoleBy (person_id, film_id, role_name) values (1, 6, "Kristian Eikjord");
insert into RoleBy (person_id, film_id, role_name) values (1, 7, "Kristian Eikjord");
insert into Person (full_name, birthdate, country) values ("Ane Dahl Torp", '1975-08-01', "Norge");
insert into RoleBy (person_id, film_id, role_name) values (2, 6, "Idun Eikjord");
insert into RoleBy (person_id, film_id, role_name) values (2, 7, "Idun Eikjord");
insert into Person (full_name, birthdate, country) values ("Steve Carell", '1962-08-16', "USA");
insert into RoleBy (person_id, film_id, role_name) values (3, 3, "Gru");
insert into RoleBy (person_id, film_id, role_name) values (3, 4, "Gru");
insert into RoleBy (person_id, film_id, role_name) values (3, 5, "Gru");

insert into Person (full_name, birthdate, country) values ("Chris Renaud", '1966-12-01', "USA");
insert into DirectedBy (film_id, person_id) values (3, 4);
insert into DirectedBy (film_id, person_id) values (4, 4);
insert into DirectedBy (film_id, person_id) values (5, 4);
insert into Person (full_name, birthdate, country) values ("Roar Uthaug", '1973-08-25', "Norge");
insert into DirectedBy (film_id, person_id) values (6, 5);
insert into DirectedBy (film_id, person_id) values (7, 5);
