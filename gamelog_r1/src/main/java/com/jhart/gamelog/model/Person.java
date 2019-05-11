package com.jhart.gamelog.model;

//import java.time.LocalDate;
import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.types.BattingType;
import com.jhart.gamelog.types.ThrowingType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString 
@Data
public class Person implements IModel{
	protected long personId;
	protected String id;   // ID i.e. 
	protected String name;
	protected String firstName; // First
	protected String lastName; //Last
	protected String middleName;
	protected String dateOfBirth;
	protected String countryOfBirth;
	protected String stateOfBirth;
	protected String cityOfBirth;
	protected String dateOfDeath;
	protected String height;
	protected String weight;
	protected String  bats;
	protected String throwingArm;
	protected String playerDebut;
	protected String managerDebut;
	protected String coachDebut;
	protected String umpireDebut;

	public Person() {
	}
	
	public Person(String name, String id) {
		this.name = name;
		this.id = id;
	}
}
