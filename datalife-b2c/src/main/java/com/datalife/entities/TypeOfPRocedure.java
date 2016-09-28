package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by barath on 1/29/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "typeOfPRocedure", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class TypeOfPRocedure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "procedureName")
    private String procedureName;

    @ManyToOne
    @JoinColumn(name = "specialityId")
    private RecordSpeciality recordSpeciality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public RecordSpeciality getRecordSpeciality() {
        return recordSpeciality;
    }

    public void setRecordSpeciality(RecordSpeciality recordSpeciality) {
        this.recordSpeciality = recordSpeciality;
    }
}
