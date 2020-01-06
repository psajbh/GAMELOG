use gamelog;

-- {MySQL - How to temporarily disable a foreign keyconstraint?|https://tableplus.com/blog/2018/08/mysql-how-to-temporarily-disable-foreign-key-constraints.html]]

SET FOREIGN_KEY_CHECKS=0;

DELETE from division where LEFT(date_created,4)  = '2020';
DELETE from franchise where LEFT(date_created,4)  = '2020';
DELETE from weekday where LEFT(date_created,4)  = '2020';
DELETE from gametype where LEFT(date_created,4)  = '2020';
DELETE from league where LEFT(date_created,4)  = '2020';
DELETE from park where LEFT(date_created,4)  = '2020';
DELETE from season where LEFT(date_created,4)  = '2020';
DELETE from location where LEFT(date_created,4)  = '2020';
DELETE from ump_gamelog where LEFT(date_created,4)  = '2020';
DELETE from tm_gameline_bat where LEFT(date_created,4)  = '2020';
DELETE from tm_gameline_defense where LEFT(date_created,4)  = '2020';
DELETE from tm_gameline_pitch where LEFT(date_created,4)  = '2020';
DELETE from tm_gamelog where LEFT(date_created,4)  = '2020';
DELETE from lineitem_order where LEFT(date_created,4)  = '2020';
DELETE from lineitem_pos where LEFT(date_created,4)  = '2020';
DELETE from team where LEFT(date_created,4)  = '2020';
DELETE from person where LEFT(date_created,4)  = '2020';
DELETE from gamelog_awards where LEFT(date_created,4)  = '2020';
DELETE from gamelog where LEFT(date_created,4)  = '2020';

SET FOREIGN_KEY_CHECKS=1;
