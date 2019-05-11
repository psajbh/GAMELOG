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
import com.jhart.gamelog.entities.team.EntityTeam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="lineitem_order")
public class EntityLineItemOrder implements IEntity, Serializable{
	private static final long serialVersionUID = -5717184954046705600L;

	@Id
    @Column(name="TM_LINE_ITEM_ORDER_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineitemOrderId;
    
	@OneToOne
    @JoinColumn(name="TM_GAMELOG_ID_FK")
    private EntityTeamGamelog tmGamelog;
    
    @Column(name="GAME_CODE")
    private String gameCode;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_1_PERSON_ID_FK")
    private EntityPerson order1Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_2_PERSON_ID_FK")
    private EntityPerson order2Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_3_PERSON_ID_FK")
    private EntityPerson order3Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_4_PERSON_ID_FK")
    private EntityPerson order4Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_5_PERSON_ID_FK")
    private EntityPerson order5Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_6_PERSON_ID_FK")
    private EntityPerson order6Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_7_PERSON_ID_FK")
    private EntityPerson order7Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_8_PERSON_ID_FK")
    private EntityPerson order8Player;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_9_PERSON_ID_FK")
    private EntityPerson order9Player;
	
}
