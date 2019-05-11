package com.jhart.gamelog.entities.person;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="person_date")
public class EntityPersonDate implements IEntity, Serializable{
	private static final long serialVersionUID = 4895274692101164893L;

	@Id
	@Column(name="PERSON_DATE_ID_PK")
	@GeneratedValue(generator="gen")
	@GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="personDetail"))
	private Integer personDateId;
	
	@OneToOne
    @PrimaryKeyJoinColumn
    private EntityPersonDetail personDetail;
	
	@Column(name="DATE_OF_BIRTH")
	private LocalDate dob;
	
	@Column(name="DATE_OF_DEATH")
	private LocalDate dod;
	
	@Column(name="DATE_OF_DEBUT_COACH")
	private LocalDate coachDebut;
	
	@Column(name="DATE_OF_DEBUT_MANAGER")
	private LocalDate managerDebut;
	
	@Column(name="DATE_OF_DEBUT_PLAYER")
	private LocalDate playerDebut;
	
	@Column(name="DATE_OF_DEBUT_UMPIRE")
	private LocalDate umpireDebut;


}
