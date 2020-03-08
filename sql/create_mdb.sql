create database moviedb;
use moviedb;

-- 'User' is a reserved keyword
drop table UserProfile;
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
show tables from moviedb;
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
	pid integer not null auto_increment,
    full_name varchar(64) not null,
    birth_date date,
    country varchar(64),
    constraint pk primary key (pid)
);

create table Film (
	film_id integer not null auto_increment,
    series_id integer not null,
    title varchar(64) not null,
    runlength integer,
    pub_year integer,
    pub_date date,
    storyline text,
    season integer default 1,
    episode integer default 1,
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
    pid integer not null,
    _role varchar(64) not null,
    constraint role_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint role_fk2 foreign key (pid)
		references Person(pid)
			on update cascade
            on delete cascade
);

create table DirectedBy (
	film_id integer not null,
    pid integer not null,
    constraint director_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint director_fk2 foreign key (pid)
		references Person(pid)
			on update cascade
            on delete cascade
);

create table WrittenBy (
	film_id integer not null,
    pid integer not null,
    constraint writer_fk1 foreign key (film_id)
		references Film(film_id)
			on update cascade
            on delete cascade,
	constraint writer_fk2 foreign key (pid)
		references Person(pid)
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