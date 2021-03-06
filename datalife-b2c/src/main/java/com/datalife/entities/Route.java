package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by Dipak on 12/17/2014.
 * <p/>
 * Entity class for ICD-9 Codes
 */
@Entity
@Table(name = "routes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class Route {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

    @Column(name = "name")
    private String name;

    public Route() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
