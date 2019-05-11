package com.jhart.gamelog.entities.person;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.types.BattingType;
import com.jhart.gamelog.types.ThrowingType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Deprecated
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="biostat")
public class EntityBioStat implements IEntity, Serializable{
	private static final long serialVersionUID = -1936846479901908248L;

	@Id
	@Column(name="BIOSTAT_ID_PK")
	@GeneratedValue(generator="gen")
	@GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="personDetail"))
	private Integer bioStatId;
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private EntityPersonDetail personDetail;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	private BattingType battingType;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	private ThrowingType throwingType;
	
	
	

}
