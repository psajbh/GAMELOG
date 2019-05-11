package com.jhart.gamelog.transform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.api.transform.Transformer;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.model.parks.Park;

public class ParkTransformer implements Transformer{

	public ParkTransformer() {}

	@Override
	public IEntity transform(IModel park) {
	    Park modelPark = (Park) park;
	    EntityPark entityPark = new EntityPark();
	    entityPark.setCode(modelPark.getParkId());
	    entityPark.setName(modelPark.getName());
	    
	    String aka = modelPark.getAka();
	    if (null != aka && aka.equals("-1")) {
	    	entityPark.setAlias(null);
	    	//entityPark.setAlias(modelPark.getAka().equals("-1") ? null : modelPark.getAka());
	    }
	    if (null != aka) {
	    	entityPark.setAlias(aka);
	    }
	    //entityPark.setAlias(modelPark.getAka().equals("-1") ? null : modelPark.getAka());
	    
	    entityPark.setStart(LocalDate.parse( modelPark.getStart()));
	    
	    if (null != modelPark.getEnd()) {
	    	entityPark.setEnd(LocalDate.parse( modelPark.getEnd()));
	    }
	    
	    EntityLocation location = new EntityLocation();
	    
	    if (modelPark.getState().length() > 6) {
	    	modelPark.setState(modelPark.getState().substring(0,6).toUpperCase());
	    }
	    	
	    location.setStateCode(modelPark.getState());
	    
	    
	    location.setCity(modelPark.getCity());
	    entityPark.setLocation(location);
	    
	    return entityPark;
	}
	
//	//09/11/1880
//	private String transformDateType1ToLocalDateFormat(String strDate) {
//		String[] calendarElements = strDate.split("/",2);
//		
//		return null;
//	}
	
	@Override
	public IModel transform(IEntity park) {
	    EntityPark entityPark = (EntityPark) park;
	    Park modelPark = new Park();
	    
	    modelPark.setId(entityPark.getParkId());
	    modelPark.setParkId(entityPark.getCode());
	    modelPark.setName(entityPark.getName());
	    modelPark.setAka(entityPark.getAlias() == null ? "-1" : entityPark.getAlias());
	    modelPark.setStart(entityPark.getStart().toString());
	    modelPark.setEnd(entityPark.getEnd().toString());
	    
	    modelPark.setCity(entityPark.getLocation().getCity());
	    modelPark.setState(entityPark.getLocation().getStateCode());
	    
	    return modelPark;
	}

}
