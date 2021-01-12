drop database if exists shipmanagmentdb;
create database shipmanagmentdb;
use shipmanagmentdb;

CREATE TABLE utente (
    cod_fiscale 			varchar(16),
    password 		        BINARY(20) not null,
    nome 					varchar(50) NOT NULL,
    cognome 				varchar(50) NOT NULL,
    data_nascita 			timestamp NOT NULL,
    luogo_nascita 			varchar(50) NOT NULL,
    email 					varchar(255) unique,
    telefono 				varchar(15) unique,
    attivato 				varchar(30),
    ruolo					enum('cliente', 'armatore', 'broker') NOT NULL,
							PRIMARY KEY (cod_fiscale),
                            FULLTEXT(nome,cognome)
);

CREATE TABLE porto(
	localcode				varchar(6),
	nome					varchar(50),
    id_area				    bigint REFERENCES area(id) on UPDATE CASCADE on DELETE CASCADE,
							PRIMARY KEY (localcode)
);

CREATE TABLE area(
	id					    bigint auto_increment,
	nome					varchar(10),
							PRIMARY KEY (id)
);

CREATE TABLE notifica (
	id						bigint auto_increment,
    oggetto 				varchar(50) NOT NULL,
    corpo 					text NOT NULL,
							PRIMARY KEY (id)
);

CREATE TABLE notifica_utente (
	id_notifica				bigint REFERENCES notifica(id) on UPDATE CASCADE on DELETE CASCADE,
	cod_fiscale_utente		varchar(16) REFERENCES utente(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
							PRIMARY KEY (id_notifica, cod_fiscale_utente)
);

CREATE TABLE compagnia_broker (
    cod_fiscale 			varchar(16),
    nome 					varchar(50) NOT NULL,
    telefono 				varchar(30) unique,
    sede_legale 			varchar(50) NOT NULL,
    sito_web 				varchar(255) unique,
							PRIMARY KEY (cod_fiscale)
);

CREATE TABLE compagnia_broker_utente(
    cod_fiscale_compagnia   varchar(16) REFERENCES compagnia_broker(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
	cod_fiscale_utente 		varchar(16) REFERENCES utente(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
							PRIMARY KEY (cod_fiscale_compagnia, cod_fiscale_utente)
);

CREATE TABLE imbarcazione (
	cod_fiscale_utente 		varchar(16) REFERENCES utente(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
    imo 					varchar(30),
    nome 					varchar(50) NOT NULL,
    tipologia 				enum('Portacontainer', 'Carboniera','Chimichiera','Lift-on/Lift-off','Nave da Carico', 'Nave Frigorifera', 'Portarinfuse', 'Roll-on/Roll-off') NOT NULL,
    anno_costruzione 		integer(4) NOT NULL,
    bandiera				enum('AF','AL','DZ','AD','AO','AI','AQ','AG','AN','SA','AR','AM','AW','AU','AT','AZ','BS','BH','BD','BB','BE','BZ','BJ','BM','BY','BT','BO','BA','BW','BR','BN','BG','BF','BI','KH','CM','CA','CV','TD','CL','CN','CY','VA','CO','KM','KP','KR','CR','CI','HR','CU','DK','DM','EC','EG','IE','SV','AE','ER','EE','ET','RU','FJ','PH','FI','FR','GA','GM','GE','DE','GH','JM','JP','GI','DJ','JO','GR','GD','GL','GP','GU','GT','GN','GW','GQ','GY','GF','HT','HN','HK','IN','ID','IR','IQ','BV','CX','HM','KY','CC','CK','FK','FO','MH','MP','UM','NF','SB','TC','VI','VG','IL','IS','IT','KZ','KE','KG','KI','KW','LA','LV','LS','LB','LR','LY','LI','LT','LU','MO','MK','MG','MW','MV','MY','ML','MT','MA','MQ','MR','MU','YT','MX','MD','MC','MN','MS','MZ','MM','NA','NR','NP','NI','NE','NG','NU','NO','NC','NZ','OM','NL','PK','PW','PA','PG','PY','PE','PN','PF','PL','PT','PR','QA','GB','CZ','CF','CG','CD','DO','RE','RO','RW','EH','KN','PM','VC','WS','AS','SM','SH','LC','ST','SN','XK','SC','SL','SG','SY','SK','SI','SO','ES','LK','FM','US','ZA','GS','SD','SR','SJ','SE','CH','SZ','TJ','TH','TW','TZ','IO','TF','PS','TL','TG','TK','TO','TT','TN','TR','TM','TV','UA','UG','HU','UY','UZ','VU','VE','VN','WF','YE','ZM','ZW','RS','ME','TP','GG') NOT NULL,
    quantita_max	        float NOT NULL,
    lunghezza_fuori_tutto	float NOT NULL,
    ampiezza 				float NOT NULL,
    altezza 				float NOT NULL,
	posizione				bigint REFERENCES area(id) on UPDATE CASCADE on DELETE CASCADE,
    disponibile 			boolean default true,
    documento 				blob,
							PRIMARY KEY (imo)
);


CREATE TABLE richiesta (
	id						bigint auto_increment,
	cod_fiscale_utente 		varchar(16) REFERENCES utente(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
    tipo_carico 			varchar(50) NOT NULL,
    quantita 			float NOT NULL,
    data_partenza 			timestamp NOT NULL,
    porto_partenza 			varchar(6) REFERENCES porto(localcode) on UPDATE CASCADE on DELETE CASCADE,
    data_arrivo 			timestamp NOT NULL,
    porto_arrivo 			varchar(6) REFERENCES porto(localcode) on UPDATE CASCADE on DELETE CASCADE,
    stato		 			enum('Disponibile', 'In Lavorazione', 'Terminata') default 'Disponibile',
    documento 				blob,
							PRIMARY KEY (id)
);


CREATE TABLE mediazione (
	id						bigint auto_increment,
    nome 					varchar(50) NOT NULL,
    stato 					enum('Default', 'In Corso', 'Richiesta Modifica', 'Richiesta Terminazione', 'In Attesa di Firma', 'Terminta') default 'Default',
    contratto 				blob,
	cod_fiscale_utente 		varchar(16) REFERENCES utente(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
							PRIMARY KEY (id)
);


CREATE TABLE mediazione_richiesta (
    id_mediazione 			bigint REFERENCES mediazione(id) on UPDATE CASCADE on DELETE CASCADE,
	id_richiesta 			varchar(16) REFERENCES utente(cod_fiscale) on UPDATE CASCADE on DELETE CASCADE,
							PRIMARY KEY (id_mediazione, id_richiesta)
);

CREATE TABLE mediazione_imbarcazione (
    id_mediazione 			bigint REFERENCES mediazione(id) on UPDATE CASCADE on DELETE CASCADE,
	imo_imbarcazione		varchar(30) REFERENCES imbarcazione(imo) on UPDATE CASCADE on DELETE CASCADE,
							PRIMARY KEY (id_mediazione, imo_imbarcazione)
);
