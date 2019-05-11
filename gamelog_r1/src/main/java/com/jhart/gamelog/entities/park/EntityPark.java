package com.jhart.gamelog.entities.park;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.location.EntityLocation;

//import lombok.Data;  don't use this with an Entity object
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="park")
@NamedQueries({
    @NamedQuery(name="get_park_from_code", 
            query="from EntityPark p where p.code=:code")
})
public class EntityPark implements IEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PARK_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int parkId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LOCATION_ID_FK")/*, referencedColumnName="LOCATION_ID_PK")*/
	private EntityLocation location;
	
	@Column(name="PARK_CODE", unique=true, nullable=false)
	private String code;
	
	@Column(name="NAME", nullable=false)
	private String name;

	@Column(name="ALIAS")
	private String alias;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="START")
	private java.time.LocalDate start;

	//@Temporal(TemporalType.DATE)
	@Column(name="END")
	private java.time.LocalDate end;

}
