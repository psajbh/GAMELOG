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
import com.jhart.gamelog.entities.team.EntityTeam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="tm_gameline_pitch")
public class EntityGameLinePitch implements IEntity, Serializable{
	private static final long serialVersionUID = -6129563148738916218L;

	@Id
    @Column(name="TM_GAMELINE_PITCH_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tmGamelinePitchId;
    
	//@OneToOne(mappedBy = "gameLinePitch")
	@OneToOne
    @JoinColumn(name="TM_GAMELOG_ID_FK")
    private EntityTeamGamelog tmGamelog;
    
    @Column(name="GAME_CODE")
    private String gameCode;
    
    @Column(name="PITCHERS_USED")
    private int pitchersUsed;
    
    @Column(name="INDIVIDUAL_ER")
    private int individualEr;
    
    @Column(name="TEAM_ER")
    private int teamEr;
    
    @Column(name="WP")
    private int wp;
    
    @Column(name="BALKS")
    private int balks;
}
