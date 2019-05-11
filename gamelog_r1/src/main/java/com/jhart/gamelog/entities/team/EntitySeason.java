package com.jhart.gamelog.entities.team;

import java.io.Serializable;

/*
 * this class will not be bi-directional since it is associated with more than one other object type,
 * being team, gamelog and possibly more.
 * 
 */

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
@Table(name="season")
@NamedQueries({
    @NamedQuery(name="get_season_from_code", 
            query="from EntitySeason s where s.code=:code"),
    @NamedQuery(name="get_season_from_codeValue", 
    		query="from EntitySeason s where s.value=:value")
})
public class EntitySeason implements IEntity, Serializable{

    private static final long serialVersionUID = 3365520696650569445L;

    @Id
    @Column(name="SEASON_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seasonId;

    @Column(name="SEASON_CODE", unique=true, nullable=false)
    private String code;
    
    @Column(name="SEASON_VALUE", unique=true, nullable=false)
    private int value;
    
}

/**
 * create table if not exists season (
    id int not null auto_increment,
    `code` char(4) not null,
    `name` int not null,  -- useful for grouping/filtering ect.
    primary key(`id`),  
    unique(`code`)
)engine=InnoDB;

 */


