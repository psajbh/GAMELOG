package com.jhart.gamelog.entities.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
//import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.jhart.gamelog.api.entities.IEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Immutable
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="bat")
@NamedQueries({
	@NamedQuery(name="get_bat_by_code", 
			query="from EntityBat bat where bat.batCode=:batCode ")
})
public class EntityBat implements IEntity, Serializable{
	private static final long serialVersionUID = 5189531280789195707L;

	@Id
	@Column(name="BAT_ID_PK")
	private Integer batId;

	@Column(name="BAT_CODE")
	private String batCode;
	
	@Column(name="BAT_NAME")
	private String batName;

}
