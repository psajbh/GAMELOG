package com.jhart.gamelog.utils;

import java.util.HashMap;
import java.util.Map;

import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.entities.person.EntityPerson;

public class GamelogDuplicateManager {
	
	private Map<String, EntityPark> parkMap = new HashMap<>();
	private Map<String, EntityPerson> personMap = new HashMap<>();
	
	public EntityPark getDuplicatePark(String code) {
		EntityPark ep = null;
		ep = parkMap.get(code);
		return ep;
	}
	
	public void setEntityPark(EntityPark entityPark) {
		parkMap.put(entityPark.getCode(), entityPark);
	}
	
	public EntityPerson getDuplicatePerson(String code) {
		EntityPerson ep = null;
		ep = personMap.get(code);
		return ep;
	}
	
	public void setEntityPerson(EntityPerson entityPerson) {
		personMap.put(entityPerson.getCode(), entityPerson);
	}
	
	
	

}
