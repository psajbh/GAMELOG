CREATE VIEW gamelog.v_catcher AS 
select catchers.tm_gamelog_id_fk as id, catchers.game_code as gc, p.full_name as 'C'
from lineitem_pos catchers, person p
where catchers.pos_catcher_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_firstbase AS
select firstbase.tm_gamelog_id_fk as id, firstbase.game_code as gc, p.full_name as 'FB'
from lineitem_pos firstbase, person p
where firstbase.pos_firstbase_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_secondbase AS
select secondbase.tm_gamelog_id_fk as id, secondbase.game_code as gc, p.full_name as 'SB'
from lineitem_pos secondbase, person p
where secondbase.pos_secondbase_person_id_fk = p.person_id_pk;

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

CREATE VIEW gamelog.v_rightfield AS
select right_field.tm_gamelog_id_fk as id, right_field.game_code as gc, p.full_name as 'RF'
from lineitem_pos right_field, person p
where right_field.pos_right_field_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_manager AS 
select manager.tm_gamelog_id_fk as id, manager.game_code as gc, p.full_name as Manager
from lineitem_pos manager, person p
where manager.pos_manager_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_sp AS
select pitchers.tm_gamelog_id_fk id, pitchers.game_code gc, p.full_name as 'SP'
from lineitem_pos pitchers, person p
where pitchers.pos_pitcher_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_dh AS
select dh.tm_gamelog_id_fk id, dh.game_code gc, p.full_name as 'DH'
from lineitem_pos dh, person p
where dh.pos_designated_hitter_person_id_fk = p.person_id_pk;

-- http://www.mysqltutorial.org/mysql-join/

CREATE VIEW v_game_start_pos AS 
select tgl.gameLog_id_fk as gameId, 
m.id as teamGameId, 
m.gc, tm.team_code as tmcode, concat(tm.name, ' ', tm.alias) as teamName, m.Manager as M, 
sp.SP as SP, c.C as C, f.FB, s.SB, ss.SS, t.TB,
l.LF, cf.CF, r.RF, dh.DH
from v_manager m, v_sp sp, v_catcher c , v_firstbase f,
v_secondbase s,  v_thirdbase t, v_shortstop ss, 
v_leftfield l, v_centerfield cf, v_rightfield r, team tm,
tm_gamelog tgl
LEFT JOIN gamelog.v_dh as dh  ON tgl.tm_gamelog_id_pk = dh.id
where 
tgl.tm_gamelog_id_pk = m.id
and tgl.team_id_fk = tm.team_id_pk
and m.id = sp.id
and c.id = sp.id
and f.id = c.id
and s.id = f.id
and t.id = s.id
and ss.id = t.id
and l.id = ss.id
and cf.id = l.id
and r.id = cf.id
order by gameId, m.id desc, m.gc;

CREATE VIEW gamelog.v_bo1 AS
select lo1.tm_gamelog_id_fk id, lo1.game_code gc, p.full_name o1 
from lineitem_order lo1 , person p
where lo1.order_1_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo2 AS
select lo2.tm_gamelog_id_fk id, lo2.game_code gc, p.full_name o2 
from lineitem_order lo2 , person p
where lo2.order_2_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo3 AS
select lo3.tm_gamelog_id_fk id, lo3.game_code gc, p.full_name o3 
from lineitem_order lo3 , person p
where lo3.order_3_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo4 AS
select lo4.tm_gamelog_id_fk id, lo4.game_code gc, p.full_name o4 
from lineitem_order lo4 , person p
where lo4.order_4_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo5 AS
select lo5.tm_gamelog_id_fk id, lo5.game_code gc, p.full_name o5 
from lineitem_order lo5 , person p
where lo5.order_5_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo6 AS
select lo6.tm_gamelog_id_fk id, lo6.game_code gc, p.full_name o6 
from lineitem_order lo6 , person p
where lo6.order_6_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo7 AS
select lo7.tm_gamelog_id_fk id, lo7.game_code gc, p.full_name o7 
from lineitem_order lo7 , person p
where lo7.order_7_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo8 AS
select lo8.tm_gamelog_id_fk id, lo8.game_code gc, p.full_name o8 
from lineitem_order lo8 , person p
where lo8.order_8_person_id_fk = p.person_id_pk;

CREATE VIEW gamelog.v_bo9 AS
select lo9.tm_gamelog_id_fk id, lo9.game_code gc, p.full_name o9 
from lineitem_order lo9 , person p
where lo9.order_9_person_id_fk = p.person_id_pk;
 
CREATE VIEW v_game_start_order AS 
select tgl.gameLog_id_fk as gameId, 
bo1.id as teamGameId, 
bo1.gc, tm.team_code as tmcode, concat(tm.name, ' ', tm.alias) as teamName,
bo1.o1, bo2.o2, bo3.o3, bo4.o4, bo5.o5, bo6.o6, bo7.o7,
bo8.o8, bo9.o9 
from tm_gamelog tgl, team tm, 
v_bo1 bo1, v_bo2 bo2, v_bo3 bo3, v_bo4 bo4, v_bo5 bo5, v_bo6 bo6,
v_bo7 bo7, v_bo8 bo8, v_bo9 bo9 
where tgl.tm_gamelog_id_pk = bo1.id and tgl.team_id_fk = tm.team_id_pk
and bo1.id = bo2.id and bo2.id = bo3.id and bo3.id = bo4.id
and bo4.id = bo5.id and bo5.id = bo6.id and bo6.id = bo7.id
and bo7.id = bo8.id and bo8.id = bo9.id;

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

-- umpire views 
CREATE VIEW gamelog.v_uhp AS
select uhp.gamelog_id_fk, p.full_name, 'HPU', g.gamelog_code
from ump_gamelog uhp, person p, gamelog g
where uhp.hp_person_id_fk = p.person_id_pk
and uhp.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW gamelog.v_u1b AS
select u1b.gamelog_id_fk, p.full_name, '1BU', g.gamelog_code
from ump_gamelog u1b, person p, gamelog g
where u1b.fb_person_id_fk = p.person_id_pk
and u1b.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW gamelog.v_u2b AS
select u2b.gamelog_id_fk, p.full_name, '2BU', g.gamelog_code
from ump_gamelog u2b, person p, gamelog g
where u2b.sb_person_id_fk = p.person_id_pk
and u2b.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW gamelog.v_u3b AS
select u3b.gamelog_id_fk, p.full_name, '3BU', g.gamelog_code
from ump_gamelog u3b, person p, gamelog g
where u3b.tb_person_id_fk = p.person_id_pk
and u3b.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW gamelog.v_ulf AS
select ulf.gamelog_id_fk, p.full_name, 'LFU', g.gamelog_code
from ump_gamelog ulf, person p, gamelog g
where ulf.lf_person_id_fk = p.person_id_pk
and ulf.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW gamelog.v_urf AS
select urf.gamelog_id_fk, p.full_name, 'RFU', g.gamelog_code
from ump_gamelog urf, person p, gamelog g
where urf.rf_person_id_fk = p.person_id_pk
and urf.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW gamelog.v_umpire AS
select hp.gamelog_id_fk, hp.full_name as HP, fb.full_name as FB, 
sb.full_name as SB,  tb.full_name as TB,
lf.full_name as LF, rf.full_name as RF
from v_uhp hp
JOIN v_u1b fb ON hp.gamelog_id_fk = fb.gamelog_id_fk
JOIN v_u2b sb ON hp.gamelog_id_fk = sb.gamelog_id_fk
JOIN v_u3b tb ON hp.gamelog_id_fk = tb.gamelog_id_fk
LEFT JOIN v_ulf lf ON hp.gamelog_id_fk = lf.gamelog_id_fk
LEFT JOIN v_urf rf ON hp.gamelog_id_fk = rf.gamelog_id_fk;

CREATE VIEW gamelog.v_umpire_one_column AS
select hp.gamelog_id_fk, CONCAT('hp: ', hp.full_name, ' 1b: ', fb.full_name, 
' 2b: ', sb.full_name,  ' 3b: ', tb.full_name,
 ' lf: ', IFNULL(lf.full_name, '-'), ' rf: ', IFNULL(rf.full_name, '-')) as umps
from v_uhp hp
JOIN v_u1b fb ON hp.gamelog_id_fk = fb.gamelog_id_fk
JOIN v_u2b sb ON hp.gamelog_id_fk = sb.gamelog_id_fk
JOIN v_u3b tb ON hp.gamelog_id_fk = tb.gamelog_id_fk
LEFT JOIN v_ulf lf ON hp.gamelog_id_fk = lf.gamelog_id_fk
LEFT JOIN v_urf rf ON hp.gamelog_id_fk = rf.gamelog_id_fk;

-- award views

CREATE VIEW v_award_WP AS
select gla.gamelog_id_fk, p.full_name as WP, g.gamelog_code
from gamelog_awards gla
LEFT JOIN person p ON gla.wp_person_id_fk = p.person_id_pk
JOIN gamelog g ON gla.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW v_award_LP AS
select gla.gamelog_id_fk, p.full_name as LP, g.gamelog_code
from gamelog_awards gla 
LEFT JOIN person p ON gla.lp_person_id_fk = p.person_id_pk
JOIN gamelog g ON gla.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW v_award_S AS
select gla.gamelog_id_fk, p.full_name as SP, g.gamelog_code
from gamelog_awards gla 
LEFT JOIN person p ON gla.sp_person_id_fk = p.person_id_pk
JOIN gamelog g ON gla.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW v_award_GWRBI AS
select gla.gamelog_id_fk, p.full_name as GWRBI, g.gamelog_code
from gamelog_awards gla 
LEFT JOIN person p ON gla.gwrbi_person_id_fk = p.person_id_pk
JOIN gamelog g ON gla.gamelog_id_fk = g.gamelog_id_pk;

CREATE VIEW v_awards AS
select wp.gamelog_id_fk, wp.wp, lp.lp, s.sp, gwrbi.GWRBI
from v_award_wp wp
JOIN v_award_lp lp ON wp.gamelog_id_fk = lp.gamelog_id_fk
LEFT JOIN v_award_s s ON wp.gamelog_id_fk = s.gamelog_id_fk
LEFT JOIN v_award_gwrbi gwrbi ON wp.gamelog_id_fk = gwrbi.gamelog_id_fk;

-- gameline views

CREATE VIEW gamelog.v_gameline_bat AS
select tg.tm_gamelog_id_pk, /*g.gamelog_id_pk,*/ g.gamelog_code, -- tgb.tm_gameline_bat_id_pk,
tgb.ab, tgb.h, tgb.d, tgb.t, tgb.hr, tgb.rbi, tgb.sh, tgb.sf, tgb.hbp, tgb.bb, tgb.ibb,
tgb.so, tgb.sb, tgb.cs, tgb.gidp, tgb.awci, tgb.lob
from tm_gamelog tg
JOIN gamelog g ON g.gamelog_id_pk = tg.gamelog_id_fk
JOIN tm_gameline_bat tgb ON tgb.tm_gamelog_id_fk = tg.tm_gamelog_id_pk;

CREATE VIEW gamelog.v_gameline_defense AS
select tg.tm_gamelog_id_pk, /*g.gamelog_id_pk,*/ g.gamelog_code, 
tgd.putouts, tgd.assists, tgd.errors, tgd.pb, tgd.dp, tgd.tp
from tm_gamelog tg
JOIN gamelog g ON g.gamelog_id_pk = tg.gamelog_id_fk
JOIN tm_gameline_defense tgd ON tgd.tm_gamelog_id_fk = tg.tm_gamelog_id_pk;

CREATE VIEW gamelog.v_gameline_pitch AS
select tg.tm_gamelog_id_pk, /*g.gamelog_id_pk,*/ g.gamelog_code, 
tgp.pitchers_used, tgp.individual_er, tgp.team_er, tgp.wp, tgp.balks
from tm_gamelog tg
JOIN gamelog g ON g.gamelog_id_pk = tg.gamelog_id_fk
JOIN tm_gameline_pitch tgp ON tgp.tm_gamelog_id_fk = tg.tm_gamelog_id_pk;

CREATE VIEW v_gamelog AS
select g.gamelog_id_pk, g.gamelog_code, s.season_code as season, p.park_code, p.name,
CONCAT(loc.city,', ',loc.state_code, ' at (', IFNULL(p.name, '-'),')') as location,
g.date_of_game as dateOfGame, w.weekday_code as day, IF(g.day_game > 0,'Night','Day') as DN,
CONCAT(g.date_of_game, ' (', w.weekday_code, ':', IF(g.day_game > 0,'Night','Day'),')' ) as daytime,
g.attendance, g.total_outs outs, 
CONCAT(tv.name, ' ', tv.alias) V_TM,
CONCAT(th.name, ' ', th.alias) H_TM, 
g.v_tm_game_score as V_SCORE_LINE, 
g.v_score V_SCORE, 
g.h_tm_game_score as H_SCORE_LINE,
g.h_score H_SCORE,
IF(g.v_score > g.h_score, 'V', 'H') winner, 
IF(g.v_score > g.h_score, 'H', 'V') loser, 
-- umpire stuff
vu.HP as 'HP_UMP', vu.FB as '1B_UMP', vu.SB as '2B_UMP', vu.TB as '3B_UMP',
vu.LF as 'LF_UMP', vu.RF as 'RF_UMP',
va.wp AS WP, va.lp AS LP, va.sp AS S, va.GWRBI AS GWRBI,
IF(g.v_score > g.h_score, tv.team_code, th.team_code) tm_winner,
IF(g.v_score < g.h_score, tv.team_code, th.team_code) tm_loser
from gamelog g
JOIN tm_gamelog ht ON g.tm_gamelog_home_fk = ht.tm_gamelog_id_pk
	JOIN team th ON ht.team_id_fk = th.team_id_pk
		-- JOIN franchise fth ON th.franchise_id_fk = fth.franchise_id_pk
JOIN tm_gamelog vt ON g.tm_gamelog_visitor_fk = vt.tm_gamelog_id_pk
	JOIN team tv ON vt.team_id_fk = tv.team_id_pk
		-- JOIN franchise ftv ON tv.franchise_id_fk = ftv.franchise_id_pk
JOIN season s ON g.season_id_fk = s.season_id_pk
JOIN park p ON g.park_id_fk = p.park_id_pk
	JOIN location loc ON p.location_id_fk = loc.location_id_pk
JOIN weekday w ON g.day_of_week_fk = UPPER(w.weekday_code)
JOIN v_umpire vu ON g.gamelog_id_pk = vu.gamelog_id_fk
JOIN v_awards va ON g.gamelog_id_pk = va.gamelog_id_fk;

-- select * from v_gamelog;

CREATE VIEW v_visit_team AS
select g.gamelog_id_pk, g.gamelog_code, g.v_tm, g.h_tm, 
vso.tmCode as v_team, vso.o1, vso.o2, vso.o3, vso.o4, vso.o5, vso.o6, vso.o7, vso.o8, vso.o9 ,
vpos.M, vpos.SP, vpos.C, vpos.FB as '1B', vpos.SB as '2B', vpos.SS, vpos.TB as '3B', vpos.LF, vpos.CF, vpos.RF, vpos.DH,
vgb.ab, vgb.h, vgb.d, vgb.t, vgb.hr, vgb.rbi, vgb.sh, vgb.sf, vgb.hbp, vgb.bb, vgb.ibb, vgb.so, vgb.sb, vgb.cs, vgb.gidp, vgb.awci, vgb.lob,
vgd.putouts, vgd.assists, vgd.errors, vgd.pb, vgd.dp, vgd.tp,
vgp.pitchers_used as p_used, vgp.individual_er as i_er, vgp.team_er, vgp.wp, vgp.balks
from v_gamelog g
JOIN v_game_start_order vso ON g.gamelog_id_pk = vso.gameId and g.v_tm = vso.teamGameId
JOIN v_game_start_pos vpos ON g.gamelog_id_pk = vpos.gameId and g.v_tm = vpos.teamGameId
JOIN v_gameline_bat vgb ON g.v_tm = vgb.tm_gamelog_id_pk
JOIN v_gameline_defense vgd ON g.v_tm = vgd.tm_gamelog_id_pk
JOIN v_gameline_pitch vgp ON g.v_tm = vgp.tm_gamelog_id_pk;

CREATE VIEW v_home_team AS
select g.gamelog_id_pk, g.gamelog_code, g.v_tm, g.h_tm, 
hso.tmCode as v_team, hso.o1, hso.o2, hso.o3, hso.o4, hso.o5, hso.o6, hso.o7, hso.o8, hso.o9 ,
hpos.M, hpos.SP, hpos.C, hpos.FB as '1B', hpos.SB as '2B', hpos.SS, hpos.TB as '3B', hpos.LF, hpos.CF, hpos.RF, hpos.DH,
hgb.ab, hgb.h, hgb.d, hgb.t, hgb.hr, hgb.rbi, hgb.sh, hgb.sf, hgb.hbp, hgb.bb, hgb.ibb, hgb.so, hgb.sb, hgb.cs, hgb.gidp, hgb.awci, hgb.lob,
hgd.putouts, hgd.assists, hgd.errors, hgd.pb, hgd.dp, hgd.tp,
hgp.pitchers_used as p_used, hgp.individual_er as i_er, hgp.team_er, hgp.wp, hgp.balks
from v_gamelog g
JOIN v_game_start_order hso ON g.gamelog_id_pk = hso.gameId and g.h_tm = hso.teamGameId
JOIN v_game_start_pos hpos ON g.gamelog_id_pk = hpos.gameId and g.h_tm = hpos.teamGameId
JOIN v_gameline_bat hgb ON g.h_tm = hgb.tm_gamelog_id_pk
JOIN v_gameline_defense hgd ON g.h_tm = hgd.tm_gamelog_id_pk
JOIN v_gameline_pitch hgp ON g.h_tm = hgp.tm_gamelog_id_pk;

CREATE VIEW v_season AS
 select l.league_code, l.name as leagueName, d.division_code, d.name as divisionName,
 t.team_code, t.name, t.alias, s.season_code, f.franchise_code, 
 t.team_id_pk, s.season_id_pk, l.league_id_pk
 from league as l 
 LEFT JOIN division as d on l.division_id_fk = d.division_id_pk
 LEFT JOIN team as t on t.league_id_fk = l.league_id_pk
 LEFT JOIN season as s on s.season_id_pk = t.season_id_fk
 LEFT JOIN franchise as f on t.franchise_id_fk = f.franchise_id_pk
 order by f.franchise_code, s.season_code;



