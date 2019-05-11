package com.jhart.gamelog.entities.location;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Location is not exact like an address. It only refers to a general area 
// defined by country, state and city. It will be used in unidirectionally
// form those objects that have location interest like Park, Team and Person.
// unidirectional since many different entity types will have a many-to-one relationship
// with location. such as park, team, player.

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="location")
@NamedQueries({
	@NamedQuery(name="get_location_by_state_city", 
			query="from EntityLocation loc where loc.stateCode=:stateCode and loc.city=:city ")
})
public class EntityLocation implements IEntity, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="LOCATION_ID_PK", updatable = false, nullable = false)
	private int locationId;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="COUNTRY_CODE")
	private String countryCode;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="STATE_CODE")
	private String stateCode;
	
	@Column(name="CITY")
	private String city;
}
