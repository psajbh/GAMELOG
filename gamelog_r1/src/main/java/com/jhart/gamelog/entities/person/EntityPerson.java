package com.jhart.gamelog.entities.person;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.types.BattingType;
import com.jhart.gamelog.types.ThrowingType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * http://websystique.com/hibernate/hibernate-one-to-one-bidirectional-with-shared-primary-key-annotation-example/
 *
 */

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="person")
@NamedQueries({
	@NamedQuery(name=EntityPerson.NAME_GET_PERSON_FROM_CODE, 
			query=EntityPerson.QUERY_GET_PERSON_FROM_CODE)
})
public class EntityPerson implements IEntity, Serializable{
	private static final long serialVersionUID = 213231634963257650L;
	public static final String NAME_GET_PERSON_FROM_CODE = "get_person_from_code";
	public static final String QUERY_GET_PERSON_FROM_CODE = "from EntityPerson ep where ep.code=:code";
	

	@Id
	@Column(name="PERSON_ID_PK")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer personId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="LOCATION_ID_FK")
    private EntityLocation birthplace;
	
	@Column(name="PERSON_CODE")
	private String code;
	
	@Column(name="FULL_NAME")
	private String fullName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="MIDDLE_NAME")	
	private String middleName;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="NICK_NAME")
	private String nickname;
	
	@Column(name="OKA")
	private String oka;
	
	@Column(name="HEIGHT")
	private int height;
	
	@Column(name="WEIGHT")
	private int weight;
	
	@Enumerated(EnumType.STRING)
	@Column(name="BATS")
	private BattingType bats;
	
	@Enumerated(EnumType.STRING)
	@Column(name="THROWS")
	private ThrowingType thros;
	
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
