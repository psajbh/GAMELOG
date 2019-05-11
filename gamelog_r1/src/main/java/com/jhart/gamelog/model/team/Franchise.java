package com.jhart.gamelog.model.team;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Franchise {

	public Franchise() {}
	String franchiseName;
	List<FranchiseTeam> teams = new ArrayList<>();
}
