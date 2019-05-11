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
//import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.hibernate.FetchMode;
//import org.hibernate.FetchMode;
//import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.entities.location.EntityLocation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team")
@NamedQueries({
        @NamedQuery(name = "get_team_from_code_and_season", query = "from EntityTeam t where t.code=:code and t.season=:season") })
public class EntityTeam implements IEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TEAM_ID_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    // https://stackoverflow.com/questions/2302802/object-references-an-unsaved-transient-instance-save-the-transient-instance-be/10680218
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FRANCHISE_ID_FK")
    private EntityFranchise franchise;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID_FK")
    private EntityLeague league;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEASON_ID_FK")
    private EntitySeason season;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID_FK")
    private EntityLocation location;

    @Column(name = "TEAM_CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ALIAS")
    private String alias;

}
