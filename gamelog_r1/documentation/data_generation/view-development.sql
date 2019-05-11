

drop view pos_starters;
use gamelog;

-- https://stackoverflow.com/questions/21936329/change-value-from-1-to-y-in-mysql-select-statement
select gl.gamelog_id_pk gPK, gl.gamelog_code gl_code, 
-- s.season_code, 
gl.date_of_game, 
 IF(gl.day_game = 0, 'D', 'N') AS 'D/N'
, gl.day_of_week_fk DOW, 
gl.attendance attend, gl.time_of_game TOV,
t1.team_code tm, tmgl.home_away_code H_A, glbat.ab, glbat.H,
sp_person.full_name AS SP, 
v.score 
from tm_gamelog tmgl, gamelog gl, season s, team t1,
tm_gameline_bat glbat, lineitem_pos pos, person sp_person
, vhscore v
where tmgl.gamelog_id_fk = gl.gamelog_id_pk
and s.season_id_pk = gl.season_id_fk
and t1.team_id_pk = tmgl.team_id_fk
and pos.tm_gamelog_id_fk = tmgl.tm_gamelog_id_pk
and pos.pos_pitcher_person_id_fk = sp_person.person_id_pk   
-- and pos.pos_catcher_person_id_fk = sp_person.person_id_pk
and glbat.tm_gamelog_id_fk = tmgl.tm_gamelog_id_pk
and v.tmGameLogId = tmgl.tm_gamelog_id_pk
order by gPK, season_code, date_of_game, H_A desc
;


select manager.tm_gamelog_id_fk, manager.game_code, p.full_name , 'M'
from lineitem_pos manager, person p
where manager.pos_manager_person_id_fk = p.person_id_pk;

select pitchers.tm_gamelog_id_fk, pitchers.game_code, p.full_name , 'SP'
from lineitem_pos pitchers, person p
where pitchers.pos_pitcher_person_id_fk = p.person_id_pk;



-- CREATE VIEW gamelog.pos_starters AS 

select pitchers.tm_gamelog_id_fk, pitchers.game_code, p.full_name , 'SP'
from lineitem_pos pitchers, person p
where pitchers.pos_pitcher_person_id_fk = p.person_id_pk;

select catchers.tm_gamelog_id_fk, catchers.game_code, p.full_name , 'C'
from lineitem_pos catchers, person p
where catchers.pos_catcher_person_id_fk = p.person_id_pk;

select firstbase.tm_gamelog_id_fk, firstbase.game_code, p.full_name , '1B'
from lineitem_pos firstbase, person p
where firstbase.pos_firstbase_person_id_fk = p.person_id_pk;

select secondbase.tm_gamelog_id_fk, secondbase.game_code, p.full_name , '2B'
from lineitem_pos secondbase, person p
where secondbase.pos_secondbase_person_id_fk = p.person_id_pk;

select thirdbase.tm_gamelog_id_fk, thirdbase.game_code, p.full_name , '3B'
from lineitem_pos thirdbase, person p
where thirdbase.pos_thirdbase_person_id_fk = p.person_id_pk;

select shortstop.tm_gamelog_id_fk, shortstop.game_code, p.full_name , '3B'
from lineitem_pos shortstop, person p
where shortstop.pos_shortstop_person_id_fk = p.person_id_pk;

select shortstop.tm_gamelog_id_fk, shortstop.game_code, p.full_name , 'SS'
from lineitem_pos shortstop, person p
where shortstop.pos_shortstop_person_id_fk = p.person_id_pk;

select left_field.tm_gamelog_id_fk, left_field.game_code, p.full_name , 'LF'
from lineitem_pos left_field, person p
where left_field.pos_left_field_person_id_fk = p.person_id_pk;

select center_field.tm_gamelog_id_fk, center_field.game_code, p.full_name , 'CF'
from lineitem_pos center_field, person p
where center_field.pos_center_field_person_id_fk = p.person_id_pk;

select manager.tm_gamelog_id_fk, manager.game_code, p.full_name , 'M'
from lineitem_pos manager, person p
where manager.pos_manager_person_id_fk = p.person_id_pk;





CREATE VIEW gamelog.v_catcher AS 
select catchers.tm_gamelog_id_fk as id, catchers.game_code as gc, p.full_name as 'C'
from lineitem_pos catchers, person p
where catchers.pos_catcher_person_id_fk = p.person_id_pk;

select * from gamelog.v_firstbase;

DROP VIEW gamelog.v_firstbase;

CREATE VIEW gamelog.v_firstbase AS
select firstbase.tm_gamelog_id_fk as id, firstbase.game_code as gc, p.full_name as 'FB'
from lineitem_pos firstbase, person p
where firstbase.pos_firstbase_person_id_fk = p.person_id_pk;

DROP VIEW gamelog.v_second;

CREATE VIEW gamelog.v_secondbase AS
select secondbase.tm_gamelog_id_fk as id, secondbase.game_code as gc, p.full_name as 'SB'
from lineitem_pos secondbase, person p
where secondbase.pos_secondbase_person_id_fk = p.person_id_pk;

DROP VIEW gamelog.v_thirdbase;

CREATE VIEW gamelog.v_thirdbase AS
select thirdbase.tm_gamelog_id_fk as id, thirdbase.game_code as gc, p.full_name as 'TB'
from lineitem_pos thirdbase, person p
where thirdbase.pos_thirdbase_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_shortstop AS
select shortstop.tm_gamelog_id_fk as id, shortstop.game_code as gc, p.full_name as 'SS'
from lineitem_pos shortstop, person p
where shortstop.pos_shortstop_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_leftfield AS
select left_field.tm_gamelog_id_fk as id, left_field.game_code as gc , p.full_name as 'LF'
from lineitem_pos left_field, person p
where left_field.pos_left_field_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_centerfield AS
select center_field.tm_gamelog_id_fk as id, center_field.game_code as gc, p.full_name as 'CF'
from lineitem_pos center_field, person p
where center_field.pos_center_field_person_id_fk = p.person_id_pk;

drop view gamelog.v_rightfield;

CREATE VIEW gamelog.v_rightfield AS
select right_field.tm_gamelog_id_fk as id, right_field.game_code as gc, p.full_name as 'RF'
from lineitem_pos right_field, person p
where right_field.pos_right_field_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_manager AS 
select manager.tm_gamelog_id_fk as id, manager.game_code as gc, p.full_name as Manager
from lineitem_pos manager, person p
where manager.pos_manager_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_sp AS
select pitchers.tm_gamelog_id_fk id, pitchers.game_code gc, p.full_name as SP
from lineitem_pos pitchers, person p
where pitchers.pos_pitcher_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_dh AS
select pitchers.tm_gamelog_id_fk id, pitchers.game_code gc, p.full_name as DH
from lineitem_pos dh, person p
where dh.pos_dh_person_id_fk = p.person_id_pk;



select m.id, m.gc, m.Manager as MAN, 
sp.SP as SP, 
c.C as C, 
f.FB, 
s.SB,
ss.SS,
t.TB,
l.LF,
cf.CF,
r.RF
from v_manager m, v_sp sp, v_catcher c , v_firstbase f,
v_secondbase s,  v_thirdbase t, v_shortstop ss, 
v_leftfield l, v_centerfield cf, v_rightfield r
where m.id = sp.id
and c.id = sp.id
and f.id = c.id
and s.id = f.id
and t.id = s.id
and ss.id = t.id
and l.id = ss.id
and cf.id = l.id
and r.id = cf.id
order by m.gc;





-- Error Code: 1054. Unknown column 'firstbase.pos_firstbase_id_fk' in 'where clause'


select * from vhscore;

CREATE VIEW gamelog.vhscore AS 
select g.gamelog_id_pk gameLogId, t.tm_gamelog_id_pk tmGameLogId, 'V' home,  g.v_score score
from gamelog g, tm_gamelog t
where g.gamelog_id_pk = t.gamelog_id_fk
and t.home_away_code = 'V'
UNION ALL
select g.gamelog_id_pk, t.tm_gamelog_id_pk, 'H' home, g.h_score score
from gamelog g, tm_gamelog t
where g.gamelog_id_pk = t.gamelog_id_fk
and t.home_away_code = 'H'
order by gameLogId, home desc;
