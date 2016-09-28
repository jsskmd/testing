package com.datalife.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DATASCRIBE on 6/15/2015.
 */
@Entity
@Table(name = "recordSpeciality", uniqueConstraints = {
        @UniqueConstraint(columnNames = "specialityId"),
        @UniqueConstraint(columnNames = "name")})
public class RecordSpeciality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "specialityId", unique = true, nullable = false)
    private Long specialityId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "recordSpeciality", fetch = FetchType.LAZY)
    private List<TypeOfPRocedure> typeOfPRocedureList;


    public RecordSpeciality() {
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeOfPRocedure> getTypeOfPRocedureList() {
        return typeOfPRocedureList;
    }

    public void setTypeOfPRocedureList(List<TypeOfPRocedure> typeOfPRocedureList) {
        this.typeOfPRocedureList = typeOfPRocedureList;
    }
}
