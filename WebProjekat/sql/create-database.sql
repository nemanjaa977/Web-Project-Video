DROP SCHEMA IF EXISTS webvideo;
CREATE SCHEMA webvideo DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE webvideo;

CREATE TABLE users (
	korisnickoIme VARCHAR(15) NOT NULL,
	lozinka VARCHAR(15) NOT NULL,
	ime VARCHAR(12),
	prezime VARCHAR(12),
	email VARCHAR(20) NOT NULL,
	opis VARCHAR(70),
	datumRegistracije DATETIME NOT NULL,
	blokiran BOOLEAN NOT NULL DEFAULT FALSE,
	uloga ENUM('KORISNIK', 'ADMINISTRATOR') NOT NULL DEFAULT 'KORISNIK',
	PRIMARY KEY(korisnickoIme)
);

INSERT INTO users(korisnickoIme,lozinka,ime,prezime,email,uloga,datumRegistracije) VALUES('marko','123','Marko','Markovic','marko@gmail.com','KORISNIK','1.1.2018');