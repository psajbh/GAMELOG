package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
@Table(name="lineitem_pos")
public class EntityLineItemPos implements IEntity, Serializable{
	private static final long serialVersionUID = 3035644648160422654L;

	@Id
    @Column(name="TM_LINE_ITEM_POS_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineitemPosId;
	
	@OneToOne
    @JoinColumn(name="TM_GAMELOG_ID_FK")
    private EntityTeamGamelog tmGamelog;
    
    @Column(name="GAME_CODE")
    private String gameCode;
    
    @ManyToOne
    @JoinColumn(name="POS_PITCHER_PERSON_ID_FK")
    private EntityPerson pitcher;
    
    @ManyToOne
    @JoinColumn(name="POS_CATCHER_PERSON_ID_FK")
    private EntityPerson catcher;

    @ManyToOne
    @JoinColumn(name="POS_FIRSTBASE_PERSON_ID_FK")
    private EntityPerson firstbase;

    @ManyToOne
    @JoinColumn(name="POS_SECONDBASE_PERSON_ID_FK")
    private EntityPerson secondbase;

    @ManyToOne
    @JoinColumn(name="POS_THIRDBASE_PERSON_ID_FK")
    private EntityPerson thirdbase;

    @ManyToOne
    @JoinColumn(name="POS_SHORTSTOP_PERSON_ID_FK")
    private EntityPerson shortstop;

    @ManyToOne
    @JoinColumn(name="POS_LEFT_FIELD_PERSON_ID_FK")
    private EntityPerson leftfield;

    @ManyToOne
    @JoinColumn(name="POS_CENTER_FIELD_PERSON_ID_FK")
    private EntityPerson centerfield;
	
    @ManyToOne
    @JoinColumn(name="POS_RIGHT_FIELD_PERSON_ID_FK")
    private EntityPerson rightfield;
    
    @ManyToOne
    @JoinColumn(name="POS_MANAGER_PERSON_ID_FK")
    private EntityPerson manager;

    @ManyToOne
    @JoinColumn(name="POS_DESIGNATED_HITTER_PERSON_ID_FK")
    private EntityPerson designatedhitter;
    
    
    
//    private Map<String, EntityPerson> getLineItemMap(){
//        Map<String, EntityPerson> personMap = new HashMap<>();
//        personMap.put(getPitcher().getCode(), getPitcher());
//        personMap.put(getCatcher().getCode(), getCatcher());
//        personMap.put(getDesignatedhitter().getCode(), getDesignatedhitter());
//        personMap.put(getFirstbase().getCode(), getFirstbase());
//        personMap.put(getSecondbase().getCode(), getSecondbase());
//        personMap.put(getThirdbase().getCode(), getThirdbase());
//        personMap.put(getShortstop().getCode(), getShortstop());
//        personMap.put(getLeftfield().getCode(), getLeftfield());
//        personMap.put(getCenterfield().getCode(), getCenterfield());
//        personMap.put(getRightfield().getCode(), getRightfield());
//        return personMap;
//    }
    
//    public EntityPerson getEntityFromCode(String code) {
//        
//        Map<String, EntityPerson> lineItemMap = getLineItemMap();
//        EntityPerson person = lineItemMap.get(code);
//        if (null != person) {
//            return person;
//        }
//        else {
//            return null;
//        }
//    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((catcher == null) ? 0 : catcher.hashCode());
        result = prime * result + ((centerfield == null) ? 0 : centerfield.hashCode());
        result = prime * result + ((designatedhitter == null) ? 0 : designatedhitter.hashCode());
        result = prime * result + ((firstbase == null) ? 0 : firstbase.hashCode());
        result = prime * result + ((gameCode == null) ? 0 : gameCode.hashCode());
        result = prime * result + ((leftfield == null) ? 0 : leftfield.hashCode());
        result = prime * result + ((lineitemPosId == null) ? 0 : lineitemPosId.hashCode());
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
        result = prime * result + ((pitcher == null) ? 0 : pitcher.hashCode());
        result = prime * result + ((rightfield == null) ? 0 : rightfield.hashCode());
        result = prime * result + ((secondbase == null) ? 0 : secondbase.hashCode());
        result = prime * result + ((shortstop == null) ? 0 : shortstop.hashCode());
        result = prime * result + ((thirdbase == null) ? 0 : thirdbase.hashCode());
        result = prime * result + ((tmGamelog == null) ? 0 : tmGamelog.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityLineItemPos other = (EntityLineItemPos) obj;
        if (catcher == null) {
            if (other.catcher != null)
                return false;
        } else if (!catcher.equals(other.catcher))
            return false;
        if (centerfield == null) {
            if (other.centerfield != null)
                return false;
        } else if (!centerfield.equals(other.centerfield))
            return false;
        if (designatedhitter == null) {
            if (other.designatedhitter != null)
                return false;
        } else if (!designatedhitter.equals(other.designatedhitter))
            return false;
        if (firstbase == null) {
            if (other.firstbase != null)
                return false;
        } else if (!firstbase.equals(other.firstbase))
            return false;
        if (gameCode == null) {
            if (other.gameCode != null)
                return false;
        } else if (!gameCode.equals(other.gameCode))
            return false;
        if (leftfield == null) {
            if (other.leftfield != null)
                return false;
        } else if (!leftfield.equals(other.leftfield))
            return false;
        if (lineitemPosId == null) {
            if (other.lineitemPosId != null)
                return false;
        } else if (!lineitemPosId.equals(other.lineitemPosId))
            return false;
        if (manager == null) {
            if (other.manager != null)
                return false;
        } else if (!manager.equals(other.manager))
            return false;
        if (pitcher == null) {
            if (other.pitcher != null)
                return false;
        } else if (!pitcher.equals(other.pitcher))
            return false;
        if (rightfield == null) {
            if (other.rightfield != null)
                return false;
        } else if (!rightfield.equals(other.rightfield))
            return false;
        if (secondbase == null) {
            if (other.secondbase != null)
                return false;
        } else if (!secondbase.equals(other.secondbase))
            return false;
        if (shortstop == null) {
            if (other.shortstop != null)
                return false;
        } else if (!shortstop.equals(other.shortstop))
            return false;
        if (thirdbase == null) {
            if (other.thirdbase != null)
                return false;
        } else if (!thirdbase.equals(other.thirdbase))
            return false;
        if (tmGamelog == null) {
            if (other.tmGamelog != null)
                return false;
        } else if (!tmGamelog.equals(other.tmGamelog))
            return false;
        return true;
    }



    @Override
    public String toString() {
        return "EntityLineItemPos [lineitemPosId=" + lineitemPosId + ", tmGamelog=" + tmGamelog + ", gameCode="
                + gameCode + ", pitcher=" + pitcher + ", catcher=" + catcher + ", firstbase=" + firstbase
                + ", secondbase=" + secondbase + ", thirdbase=" + thirdbase + ", shortstop=" + shortstop
                + ", leftfield=" + leftfield + ", centerfield=" + centerfield + ", rightfield=" + rightfield
                + ", manager=" + manager + ", designatedhitter=" + designatedhitter + "]";
    }
    
    
	 
}
