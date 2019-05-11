package com.jhart.gamelog.entities.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="throw")
public class EntityThrow implements IEntity, Serializable{
	private static final long serialVersionUID = 4974258862384721819L;

	@Id
	@Column(name="THROW_ID_PK")
	private Integer throwId;
	
	@Column(name="THROW_CODE")
	private String throwCode;
	
	@Column(name="THROW_NAME")
	private String throwName;
}
