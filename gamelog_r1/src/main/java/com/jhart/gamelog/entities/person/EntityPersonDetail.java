package com.jhart.gamelog.entities.person;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.location.EntityLocation;
//import com.jhart.gamelog.entities.team.EntityLeague;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Deprecated
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="person_detail")
public class EntityPersonDetail implements IEntity, Serializable{
	private static final long serialVersionUID = 5378337619699507231L;
	
	@Id
	@Column(name="PERSON_DETAIL_ID_PK")
	@GeneratedValue(generator="gen")
	@GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="person"))
	private Integer personDetailId;
	
	@OneToOne(mappedBy="personDetail", cascade = CascadeType.ALL)
	private EntityBioStat bioStat;

	
	@OneToOne
    @PrimaryKeyJoinColumn
    private EntityPerson person;
	
	
	@OneToOne(mappedBy="personDetail", cascade=CascadeType.ALL)
	private EntityPersonDate entityPersonDate;

	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="LOCATION_ID_FK")
    private EntityLocation birthplace;

	
	
}