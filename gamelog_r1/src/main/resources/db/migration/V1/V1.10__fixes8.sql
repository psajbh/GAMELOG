ALTER TABLE LINEITEM_POS DROP COLUMN POS_DESIGNATED_HITTER_PERSON_ID_FK;
ALTER TABLE LINEITEM_POS ADD COLUMN POS_DESIGNATED_HITTER_PERSON_ID_FK bigint(20) unsigned;

ALTER TABLE GAMELOG DROP FOREIGN KEY FK_GAMELOG_PERSON_WP;
ALTER TABLE GAMELOG DROP FOREIGN KEY FK_GAMELOG_PERSON_LP;
ALTER TABLE GAMELOG DROP FOREIGN KEY FK_GAMELOG_PERSON_SP;
ALTER TABLE GAMELOG DROP FOREIGN KEY FK_GAMELOG_PERSON_GWRBI;

ALTER TABLE GAMELOG DROP COLUMN WP_PERSON_ID_FK;
ALTER TABLE GAMELOG DROP COLUMN LP_PERSON_ID_FK;
ALTER TABLE GAMELOG DROP COLUMN SP_PERSON_ID_FK;
ALTER TABLE GAMELOG DROP COLUMN GWRBI_PERSON_ID_FK;

ALTER TABLE GAMELOG ADD COLUMN GAMELOG_AWARDS_ID_FK bigint(20) unsigned;
-- ALTER TABLE GAMELOG ADD COLUMN POS_DESIGNATED_HITTER_PERSON_ID_FK varchar(11);

CREATE TABLE if not exists GAMELOG_AWARDS(
	GAMELOG_AWARDS_ID_PK bigint UNSIGNED not null auto_increment,
    GAMELOG_ID_FK bigint UNSIGNED not null,
	GAMELOG_CODE varchar(11),
    WP_PERSON_ID_FK bigint UNSIGNED,
	LP_PERSON_ID_FK bigint UNSIGNED,
	SP_PERSON_ID_FK bigint UNSIGNED,
	GWRBI_PERSON_ID_FK bigint UNSIGNED,
	VERSION int(11) DEFAULT '0',
  	CREATED_BY varchar(255) DEFAULT 'FLYWAY' ,
  	DATE_CREATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	UPDATED_BY varchar(255) DEFAULT 'FLYWAY',
  	DATE_UPDATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	PRIMARY KEY (GAMELOG_AWARDS_ID_PK)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=UTF8MB4;

ALTER TABLE GAMELOG_AWARDS ADD constraint FK_GAMELOG_AWARDS_PERSON_WP
	foreign key (WP_PERSON_ID_FK)
	references PERSON(PERSON_ID_PK);
	
ALTER TABLE GAMELOG_AWARDS ADD constraint FK_GAMELOG_AWARDS_PERSON_LP
	foreign key (LP_PERSON_ID_FK)
	references PERSON(PERSON_ID_PK);
	
ALTER TABLE GAMELOG_AWARDS ADD constraint FK_GAMELOG_AWARDS_PERSON_SP
	foreign key (SP_PERSON_ID_FK)
	references PERSON(PERSON_ID_PK);
	
ALTER TABLE GAMELOG_AWARDS ADD constraint FK_GAMELOG_AWARDS_PERSON_GWRBI
	foreign key (GWRBI_PERSON_ID_FK)
	references PERSON(PERSON_ID_PK);

ALTER TABLE GAMELOG ADD constraint FK_GAMELOG_AWARDS
	foreign key (GAMELOG_AWARDS_ID_FK)
	references GAMELOG_AWARDS(GAMELOG_AWARDS_ID_PK);
