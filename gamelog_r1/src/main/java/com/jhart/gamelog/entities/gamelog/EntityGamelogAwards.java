package com.jhart.gamelog.entities.gamelog;

import java.io.Serializable;

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
@Table(name="gamelog_awards")
public class EntityGamelogAwards implements IEntity, Serializable{
    private static final long serialVersionUID = 2687591097159071508L;
    
    @Id
    @Column(name="GAMELOG_AWARDS_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gamelogAwardsId;

    @OneToOne
    @JoinColumn(name="GAMELOG_ID_FK")
    private EntityGamelog gamelog;
    
    @Column(name="GAMELOG_CODE")
    private String gameCode;
    
    @ManyToOne
    @JoinColumn(name="WP_PERSON_ID_FK")
    private EntityPerson winningPitcher;
    
    @ManyToOne
    @JoinColumn(name="LP_PERSON_ID_FK")
    private EntityPerson losingPitcher;
    
    @ManyToOne
    @JoinColumn(name="SP_PERSON_ID_FK")
    private EntityPerson save;
    
    @ManyToOne
    @JoinColumn(name="GWRBI_PERSON_ID_FK")
    private EntityPerson gwrbi;
    

}
