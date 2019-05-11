example code:
-- https://www.fastwebhost.in/blog/mysql-list-users-how-to-list-mysql-user-accounts-via-command-line/
select User from mysql.user;

rename USER 'tester'@'localhost' TO 'john'@'localhost';
-- user john pw = 'tester'

GRANT ALL ON gamelog.* TO 'john'@'localhost';

-- https://stackoverflow.com/questions/50387952/how-to-resolve-unable-to-load-authentication-plugin-caching-sha2-password-issu
ALTER USER 'john'@'localhost' IDENTIFIED WITH mysql_native_password BY 'tester';
