package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="tm_gameline_defense")
public class EntityGameLineDefense implements IEntity, Serializable{
	private static final long serialVersionUID = -7361727372856439117L;

	@Id
    @Column(name="TM_GAMELINE_DEFENSE_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tmGamelineDefenseId;
	
	@OneToOne
    @JoinColumn(name="TM_GAMELOG_ID_FK")
    private EntityTeamGamelog tmGamelog;

    @Column(name="GAME_CODE")
    private String gameCode;
    
    @Column(name="PUTOUTS")
    private int putouts;
    
    @Column(name="ASSISTS")
    private int assists;

    @Column(name="ERRORS")
    private int errors;

    @Column(name="PB")
    private int pb;
    
    @Column(name="DP")
    private int dp;

    @Column(name="TP")
    private int tp;

}
