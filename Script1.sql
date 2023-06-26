drop table if not exists autori
create table autori
(
	id int primary key auto_increment,
	nome varchar (20),
	cognome varchar (20),
	data_nascita Date,
	data_morte Date,
	nazione varchar (20)
);

insert into autori (nome, cognome, data_nascita, data_morte, nazione)
values
	('Stephen', 'King', '1947-09-21', 'USA');
	('Bruno', 'Vespa', '1944-05-28', 'ITA');


CREATE DEFINER=`root`@`localhost` PROCEDURE `ageautore`.`get_age_autori_nazione`(IN nazione VARCHAR(255))
BEGIN
    -- Elimina la tabella autori_eta_temp se esiste
    DROP TABLE IF EXISTS autori_eta_temp;
    -- Crea la tabella autori_eta_temp (nome, cognome, eta)
    CREATE TABLE autori_eta_temp (
        nome VARCHAR(255),
        cognome VARCHAR(255),
        eta INT
    );
    -- Estrai tutti gli autori viventi della nazione indicata
    INSERT INTO autori_eta_temp (nome, cognome, eta)
    SELECT nome, cognome, DATEDIFF(CURRENT_DATE, data_nascita) / 365 AS eta
    FROM autori
    WHERE nazione = nazione;
    -- Puoi anche effettuare altre operazioni sui dati inseriti nella tabella autori_eta_temp
end

DROP PROCEDURE IF EXISTS get_age_autori_nazione_cursore;
DELIMITER //
CREATE PROCEDURE get_age_autori_nazione_cursore(IN nazioneIn VARCHAR(255))
BEGIN
    -- Dichiarazione delle variabili per i dati del cursore
    DECLARE done INT DEFAULT 0;
    DECLARE nome_autore VARCHAR(255);
    DECLARE cognome_autore VARCHAR(255);
    DECLARE data_di_nascita DATE;
       declare age int;

    DECLARE cur CURSOR for
        SELECT nome,cognome,data_nascita
        FROM autori
        WHERE nazione = nazioneIn AND data_morte IS NULL;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    DROP TABLE IF EXISTS autori_eta_temp;
    CREATE TABLE autori_eta_temp (
        nome VARCHAR(255),
        cognome VARCHAR(255),
        eta INT
    );

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO nome_autore, cognome_autore, data_di_nascita;
        IF done = 1 THEN 
            LEAVE read_loop;
        END IF;
           SET age = DATEDIFF(CURRENT_DATE, data_di_nascita) / 365;
        INSERT INTO autori_eta_temp (nome, cognome, eta)
        VALUES (nome_autore, cognome_autore, age);
    END LOOP read_loop;
    CLOSE cur;
       select 'operazione conclusa';
end; //

