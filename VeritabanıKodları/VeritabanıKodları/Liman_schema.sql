DROP TABLE tekne CASCADE;
DROP TABLE ozel_tekne CASCADE;
DROP TABLE ticari_tekne CASCADE;
DROP TABLE sahip CASCADE;
DROP TABLE liman CASCADE;
DROP TABLE kayit CASCADE;
DROP TABLE yuk CASCADE;

CREATE TABLE tekne(
	tekne_id int,
	model varchar(32) NOT NULL,
	liman_id int,
	owner_id char(11),
	uzunluk numeric(4,2) check(uzunluk > 0),
	motor_gucu int NOT NULL check(motor_gucu > 0),
	vergi numeric(6,2),
	primary key (tekne_id)
);

CREATE TABLE ozel_tekne(
	ot_id int,
	tekne_ad VARCHAR(40) NOT NULL,
	denizci_belgesi char(1) NOT NULL,
	primary key (ot_id),
	CONSTRAINT fk_ozel FOREIGN KEY
	(ot_id) REFERENCES tekne (tekne_id)
	ON DELETE CASCADE
);

CREATE TABLE ticari_tekne(
	tt_id int,
	temp_num SMALLINT NOT NULL,
	yuk_id int,
	plaka char(9) NOT NULL,
	primary key (tt_id),
	CONSTRAINT fk_ticari FOREIGN KEY
	(tt_id) REFERENCES tekne (tekne_id)
	ON DELETE CASCADE
);

CREATE TABLE sahip(
	owner_id char(11) NOT NULL,
	ad_soyad VARCHAR(30) NOT NULL,
	tel_no CHAR(10) NOT NULL,
	adres VARCHAR(50) NOT NULL,
	mail VARCHAR(40) NOT NULL,
	hesap_no CHAR(16) NOT NULL,
	primary key (owner_id)
);

CREATE TABLE liman(
	liman_id int,
	liman_ad VARCHAR(20) NOT NULL,
	adres VARCHAR(50) NOT NULL,
	sehir VARCHAR(20) NOT NULL,
	lemp_num int check(lemp_num >=0),
	ship_num int check(ship_num>=0),
	primary key (liman_id)
);

CREATE TABLE kayit(
	kyt_id int,
	gemi_id int,
	klks_yer VARCHAR(20) NOT NULL,
	vrs_yer VARCHAR(20) NOT NULL,
	klks_trh DATE NOT NULL,
	vrs_trh DATE NOT NULL,
	primary key (kyt_id)
); 

CREATE TABLE yuk(
	yuk_id int NOT NULL,
	cins VARCHAR(30) NOT NULL,
	kutle float NOT NULL check(kutle > 0),
	deger float NOT NULL check(deger >0),
	firma VARCHAR(30) NOT NULL,
	primary key (yuk_id)
);

ALTER TABLE ticari_tekne ADD CONSTRAINT foreign_key_const foreign key (yuk_id) REFERENCES yuk(yuk_id) ON DELETE CASCADE;
ALTER TABLE kayit ADD FOREIGN KEY (gemi_id) REFERENCES tekne(tekne_id) ON DELETE SET NULL;
ALTER TABLE tekne ADD FOREIGN KEY (liman_id) REFERENCES liman(liman_id) ON DELETE SET NULL;
ALTER TABLE tekne ADD FOREIGN KEY (owner_id) REFERENCES sahip(owner_id) ON DELETE SET NULL;