use gamelog;

-- select * from team where

select * from tm_gamelog;

select * from v_gamelog where gamelog_code LIKE '2019-%';
select * from gamelog where gamelog_code LIKE '2019-%';

select count(*) from gamelog where gamelog_code LIKE '2017-%';
select count(*) from gamelog where gamelog_code LIKE '2018-%';
select count(*) from gamelog where gamelog_code LIKE '2019-%';

