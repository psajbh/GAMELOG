package com.jhart.gamelog.model.parks;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.types.LeagueType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString 
public class Park implements IModel{
	int id;
	String parkId;
	String name;
	String aka;
	String city;
	String state;
	String start;
	String end;
	LeagueType league;
//	Location location;
	
	String notes;

	public Park() {
	}
}
