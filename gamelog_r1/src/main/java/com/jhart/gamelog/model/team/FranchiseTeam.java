package com.jhart.gamelog.model.team;

import java.time.LocalDate;

import com.jhart.gamelog.types.DivisionType;
import com.jhart.gamelog.types.LeagueType;

import lombok.Data;

@Data
public class FranchiseTeam {

	public FranchiseTeam() {}
	
	private Franchise parent;
	private String name;
	private LeagueType league;
	private DivisionType division;  //can be null
	private String location;
	private String nickname;
	private String alternativeNames;
	private LocalDate startDate;
	private LocalDate endDate;
	private String city;
	private String state;
}
