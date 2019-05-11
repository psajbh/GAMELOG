package com.jhart.gamelog.entities.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="measure")
public class EntityMeasure implements IEntity, Serializable{
	private static final long serialVersionUID = 7365129466548292181L;

	@Id
	@Column(name="MEASURE_ID_PK")
	private Integer measureId;
	
	@Column(name="BIOSTAT_ID_FK")
	private EntityBioStat entityBioStat;

	@Column(name="HEIGHT")
	private int height;
	
	@Column(name="WEIGHT")
	private int weight;
}
