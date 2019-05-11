package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;
import java.time.LocalDate;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.entities.team.EntitySeason;
import com.jhart.gamelog.types.DayOrNight;
import com.jhart.gamelog.types.GameType;
import com.jhart.gamelog.types.WeekDay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="gamelog")
@NamedQueries({
    @NamedQuery(name="get_gamelog_by_gamecode", 
            query="from EntityGamelog egl where egl.gameCode=:gameCode")
})

public class EntityGamelog implements IEntity, Serializable{
    private static final long serialVersionUID = 1576184534472584631L;
    
    @Id
    @Column(name="GAMELOG_ID_PK", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gamelogId;
    
    @Column(name="GAMELOG_CODE")
    private String gameCode;
    
    //many gamelogs can have the same season.
    @ManyToOne(fetch=FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="SEASON_ID_FK")
    private EntitySeason season;
    
    // gamelog can be associated to one teamGameLog.
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="TM_GAMELOG_HOME_FK")
    private EntityTeamGamelog homeTeam;
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="TM_GAMELOG_VISITOR_FK")
    private EntityTeamGamelog visitTeam; 
     
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="UMP_GAMELOG_ID_FK")
    private EntityUmpireGamelog umpires;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="PARK_ID_FK")
    private EntityPark entityPark;
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="GAMELOG_AWARDS_ID_FK")
    private EntityGamelogAwards entityGamelogAwards;
    
//    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//    @JoinColumn(name="GAMELOG_AWARDS_ID_FK")
//    private EntityUmpireGamelog entityUmpireGamelog;
//    
    
//    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="WP_PERSON_ID_FK")
//    private EntityPerson winningPitcher;
//    
//    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="LP_PERSON_ID_FK")
//    private EntityPerson losingPitcher;
//    
//    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="SP_PERSON_ID_FK")
//    private EntityPerson savePitcher;
//
//    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="GWRBI_PERSON_ID_FK")
//    private EntityPerson gwRbiBatter;

    @Enumerated(EnumType.STRING)
    @Column(name="GAMETYPE_ID_FK")
    private GameType gameType;

    @Enumerated(EnumType.STRING)
	@Column(name="DAY_OF_WEEK_FK")
    private WeekDay weekday;

    @Column(name="DATE_OF_GAME")
    private LocalDate dateOfGame;
    
    @Enumerated(EnumType.ORDINAL)
	@Column(name="DAY_GAME")
    private DayOrNight dayOrNight;
    
    @Column(name="ATTENDANCE")
    private int attendance;
    
    @Column(name="TOTAL_OUTS")
    private int totalOuts;

    @Column(name="TIME_OF_GAME")
    private int timeOfGame;
    
    @Column(name="V_TM_GAME_SCORE")
    private String visitorScoreLine;
    
    @Column(name="H_TM_GAME_SCORE")
    private String homeScoreLine;
    
    @Column(name="V_SCORE")
    private int visitorTeamScore;
    
    @Column(name="H_SCORE")
    private int homeTeamScore;
    
}
