-------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.filtre_liman_view(
	limit_kutle double precision)
    RETURNS TABLE(lid integer, limanadi character varying, adres character varying, sehir character varying, emp_num integer, ship_num integer) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$

BEGIN

	RETURN QUERY SELECT l1.liman_id,l1.liman_ad,l1.adres,l1.sehir,l1.lemp_num,l1.ship_num FROM liman l1 WHERE l1.liman_id IN(
	SELECT l.liman_id
	FROM tekne t,ticari_tekne c,liman l,yuk y
	WHERE t.tekne_id = c.tt_id AND c.yuk_id = y.yuk_id AND t.liman_id = l.liman_id
	GROUP BY l.liman_id
	HAVING sum(y.kutle) > limit_kutle);

END;
$BODY$;



-------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION ort_lemp_num()
RETURNS real AS $$

DECLARE
	ort_isci numeric;
BEGIN

	SELECT avg(lemp_num) INTO ort_isci
	FROM liman;
	RETURN ort_isci;

END;
$$ LANGUAGE 'plpgsql';


-------------------------------------------------------------------------------------------------


CREATE TYPE istikamet AS (gemi_isim VARCHAR(40), kalkis VARCHAR(20), varis VARCHAR(20));

/*record ve fonksiyon*/
CREATE OR REPLACE FUNCTION tekne_ozet(t_id tekne.tekne_id%type)
RETURNS istikamet AS $$
DECLARE
	bilgi istikamet;
BEGIN
	SELECT klks_yer, vrs_yer INTO bilgi
	FROM tekne o, kayit
	WHERE gemi_id = t_id
	ORDER BY kayit.vrs_trh DESC
	LIMIT 1 OFFSET 0;
	RAISE NOTICE 'Tekne ismi: %, kalkis yeri: %, varis yeri: % ',
		bilgi.gemi_isim, bilgi.kalkis, bilgi.varis;
	RETURN bilgi;
END;
$$ LANGUAGE 'plpgsql';


-------------------------------------------------------------------------------------------------


/*view*/
CREATE VIEW tekne_bilgi
AS
SELECT distinct tekne.tekne_id, model, liman_id,owner_id,uzunluk,motor_gucu, vergi, tekne_ad, denizci_belgesi,temp_num,yuk_id,plaka
FROM tekne natural left outer join ozel_tekne natural left outer join ticari_tekne;


-------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION trig_fonk_ayni_isim()
RETURNS TRIGGER AS $$
DECLARE 
	yeni_cur CURSOR FOR SELECT ot_id,tekne_ad FROM ozel_tekne;
	eskiLiman INTEGER;
	yeniLiman INTEGER;
BEGIN
	FOR isim IN yeni_cur LOOP
		IF(isim.tekne_ad = new.tekne_ad) THEN
			SELECT liman_id INTO eskiLiman FROM tekne,ozel_tekne WHERE isim.ot_id = tekne_id;
			SELECT liman_id INTO yeniLiman FROM tekne,ozel_tekne WHERE new.ot_id = tekne_id;
			IF(eskiLiman = yeniLiman) THEN
				DELETE FROM tekne WHERE new.ot_id = tekne_id ;
				RAISE NOTICE 'Aynı isimde tekne ekleyemezsiniz';
				RETURN null;
			END IF;
		END IF;
	END LOOP;
	RETURN new;
END;
$$ LANGUAGE 'plpgsql';



-------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION trig_fonk_ayni_plaka()
RETURNS TRIGGER AS $$
DECLARE 
	yeni_cur2 CURSOR FOR SELECT tt_id,plaka FROM ticari_tekne;
	eskiLiman INTEGER;
	yeniLiman INTEGER;
BEGIN
	FOR isim IN yeni_cur2 LOOP
		IF(isim.plaka = new.plaka) THEN
			SELECT liman_id INTO eskiLiman FROM tekne,ticari_tekne WHERE isim.tt_id = tekne_id;
			SELECT liman_id INTO yeniLiman FROM tekne,ozel_tekne WHERE new.tt_id = tekne_id;
			IF(eskiLiman = yeniLiman) THEN
				DELETE FROM tekne WHERE new.tt_id = tekne_id;
				RAISE NOTICE 'Aynı plakada tekne ekleyemezsiniz';
				RETURN null;
			END IF;
		END IF;
	END LOOP;
	RETURN new;
END;
$$ LANGUAGE 'plpgsql';


-------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION trig_fonk_shipnum_arttir()
RETURNS TRIGGER AS $$

BEGIN

	UPDATE liman SET ship_num=ship_num+1 WHERE liman_id IN(SELECT l.liman_id 
							FROM tekne,liman l
							WHERE new.liman_id=l.liman_id);
	RETURN new;

END;
$$ LANGUAGE 'plpgsql';


-------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION trig_fonk_shipnum_azalt()
RETURNS TRIGGER AS $$

BEGIN

	UPDATE liman SET ship_num=ship_num-1 WHERE liman_id IN(SELECT l.liman_id 
							FROM tekne,liman l
							WHERE old.liman_id=l.liman_id);
	RETURN new;

END;
$$ LANGUAGE 'plpgsql';


-------------------------------------------------------------------------------------------------


--DROP TRIGGER ayni_isim ON ozel_tekne
CREATE TRIGGER ayni_isim
BEFORE INSERT
ON ozel_tekne
FOR EACH ROW EXECUTE PROCEDURE trig_fonk_ayni_isim();


-------------------------------------------------------------------------------------------------


--DROP TRIGGER ayni_plaka ON ticari_tekne
CREATE TRIGGER ayni_plaka
BEFORE INSERT
ON ticari_tekne
FOR EACH ROW EXECUTE PROCEDURE trig_fonk_ayni_plaka();


-------------------------------------------------------------------------------------------------


--DROP TRIGGER shipnum_arttir ON tekne
CREATE TRIGGER shipnum_arttir
AFTER INSERT
ON tekne
FOR EACH ROW EXECUTE PROCEDURE trig_fonk_shipnum_arttir();


-------------------------------------------------------------------------------------------------


--DROP TRIGGER shipnum_azalt ON tekne
CREATE TRIGGER shipnum_azalt
AFTER DELETE
ON tekne
FOR EACH ROW EXECUTE PROCEDURE trig_fonk_shipnum_azalt();


-------------------------------------------------------------------------------------------------
SELECT DISTINCT ot_id, tekne_ad
FROM ozel_tekne,sahip,tekne 
WHERE ot_id=tekne_id AND tekne.owner_id = ? AND 
ot_id IN (SELECT tekne_id 
FROM tekne 
EXCEPT 
SELECT tekne_id 
FROM tekne, ticari_tekne 
WHERE tekne.tekne_id = tt_id);
			
SELECT DISTINCT tt_id, plaka
FROM ticari_tekne,sahip,tekne 
WHERE  tt_id=tekne_id AND tekne.owner_id = ? AND 
tt_id IN (SELECT tekne_id 
FROM tekne 
EXCEPT 
SELECT tekne_id 
FROM tekne, ozel_tekne 
WHERE tekne.tekne_id = ot_id);