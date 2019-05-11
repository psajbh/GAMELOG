package com.jhart.gamelog.entities.team;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="league")
@NamedQueries({
	@NamedQuery(name="get_league_by_lgcode_divcode", 
		query="from EntityLeague el where el.code=:lgCode and el.division.code=:divCode"),
	@NamedQuery(name="get_league_by_lgcode_null_divcode", 
		query="from EntityLeague el where el.code=:lgCode and el.division is null")
})
public class EntityLeague implements IEntity, Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="LEAGUE_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leagueId;
    
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="DIVISION_ID_FK")
    private EntityDivision division;
    
    @Column(name="LEAGUE_CODE")
    private String code;
    
    @Column(name="NAME")
    private String name;
    
}
