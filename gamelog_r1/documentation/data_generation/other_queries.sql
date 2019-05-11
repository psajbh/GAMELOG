-- sample queries
select GWRBI as name, count(*) as gwrbis from v_gamelog vg
where  vg.season = 2018  and GWRBI IS NOT NULL
group by GWRBI order by gwrbis desc;

select * from (
select gamelog_code, daytime, location, tm_winner, tm_loser,
	HP_UMP, WP, LP, S, GWRBI
	from v_gamelog where tm_winner = 'WAS' 
UNION ALL
select gamelog_code, daytime, location, tm_winner, tm_loser,
	HP_UMP, WP, LP, S, GWRBI
    from v_gamelog where tm_loser = 'WAS' 
) combined where HP_UMP = 'Joe West' order by gamelog_code;

-- select gamelog_code, daytime, location, null, 

select daytime, name, tm_winner, tm_loser, v_score, h_score, attendance, v_tm, h_tm, hp_ump, wp, lp, s, gwrbi from v_gamelog 
where tm_winner = 'WAS'
and season = 2018 
order by gamelog_code;





