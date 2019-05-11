package com.jhart.gamelog.transform;

import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.api.transform.Transformer;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.person.EntityPerson;
//import com.jhart.gamelog.entities.person.EntityThrow;
import com.jhart.gamelog.model.Person;
//import com.jhart.gamelog.service.PersonService;
import com.jhart.gamelog.types.BattingType;
import com.jhart.gamelog.types.ThrowingType;
import com.jhart.gamelog.utils.Utils;

public class PersonTransformer implements Transformer{
	private static final Logger LOG = LoggerFactory.getLogger(PersonTransformer.class);
	private static final String MODAYEAR_PATTERN =  "MM/dd/yyyy";
	//private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(PersonTransformer.DATE_PATTERN);
	//Person(personId=0, id=aardd001, name=David Aardsma, firstName=David, lastName=Aardsma, middleName=null, 
	//dateOfBirth=null, countryOfBirth=null, stateOfBirth=null, cityOfBirth=null, dateOfDeath=null, 
	//height=0, weight=0, bats=null, throwingArm=null, playerDebut=04/06/2004, managerDebut=null, coachDebut=null, umpireDebut=null)
	  public IEntity transform(IModel person) {
		  Person modelPerson = (Person)person;
		  if (StringUtils.isBlank(modelPerson.getId())) {
			  return null;
		  }
		  
		  EntityPerson entityPerson = new EntityPerson();
		  entityPerson.setCode(modelPerson.getId());
		  entityPerson.setFullName(modelPerson.getName());
		  entityPerson.setLastName(modelPerson.getLastName());
		  entityPerson.setFirstName(modelPerson.getFirstName());
		  entityPerson.setMiddleName(modelPerson.getMiddleName());
		  
		  if (StringUtils.isBlank(modelPerson.getStateOfBirth()) ||
				  StringUtils.isBlank(modelPerson.getStateOfBirth())) {
			  LOG.trace("PersonTransformer:transform - no location data to persist");
		  }
		  else {
			  LOG.trace("PersonTransformer:transform - location data is available persist");
			  EntityLocation entityLocation = new EntityLocation();
			  entityLocation.setStateCode(modelPerson.getStateOfBirth());
			  entityLocation.setCity(modelPerson.getCityOfBirth());
			  entityPerson.setBirthplace(entityLocation);
		  }
	 	  
		  LOG.trace("PersonTransformer:transform - processing battingType");
		  BattingType battingType = getBattingType(modelPerson);
		  if (null != battingType) {
			  entityPerson.setBats(battingType);
		  }
		  LOG.trace("PersonTransformer:transform - processing throwingType");
		  ThrowingType throwingType = getThrowingArm(modelPerson);
		  if (null != throwingType) {
			  entityPerson.setThros(throwingType);
		  }
		  
		  LOG.trace("PersonTransformer:transform - processing dates");
		  setProcessDates(entityPerson, modelPerson);
		  LOG.trace("PersonTransformer:transform - finished processing dates");
		  
		  return entityPerson;
	  }
	  
	  public IModel transform (IEntity entityPerson) {
		  Person person = new Person();
		  return person;
	  }
	  
	  private BattingType getBattingType(Person modelPerson){
		  String value = modelPerson.getBats();
		  
		  if (null == value) {
			  return null; 
		  }
		  
		  switch(value) {
		  	case "R":
		  		return BattingType.R;
		  		
		  	case "L": 
		  		return BattingType.L;
		  	
		  	case "B":
		  		return BattingType.S;

		  	default:
		  		return null;
		  }
	  }
	  
	  private ThrowingType getThrowingArm(Person modelPerson){
		  String value = modelPerson.getThrowingArm();
		  
		  if (null == value) {
			  return null; 
		  }
		  
		  switch (value) {
		  
		  	case "L":
		  		return ThrowingType.L;
		  		
		  	case "R":	
		  		return ThrowingType.R;
		  		
		  	default:
		  		return null;
		  }
	  }
	  
	  private LocalDate getLocalDate(String strDate) {
		  try {
			  return Utils.getFormattedLocalDate(strDate,PersonTransformer.MODAYEAR_PATTERN);
		  }
		  catch(DateTimeParseException dte) {
			  LOG.error("failed to parse strDate: " + strDate + " msg: " + dte.getMessage());
			  return null;
		  }
	  }
	  
	  
	  private void setProcessDates(EntityPerson entityPerson, Person modelPerson) {
		  LOG.debug("PersonTransformser : setProcessDates -start");
		  try {
			  String strDate = modelPerson.getDateOfBirth();
			  if (null != strDate) {
				  entityPerson.setDob(getLocalDate(strDate));
			  }
			  
			  strDate = modelPerson.getDateOfDeath();
			  if (null != strDate) {
				  entityPerson.setDod(getLocalDate(strDate));  
			  }

			  strDate = modelPerson.getPlayerDebut();
			  if (null != strDate) {
				  entityPerson.setPlayerDebut(getLocalDate(strDate));  
			  }
			  
			  strDate = modelPerson.getManagerDebut();
			  if (null != strDate) {
				  entityPerson.setManagerDebut(getLocalDate(strDate));  
			  }

			  strDate = modelPerson.getCoachDebut();
			  if (null != strDate) {
				  entityPerson.setCoachDebut(getLocalDate(strDate));  
			  }
			  
			  strDate = modelPerson.getUmpireDebut();
			  if (null != strDate) {
				  entityPerson.setUmpireDebut(getLocalDate(strDate));  
			  }
			  
//			  //entityPerson.setDod(Utils.getFormattedLocalDate(modelPerson.getDateOfDeath(), PersonTransformer.MODAYEAR_PATTERN));
//			  entityPerson.setManagerDebut(Utils.getFormattedLocalDate(modelPerson.getManagerDebut(), PersonTransformer.MODAYEAR_PATTERN));  
//			  entityPerson.setPlayerDebut(Utils.getFormattedLocalDate(modelPerson.getPlayerDebut(), PersonTransformer.MODAYEAR_PATTERN));
//			  entityPerson.setCoachDebut(Utils.getFormattedLocalDate(modelPerson.getCoachDebut(), PersonTransformer.MODAYEAR_PATTERN));
//			  entityPerson.setUmpireDebut(Utils.getFormattedLocalDate(modelPerson.getUmpireDebut(), PersonTransformer.MODAYEAR_PATTERN));
		  }
		  catch(Exception e) {
			  LOG.error("PersonTransformer : setProcessDates - failure  msg: " + e.getMessage(),e);
		  }
		  LOG.debug("PersonTransformser : setProcessDates -finish");
	  }
}
