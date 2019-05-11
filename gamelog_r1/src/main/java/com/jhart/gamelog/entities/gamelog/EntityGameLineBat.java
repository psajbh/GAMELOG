package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name="tm_gameline_bat")
public class EntityGameLineBat implements IEntity, Serializable{
	private static final long serialVersionUID = -2847812663472190473L;

	@Id
    @Column(name="TM_GAMELINE_BAT_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tmGamelineBatId;
    
	//@OneToOne(mappedBy = "gameLineBat")
	@OneToOne
    @JoinColumn(name="TM_GAMELOG_ID_FK")
    private EntityTeamGamelog tmGamelog;
    
    @Column(name="GAME_CODE")
    private String gameCode;

    @Column(name="AB")
    private int ab;
    
    @Column(name="H")
    private int h;
    
    @Column(name="D")
    private int d;
    
    @Column(name="T")
    private int t;
    
    @Column(name="HR")
    private int hr;
    
    @Column(name="RBI")
    private int rbi;
    
    @Column(name="SH")
    private int sh;
    
    @Column(name="SF")
    private int sf;

    @Column(name="HBP")
    private int hbp;
    
    @Column(name="BB")
    private int bb;
    
    @Column(name="IBB")
    private int ibb;

    @Column(name="SO")
    private int so;
    
    @Column(name="SB")
    private int sb;

    @Column(name="CS")
    private int cs;

    @Column(name="GIDP")
    private int gidp;
    
    @Column(name="AWCI")
    private int awci;

    @Column(name="LOB")
    private int lob;

}
