package com.jhart.gamelog.entities.team;

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

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="franchise")
@NamedQueries({
	@NamedQuery(name="get_franchise_by_code", 
			query="from EntityFranchise franchise where franchise.code=:code ")
})
public class EntityFranchise implements IEntity, Serializable{
	
    private static final long serialVersionUID = -8965855539786683429L;

    @Id
	@Column(name="FRANCHISE_ID_PK")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int franchiseId;
	
	@Column(name="FRANCHISE_CODE")
	private String code;
	

}
