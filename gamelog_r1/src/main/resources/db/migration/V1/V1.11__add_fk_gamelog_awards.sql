ALTER TABLE gamelog_awards ADD constraint FK_GAMELOG_AWARDS_GAMELOG
	foreign key (GAMELOG_ID_FK)
    references GAMELOG(GAMELOG_ID_PK);
