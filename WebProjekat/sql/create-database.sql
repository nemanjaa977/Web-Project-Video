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

INSERT INTO users(korisnickoIme,slicica,lozinka,ime,prezime,email,uloga,datumRegistracije,brojPratioca) VALUES('mare','photos/Zac.jpg','123','Marko','Markovic','marko@gmail.com','KORISNIK','2018-1-1',0);
INSERT INTO users(korisnickoIme,slicica,lozinka,ime,prezime,email,uloga,datumRegistracije,brojPratioca) VALUES('dare','photos/Zac.jpg','123','Darko','Darkovic','darko@gmail.com','KORISNIK','2018-1-3',0);
INSERT INTO users(korisnickoIme,slicica,lozinka,ime,prezime,email,uloga,datumRegistracije,brojPratioca) VALUES('pera','photos/Zac.jpg','123','Petar','Petrovic','petar@gmail.com','ADMINISTRATOR','2018-2-2',0);

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
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/aWkj2d7m1Zw','photos/video.jpg','We Are Your Friends',
'The best movie!','PUBLIC',true,0,0,false,true,100,'2018-2-3',false,'mare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/v-4rYf0x-F4','photos/video.jpg','Transformers - The Last Knight',
'The best movie!','PUBLIC',true,0,0,false,true,200,'2018-2-4',false,'dare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/7BWWWQzTpNU','photos/video.jpg','PASSENGERS - Official Trailer (HD)',
'The best movie!','PRIVATE',true,0,0,false,true,300,'2018-2-4',false,'mare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/CfMMosvPHfQ','photos/video.jpg','I am Number Four | trailer #2',
'The best movie!','PRIVATE',true,0,0,false,true,400,'2018-2-4',false,'dare');
INSERT INTO videos(videoUrl,slicica,nazivVideo,opis,vidljivost,dozvoljeniKomentari,brojLike,brojDislike
,blokiran,rejtingVidljivost,brojPregleda,datumKreiranja,obrisan,vlasnik) VALUES('https://www.youtube.com/embed/23VflsU3kZE','photos/video.jpg','San Andreas - Official Trailer 2 [HD]',
'The best movie!','PUBLIC',true,0,0,false,true,500,'2018-2-4',false,'mare');

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
