package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.types.LocationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="tm_gamelog")
public class EntityTeamGamelog implements IEntity, Serializable{
	private static final long serialVersionUID = -1989739695241083688L;

	@Id
    @Column(name="TM_GAMELOG_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tmGamelogId;

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="GAMELOG_ID_FK")
	private EntityGamelog gameLog;
    
    @Column(name="GAME_CODE")
    private String gameCode;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TEAM_ID_FK")
    private EntityTeam team;

    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TM_GAMELINE_PITCH_ID_FK")
    private EntityGameLinePitch gameLinePitch;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TM_GAMELINE_BAT_ID_FK")
    private EntityGameLineBat gameLineBat;

    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TM_GAMELINE_DEFENSE_ID_FK")
    private EntityGameLineDefense gameLineDefense;
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TM_LINE_ITEM_POS_ID_FK")
    private EntityLineItemPos lineItemPos; 
    
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="TM_LINE_ITEM_ORDER_ID_FK")
    private EntityLineItemOrder lineItemOrder;
    
    @Enumerated(EnumType.STRING)
	@Column(name="HOME_AWAY_CODE")
    private LocationType locationType;
	
}
