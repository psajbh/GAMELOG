alter table gamelog MODIFY COLUMN GAMETYPE_ID_FK VARCHAR(5);
alter table gamelog MODIFY COLUMN DAY_OF_WEEK_FK VARCHAR(3);
alter table gametype MODIFY COLUMN GAMETYPE_CODE VARCHAR(5);

DELETE FROM gamelog.gametype;

insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('ZERO', 'single game');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('ONE', 'the first game of a double (or triple) header');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('TWO', 'the second game of a double (or triple) header');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('THREE', 'the third game of a triple-header');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('A', 'the first game of a double-header involving 3 teams');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('B', 'the second game of a double-header involving 3 teams');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('UNK', 'unknown game type');
