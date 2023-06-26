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
END