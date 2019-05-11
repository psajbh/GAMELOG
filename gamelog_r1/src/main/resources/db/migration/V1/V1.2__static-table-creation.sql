insert into DIVISION (DIVISION_CODE, NAME) values ('E', 'East');
insert into DIVISION (DIVISION_CODE, NAME) values ('C', 'Central');
insert into DIVISION (DIVISION_CODE, NAME) values ('W', 'West');

insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('NL', 'National League', (select DIVISION_ID_PK from DIVISION where DIVISION_CODE = 'E'));
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('NL', 'National League', (select DIVISION_ID_PK from DIVISION where DIVISION_CODE = 'C'));
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('NL', 'National League', (select DIVISION_ID_PK from DIVISION where DIVISION_CODE = 'W'));
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('NL', 'National League', null);
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('AL', 'American League', (select DIVISION_ID_PK from DIVISION where DIVISION_CODE = 'E'));
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('AL', 'American League', (select DIVISION_ID_PK from DIVISION where DIVISION_CODE = 'C'));
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('AL', 'American League', (select DIVISION_ID_PK from DIVISION where DIVISION_CODE = 'W'));
insert into LEAGUE (LEAGUE_CODE, NAME, DIVISION_ID_FK) values ('AL', 'American League', null);

insert into LEAGUE (LEAGUE_CODE, NAME) values ('NA', 'National Association');
insert into LEAGUE (LEAGUE_CODE, NAME) values ('AA', 'American Association');
insert into LEAGUE (LEAGUE_CODE, NAME) values ('FL', 'Federal League');
insert into LEAGUE (LEAGUE_CODE, NAME) values ('PL', 'Players League');
insert into LEAGUE (LEAGUE_CODE, NAME) values ('UD', 'Undetermined');
insert into LEAGUE (LEAGUE_CODE, NAME) values ('UA', 'Union Association');

insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('0', 'single game');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('1', 'the first game of a double (or triple) header');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('2', 'the second game of a double (or triple) header');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('3', 'the third game of a triple-header');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('A', 'the first game of a double-header involving 3 teams');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('B', 'the second game of a double-header involving 3 teams');
insert into GAMETYPE (GAMETYPE_CODE, GAMETYPE_DESC) values ('UNK', 'unknown game type');

insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Sun', 1);
insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Mon', 2);
insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Tue', 3);
insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Wed', 4);
insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Thu', 5);
insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Fri', 6);
insert into WEEKDAY (WEEKDAY_CODE, WEEKDAY_NUM) values ('Sat', 7);


