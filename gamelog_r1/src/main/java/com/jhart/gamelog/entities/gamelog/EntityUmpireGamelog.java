package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.person.EntityPerson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="ump_gamelog")
public class EntityUmpireGamelog implements IEntity, Serializable{
	private static final long serialVersionUID = -1883455956809950162L;

	@Id
    @Column(name="UMP_GAMELOG_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer umpireGameLogId;
	
    @Column(name="GAME_CODE")
    private String gameCode;
	
    @OneToOne
    @JoinColumn(name="GAMELOG_ID_FK")
	private EntityGamelog gameLog;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="HP_PERSON_ID_FK ")
    private EntityPerson homePlateUmpire;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="FB_PERSON_ID_FK")
    private EntityPerson firstBaseUmpire;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="SB_PERSON_ID_FK")
    private EntityPerson secondBaseUmpire;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="TB_PERSON_ID_FK")
    private EntityPerson thirdBaseUmpire;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="LF_PERSON_ID_FK")
    private EntityPerson leftFieldUmpire;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="RF_PERSON_ID_FK")
    private EntityPerson rightFieldUmpire;

}
