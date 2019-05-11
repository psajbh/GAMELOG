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
@Table(name="division")
@NamedQueries({
    @NamedQuery(name="get_division", 
            query="from EntityDivision div where div.code=:divcode")
})
public class EntityDivision implements IEntity, Serializable{

    private static final long serialVersionUID = 616646775380829920L;
    
    @Id
    @Column(name="DIVISION_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int divisionId;

    @Column(name="DIVISION_CODE", unique=true, nullable=false)
    private String code;
    
    @Column(name="NAME", unique=true, nullable=false)
    private String name;

}
