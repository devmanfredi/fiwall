CREATE TABLE users (
	id integer NOT NULL,
	email varchar(255) NOT NULL,
	fullName varchar(255) NOT NULL,
	document varchar(14) NOT NULL,
	"password" varchar(255) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);