ALTER TABLE UMP_GAMELOG ADD COLUMN GAME_CODE varchar(11);

ALTER TABLE TM_GAMELOG ADD COLUMN GAME_CODE varchar(11);

ALTER TABLE TM_GAMELINE_BAT ADD COLUMN GAME_CODE varchar(11);

ALTER TABLE TM_GAMELINE_PITCH ADD COLUMN GAME_CODE varchar(11);

ALTER TABLE TM_GAMELINE_DEFENSE ADD COLUMN GAME_CODE varchar(11);

ALTER TABLE LINEITEM_ORDER ADD COLUMN GAME_CODE varchar(11);

ALTER TABLE LINEITEM_POS ADD COLUMN GAME_CODE varchar(11);

