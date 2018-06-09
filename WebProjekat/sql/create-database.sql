DROP SCHEMA IF EXISTS webvideo;
CREATE SCHEMA webvideo DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE webvideo;

CREATE TABLE users (
	korisnickoIme VARCHAR(15) NOT NULL,
	slicica VARCHAR(100) NOT NULL,
	lozinka VARCHAR(15) NOT NULL,
	ime VARCHAR(12),
	prezime VARCHAR(12),
	email VARCHAR(30) NOT NULL,
	opis VARCHAR(70),
	datumRegistracije DATE NOT NULL,
	uloga ENUM('KORISNIK', 'ADMINISTRATOR') NOT NULL DEFAULT 'KORISNIK',
	blokiran BOOLEAN NOT NULL DEFAULT FALSE,
	obrisan BOOLEAN NOT NULL DEFAULT FALSE,
	brojPratioca BIGINT,
	PRIMARY KEY(korisnickoIme)
);

INSERT INTO users(korisnickoIme,slicica,lozinka,ime,prezime,email,uloga,datumRegistracije,brojPratioca) VALUES('mare','photos/korisnik.png','123','Marko','Markovic','marko@gmail.com','KORISNIK','2018-1-1',0);
INSERT INTO users(korisnickoIme,slicica,lozinka,ime,prezime,email,uloga,datumRegistracije,brojPratioca) VALUES('dare','photos/korisnik.png','123','Darko','Darkovic','darko@gmail.com','KORISNIK','2018-1-3',0);
INSERT INTO users(korisnickoIme,slicica,lozinka,ime,prezime,email,uloga,datumRegistracije,brojPratioca) VALUES('pera','photos/korisnik.png','123','Petar','Petrovic','petar@gmail.com','ADMINISTRATOR','2018-2-2',0);

CREATE TABLE videos(
	id BIGINT AUTO_INCREMENT,
	videoUrl VARCHAR(100) NOT NULL,
	slicica VARCHAR(100) NOT NULL,
	nazivVideo VARCHAR(50) NOT NULL,
	opis VARCHAR(50),
	vidljivost ENUM ('PRIVATE','PUBLIC','UNLISTED') NOT NULL,
	dozvoljeniKomentari BOOLEAN NOT NULL DEFAULT TRUE,
	brojLike BIGINT NOT NULL ,
	brojDislike BIGINT NOT NULL,
	blokiran BOOLEAN NOT NULL DEFAULT FALSE,
	rejtingVidljivost BOOLEAN DEFAULT TRUE,
	brojPregleda BIGINT NOT NULL,
	datumKreiranja  DATE NOT NULL,
	obrisan BOOLEAN NOT NULL DEFAULT FALSE,
	vlasnik VARCHAR(10) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (vlasnik) REFERENCES users(korisnickoIme) ON DELETE RESTRICT
);

INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/tWG13C3gQbE','photos/video.jpg','Alan Walker - The Spectre (LUM!X Remix)',
'Dont forget to Like & Share the mix','PUBLIC',true,0,0,false,true,100,'2018-2-3',false,'mare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/WldLcgvy9nA','photos/video.jpg','Meg & Dia - Monster ♫ Shuffle Dance',
'Meg & Dia - Monster (Music video) bootleg','PUBLIC',true,0,0,false,true,200,'2018-2-4',false,'dare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/rMi6ViOEMbk','photos/video.jpg','Issues - Julia Michaels (Remix)',
'Subscribe to Munkee Beatz for more','PRIVATE',true,0,0,false,true,300,'2018-2-4',false,'mare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/A8agOHX4tWI','photos/video.jpg','Avicii - Wake Me Up (Remix)',
'Avicii - Wake Me Up(Music video) chill Mix','PRIVATE',true,0,0,false,true,400,'2018-2-4',false,'dare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/LvVOAITOY_s','photos/video.jpg','Alan Walker - All falls Down (Remix)',
'Alan Walker - ♫ Shuffle Dance Electro House','PUBLIC',true,0,0,false,true,500,'2018-2-4',false,'pera');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/JvYFkoK0Dgw','photos/video.jpg','Leaff - Time(Inspired By Alan Walker)',
'Give a warm welcome to our new artist Leaff','PRIVATE',true,0,0,false,true,500,'2018-2-4',false,'pera');

CREATE TABLE likeDislike(
	id BIGINT AUTO_INCREMENT,
	lajkovan BOOLEAN NOT NULL,
	datumKreiranja DATE NOT NULL,
	vlasnik VARCHAR(15),
	PRIMARY KEY (id),
	FOREIGN KEY (vlasnik) REFERENCES users(korisnickoIme) ON DELETE RESTRICT
);

CREATE TABLE likeDislikeVideo(
	likeId BIGINT,
	videoId BIGINT,
	FOREIGN KEY (likeId) REFERENCES likeDislike (id) ON DELETE RESTRICT,
	FOREIGN KEY (videoId) REFERENCES videos(id) ON DELETE RESTRICT
);

CREATE TABLE subscribe(
	korisnik VARCHAR(15),
	subskrajber VARCHAR(15),
	FOREIGN KEY (korisnik) REFERENCES users(korisnickoIme) ON DELETE RESTRICT,
	FOREIGN KEY (subskrajber) REFERENCES users(korisnickoIme) ON DELETE RESTRICT
);

CREATE TABLE comment(
	id BIGINT AUTO_INCREMENT,
	sadrzaj VARCHAR(120),
	datumKreiranja DATE NOT NULL,
	vlasnik VARCHAR(20),
	video BIGINT NOT NULL,
	brojLike BIGINT NOT NULL,
	brojDislike BIGINT NOT NULL,
	obrisan BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (id),
	FOREIGN KEY (vlasnik) REFERENCES users(korisnickoIme) ON DELETE RESTRICT,
	FOREIGN KEY (video) REFERENCES videos(id) ON DELETE RESTRICT
);

INSERT INTO comment(sadrzaj,datumKreiranja,vlasnik,video,brojLike,brojDislike)
VALUES('Best movieee','2018-1-1','mare',1,0,0);

CREATE TABLE likeDislikeComment(
	likeId BIGINT,
	commentId BIGINT,
	FOREIGN KEY (likeId) REFERENCES likeDislike (id) ON DELETE RESTRICT,
	FOREIGN KEY (commentId) REFERENCES comment (id) ON DELETE RESTRICT
);
