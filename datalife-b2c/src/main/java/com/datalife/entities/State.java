package com.datalife.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by supriya gondi on 10/28/2014.
 *
 * Entity class for State
 * This is for user to select State from Dropdown.
 */
@Entity
@Table(name = "state", uniqueConstraints = {
        @UniqueConstraint(columnNames = "stateId"),
        @UniqueConstraint(columnNames = "stateName")})
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stateId", unique = true, nullable = false)
    private Long stateId;

    @Column(name = "stateName")
    private String stateName;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    public State() {
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
