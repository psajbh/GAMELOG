-- http://www.mysqltutorial.org/mysql-join/

CREATE TABLE t1 (
    id INT PRIMARY KEY,
    pattern VARCHAR(50) NOT NULL
);
 
CREATE TABLE t2 (
    id VARCHAR(50) PRIMARY KEY,
    pattern VARCHAR(50) NOT NULL
);

INSERT INTO t1(id, pattern)
VALUES(1,'Divot'),
      (2,'Brick'),
      (3,'Grid');
 
INSERT INTO t2(id, pattern)
VALUES('A','Brick'),
      ('B','Grid'),
      ('C','Diamond');
      
 select * from t1;
 select * from t2;
 
 -- cross join
 SELECT t1.id,  t2.id
 FROM t1
 CROSS JOIN t2;
 
 SELECT  t1.id, t2.id
 FROM t1
  INNER JOIN  t2 
  ON t1.pattern = t2.pattern;
  
SELECT t1.id, t2.id
FROM  t1
LEFT JOIN t2 
ON t1.pattern = t2.pattern
ORDER BY t1.id;  

SELECT t1.id, t2.id
FROM  t1
RIGHT JOIN  t2 on t1.pattern = t2.pattern
ORDER BY t2.id;

-- https://stackoverflow.com/questions/4796872/how-to-do-a-full-outer-join-in-mysql 
SELECT * FROM t1
LEFT JOIN t2 ON t1.id = t2.id
UNION
SELECT * FROM t1;
-- RIGHT JOIN t2 ON t1.id = t2.id


