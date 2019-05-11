use gamelog;

-- Error Code: 1175. You are using safe update mode and you tried to update a table 
-- without a WHERE that uses a KEY column.  To disable safe mode, toggle the option in 
-- Preferences -> SQL Editor and reconnect.
-- -- SET SQL_SAFE_UPDATES = 0;

-- Error Code: 1217. Cannot delete or update a parent row: a foreign key constraint fails
-- SET FOREIGN_KEY_CHECKS=0;   
-- after deletes, reset foreign key checks y
-- SET FOREIGN_KEY_CHECKS=1;


select * from ump_gamelog where date_created >= '2019-02-18';

select count(*) from gamelog;

SET FOREIGN_KEY_CHECKS=0;
delete from ump_gamelog where date_created >= '2019-03-01';
delete from gamelog_awards where date_created >= '2019-02-18';
delete from tm_gameline_bat where date_created >= '2019-02-18';
delete from tm_gameline_defense where date_created >= '2019-02-18';
delete from tm_gameline_pitch where date_created >= '2019-02-18';
delete from tm_gamelog where date_created >= '2019-02-18';
delete from park where date_created >= '2019-02-18';
delete from team where date_created >= '2019-02-18';
delete from lineitem_order where date_created >= '2019-02-18';
delete from lineitem_pos where date_created >= '2019-02-18';
delete from gamelog where date_created >= '2019-02-18';
delete from gamelog_awards where date_created >= '2019-02-18';
delete from person where date_created >= '2019-02-18';
select count(*) from season where date_created >= '2019-02-18';
delete from season where date_created >= '2019-02-18';
SET FOREIGN_KEY_CHECKS=1;

