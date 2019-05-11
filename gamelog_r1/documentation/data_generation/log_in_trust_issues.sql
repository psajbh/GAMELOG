use sys;
select * from sys_config order by variable;

use gamelog;

delimiter $
create function gamelog.getnumber()
returns int DETERMINISTIC
begin
 declare num int;
 set num = 50;
 return num ;
end$
delimiter ;

select getNumber() from dual;

select 1 from dual;

-- Error Code: 1419. 
-- You do not have the SUPER privilege and binary logging is 
-- enabled (you *might* want to use the less safe log_bin_trust_function_creators variable)
-- http://wiki.ispirer.com/sqlways/troubleshooting-guide/mysql/import/binary-logging
-- need to log in as root
SET GLOBAL log_bin_trust_function_creators = 1;
--  Error Code: 1227. Access denied; you need (at least one of) the SUPER or SYSTEM_VARIABLES_ADMIN privilege(s) 
-- for this operation


