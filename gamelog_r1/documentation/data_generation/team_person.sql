use gamelog;

SELECT t.team_id_pk, t.team_code, t.name, t.alias,
f.franchise_code , s.season_code,
l.league_code, d.division_code,
loc.city, loc.state_code
FROM team t, franchise f, season s
, league l , division d, location loc
where t.franchise_id_fk = f.franchise_Id_pk 
and t.season_id_fk = s.season_id_pk
and t.league_id_fk = l.league_id_pk
and l.division_id_fk = d.division_id_pk
and t.location_id_fk = loc.location_id_pk
;

use gamelog;

-- =====================================


create table if not exists PERSON(
	PERSON_ID_PK bigint not null auto_increment,
    PERSON_CODE  varchar(15) unique,
    FULL_NAME varchar(92) not null,
    LAST_NAME varchar(30),
    MIDDLE_NAME varchar (30),
    FIRST_NAME varchar(30),
    NICKNAME varchar(30),
    OKA varchar(200),
	primary key (PERSON_ID_PK)
)engine=InnoDB;

create table if not exists PERSON_DATE(
	PERSON_DATE_ID_PK bigint not null auto_increment,
    PERSON_DETAIL_FK bigint not null,
    DATE_CODE varchar(10),
    DATE_VALUE date,
    primary key (PERSON_DATE_ID_PK)
)engine=InnoDB;

create table if not exists PERSON_DETAIL (
	PERSON_DETAIL_ID_PK bigint not null,
    BIOSTAT_FK bigint, 
    PERSON_DATE_FK bigint,
    primary key (PERSON_DETAIL_ID_PK),
    constraint FK_PERSON_DETAIL_PERSON foreign key (PERSON_DETAIL_ID_PK) references PERSON(PERSON_ID_PK),
    constraint FK_PERSON_DATE_PERSON foreign key (PERSON_DATE_FK) references PERSON_DATE(PERSON_DATE_ID_PK)
)engine=InnoDB;

create table if not exists BIOSTAT (
	BIOSTAT_ID_PK bigint not null,
	BAT char(1) DEFAULT 'U',
    THROW char(1) DEFAULT 'U',
    HEIGHT tinyint DEFAULT 0,
    WEIGHT smallInt DEFAULT 0,
    VERSION int(11) DEFAULT 0,
  	CREATED_BY varchar(255) DEFAULT 'FLYWAY' ,
  	DATE_CREATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	UPDATED_BY varchar(255) DEFAULT 'FLYWAY',
  	DATE_UPDATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (BIOSTAT_ID_PK)
)engine=InnoDB;


/*
create table if not exists BIOSTAT (
	BIOSTAT_ID_PK bigint not null,
    PERSON_DETAIL_ID_FK bigint not null,
    BAT_ID_FK int,
    THROW_ID_FK int,
    MEASURE_ID_FK int,
    primary key (BIOSTAT_ID_PK),
    constraint FK_BIOSTAT_PERSON_DETAIL foreign key (PERSON_DETAIL_ID_FK) references PERSON_DETAIL(PERSON_DETAIL_ID_PK)
)engine=InnoDB;
*/

create table if not exists BAT (
	BAT_ID_PK int not null auto_increment,
    BAT_CODE char(1),
    BAT_NAME varchar(5),
    primary key(BAT_ID_PK)
)engine=InnoDB;

insert into BAT (BAT_CODE, BAT_NAME) values ('L', 'Left');
insert into BAT (BAT_CODE, BAT_NAME) values ('R', 'Right');
insert into BAT (BAT_CODE, BAT_NAME) values ('B', 'Both');


create table if not exists THROW (
	THROW_ID_PK int not null auto_increment,
    THROW_CODE char(1),
    THROW_NAME varchar(5),
    primary key(THROW_ID_PK)
)engine=InnoDB;

insert into THROW (THROW_CODE, THROW_NAME) values ('L', 'Left');
insert into THROW (THROW_CODE, THROW_NAME) values ('R', 'Right');

drop table Measure;

create table if not exists MEASURE (
	MEASURE_ID_PK bigint not null auto_increment,
    BIOSTAT_ID_FK bigint,
    HEIGHT int,
    WEIGHT int,
    primary key (MEASURE_ID_PK),
    constraint FK_BIOSTAT_MEASURE foreign key (BIOSTAT_ID_FK) references BIOSTAT(BIOSTAT_ID_PK)
)engine=InnoDB;

create table if not exists PERSON_DATE(
	PERSON_DATE_ID_PK bigint not null auto_increment,
    PERSON_DETAIL_FK bigint not null,
    DATE_CODE varchar(10),
    DATE_VALUE date,
    primary key (PERSON_DATE_ID_PK)
)engine=InnoDB;


create table if not exists PERSON_DATE(
	PERSON_DATE_ID_PK bigint not null auto_increment,
    PERSON_DETAIL_FK bigint not null,
    DATE_CODE varchar(10),
    DATE_VALUE date,
	VERSION int(11) DEFAULT '0',
  	CREATED_BY varchar(255) DEFAULT 'FLYWAY' ,
  	DATE_CREATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	UPDATED_BY varchar(255) DEFAULT 'FLYWAY',
  	DATE_UPDATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (PERSON_DATE_ID_PK)
)engine=InnoDB;

alter table PERSON_DATE DROP COLUMN DATE_CODE;
alter table PERSON_DATE DROP COLUMN DATE_VALUE;

alter table PERSON_DATE ADD COLUMN DATE_OF_BIRTH DATE;
alter table PERSON_DATE ADD COLUMN DATE_OF_DEATH DATE;
alter table PERSON_DATE ADD COLUMN DATE_OF_DEBUT_COACH DATE;
alter table PERSON_DATE ADD COLUMN DATE_OF_DEBUT_MANAGER DATE;
alter table PERSON_DATE ADD COLUMN DATE_OF_DEBUT_PLAYER DATE;
alter table PERSON_DATE ADD COLUMN DATE_OF_DEBUT_UMPIRE DATE;

use gamelog;

alter table PERSON_DATE ADD COLUMN DATE_OF_DEBUT_UMPIRE DATE;
alter table PERSON_DATE DROP COLUMN DATE_OF_DEBUT_UMPIRE;

-- comment

alter table person_detail DROP COLUMN BIOSTAT_FK;

ALTER TABLE person_detail DROP FOREIGN KEY FK_BIOSTAT_PERSON_DETAIL;


--  

alter table biostat DROP COLUMN PERSON_DETAIL_ID_FK;

ALTER TABLE person_date DROP COLUMN PERSON_DETAIL_FK;
ALTER TABLE person_date DROP COLUMN PERSON_DATE_ID_PK;
ALTER TABLE person_date ADD COLUMN PERSON_DATE_ID_PK

ALTER TABLE person_date DROP COLUMN PERSON_DETAIL_FK

alter table person_detail DROP COLUMN BIOSTAT_FK;
alter table person_detail DROP COLUMN PERSON_DATE_FK;
alter table person_detail ADD COLUMN LOCATION_FK;


-- DROP FOREIGN KEY FK_PERSON_DATE_PERSON;
  
ALTER TABLE person_detail
  DROP FOREIGN KEY FK_BIOSTAT_PERSON_DETAIL;
  
ALTER TABLE person_detail 
	ADD COLUMN LOCATION_ID_FK bigint;
	
ALTER TABLE person_detail
	ADD CONSTRAINT PERSON_LOCATION_FK
    FOREIGN KEY(LOCATION_ID_FK)
    REFERENCES LOCATION(LOCATION_ID_PK);


alter table biostat DROP COLUMN PERSON_DETAIL_ID_FK;
-- Error Code: 1828. Cannot drop column 'PERSON_DETAIL_ID_FK': needed in a foreign key constraint 'FK_BIOSTAT_PERSON_DETAIL'


ALTER TABLE person_date DROP COLUMN PERSON_DETAIL_FK;


ALTER TABLE person_date DROP COLUMN PERSON_DATE_ID_PK;

ALTER TABLE person_date ADD COLUMN PERSON_DATE_ID_PK bigint;


ALTER TABLE person_date DROP COLUMN PERSON_DETAIL_FK;

alter table person_detail DROP COLUMN BIOSTAT_FK;
alter table person_detail DROP COLUMN PERSON_DATE_FK;
alter table person_detail ADD COLUMN LOCATION_FK bigint;




-- =====================================

-- alter table MEASURE add constraint FK_BIOSTAT_MEASURE foreign key (BIOSTAT_ID_FK) references BIOSTAT(BIOSTAT_ID_PK)


-- alter table BIOSTAT add constraint FK_PERSON_DETAIL foreign key (PERSON_DETAIL_FK) references MEASURE(PERSON_DETAIL_ID_PK);


-- alter table PERSON_DETAIL add constraint FK_PERSON foreign key (PERSON_ID_FK) references PERSON(PERSON_ID_PK);
-- alter table PERSON_DETAIL add constraint FK_BIOSTAT foreign key (BIOSTAT_FK) references BIOSTAT(BIOSTAT_ID_PK);
-- alter table PERSON_DETAIL add constraint FK_PERSON_NAME foreign key (PERSON_NAME_FK) references PERSON_NAME(PERSON_NAME_ID_PK);
-- alter table PERSON_DETAIL add constraint FK_PERSON_DATE foreign key (PERSON_DATE_FK) references PERSON_DATE(PERSON_DATE_ID_PK);
-- alter table PERSON_DETAIL add constraint FK_LOCATION foreign key (LOCATION_FK) references LOCATION(LOCATION_ID_PK);

-- create table if not exists PERSON_NAME(
-- 	PERSON_NAME_ID_PK int not null auto_increment,
--     PERSON_DETAIL_FK int not null,
-- 	primary key (PERSON_NAME_ID_PK)
-- )engine=InnoDB;

-- alter table PERSON_NAME add constraint FK_PERSON_DETAIL foreign key (PERSON_DETAIL_FK) references PERSON_DETAIL(PERSON_DETAIL_ID_PK);

-- alter table PERSON_DATE add constraint FK_PERSON_DETAIL foreign key (PERSON_DETAIL_FK) references PERSON_DETAIL(PERSON_DETAIL_ID_PK);



