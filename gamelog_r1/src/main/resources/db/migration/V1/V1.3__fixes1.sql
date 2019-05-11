-- fix to team which inadvertently made TEAM_CODE unique.
ALTER TABLE TEAM DROP INDEX TEAM_CODE;

-- just an example on how to add a constraint to an existing table:
-- ALTER TABLE team ADD CONSTRAINT TEAM_CODE UNIQUE (TEAM_CODE);