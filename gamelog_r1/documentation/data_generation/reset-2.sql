use gamelog;

SET SQL_SAFE_UPDATES=0;
delete from ump_gamelog;
delete from lineitem_order;
delete from lineitem_pos;
delete from tm_gameline_bat;
delete from tm_gameline_pitch;
delete from tm_gameline_defense;
delete from tm_gamelog;
delete from gamelog;
delete from gamelog_awards;
SET SQL_SAFE_UPDATES=1;

show table status;
-- use this on 
use gamelog;
SET FOREIGN_KEY_CHECKS = 0;
delete from ump_gamelog;
delete from lineitem_order;
delete from lineitem_pos;
delete from tm_gameline_bat;
delete from tm_gameline_pitch;
delete from tm_gameline_defense;
delete from tm_gamelog;
delete from gamelog;
delete from gamelog_awards;
SET FOREIGN_KEY_CHECKS = 1;