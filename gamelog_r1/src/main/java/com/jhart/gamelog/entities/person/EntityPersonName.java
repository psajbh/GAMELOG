package com.jhart.gamelog.entities.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Deprecated
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="person_name")
public class EntityPersonName implements IEntity, Serializable{
	private static final long serialVersionUID = -3500033727741466772L;

	@Id
	@Column(name="PERSON_NAME_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer personNameId;
	
//	@OneToOne
//    @PrimaryKeyJoinColumn
//	private EntityPersonDetail personDetail;
//	
//	private String fullName;
//	private String lastName;
//	private String middleName;
//	private String firstName;
//	private String nickname;
//	private String oka;
	

}
